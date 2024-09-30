package edu.upc.etsetb.arqsoft.domain;

public class TupleKey {
    private int row; //It's unsigned
    private String column;

    public TupleKey(int row, String column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return this.row;
    }

    /* 
    No sense, you are not changing a value of the key, you will change its content.
    public void setRow(int row) {
        this.row = row;
    }
    */
    public String getColumn() {
        return this.column;
    }

    /*
    No sense.
    public void setColumn(String column) {
        this.column = column;
    }
    */
    
    @Override
    public String toString() {
        return column + row;
    }
}
