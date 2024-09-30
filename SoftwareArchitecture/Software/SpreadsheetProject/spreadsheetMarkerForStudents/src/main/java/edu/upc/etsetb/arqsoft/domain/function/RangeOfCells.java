package edu.upc.etsetb.arqsoft.domain.function;

import java.util.ArrayList;
import java.util.List;

import edu.upc.etsetb.arqsoft.domain.Cell;

public class RangeOfCells implements Argument{
    private Cell initialCell;
    private Cell finalCell;
    private List<Cell> cells;

    public RangeOfCells(Cell initialCell, Cell finalCell, List<Cell> cells) {
        this.initialCell = initialCell;
        this.finalCell = finalCell;
        this.cells = cells;
    }

    public Cell getInitialCell() {
        return this.initialCell;
    }

    public Cell getFinalCell() {
        return this.finalCell;
    }

    public List<Cell> getCells() {
        return this.cells;
    }

    //Setters have no sense

    @Override
    public List<Number> getValue() {
        List<Number> list = new ArrayList<>();

        for (Cell cell: cells) {
            if (cell != null)
                list.addAll(cell.getValue());
            else
                list.add(0);
        }

        return list;
    }
    
    @Override
    public String toString() {
        return cells.get(0).getKey() + ":" + cells.get(cells.size()-1).getKey();
    }

}
