/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker;

import edu.upc.etsetb.arqsoft.controller.SpreadsheetController;
import edu.upc.etsetb.arqsoft.controller.SpreadsheetLoader;
import edu.upc.etsetb.arqsoft.controller.SpreadsheetSaver;
import edu.upc.etsetb.arqsoft.view.SpreadsheetPrinter;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 * An interface that you MUST fill for ENCAPSULATING YOUR OWN PROCESS FOR 
 * creating the whole framework of objects that your code needs, and create your 
 * initial spreadsheet, your own controller and the class that implements the 
 * ISpreadsheetControllerForChecker interface, so that once finalized the execution 
 * of the static method createSpreadsheetController(), everything is ready for 
 * invoking the methods of the object implementing the ISpreadsheetControllerForChecker interface 
 * and starting setting contents in the spreadsheet cells and start checking results 
 * and marking your code
 */
public interface ISpreadsheetFactoryForChecker {
    
    public static ISpreadsheetControllerForChecker createSpreadsheetController() {
        
        SpreadsheetSaver saver = new SpreadsheetSaver();
        SpreadsheetLoader loader = new SpreadsheetLoader();
        SpreadsheetPrinter printer = new SpreadsheetPrinter();
        ISpreadsheetControllerForChecker controller = new SpreadsheetController(printer, loader, saver);
        
        return controller;
    }
}
