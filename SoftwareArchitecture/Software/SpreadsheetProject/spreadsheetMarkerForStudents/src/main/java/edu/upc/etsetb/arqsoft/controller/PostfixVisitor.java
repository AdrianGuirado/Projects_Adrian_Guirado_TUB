package edu.upc.etsetb.arqsoft.controller;

import edu.upc.etsetb.arqsoft.exceptions.ZeroDivisionException;
import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.formula.OperandNumber;
import edu.upc.etsetb.arqsoft.domain.formula.Operator;
import edu.upc.etsetb.arqsoft.domain.function.Function;

/**
 *
 * @author Juan Parada
 */
public interface PostfixVisitor {
    public void visitOperator (Operator op) throws ZeroDivisionException;
    public void visitOperandNumber(OperandNumber oNumb);
    public void visitCell(Cell cell);
    public void visitFunction (Function fun);
}
