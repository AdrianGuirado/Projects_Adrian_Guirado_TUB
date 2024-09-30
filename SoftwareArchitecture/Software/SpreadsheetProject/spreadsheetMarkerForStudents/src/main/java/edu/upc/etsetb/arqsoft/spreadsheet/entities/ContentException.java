/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.arqsoft.spreadsheet.entities;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña. 
 * 
 * This is the exception that the class that implements the ISpreadsheetControllerForChecker 
 * has to throw when the tokenizer or the parser detect a problem in the formula. Also when 
 * there is a problem during the computation of a formula different than the existence of a
 * bad coordinate, circular dependencies or a reference to a cell from which it is not possible 
 * to get a number.
 */
public class ContentException extends Exception {

    /**
     * Creates a new instance of <code>ContentException</code> without detail
     * message.
     */
    public ContentException() {
    }

    /**
     * Constructs an instance of <code>ContentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ContentException(String msg) {
        super(msg);
    }
}
