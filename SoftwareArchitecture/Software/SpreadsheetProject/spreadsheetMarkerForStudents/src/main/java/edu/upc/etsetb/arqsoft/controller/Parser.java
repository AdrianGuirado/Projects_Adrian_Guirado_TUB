package edu.upc.etsetb.arqsoft.controller;

import java.util.List;
import java.util.Stack;

import edu.upc.etsetb.arqsoft.domain.formula.Token;
import edu.upc.etsetb.arqsoft.exceptions.BadFormulaException;

public class Parser {
           
    public Parser(){}
    
    public List<Token> parseFormula(List<Token> formula) throws BadFormulaException{
        Stack<Token.Type> l_parenthesis_stack = new Stack<Token.Type>();
        Stack<Token.Type> function_stack = new Stack<Token.Type>(); //Remark that can't be formulas inside a function
        
        for (int i=0; i<formula.size(); i++) {
            Token token_a = formula.get(i);
            Token token_p = null;
            if (i-1>=0) {
                token_p = formula.get(i-1);
            }
            if (i+1<formula.size()) {
                Token token_b = formula.get(i+1);
                
                switch (token_a.getType()) {
                    case SUMA:
                        if (token_b.getType() != Token.Type.L_PARENTHESIS) {
                            throw new BadFormulaException("There isn't left parenthesis after suma at token: " + i);
                        }
                        function_stack.push(token_a.getType());
                        break;
                    
                    case PROMEDIO:
                        if (token_b.getType() != Token.Type.L_PARENTHESIS) {
                            throw new BadFormulaException("There isn't left parenthesis after promedio at token: " + i);
                        }
                        function_stack.push(token_a.getType());
                        break;  
                    
                    case MAX:
                        if (token_b.getType() != Token.Type.L_PARENTHESIS) {
                            throw new BadFormulaException("There isn't left parenthesis after max at token: " + i);
                        }
                        function_stack.push(token_a.getType());
                        break;
                        
                    case MIN:
                        if (token_b.getType() != Token.Type.L_PARENTHESIS) {
                            throw new BadFormulaException("There isn't left parenthesis after min at token: " + i);
                        }
                        function_stack.push(token_a.getType());
                        break;    
                    
                    case CELL:
                        if (!function_stack.isEmpty()) { //If inside a function, only semicolon valid
                            if (token_b.getType()!=Token.Type.SEMICOLON) {
                                if (token_b.getType()!=Token.Type.R_PARENTHESIS) {
                                    throw new BadFormulaException("Cell inside function without semicolon or r. par. after it at token: " + i);
                                }
                            }
                        }
                        else if (!l_parenthesis_stack.isEmpty()){ 
                            if (token_b.getType()!=Token.Type.OPERATOR) {
                                if (token_b.getType()!=Token.Type.R_PARENTHESIS) {
                                    throw new BadFormulaException("Cell without an operator and r. par. after it at token: " + i);
                                }
                            }
                        }
                        else { //Only operator valid
                            if (token_b.getType()!=Token.Type.OPERATOR) {
                                throw new BadFormulaException("Cell without an operator after it at token: " + i);
                            }
                        }
                        break;
                    
                    case RNG_CELL:
                        if (!function_stack.isEmpty()) { //Only valid inside a function
                            if (token_b.getType()!=Token.Type.SEMICOLON) {
                                if (token_b.getType()!=Token.Type.R_PARENTHESIS) {
                                    throw new BadFormulaException("Range of cells without semicolon or r. par. after it at token: " + i);
                                }
                            }
                        }
                        break;
                    
                    case L_PARENTHESIS:
                        if (!function_stack.isEmpty()) { //If inside a function, and instantly after it is valid
                            if (token_p.getType()!=Token.Type.MAX) {
                                if (token_p.getType()!=Token.Type.MIN) {
                                    if (token_p.getType()!=Token.Type.SUMA) {
                                        if (token_p.getType()!=Token.Type.PROMEDIO) {
                                            throw new BadFormulaException("L. par. in the middle of a function at: " + i);
                                        }
                                    }
                                }
                            }
                        }
                        else { //Only r. par., operator, semicolon, rng_cells invalid
                            if (token_b.getType()==Token.Type.OPERATOR) {
                                throw new BadFormulaException("L. par. with an operator after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.SEMICOLON) {
                                throw new BadFormulaException("L. par. with a semicolon after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.RNG_CELL) {
                                throw new BadFormulaException("L. par. with a range of cells after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.R_PARENTHESIS) {
                                throw new BadFormulaException("L. par. with a r. par. after it at token: " + i);
                            }
                            l_parenthesis_stack.push(token_b.getType());
                            //System.out.println("L. Par. stack size: " + l_parenthesis_stack.size());
                        }
                        break;
                    
                    case R_PARENTHESIS:
                        if (!function_stack.isEmpty()) { //Right par. of a function
                            function_stack.pop();
                        }
                        else if (!l_parenthesis_stack.isEmpty()) { //Right par. of a formula
                            l_parenthesis_stack.pop();
                            //System.out.println("L. Par. stack size: " + l_parenthesis_stack.size());
                        }
                        else { //Right par. without left par.
                            throw new BadFormulaException("Right parenthesis without a left at token: " + i);
                        }
                        if (!function_stack.isEmpty()) { // Semicolon only valid
                            if (token_b.getType()!=Token.Type.SEMICOLON) {
                                if (token_b.getType()!=Token.Type.R_PARENTHESIS) {
                                    throw new BadFormulaException("R. par. inside a function without a ; or a ) after it at token: " + i);
                                }
                            }
                            
                        }
                        else if (token_b.getType()!=Token.Type.OPERATOR) { //After a right par. only is valid an operator
                            //System.out.println("L. Par. stack size: " + l_parenthesis_stack.size());
                            if (!l_parenthesis_stack.isEmpty()) {
                                if (token_b.getType()!=Token.Type.R_PARENTHESIS)
                                    throw new BadFormulaException("Right parenthesis with a left not close without an operator after it at token: " + i);
                            }
                            else
                                throw new BadFormulaException("Right parenthesis without an operator after it at token: " + i);
                        }
                        break;
                    
                    case NUMBER:
                        if (!function_stack.isEmpty()) { //If inside a function, only semicolon valid
                            if (token_b.getType()!=Token.Type.SEMICOLON) {
                                if (token_b.getType()!=Token.Type.R_PARENTHESIS) {
                                    throw new BadFormulaException("Number inside function without semicolon or r. par. after it at token: " + i);
                                }
                            }
                        }
                        else if (!l_parenthesis_stack.isEmpty()){ 
                            if (token_b.getType()!=Token.Type.OPERATOR) {
                                if (token_b.getType()!=Token.Type.R_PARENTHESIS) {
                                    throw new BadFormulaException("Number without an operator and r. par. after it at token: " + i);
                                }
                            }
                        }
                        else { //Only operator valid
                            if (token_b.getType()!=Token.Type.OPERATOR) {
                                throw new BadFormulaException("Number without an operator after it at token: " + i);
                            }
                        }
                        break;
                    
                    case OPERATOR:
                        if (!function_stack.isEmpty()) { //Operator not valid inside a function
                            throw new BadFormulaException("Operator inside function at token: " + i);
                        }
                        else {
                            if (token_b.getType()==Token.Type.RNG_CELL) {
                                throw new BadFormulaException("Operator with range of cell after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.SEMICOLON) {
                                throw new BadFormulaException("Operator with semicolon after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.R_PARENTHESIS) {
                                throw new BadFormulaException("Operator with right parenthesis after it it at token: " + i);
                            }
                        }
                        break;
                    
                    case SEMICOLON:
                        if (!function_stack.isEmpty()) { //Semicolon inside a function
                            if (token_b.getType()==Token.Type.SEMICOLON) {
                                throw new BadFormulaException("Semicolon with a semicolon after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.OPERATOR) {
                                throw new BadFormulaException("Semicolon with an operator after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.R_PARENTHESIS) {
                                throw new BadFormulaException("Semicolon with a right par. after it at token: " + i);
                            }
                            if (token_b.getType()==Token.Type.L_PARENTHESIS) {
                                throw new BadFormulaException("Semicolon with a left par. after it at token: " + i);
                            }
                        }
                        else {
                            throw new BadFormulaException("Semicolon outside function at token: " + i);
                        }
                        break;
                    
                    default:
                        break;
                }
            }
            else { //token_a is the last, check stacks
                if (!function_stack.isEmpty()) {
                    if (token_a.getType()!=Token.Type.R_PARENTHESIS)
                        throw new BadFormulaException("Some parenthesis of function missing");
                    else
                        function_stack.pop();
                }
                if (!l_parenthesis_stack.isEmpty()) {
                    if (token_a.getType()!=Token.Type.R_PARENTHESIS)
                        throw new BadFormulaException("Some parenthesis of formula missing");
                    else
                        l_parenthesis_stack.pop();
                }
            }    
        }
        
        return formula;
    }
}
