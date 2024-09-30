/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.arqsoft.spreadsheet.entities;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña. 
 * This is the exception that the class that implements the ISpreadsheetControllerForChecker 
 * has to throw when during the process of evaluating a formula, it is not possible to 
 * get a number from a cell.
 */
public class NoNumberException extends ContentException {

    /**
     * Creates a new instance of <code>NoNumberException</code> without detail
     * message.
     */
    public NoNumberException() {
    }

    /**
     * Constructs an instance of <code>NoNumberException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NoNumberException(String msg) {
        super(msg);
    }
}
