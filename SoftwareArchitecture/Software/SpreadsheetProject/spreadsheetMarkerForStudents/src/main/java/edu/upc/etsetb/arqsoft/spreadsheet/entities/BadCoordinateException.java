/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.etsetb.arqsoft.spreadsheet.entities;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña. This is the exception that the class that implements the ISpreadsheetControllerForChecker 
 * has to throw when  there is an argument that should be a proper coordinate, but it is not 
 *
 */

public class BadCoordinateException extends IllegalArgumentException {

    /**
     * Creates a new instance of <code>BadCoordinateException</code> without
     * detail message.
     */
    public BadCoordinateException() {
    }

    /**
     * Constructs an instance of <code>BadCoordinateException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public BadCoordinateException(String msg) {
        super(msg);
    }
}
