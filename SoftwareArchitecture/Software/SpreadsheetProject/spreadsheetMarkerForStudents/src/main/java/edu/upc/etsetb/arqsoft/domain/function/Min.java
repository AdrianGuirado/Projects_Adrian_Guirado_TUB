package edu.upc.etsetb.arqsoft.domain.function;

import java.util.ArrayList;
import java.util.List;

public class Min extends Function{

    public Min() {
        super();
    }

    public Min(List<Argument> arguments) {
        super(arguments);
    }

    @Override
    public List<Number> getValue() {
        List<Number> values = new ArrayList<>();
        
        for (Argument arg:super.getArguments()) {
            values.addAll(arg.getValue());
        }

        Number minValue = values.get(0);

        for (int i = 1; i < values.size(); i++) {
            if (values.get(i).doubleValue() > minValue.doubleValue()) {
                values.remove(i);
                i--;
            } else {
                minValue = values.get(i);
            }
        }
        
        return values;
    }
    
    @Override
    public String toString() {
        String result = "MIN(";
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
