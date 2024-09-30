package edu.upc.etsetb.arqsoft;


import edu.upc.etsetb.arqsoft.controller.SpreadsheetController;
import edu.upc.etsetb.arqsoft.controller.SpreadsheetLoader;
import edu.upc.etsetb.arqsoft.controller.SpreadsheetSaver;
import edu.upc.etsetb.arqsoft.view.SpreadsheetPrinter;
import edu.upc.etsetb.arqsoft.view.SpreadsheetUI;

public class App {
    public static void main(String[] args) {

        SpreadsheetPrinter printer = new SpreadsheetPrinter(); 
        SpreadsheetLoader loader = new SpreadsheetLoader();    
        SpreadsheetSaver saver = new SpreadsheetSaver();

        SpreadsheetController controller = new SpreadsheetController(printer, loader, saver);

        SpreadsheetUI ui = new SpreadsheetUI(controller);
        ui.runMainMenu();
    }
}