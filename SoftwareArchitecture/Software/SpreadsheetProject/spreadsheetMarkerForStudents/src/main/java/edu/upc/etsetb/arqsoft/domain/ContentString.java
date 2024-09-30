package edu.upc.etsetb.arqsoft.domain;

public class ContentString implements Content{
    private String string;

    public ContentString(String string) {
        this.string = string;
    }

    public String getString() {
        return this.string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String getContentValue() {
        return this.string;
    }
    
    @Override
    public String toString() {
        return this.string;
    }

}
