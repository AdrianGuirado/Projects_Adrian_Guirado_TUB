package edu.upc.etsetb.arqsoft.domain;

import edu.upc.etsetb.arqsoft.spreadsheet.entities.BadCoordinateException;
import java.util.HashMap;
import java.util.Set;

public class Spreadsheet {
    private HashMap<TupleKey,Cell> cells;
    private HashMap<TupleKey,Cell> emptyCells;

    public Spreadsheet(){
        this.cells = new HashMap<TupleKey,Cell>();
        this.emptyCells = new HashMap<>();
    }
    
    public HashMap<TupleKey,Cell> getCells() {
        return this.cells;
    }

    public void setCells(HashMap<TupleKey,Cell> cells) {
        this.cells = cells;
    }
    
    public Cell getCell(TupleKey key) {
        return cells.get(key);
    }

    public void setCell (Cell cell) {
        cells.put(cell.getKey(), cell);
    }
    
    public void deleteCell (Cell cell) {
        cells.remove(cell.getKey());
    }
    
    public HashMap<TupleKey,Cell> getEmptyCells() {
        return this.emptyCells;
    }

    public void setEmptyCells(HashMap<TupleKey,Cell> emptyCells) {
        this.emptyCells = emptyCells;
    }

    public Cell getEmptyCell(TupleKey key) {
        return emptyCells.get(key);
    }

    public void setEmptyCell (Cell cell) {
        emptyCells.put(cell.getKey(), cell);
    }
    
    public void deleteEmptyCell (Cell cell) {
        emptyCells.remove(cell.getKey());
    }
    
    public TupleKey getKey(String key) throws BadCoordinateException{
        String r = key.replaceAll("[^0-9]","");
        String c = key.replaceAll("[0-9]","");
        TupleKey result = null;
                
        if ((c+r).compareTo(key) == 0) {
            Set<TupleKey> keys = cells.keySet();
            int row = Integer.parseInt(r);
            for (TupleKey k: keys) {
                if (k.getColumn().equals(c) && k.getRow()==row)
                    result = k;
            }
            keys = emptyCells.keySet();
            for (TupleKey k: keys) {
                if (k.getColumn().equals(c) && k.getRow()==row)
                    result = k;
            }
            if (result==null) {
                result = new TupleKey(row,c);
            }
        }
        else {
            throw new BadCoordinateException("Coordinate passed: " + key + ". Coordinate processed: " + (r+c));
        }
        return result;
    }
}