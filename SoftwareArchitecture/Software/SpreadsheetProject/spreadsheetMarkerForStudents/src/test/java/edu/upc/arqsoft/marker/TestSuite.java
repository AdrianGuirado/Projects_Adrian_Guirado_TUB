/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.arqsoft.marker;

import edu.upc.etsetb.arqsoft.spreadsheet.marker.CircularDependenciesTest;
import edu.upc.etsetb.arqsoft.spreadsheet.marker.DependentCellsTest;
import edu.upc.etsetb.arqsoft.spreadsheet.marker.FormulaContentTest;
import edu.upc.etsetb.arqsoft.spreadsheet.marker.NumberContentTest;
import edu.upc.etsetb.arqsoft.spreadsheet.marker.SaveTest;
import edu.upc.etsetb.arqsoft.spreadsheet.marker.LoadTest;
import edu.upc.etsetb.arqsoft.spreadsheet.marker.TextContentTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
@RunWith(Suite.class)
@SuiteClasses({
    TextContentTest.class,
    NumberContentTest.class,
    FormulaContentTest.class,
    DependentCellsTest.class,
    CircularDependenciesTest.class,
    SaveTest.class,
    LoadTest.class
})
public class TestSuite {

}
