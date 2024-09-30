/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.etsetb.arqsoft.exceptions;

public class BadFormulaException extends Exception{
    private String statement;
    
    public BadFormulaException (String statement){
        this.statement = statement;
    }

    public String getStatement () {
        return this.statement;
    }
}
