/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
public class SavingSpreadSheetException extends Exception{

    /**
     * Creates a new instance of <code>SavingSpreadSheetException</code> without
     * detail message.
     */
    public SavingSpreadSheetException() {
    }

    /**
     * Constructs an instance of <code>SavingSpreadSheetException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public SavingSpreadSheetException(String msg) {
        super(msg);
    }
}
