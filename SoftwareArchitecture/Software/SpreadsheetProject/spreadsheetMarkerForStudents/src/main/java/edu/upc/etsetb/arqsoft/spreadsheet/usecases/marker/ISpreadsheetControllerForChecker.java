/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker;

import edu.upc.etsetb.arqsoft.spreadsheet.entities.BadCoordinateException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.CircularDependencyException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.ContentException;
import edu.upc.etsetb.arqsoft.spreadsheet.entities.NoNumberException;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 * 
 * You must get a class that implements this interface. As you can see objects 
 * of this class shall allow the marker classes to set the content of a cell, 
 * get the value of the content of a cell as a double value, and get the value 
 * of the content of a cell as a String.
 */
public interface ISpreadsheetControllerForChecker {
    
    /**
     * Tries to set the content of a cell, compute the value of this content, and 
     * properly recompute all the formulas in the spreadsheet that reference the 
     * cell whose content has been set.
     * @param cellCoord the coordinate of the cell
     * @param strContent the text representation of the purported new content
     * @throws ContentException if the content represents a formula which is not 
     * correct by any other reason than introducing a circular dependency in the spreadsheet
     * @throws BadCoordinateException if the cellCoord argument does not represent a 
     * proper spreadsheet coordinate
     * @throws CircularDependencyException if the code detects that the strContent is 
     * a formula that introduces in the spreadsheet some circular dependency
     */
    public void setCellContent(String cellCoord, String strContent)
            throws ContentException, BadCoordinateException, CircularDependencyException;
    
    /**
     * Returns the value of the content of a cell as a double 
     * @param coord the coordinate of the cell
     * @return a double value version of the content of a cell. If the cell contains a 
     * textual content with the empty string, it returns 0; if the cell contains a textual 
     * content with a string that is the textual representation of a number ("123", for instance) 
     * it returns the number represented (123 in the former example). If the cell contains 
     * a numerical content, it directly returns the value of this content. If the cell content 
     * is a formula, it returns the result of evaluating such formula
     * @throws BadCoordinateException if the coord argument does not represent a legal coordinate in 
     * the spreadsheet
     * @throws NoNumberException if the cell has a textual content whose value is a 
     * string that does not represent any number ("123a", for instance)
     */
    public double getCellContentAsDouble(String coord) throws BadCoordinateException, NoNumberException;
    
    /**
     * Returns the value of the content of a cell as a string 
     * 
     * @param cooord the coordinate of the cell
     * 
     * @return a string  version of the content of a cell. If the cell contains a 
     * textual content it directly shall return its string value. If the cell contains 
     * a numerical content, it returns the textual representation of the number (NOTE: 
     * use the static method of String valueOf for getting such strings). If the cell content 
     * is a formula, it returns the string representing the number resulting of evaluating such formula
     * 
     * @throws BadCoordinateException if the coord argument does not represent a legal coordinate in 
     * the spreadsheet
     */
    public String getCellContentAsString(String cooord) throws BadCoordinateException;
    
    /**
     * Tries to return the textual representation of the formula present in 
     * the cell whose coordiantes are represented by argument coord; the textual 
     * representation of a formula MUST NOT INCLUDE THE '=' character, and there
     * must not be any whitespace
     * @param coord the coordinate of the cell
     * @return a String containing the textual representation of a formula without 
     * the initial '=' character. Example "A1*B5*SUMA(A2:B27)"
     * @throws BadCoordinateException if the coord argument does not represent a legal coordinate in 
     * the spreadsheet OR if the coord argument represents a legal coordinate BUT 
     * the cell in this coordinate DOES NOT CONTAIN A FORMULA
     */
    public String getCellFormulaExpression(String coord)throws BadCoordinateException;
    
    /**
     * Method for saving the spreadsheet in a file
     * @param nameInUserDir the local name of the file within the netbeans folder 
     * (the netbeans folder is returned by System.getProperty("user.dir");)
     * @throws SavingSpreadSheetException if something has gone wrong while trying 
     * to write the spreadsheet into the aforementioned file.
     */
    public void saveSpreadSheetToFile(String nameInUserDir) throws SavingSpreadSheetException ;
    
    /**
     * Tries to read the spreadsheet from a file
     * @param nameInUserDir the local name of the file within the netbeans folder 
     * (the netbeans folder is returned by System.getProperty("user.dir");)
     * @throws ReadingSpreadSheetException if something has gone wrong while trying 
     * to create spreadsheet and fill it with the data present within the 
     * aforementioned file.
     */
    public void readSpreadSheetFromFile(String nameInUserDir) throws ReadingSpreadSheetException ;
    
    
    
}
