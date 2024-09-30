package edu.upc.etsetb.arqsoft.controller;

import edu.upc.etsetb.arqsoft.domain.formula.Token;
import edu.upc.etsetb.arqsoft.domain.function.ArgumentNumber;
import edu.upc.etsetb.arqsoft.domain.function.Function;
import edu.upc.etsetb.arqsoft.domain.function.Max;
import edu.upc.etsetb.arqsoft.domain.function.Min;
import edu.upc.etsetb.arqsoft.domain.function.Promedio;
import edu.upc.etsetb.arqsoft.domain.function.RangeOfCells;
import edu.upc.etsetb.arqsoft.domain.function.Suma;
import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.ContentNumber;
import edu.upc.etsetb.arqsoft.domain.Spreadsheet;
import edu.upc.etsetb.arqsoft.domain.TupleKey;
import edu.upc.etsetb.arqsoft.domain.formula.Formula;
import edu.upc.etsetb.arqsoft.domain.formula.OperandNumber;
import edu.upc.etsetb.arqsoft.domain.formula.Operator;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class FormulaGenerator {
    
    public FormulaGenerator() {

    }

    public String nextColumn(String column) { // Hacer nueva clase para este metodo? Ya que se usa en spreadsheetSaver
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

    private List<TupleKey> generateListOfKeys (TupleKey initialKey, TupleKey finalKey, Spreadsheet spreadsheet) {
        List<TupleKey> keys = new ArrayList<>();
        
        int firstRow = initialKey.getRow();
        int lastRow = finalKey.getRow();
        
        String firstColumn = initialKey.getColumn();
        String lastColumn = finalKey.getColumn();

        for (int i = firstRow; i < lastRow; i++) {
            String currentColumn = firstColumn;
            if (!firstColumn.equals(lastColumn)) {
                while (!nextColumn(currentColumn).equals(lastColumn)) {
                    TupleKey currentTupleKey = spreadsheet.getKey(currentColumn+i);
                    keys.add(currentTupleKey);
                    currentColumn = nextColumn(currentColumn);
                }
            }
            else {
                TupleKey currentTupleKey = spreadsheet.getKey(currentColumn+i);
                keys.add(currentTupleKey);
            }
        }
        keys.add(finalKey);
        //System.out.println("Range of cells: "+cells);
        return keys;
    }

    public Formula generateFormula(List<Token> formula, Spreadsheet spreadsheet, String expresion, Cell currentCell) {
        Stack<Function> functionStack = new Stack<Function>();
        Formula result = new Formula(expresion);
        for (int i=0; i<formula.size(); i++) {
            //System.out.println("Iteracion");
            Token token_a = formula.get(i);

            if (!functionStack.isEmpty()) { //Diff analysis for functions
                switch (token_a.getType()) {
                    case CELL:
                        TupleKey dirTupleKey = spreadsheet.getKey(token_a.getToken());
                        Cell cell = spreadsheet.getCells().get(dirTupleKey);
                        if (cell==null) {
                            cell = spreadsheet.getEmptyCell(dirTupleKey);
                            if (cell==null) {
                                cell = new Cell();
                                cell.setKey(dirTupleKey);
                                cell.setContent(new ContentNumber(0));
                                spreadsheet.setEmptyCell(cell);
                            }
                        }
                        if (!cell.getAssociatedCells().contains(currentCell)) {
                            //System.out.println("le meto a " + cell + "esta celda" + currentCell);
                            cell.setAssociatedCell(currentCell);
                        }
                        functionStack.peek().addArgument(cell);
                        break;

                    case L_PARENTHESIS:
                        //It will appear but no action is needed, cause parser has perfomed
                        break;

                    case MAX:
                        Function max = new Max();
                        functionStack.peek().addArgument(max);
                        functionStack.push(max);
                        break;

                    case MIN:
                        Function min = new Min();
                        functionStack.peek().addArgument(min);
                        functionStack.push(min);
                        break;

                    case NUMBER:
                        String numberString = token_a.getToken();
                        Number number;
                        ArgumentNumber num;


                        if (numberString.contains(".")) { //it's double
                            number = Double.valueOf(numberString);
                        }
                        else { //it's integer
                            number = Integer.valueOf(numberString);
                        }
                        num = new ArgumentNumber(number);
                        functionStack.peek().addArgument(num);
                        break;

                    case PROMEDIO:
                        Function promedio = new Promedio();
                        functionStack.peek().addArgument(promedio);
                        functionStack.push(promedio);
                        break;

                    case RNG_CELL:
                        String firstCell = token_a.getToken().split(":")[0];
                        String secondCell = token_a.getToken().split(":")[1];
                        
                        TupleKey d1TupleKey = spreadsheet.getKey(firstCell);

                        TupleKey d2TupleKey = spreadsheet.getKey(secondCell);

                        List<TupleKey> keys = generateListOfKeys(d1TupleKey, d2TupleKey, spreadsheet);
                        List<Cell> cells = new ArrayList<>();
                        for (TupleKey k: keys) {
                            Cell c = spreadsheet.getCell(k);
                            if (c==null) {
                                c = spreadsheet.getEmptyCell(k);
                                if (c==null) {
                                    c = new Cell();
                                    c.setKey(k);
                                    c.setContent(new ContentNumber(0));
                                    spreadsheet.setEmptyCell(c);
                                }
                            }
                            if (!c.getAssociatedCells().contains(currentCell)) {
                                //System.out.println("le meto a " + c + "esta celda" + currentCell);
                                c.setAssociatedCell(currentCell);
                            }
                            cells.add(c);
                        }
                        RangeOfCells rangeOfCells = new RangeOfCells(spreadsheet.getCell(d1TupleKey), spreadsheet.getCell(d2TupleKey), cells);

                        functionStack.peek().addArgument(rangeOfCells);
                        break;

                    case R_PARENTHESIS:
                        //Simply pop a function from the stack

                        functionStack.pop();
                        break;

                    case SEMICOLON:
                        //It will appear but no action is needed, cause parser has perfomed
                        break;

                    case SUMA:

                        Function suma = new Suma();
                        functionStack.peek().addArgument(suma);
                        functionStack.push(suma);
                        break;

                    default:
                        break;
                    
                }
            }
            else {
                switch (token_a.getType()) {
                    case CELL:
                        //System.out.println("Aqui detecta una celda");

                        TupleKey dirTupleKey = spreadsheet.getKey(token_a.getToken());
                        Cell cell = spreadsheet.getCells().get(dirTupleKey);

                        if (cell==null) {
                            cell = spreadsheet.getEmptyCell(dirTupleKey);
                            if (cell==null) {
                                cell = new Cell();
                                cell.setKey(dirTupleKey);
                                cell.setContent(new ContentNumber(0));
                                spreadsheet.setEmptyCell(cell);
                            }
                        }
                        if (!cell.getAssociatedCells().contains(currentCell)) {
                            //System.out.println("le meto a " + cell + "esta celda" + currentCell);
                            cell.setAssociatedCell(currentCell);
                        }
                        result.addFormulaContent(cell);
                        break;

                    case L_PARENTHESIS:
                        //Impossible after postfixer, functions parenthesis will be treated up
                        break;

                    case MAX:
                        Function max = new Max();
                        functionStack.push(max);
                        result.addFormulaContent(max);
                        break;

                    case MIN:
                        Function min = new Min();
                        functionStack.push(min);
                        result.addFormulaContent(min);
                        break;

                    case NUMBER:
                        String numberString = token_a.getToken();
                        Number number;
                        OperandNumber num;

                        if (numberString.contains(".")) { //it's double
                            number = Double.valueOf(numberString);
                        }
                        else { //it's integer
                            number = Integer.valueOf(numberString);
                        }

                        num = new OperandNumber(number);
                        result.addFormulaContent(num);
                        break;

                    case OPERATOR:
                        String operator = token_a.getToken();
                        Operator op = new Operator(operator.charAt(0));
                        result.addFormulaContent(op);
                        break;

                    case PROMEDIO:
                        Function promedio = new Promedio();
                        functionStack.push(promedio);
                        result.addFormulaContent(promedio);
                        break;

                    case RNG_CELL:
                        //Impossible with the stack
                        break;

                    case R_PARENTHESIS:
                        //Impossible after postfixer, functions parenthesis will be treated up
                        break;

                    case SEMICOLON:
                        //Impossible with the stack                        
                        break;

                    case SUMA:
                        Function suma = new Suma();
                        functionStack.push(suma);
                        result.addFormulaContent(suma);
                        break;

                    default:
                        break;
                }                
            }
        }

        return result;
    }
}