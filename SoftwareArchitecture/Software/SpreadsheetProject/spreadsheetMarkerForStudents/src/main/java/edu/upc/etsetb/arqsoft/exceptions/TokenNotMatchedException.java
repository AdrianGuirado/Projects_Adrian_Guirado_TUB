/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.etsetb.arqsoft.exceptions;

public class TokenNotMatchedException extends Exception{
    private String info;
    
    public TokenNotMatchedException() {
    
    }
    
    public TokenNotMatchedException(String info) {
        this.info = info;
    }
    
    public String getInfo() {
        return info;
    }
}
