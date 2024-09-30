package edu.upc.etsetb.arqsoft.domain.function;

import java.util.ArrayList;
import java.util.List;

public class Promedio extends Function{

    public Promedio() {
        super();
    }

    public Promedio(List<Argument> arguments) {
        super(arguments);
    }

    @Override
    public List<Number> getValue() {

        List<Number> values = new ArrayList<>();
        List<Number> result = new ArrayList<>();
        

        for (Argument arg:super.getArguments()) {
            if (arg!=null)
                values.addAll(arg.getValue());
            else
                values.add(0);
        }

        double sum = 0;
        
        for (Number value : values) {
            sum += value.doubleValue();
        }
        double average = sum / values.size();

        result.add(average);
        
        return result;
    }
    
    @Override
    public String toString() {
        String result = "PROMEDIO(";
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
