package edu.upc.etsetb.arqsoft.controller;
import java.util.List;
import java.util.Set;

import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.formula.Formula;
import edu.upc.etsetb.arqsoft.domain.formula.FormulaContent;
import edu.upc.etsetb.arqsoft.domain.function.Argument;
import edu.upc.etsetb.arqsoft.domain.function.Function;
import edu.upc.etsetb.arqsoft.domain.function.RangeOfCells;
import edu.upc.etsetb.arqsoft.exceptions.BadPostfixerImplementationException;
import edu.upc.etsetb.arqsoft.exceptions.CircularDependenciesException;
import edu.upc.etsetb.arqsoft.exceptions.ZeroDivisionException;
import java.util.ArrayList;
import java.util.HashSet;

public class FormulaCalculator {

    public void recalculateCells (Cell Currentcell) throws CircularDependenciesException, ZeroDivisionException, BadPostfixerImplementationException {

        FormulaCalculator formulaCalculator = new FormulaCalculator();
        List<Cell> Cells = Currentcell.getAssociatedCells();

        for(Cell cell : Cells) {
            //System.out.println("Recalculating cell: " + cell.getKey());
            Formula formula = (Formula)cell.getContent();

            formulaCalculator.checkCircularDependencies(formula);       
            formula.setLastValue(formulaCalculator.evaluatePostfix(formula));
            formulaCalculator.recalculateCells(cell);

        }
    
    }
    
    public Number evaluatePostfix (Formula formula) throws ZeroDivisionException, BadPostfixerImplementationException{
        PostfixEvaluator postfixEvaluator = new PostfixEvaluator();
        for (FormulaContent cnt: formula.getFormulaContents()) {
            cnt.acceptVisitor(postfixEvaluator);
            //System.out.println("Visiting: " + cnt);
        }

        return postfixEvaluator.returnValue();
    }
    
    private List<Cell> processCellsOfFunction (Function fun){
        List<Cell> cells = new ArrayList<>();
        for (Argument arg: fun.getArguments())
            if (arg.getClass() == Cell.class) {
                cells.add((Cell)arg);
            }
            else if (arg.getClass() == RangeOfCells.class) {
                cells.addAll(((RangeOfCells)arg).getCells());
            }
            else if (arg.getClass() == Function.class) {
                cells.addAll(processCellsOfFunction((Function)arg));
            }
        
        return cells;
    }
    
    public void checkCircularDependencies(Formula formula) throws CircularDependenciesException{
        List<Cell> cells = new ArrayList<>();
        
        for (FormulaContent fc: formula.getFormulaContents()) {
            if (fc.getClass() == Cell.class){
                cells.add((Cell)fc);
            }
            else if (fc.getClass() == Function.class) {
                cells.addAll(processCellsOfFunction((Function) fc));
            }
        }
    
        //System.out.println("Complete list of cells involved in the formula: " + cells);
    
        Set<Cell> visited = new HashSet<>();
        Set<Cell> recursionStack = new HashSet<>();
    
        for (Cell cell : cells) {
            //System.out.println("Checking this cell: " + cell);
            if (dfs(cell, visited, recursionStack)) {
                throw new CircularDependenciesException();
            }
        }
    }
    
    private boolean dfs(Cell cell, Set<Cell> visited, Set<Cell> recursionStack) {

        if (recursionStack.contains(cell)) {
            return true;
        }
        if (visited.contains(cell)) {
            return false;
        }
    
        visited.add(cell);
        recursionStack.add(cell);
    
        for (Cell dependentCell : cell.getAssociatedCells()) {
            if (dfs(dependentCell, visited, recursionStack)) {
                return true;
            }
        }
    
        recursionStack.remove(cell);
        return false;
    }
}