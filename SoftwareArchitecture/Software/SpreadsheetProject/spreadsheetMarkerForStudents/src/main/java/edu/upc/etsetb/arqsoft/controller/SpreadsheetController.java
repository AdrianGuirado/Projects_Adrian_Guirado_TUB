package edu.upc.etsetb.arqsoft.controller;

import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ISpreadsheetControllerForChecker;

import java.io.IOException;

import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.ContentNumber;
import edu.upc.etsetb.arqsoft.domain.ContentString;
import edu.upc.etsetb.arqsoft.domain.Spreadsheet;
import edu.upc.etsetb.arqsoft.domain.TupleKey;
import edu.upc.etsetb.arqsoft.domain.formula.Formula;
import edu.upc.etsetb.arqsoft.domain.formula.Token;
import edu.upc.etsetb.arqsoft.exceptions.BadFormulaException;
import edu.upc.etsetb.arqsoft.exceptions.BadPostfixerImplementationException;
import edu.upc.etsetb.arqsoft.exceptions.CircularDependenciesException;
import edu.upc.etsetb.arqsoft.exceptions.IncorrectFormatException;
import edu.upc.etsetb.arqsoft.exceptions.IncorrectPathException;
import edu.upc.etsetb.arqsoft.exceptions.IsNumberException;
import edu.upc.etsetb.arqsoft.exceptions.IsStringException;
import edu.upc.etsetb.arqsoft.exceptions.SyntaxErrorException;
import edu.upc.etsetb.arqsoft.exceptions.TokenNotMatchedException;
import edu.upc.etsetb.arqsoft.exceptions.ZeroDivisionException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.BadCoordinateException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.CircularDependencyException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.ContentException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.NoNumberException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ReadingSpreadSheetException;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.SavingSpreadSheetException;
import edu.upc.etsetb.arqsoft.view.SpreadsheetPrinter;
import java.util.List;

public class SpreadsheetController implements ISpreadsheetControllerForChecker{
    private Spreadsheet spreadsheet;
    private SpreadsheetPrinter printer;
    private SpreadsheetLoader loader;
    private SpreadsheetSaver saver;

    public SpreadsheetLoader getLoader() {
        return this.loader;
    }

    public SpreadsheetSaver getSaver() {
        return this.saver;
    }

    public SpreadsheetController(SpreadsheetPrinter printer, SpreadsheetLoader loader, SpreadsheetSaver saver) {
        this.spreadsheet = new Spreadsheet();
        this.printer = printer;
        this.saver = saver;
        this.loader = loader;
    }

    public Spreadsheet getSpreadsheet() {
        return this.spreadsheet;
    }

    public void setSpreadsheet(Spreadsheet spreadsheet) {
        this.spreadsheet = spreadsheet;
    }

    public SpreadsheetPrinter getPrinter() {
        return this.printer;
    }

    public void setPrinter(SpreadsheetPrinter printer) {
        this.printer = printer;
    }

    public Spreadsheet createSpreadsheet() {
        spreadsheet = new Spreadsheet();
        return spreadsheet;
    }

    public void editCell(String key, String content) throws BadFormulaException, TokenNotMatchedException, CircularDependenciesException, ZeroDivisionException, BadPostfixerImplementationException {
        //1. Obtain the value in TupleKey
        TupleKey tupleKey = spreadsheet.getKey(key);
        //System.out.println("Editing cell: " + tupleKey);
        
        Cell currentCell = spreadsheet.getCell(tupleKey);
        if (currentCell==null) {
            currentCell = spreadsheet.getEmptyCell(tupleKey);
            //System.out.println(currentCell);
            if (currentCell==null) {
                currentCell = new Cell();
                currentCell.setKey(tupleKey);
                //System.out.println("Aquí se crea una nueva celda:" + currentCell);
            }    
            spreadsheet.setCell(currentCell);
        }

        //Create all the objects for the formula process
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser();
        Postfixer postfixer = new Postfixer();
        FormulaGenerator formulaGenerator = new FormulaGenerator();
        FormulaCalculator formulaCalculator = new FormulaCalculator();

        //2. Check if the content correspond to a Formula
        try {
            Formula.isFormula(content);

            Formula formula;
            List<Token> tokens = tokenizer.tokenizeFormula(content);
            //System.out.println("Tokens after tokenize: " + tokens);
            tokens = parser.parseFormula(tokens);
            //System.out.println("Tokens after parse: " + tokens);
            tokens = postfixer.generatePostfix(tokens);
            //System.out.println("Tokens after postfix: " + tokens);
            
            formula = formulaGenerator.generateFormula(tokens, spreadsheet, content, currentCell);
                    
            formulaCalculator.checkCircularDependencies(formula);

            formula.setLastValue(formulaCalculator.evaluatePostfix(formula));

            formulaCalculator.recalculateCells(currentCell);

            //The last step to avoid changing to a formula with errors
            currentCell.setContent(formula);

        } catch (IsNumberException e) {
            Number number;
            if (content.contains(".")) {
                //It's a double
                number = Double.valueOf(content);
            }
            else {
                //It's an integer
                number = Integer.valueOf(content);
            }
            currentCell.setContent(new ContentNumber(number));
            formulaCalculator.recalculateCells(currentCell);

        } catch (IsStringException e) {
            if (!currentCell.getAssociatedCells().isEmpty()) {
                //This cell value is used by other cells but now will be an string!!!
                //TODO
                System.out.println("This cell has associated cells and you are trying to change it to an String.");
            }
            else {
                currentCell.setContent(new ContentString(content));
            }
        }

            //After all print the result
            //TODO: change
            printer.displaySpreadsheet(spreadsheet);
        }

    @Override
    public void setCellContent(String cellCoord, String strContent) throws ContentException, BadCoordinateException, CircularDependencyException {
        try {    
            this.editCell(cellCoord, strContent);
        } catch (TokenNotMatchedException e) {
            throw new ContentException(e.toString() + ": error in the matching of tokens. Cause: " + e.getInfo());
        } catch (BadFormulaException e) {
            throw new ContentException(e.toString() + ": " + e.getStatement());
        } catch (ZeroDivisionException e) {
            throw new ContentException(e.toString() + ": there is a zero division in that formula expression.");
        } catch (BadPostfixerImplementationException e) {
            throw new ContentException(e.toString() + ": error in the postfixer implementation.");
        } catch (CircularDependenciesException e) {
            throw new CircularDependencyException();
        }
    }

    @Override
    public double getCellContentAsDouble(String coord) throws BadCoordinateException, NoNumberException {
        TupleKey tupleKey = spreadsheet.getKey(coord);
        Cell currentCell;
        double value;
        String content;
                
        currentCell = spreadsheet.getCell(tupleKey);
        if (currentCell==null) {
            value = 0.0;
        }
        else {
            content = currentCell.getContent().getContentValue();
            if (content.replaceAll("[a-zA-Z]", "").equals(content)) {
                value = Double.parseDouble(content);
            }
            else {
                throw new NoNumberException();
            }
        }
        
        return value;
    }

    @Override
    public String getCellContentAsString(String cooord) throws BadCoordinateException {
        TupleKey tupleKey = spreadsheet.getKey(cooord);
        Cell currentCell;
        String content; 

        currentCell = spreadsheet.getCell(tupleKey);
        if (currentCell==null) {
            content = "";
        }
        else {
            content = currentCell.getContent().getContentValue();
        }
        
        return content;
    }

    @Override
    public String getCellFormulaExpression(String coord) throws BadCoordinateException {
        TupleKey tupleKey = spreadsheet.getKey(coord);
        Cell currentCell;
        Formula formula;
        String expression;

        currentCell = spreadsheet.getCell(tupleKey);
        if (currentCell==null) {
            expression = "";
        }
        else {
            if (currentCell.getContent().getClass() == Formula.class) {
                formula = (Formula) currentCell.getContent();
                expression = formula.getExpressionWithoutEqual();
            }
            else {
                throw new BadCoordinateException("No formula in that cell");
            }
        }
        
        return expression;
    }

    @Override
    public void saveSpreadSheetToFile(String nameInUserDir) throws SavingSpreadSheetException {
        try {
            saver.saveSpreadSheet(spreadsheet, nameInUserDir);
        } catch (IncorrectPathException | IncorrectFormatException e) {
            throw new SavingSpreadSheetException();
        }
    }

    @Override
    public void readSpreadSheetFromFile(String nameInUserDir) throws ReadingSpreadSheetException {
        try {
            this.setSpreadsheet(loader.computeFile(nameInUserDir));
        } catch (IncorrectPathException e) {
            throw new ReadingSpreadSheetException("IncorrectPathException");
        } catch (IncorrectFormatException e) {
            throw new ReadingSpreadSheetException("IncorrectFormatException");
        } catch (IOException e) {
            throw new ReadingSpreadSheetException("IOException");
        } catch (SyntaxErrorException e) {
            throw new ReadingSpreadSheetException("SyntaxErrorException");
        }
    }
    
}