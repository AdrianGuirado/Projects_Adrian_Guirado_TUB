package edu.upc.etsetb.arqsoft.controller;

import java.util.ArrayList;
import java.util.List;
import edu.upc.etsetb.arqsoft.domain.formula.Token;
import edu.upc.etsetb.arqsoft.exceptions.TokenNotMatchedException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Tokenizer {
    private class TokenInfo {
        public final Pattern regex;
        public Token.Type type;
        
        public TokenInfo(Pattern regex, Token.Type type) {
            super();
            this.regex = regex;
            this.type = type;
        }
    }
    
    private List<TokenInfo> tokenInfos;
    
    public Tokenizer() {
        tokenInfos = new ArrayList<TokenInfo>();
        
        add("SUMA", Token.Type.SUMA);
        add("PROMEDIO", Token.Type.PROMEDIO);
        add("MIN", Token.Type.MIN);
        add("MAX", Token.Type.MAX);
        add("\\(", Token.Type.L_PARENTHESIS);
        add("\\)", Token.Type.R_PARENTHESIS);
        add("\\+",Token.Type.OPERATOR);
        add("-",Token.Type.OPERATOR);
        add("\\*",Token.Type.OPERATOR);
        add("/",Token.Type.OPERATOR);
        add("\\^",Token.Type.OPERATOR);
        add("√",Token.Type.OPERATOR);
        add("[A-Z]+[A-Z0-9_]+:[A-Z]+[A-Z0-9_]+", Token.Type.RNG_CELL);
        add("[A-Z]+[A-Z0-9_]+", Token.Type.CELL);
        add("[0-9]+", Token.Type.NUMBER);
        add("[0-9]+.[0-9]+", Token.Type.NUMBER);
        add(";", Token.Type.SEMICOLON);
    }
    
    private void add(String regex, Token.Type type) {
        tokenInfos.add(new TokenInfo(Pattern.compile("^(" + regex + ")"), type));
    }
    
    public List<Token> tokenizeFormula(String formula) throws TokenNotMatchedException{
        List<Token> tokens = new ArrayList<>();
        String copy = formula.split("=")[1]; //Formula without the equal
        
        while (!copy.equals("")) {
            boolean match = false;
            for (TokenInfo t_info: tokenInfos) {
                Matcher m = t_info.regex.matcher(copy);
                if (m.find()) {
                    match = true;
                    String token = m.group().trim();
                    
                    tokens.add(new Token(token, t_info.type));
                    
                    if (token.equals("+") || token.equals("(") || token.equals(")") || token.equals("*") || token.equals("^")) {
                        copy = copy.replaceFirst("\\"+token, "");
                    }
                    else {
                        copy = copy.replaceFirst(token, "");
                    }
                    break;                 
                }
            }
            if (match == false) {
                throw new TokenNotMatchedException("Token not matched in: " + copy);
            }
        }
        
        return tokens;
    }
}
