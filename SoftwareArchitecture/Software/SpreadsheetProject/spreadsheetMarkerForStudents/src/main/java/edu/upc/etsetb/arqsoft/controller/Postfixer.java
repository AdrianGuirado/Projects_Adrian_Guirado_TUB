package edu.upc.etsetb.arqsoft.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import edu.upc.etsetb.arqsoft.domain.formula.Token;
import edu.upc.etsetb.arqsoft.exceptions.BadFormulaException;

public class Postfixer {
    public Postfixer (){}
    
    private int operatorPriority (Token token) {
        /*
        Priorities:
        - Inmediate: )
        - High: ^ √
        - Medium: * / 
        - Low: + -
        - Wild: (
        */ 
        int priority=-1;
        
        switch (token.getType()) {
            case L_PARENTHESIS:
                priority = 0;
                break;
                
            case OPERATOR:
                switch (token.getToken()) {
                    case "^":
                    case "√":
                        priority = 3;
                        break;
                        
                    case "*":
                    case "/":
                        priority = 2;
                        break;
                        
                    case "+":
                    case "-":
                        priority = 1;
                        break;
                }
                break;
            case R_PARENTHESIS:
                priority = 4;
                break;
        
        }
        return priority;        
    }

    private void processOperator(Stack<Token> operatorParenthesisStack, List<Token> result, Token token_a) {
        if (operatorParenthesisStack.isEmpty()) { //Simply add operator
            operatorParenthesisStack.push(token_a);
        }
        else {
            Token topToken = operatorParenthesisStack.peek();
            
            if (topToken.getType()==Token.Type.L_PARENTHESIS) { //Wild priority
                //Simply push operator
                operatorParenthesisStack.push(token_a);
            }
            else if (topToken.getType()==Token.Type.OPERATOR) {
                if (operatorPriority(topToken)==3) {
                    // High priority operator at stack
                    if (operatorPriority(token_a)==3) {
                        //Current operator is high priority also
                        //Directly add to the result
                        result.add(operatorParenthesisStack.pop());
                        operatorParenthesisStack.push(token_a);
                    }
                    else if (operatorPriority(token_a)==2) {
                        //Current operator is of medium priority, pop until left par. or same priority
                        while (operatorPriority(token_a)<operatorPriority(topToken) && !topToken.getToken().equals("(") && !operatorParenthesisStack.isEmpty()) {
                            result.add(operatorParenthesisStack.pop());
                            if (!operatorParenthesisStack.isEmpty())
                                topToken = operatorParenthesisStack.peek();
                        }

                        if (topToken.getType()==Token.Type.L_PARENTHESIS) {
                            operatorParenthesisStack.push(token_a);
                        }
                        else if (operatorParenthesisStack.isEmpty()){ //Stack empty push operator
                            operatorParenthesisStack.push(token_a);
                        }
                        else if (operatorPriority(token_a)>operatorPriority(topToken)) {
                            operatorParenthesisStack.push(token_a);
                        }
                        else {
                            result.add(token_a);
                        }
                        
                    }
                    else {
                        //Current operator is of low priority, pop until left par, or same priority
                        while (operatorPriority(token_a)<operatorPriority(topToken) && !topToken.getToken().equals("(") && !operatorParenthesisStack.isEmpty()) {
                            result.add(operatorParenthesisStack.pop());
                            if (!operatorParenthesisStack.isEmpty())
                                topToken = operatorParenthesisStack.peek();
                        }

                        if (topToken.getType()==Token.Type.L_PARENTHESIS) {
                            operatorParenthesisStack.push(token_a);
                        }
                        else if (operatorParenthesisStack.isEmpty()){ //Stack empty push operator
                            operatorParenthesisStack.push(token_a);
                        }
                        else if (operatorPriority(token_a)>operatorPriority(topToken)) {
                            operatorParenthesisStack.push(token_a);
                        }
                        else {
                            result.add(token_a);
                        }

                    }
                }
                else if (operatorPriority(topToken)==2) {
                    // Medium priority operator at stack
                    if (operatorPriority(token_a)==3) {
                        //Current operator is high priority
                        //Directly push to the stack
                        operatorParenthesisStack.push(token_a);
                    }
                    else if (operatorPriority(token_a)==2){
                        //Current operator is of medium priority also
                        //Direclty add the token to the result
                        result.add(operatorParenthesisStack.pop());
                        operatorParenthesisStack.push(token_a);

                    }
                    else {
                        //Current operator is of low priority, pop until left par, or same priority
                        while (operatorPriority(token_a)<operatorPriority(topToken) && !topToken.getToken().equals("(") && !operatorParenthesisStack.isEmpty()) {
                            result.add(operatorParenthesisStack.pop());
                            if (!operatorParenthesisStack.isEmpty())
                                topToken = operatorParenthesisStack.peek();
                        }
                        
                        if (topToken.getType()==Token.Type.L_PARENTHESIS) {
                            operatorParenthesisStack.push(token_a);
                        }
                        else if (operatorParenthesisStack.isEmpty()){ //Stack empty push operator
                            operatorParenthesisStack.push(token_a);
                        }
                        else if (operatorPriority(token_a)>operatorPriority(topToken)) {
                            operatorParenthesisStack.push(token_a);
                        }
                        else {
                            result.add(token_a);
                        }

                    }
                }
                else {
                    // Low priority operator at stack
                    if (operatorPriority(token_a)==3) {
                        //Current operator is high priority
                        //Directly push to the stack
                        operatorParenthesisStack.push(token_a);
                    }
                    else if (operatorPriority(token_a)==2){
                        //Current operator is of medium priority
                        //Directly push to the stack
                        operatorParenthesisStack.push(token_a);
                    }
                    else {
                        //Current operator is of low priority also
                        //Directly add to the result
                        result.add(operatorParenthesisStack.pop());
                        operatorParenthesisStack.push(token_a);
                    }
                }
            }
            // Right parenthesis won't be pushed at the stack
        }
    }
    
    public List<Token> generatePostfix(List<Token> formula) throws BadFormulaException {
        List<Token> result = new ArrayList<>();
        Stack<Token> operatorParenthesisStack = new Stack<>();
        Stack<Token.Type> functionStack = new Stack<>();

        for (int i=0; i<formula.size(); i++) {
            Token token_a = formula.get(i);

            if (!functionStack.isEmpty()) { //If function simply add the tokens to the result (parser has checked the correctness)
                if (token_a.getType()==Token.Type.R_PARENTHESIS) { //If right par. pop one of the stack
                    functionStack.pop();
                }
                else {
                    switch (token_a.getType()) {
                        case MAX:
                            functionStack.push(token_a.getType());
                            break;
                            
                        case MIN:
                            functionStack.push(token_a.getType());
                            break;
                            
                        case PROMEDIO:
                            functionStack.push(token_a.getType());
                            break;
                            
                        case SUMA:
                            functionStack.push(token_a.getType());
                            break;
                    }
                }
                result.add(token_a);
            }
            else {
                switch (token_a.getType()) {
                    case CELL:
                        // Same as NUMBER
                        //Directly add to the result
                        result.add(token_a);
                        break;
                    case L_PARENTHESIS:
                        //Wild priority, simply add to stack
                        operatorParenthesisStack.push(token_a);
                        break;
                    case MAX:
                        functionStack.push(token_a.getType());
                        //As a NUMBER
                        //Directly add to the result
                        result.add(token_a);
                        break;
                    case MIN:
                        functionStack.push(token_a.getType());
                        //As a NUMBER
                        //Directly add to the result
                        result.add(token_a);
                        break;
                    case NUMBER:
                        //Directly add to the result
                        result.add(token_a);
                        break;
                    case OPERATOR:
                        processOperator(operatorParenthesisStack, result, token_a);
                        break;
                    case PROMEDIO:
                        functionStack.push(token_a.getType());
                        //As a NUMBER
                        //Directly add to the result
                        result.add(token_a);
                        break;
                    case RNG_CELL:
                        //In theory not possible with the function stack
                        break;
                    case R_PARENTHESIS:
                        Token topToken = operatorParenthesisStack.pop();
                        while (topToken.getType()!=Token.Type.L_PARENTHESIS) {
                            result.add(topToken);
                            if (!operatorParenthesisStack.isEmpty()) {
                                topToken = operatorParenthesisStack.pop();
                            }
                            else {
                                // Weird, parser fails somewhere
                                throw new BadFormulaException("Right parenthesis without a left somewhere");
                            }
                        }
                        break;
                    case SEMICOLON:
                        //In theory not possible with the function stack
                        break;
                    case SUMA:
                        functionStack.push(token_a.getType());
                        //As a NUMBER
                        //Directly add to the result
                        result.add(token_a);
                        break;
                    default:
                        break;
                }
            }
            if (i == formula.size() - 1) { //last token
                while (!operatorParenthesisStack.isEmpty()) {
                    Token t = operatorParenthesisStack.pop();
                    if (t.getType()!=Token.Type.L_PARENTHESIS) {
                        result.add(t);
                    }
                        
                }
                operatorParenthesisStack.clear();
            }
        }   
        
        return result;
        }
}
