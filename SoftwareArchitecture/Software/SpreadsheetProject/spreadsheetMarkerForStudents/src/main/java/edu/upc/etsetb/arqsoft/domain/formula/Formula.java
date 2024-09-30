package edu.upc.etsetb.arqsoft.domain.formula;

import java.util.ArrayList;
import java.util.List;

import edu.upc.etsetb.arqsoft.domain.Content;
import edu.upc.etsetb.arqsoft.exceptions.IsNumberException;
import edu.upc.etsetb.arqsoft.exceptions.IsStringException;

public class Formula implements Content{

    private List<FormulaContent> formulaContents;
    private String expression;
    private Number lastValue;

    public Formula(String expression) {
        formulaContents = new ArrayList<>();
        this.expression = expression;
        lastValue = null;
    }

    public List<FormulaContent> getFormulaContents() {
        return this.formulaContents;
    }

    public void addFormulaContent(FormulaContent content) {
        formulaContents.add(content);
    }

    public void setFormulaContents(List<FormulaContent> formulaContents) {
        this.formulaContents = formulaContents;
    }

    public String getExpressionWithoutEqual() {
        return this.expression.split("=")[1];
    }
    
    public String getExpression() {
         return this.expression;
    }

    @Override
    public String getContentValue() {
        return lastValue.toString();
    }

    public static void isFormula(String content) throws IsNumberException, IsStringException {
        if (content.charAt(0) != '=') {
            if (content.matches("[0-9]+")) {
                throw new IsNumberException();
            }
            else {
                throw new IsStringException();
            }
        }
    }

    public void setLastValue (Number num) {
        this.lastValue = num;
    }
    
    @Override
    public String toString() {
        return this.expression;
    }
}
