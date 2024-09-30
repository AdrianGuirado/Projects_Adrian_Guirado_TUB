package edu.upc.etsetb.arqsoft.domain;

public class ContentNumber implements Content{

    private Number number;

    public ContentNumber(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return this.number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    @Override
    public String getContentValue() {
        return number.toString();
    }
    
    @Override
    public String toString() {
        return number.toString();
    }

}
