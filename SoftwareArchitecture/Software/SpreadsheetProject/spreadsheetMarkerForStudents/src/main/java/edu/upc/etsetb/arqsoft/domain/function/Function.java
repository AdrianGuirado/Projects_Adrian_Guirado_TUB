package edu.upc.etsetb.arqsoft.domain.function;

import java.util.ArrayList;
import java.util.List;

import edu.upc.etsetb.arqsoft.domain.formula.Operand;
import edu.upc.etsetb.arqsoft.controller.PostfixVisitor;

public abstract class Function extends Operand implements Argument{
    List<Argument> arguments;

    public Function() {
        arguments = new ArrayList<Argument>();
    }

    public Function(List<Argument> arguments) {
        this.arguments = arguments;
    }

    public List<Argument> getArguments() {
        return this.arguments;
    }

    public void addArgument(Argument argument) {
        arguments.add(argument);
    }

    public void setArguments(List<Argument> arguments) {
        this.arguments = arguments;
    }

    @Override
    public abstract List<Number> getValue();
    
    @Override
    public void acceptVisitor (PostfixVisitor v) {
        v.visitFunction(this);
    }
}
