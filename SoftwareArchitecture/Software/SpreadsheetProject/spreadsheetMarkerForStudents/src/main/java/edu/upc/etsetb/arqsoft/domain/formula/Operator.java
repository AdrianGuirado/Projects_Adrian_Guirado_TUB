package edu.upc.etsetb.arqsoft.domain.formula;

import edu.upc.etsetb.arqsoft.controller.PostfixVisitor;
import edu.upc.etsetb.arqsoft.exceptions.ZeroDivisionException;

public class Operator implements FormulaContent{
    final char operator;

    public Operator(char operator) {
        this.operator = operator;
    }

    public char getOperator() {
        return this.operator;
    }
    
    @Override
    public void acceptVisitor (PostfixVisitor v) throws ZeroDivisionException {
        v.visitOperator(this);
    }
    
    @Override
    public String toString() {
        return String.valueOf(operator);
    }
}
