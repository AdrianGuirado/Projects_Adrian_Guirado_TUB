package edu.upc.etsetb.arqsoft.domain;

import java.util.List;

import edu.upc.etsetb.arqsoft.domain.formula.Operand;
import edu.upc.etsetb.arqsoft.controller.PostfixVisitor;
import edu.upc.etsetb.arqsoft.domain.formula.Formula;
import edu.upc.etsetb.arqsoft.domain.function.Argument;
import java.util.ArrayList;

public class Cell extends Operand implements Argument{
    private Content content;
    private TupleKey key;
    private List<Cell> associatedCells;

    public Cell() {
        this.associatedCells = new ArrayList<Cell>();        
    }
    
    public Cell(Content content, TupleKey key) {
        this.content = content;
        this.key = key;
        this.associatedCells = new ArrayList<Cell>();
    }

    public Content getContent() {
        return this.content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public TupleKey getKey() {
        return this.key;
    }

    public void setKey(TupleKey key) {
        this.key = key;
    }

    public List<Cell> getAssociatedCells() {
        return this.associatedCells;
    }

    public void setAssociatedCells(List<Cell> associatedCells) {
        this.associatedCells = associatedCells;
    }

    public void setAssociatedCell (Cell cell) {
        this.associatedCells.add(cell);
    }

    public void removeAssociatedCell (Cell cell) {
        this.associatedCells.remove(cell);
    }

    @Override
    public List<Number> getValue() {
        String cnt = content.getContentValue();
        List<Number> num = new ArrayList<>();
        if (!cnt.contains("[a-zA-Z]+") && cnt.length()>0) {//Not working, i think
            //IS A NUMBER, ALL GOOD
            if (cnt.contains(".")) { //It's double
                num.add(Double.valueOf(cnt));
            }
            else { //It's integer
                num.add(Integer.valueOf(cnt));
            }
        }
        else {
            //Problems, per now return empty list
            //TODO
        }

        return num;
    }

    @Override    
    public void acceptVisitor (PostfixVisitor v) {
        v.visitCell(this);
    }
    
    @Override
    public String toString() {
        return "Cell{" +
               "key=" + key +
               ", content='" + (content != null ? content.getContentValue() : "null") + '\'' +
               ", associatedCells=" + associatedCells.size() +
               '}';
    }

}