package edu.upc.etsetb.arqsoft.domain.formula;

import edu.upc.etsetb.arqsoft.controller.PostfixVisitor;
import edu.upc.etsetb.arqsoft.exceptions.ZeroDivisionException;

public interface FormulaContent {
    public void acceptVisitor (PostfixVisitor v) throws ZeroDivisionException;
}
