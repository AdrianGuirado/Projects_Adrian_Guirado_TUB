package edu.upc.etsetb.arqsoft.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.ContentNumber;
import edu.upc.etsetb.arqsoft.domain.ContentString;
import edu.upc.etsetb.arqsoft.domain.Spreadsheet;
import edu.upc.etsetb.arqsoft.domain.TupleKey;
import edu.upc.etsetb.arqsoft.domain.formula.Formula;
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
import java.util.ArrayList;
import java.util.List;

public class SpreadsheetLoader {

    public SpreadsheetLoader() {

    };

    private Spreadsheet convertStringsToRealContent(Spreadsheet spreadsheet) throws BadFormulaException, TokenNotMatchedException, CircularDependenciesException, ZeroDivisionException, BadPostfixerImplementationException {
        HashMap <TupleKey, Cell> cells = spreadsheet.getCells();

        //Create one instance of all the objects for the formula process
        Tokenizer tokenizer = new Tokenizer();
        Parser parser = new Parser();
        Postfixer postfixer = new Postfixer();
        FormulaGenerator formulaGenerator = new FormulaGenerator();
        FormulaCalculator formulaCalculator = new FormulaCalculator();
        List <Cell> toDelete = new ArrayList<>();

        for (TupleKey key: cells.keySet()) {
            Cell currentCell = cells.get(key);
            String content = currentCell.getContent().getContentValue();
            if (!content.isEmpty()) {
                try {
                    Formula.isFormula(content);
                    content = content.replaceAll(",", ";"); //change from file to memory

                    Formula formula = formulaGenerator.generateFormula(postfixer.generatePostfix(parser.parseFormula(tokenizer.tokenizeFormula(content))), spreadsheet, content, currentCell);
                    formulaCalculator.checkCircularDependencies(formula);

                    formula.setLastValue(formulaCalculator.evaluatePostfix(formula));

                    //The last step to avoid changing to a formula with errors
                    currentCell.setContent(formula);
                    //System.out.println("Added cell: " + currentCell);

                }
                catch (IsNumberException e) {
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
                    //System.out.println("Added cell: " + currentCell);
                }
                catch (IsStringException e) {
                    //Action is not needed
                    //System.out.println("Added cell: " + currentCell);
                }
            }
            else { //No content, delete cell
                toDelete.add(currentCell);
            }
        }
        for (Cell c: toDelete) {
            spreadsheet.deleteCell(c);
        }
        return spreadsheet;
    }

    private String nextColumn(String column) {
        String nextColumn;

        if (column.charAt(column.length() - 1) == 'Z') { //Checks what happens with the last char
            nextColumn = column + "A";
        }
        else {
            StringBuilder replacement = new StringBuilder(column);
            char newChar = column.charAt(column.length()-1);
            newChar++;
            replacement.setCharAt(column.length()-1, newChar);
            nextColumn = replacement.toString();
        }

        return nextColumn;
    }

    public Spreadsheet computeFile(String path) throws IncorrectPathException, IncorrectFormatException, IOException, SyntaxErrorException{
        Spreadsheet spreadsheet = new Spreadsheet();
        //1. check the file format
            if (path.contains(".s2v")){
                //1. check if file exists
                File file = new File(path);
                if (file.exists()) {
                    try {
                        Scanner fileScanner = new Scanner(file);
                        int row = 0;
                        while (fileScanner.hasNextLine()) {
                            String line = fileScanner.nextLine();
                            String[] eachCell = line.split(";");
                            String column = "A";
                            for (String cellContent: eachCell) {
                                if (!cellContent.equals("")) {
                                    //2. separate it with each content cell
                                    TupleKey key = new TupleKey(row+1, column);
                                    //Create the spreadshett with all String content
                                    Cell cell = new Cell(new ContentString(cellContent),key);
                                    spreadsheet.setCell(cell);
                                }
                                else {
                                    //Empty cell
                                    
                                }
                                    
                                column = nextColumn(column);
                            }
                            row ++;
                        }
                        fileScanner.close();
                    }
                    catch (FileNotFoundException e) {
                        // In theory, impossible
                        throw new IncorrectPathException();
                    }

                    //3. convert the content to each spreadsheet object and calculate the value of the formula
                    try {
                        spreadsheet = convertStringsToRealContent(spreadsheet);
                    } catch (TokenNotMatchedException e) {
                        System.out.println(e.toString() + ": error in the matching of tokens.");
                    } catch (BadFormulaException e) {
                        System.out.println(e.toString() + ": " + e.getStatement());
                    } catch (ZeroDivisionException e) {
                        System.out.println(e.toString() + ": there is a zero division in that formula expression.");
                    } catch (BadPostfixerImplementationException e) {
                        System.out.println(e.toString() + ": error in the postfixer implementation.");
                    } catch (CircularDependenciesException e) {
                        System.out.println(e.toString() + ": there are circular dependencies in that formula expression.");
                    }
                }
                else {
                    throw new IncorrectPathException();
                }
            }
            else {
                throw new IncorrectFormatException();
            }
        return spreadsheet;
    }

}
