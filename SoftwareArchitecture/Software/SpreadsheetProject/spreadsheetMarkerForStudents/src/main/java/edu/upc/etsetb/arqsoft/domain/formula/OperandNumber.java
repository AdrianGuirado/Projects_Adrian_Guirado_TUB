package edu.upc.etsetb.arqsoft.domain.formula;

import java.util.ArrayList;
import java.util.List;

import edu.upc.etsetb.arqsoft.controller.PostfixVisitor;

public class OperandNumber extends Operand{
    private Number number;

    public OperandNumber(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return this.number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    public List<Number> getValue() {
        List <Number> list = new ArrayList<>();
        list.add(number);
        return list;
    }
    
    @Override
    public void acceptVisitor (PostfixVisitor v) {
        v.visitOperandNumber(this);
    }
    
    public String toString() {
        return String.valueOf(number);
    }
}
