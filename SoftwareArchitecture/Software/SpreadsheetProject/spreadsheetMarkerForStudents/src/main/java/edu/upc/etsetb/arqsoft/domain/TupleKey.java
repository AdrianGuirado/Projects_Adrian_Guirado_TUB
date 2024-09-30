package edu.upc.etsetb.arqsoft.domain;

public class TupleKey {
    private int row; 
    private String column;

    public TupleKey(int row, String column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    public String getColumn() {
        return this.column;
    }
 
    @Override
    public String toString() {
        return column + row;
    }
}
