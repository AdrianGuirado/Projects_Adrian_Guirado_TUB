package edu.upc.etsetb.arqsoft.view;

import java.util.Map;
import java.util.HashMap;

import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.Spreadsheet;
import edu.upc.etsetb.arqsoft.domain.TupleKey;
import edu.upc.etsetb.arqsoft.domain.formula.Formula;

public class SpreadsheetPrinter {

    public SpreadsheetPrinter() {
        // Constructor vacío, no se necesitan inicializaciones adicionales
    }

    public void displaySpreadsheet(Spreadsheet spreadsheet) {
        HashMap<TupleKey, Cell> cells = spreadsheet.getCells();
        for (Map.Entry<TupleKey, Cell> entry : cells.entrySet()) {
            TupleKey key = entry.getKey();
            Cell cell = entry.getValue();
            if (cell.getContent().getClass() == Formula.class)
            {
                System.out.println("Cell " + key + ": " + cell.getContent().getContentValue() + ": " + ((Formula)cell.getContent()).getExpression());

            } else {
                System.out.println("Cell " + key + ": " + cell.getContent().getContentValue());
            }
        }
    }
}