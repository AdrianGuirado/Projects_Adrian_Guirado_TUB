package edu.upc.etsetb.arqsoft.controller;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serial;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import edu.upc.etsetb.arqsoft.domain.Cell;
import edu.upc.etsetb.arqsoft.domain.Spreadsheet;
import edu.upc.etsetb.arqsoft.domain.TupleKey;
import edu.upc.etsetb.arqsoft.exceptions.IncorrectFormatException;
import edu.upc.etsetb.arqsoft.exceptions.IncorrectPathException;

public class SpreadsheetSaver {

    public void saveSpreadSheet(Spreadsheet spreadsheet, String path) throws IncorrectPathException, IncorrectFormatException {
        FormulaGenerator fg = new FormulaGenerator();
        StringBuilder builder = new StringBuilder();
        
        HashMap<TupleKey, Cell> cells = spreadsheet.getCells();
        
        int maxRow = getMaxRow(cells);
        String maxColumn = getMaxColumn(cells);

        if (!path.contains(".s2v")) {
            throw new IncorrectFormatException();
        }
        
        int aux = 1;
        for (int i = 0; i < maxRow; i++){ // Itera sobre las filas
            String column = "A"; // Primera columna
            while (maxColumn != "" && column.compareTo(maxColumn)<=0) { 
                TupleKey key = spreadsheet.getKey(column+(i+1));
                Cell currentCell = cells.get(key);

                if (currentCell != null && currentCell.getContent() != null) {
                    String expression = currentCell.getContent().toString();
                    expression = expression.replace(";", ",").replace("\"", "");
                    builder.append(expression);
                }

                if (!maxColumn.equals(column)) {
                    builder.append(";");
                }
                column = fg.nextColumn(column);
            }
            builder.append("\n");
        }
        
        
        if(!Files.exists(Paths.get(path))){
            try (FileWriter writer = new FileWriter(path)) {
                writer.write(builder.toString());
            } 
            catch (IOException e) {
                throw new IncorrectPathException();
            }
        }
    }

    private int getMaxRow(HashMap<TupleKey, Cell> cells) {
        int max = -1;
        for (HashMap.Entry<TupleKey, Cell> cell : cells.entrySet()) {
            if (cell.getKey().getRow() > max) max = cell.getKey().getRow();
        }
        return max;
    }

    private String getMaxColumn(HashMap<TupleKey, Cell> cells){
        String max = "";
        for (HashMap.Entry<TupleKey, Cell> cell : cells.entrySet()) {
            if (max == "" || cell.getKey().getColumn().compareToIgnoreCase(max) > 0) {
                max = cell.getKey().getColumn();
            }
        }
        return max;
    }

}