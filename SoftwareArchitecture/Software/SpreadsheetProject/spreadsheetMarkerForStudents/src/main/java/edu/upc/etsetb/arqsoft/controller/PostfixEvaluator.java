package edu.upc.etsetb.arqsoft.controller;

import edu.upc.etsetb.arqsoft.exceptions.BadPostfixerImplementationException;
import edu.upc.etsetb.arqsoft.exceptions.ZeroDivisionException;
import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.formula.OperandNumber;
import edu.upc.etsetb.arqsoft.domain.formula.Operator;
import edu.upc.etsetb.arqsoft.domain.function.Function;
import java.util.Stack;

/**
 *
 * @author Juan Parada
 */
public class PostfixEvaluator implements PostfixVisitor{
    
    private Stack<Number> stack;
    
    public PostfixEvaluator() {
        stack = new Stack<>();
    }
    
    @Override
    public void visitOperator(Operator op) throws ZeroDivisionException {
        Number number1 = stack.pop();
        Number number2 = stack.pop();
        Number result;
        switch (op.getOperator()) {
            case '+':
                if (number1.getClass()==Double.class || number2.getClass()==Double.class)
                    result = number2.doubleValue() + number1.doubleValue();
                else
                    result = number2.intValue() + number1.intValue();    
                break;

            case '-':
                if (number1.getClass()==Double.class || number2.getClass()==Double.class)
                    result = number2.doubleValue() - number1.doubleValue();
                else
                    result = number2.intValue() - number1.intValue();    
                break;
            
            case '*':
                if (number1.getClass()==Double.class || number2.getClass()==Double.class)
                    result = number2.doubleValue() * number1.doubleValue();
                else
                    result = number2.intValue() * number1.intValue();    
                break;

            case '/':
                result = number2.doubleValue() / number1.doubleValue();   
                break;

            case '^':
                result = Math.pow(number2.doubleValue(), number1.doubleValue());
                break;

            case '√':
                result = Math.pow(number2.doubleValue(), 1.0 / number1.doubleValue());
                break;

            default:
                result = 0;
                break;
        }
        stack.push(result);
    }

    @Override
    public void visitOperandNumber(OperandNumber oNumb) {
        stack.push(oNumb.getValue().get(0));
    }

    @Override
    public void visitCell(Cell cell) {
        if (cell!=null)
            stack.push(cell.getValue().get(0));
        else
            stack.push(0);
    }

    @Override
    public void visitFunction(Function fun) {
        stack.push(fun.getValue().get(0));
    }
    
    public Number returnValue() throws BadPostfixerImplementationException{
        Number number;
        //System.out.println("Size of stack at the end: " + stack.size());
        if (stack.size()==1) {
            number = stack.pop();
        }
        else
            throw new BadPostfixerImplementationException();

        return number;
    }
}
