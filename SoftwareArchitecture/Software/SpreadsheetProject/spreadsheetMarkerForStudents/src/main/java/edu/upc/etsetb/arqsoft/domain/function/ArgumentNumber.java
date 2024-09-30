package edu.upc.etsetb.arqsoft.domain.function;

import java.util.ArrayList;
import java.util.List;

public class ArgumentNumber implements Argument{
    private Number number;

    public ArgumentNumber(Number number) {
        this.number = number;
    }

    public Number getNumber() {
        return this.number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }

    @Override
    public List<Number> getValue() {
        List <Number> list = new ArrayList<>();
        list.add(number);
        return list;
    }
    
    @Override
    public String toString() {
        return String.valueOf(number);
    }
}
