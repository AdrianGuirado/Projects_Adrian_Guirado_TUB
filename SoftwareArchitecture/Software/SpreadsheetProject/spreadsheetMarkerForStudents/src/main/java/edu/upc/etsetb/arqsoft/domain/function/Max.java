package edu.upc.etsetb.arqsoft.domain.function;

import java.util.ArrayList;
import java.util.List;

public class Max extends Function{

    public Max() {
        super();
    }

    public Max(List<Argument> arguments) {
        super(arguments);
    }

    @Override
    public List<Number> getValue() {

         List<Number> values = new ArrayList<>();

        for (Argument arg:super.getArguments()) {
            if (arg!=null)
                values.addAll(arg.getValue());
            else
                values.add(0);
        }

        Number maxValue = values.get(0);

        for (int i = 1; i < values.size(); i++) {
            if (values.get(i).doubleValue() < maxValue.doubleValue()) {
                values.remove(i);
                i--;
            } else {
                maxValue = values.get(i);
            }
        }
        
        return values;
        
    }
    
    @Override
    public String toString() {
        String result = "MAX(";
        for (int i=0; i<this.arguments.size(); i++) {
            Argument arg = this.arguments.get(i);
            if (i!=this.arguments.size()-1)
                result += arg + ";";
            else
                result += arg;
        }
        result+=")";
        return result;
    }
}
