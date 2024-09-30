package edu.upc.etsetb.arqsoft.domain.formula;

public class Token {
    public enum Type { // Check if some time is not here
        SUMA, PROMEDIO, MAX, MIN, CELL, RNG_CELL,
        L_PARENTHESIS, R_PARENTHESIS, NUMBER, OPERATOR,
        SEMICOLON
    }

    private String token;
    private Type type;
    
    public Token (String token, Type type){
        this.token = token;
        this.type = type;
    }
    
    public String getToken (){
        return token;
    }
    
    public Type getType (){
        return type;
    }
    
    public String toString() {
        return this.token;
    }
}
