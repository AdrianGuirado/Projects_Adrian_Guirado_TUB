package edu.upc.etsetb.arqsoft.domain.formula;

import java.util.List;

public abstract class Operand implements FormulaContent{
    public abstract List<Number> getValue();
}
