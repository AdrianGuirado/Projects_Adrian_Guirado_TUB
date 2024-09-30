/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.upc.etsetb.arqsoft.spreadsheet.marker;

// CHANGE THESE IMPORTS AS PER YOUR OWN PACKAGES
import edu.upc.ac.corrector.SuperClassForTests;
import edu.upc.arqsoft.marker.TestAll;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ISpreadsheetControllerForChecker;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ISpreadsheetFactoryForChecker;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LoadTest extends SuperClassForTests {

    private static int numErrorsBefore;

    private static int numInstances = 0;

    private static Map<String, Double> nota;

    private ISpreadsheetControllerForChecker instance;

    private String path;

    public LoadTest() {
        super();
        numInstances++;
        if (numInstances == 1) {
            numErrorsBefore = SuperClassForTests.indErrors.size();
        }
        this.instance = ISpreadsheetFactoryForChecker
                .createSpreadsheetController();
        this.path = System.getProperty("user.dir") + "/marker_save_test_ref.s2v";

    }

    @BeforeClass
    public static void setUpClass() {
        nota = TestAll.notas;
    }

    @AfterClass
    public static void tearDownClass() {
        showErrors(SuperClassForTests.indErrors, "LoadTest");
        nota.put("LoadTest", puntosTotales);
        puntosTotales = 0;
    }

    @Test
    public void test_01_read() {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 10;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        int numTests = 7*11;
        this.printlnAlways("\nTesting load spreadsheet. "
                + "Total value (over 10): " + valorTotal);
        try {
            this.printlnAlways("\nThe spreadsheet shall be loaded from file . "
                    + "marker_save_test_ref.s2v" + valorTotal);
            instance.readSpreadSheetFromFile("marker_save_test_ref.s2v");
            this.checkDataInFirstRow(valorTotal / numTests);
            this.checkDataInSecondRow(valorTotal / numTests);
            this.checkDataInThirdRow(valorTotal / numTests);
            this.checkDataInFourthRow(valorTotal / numTests);
            this.checkDataInFifthRow(valorTotal / numTests);
            this.checkDataInSixthRow(valorTotal / numTests);
            this.checkDataInSeventhRow(valorTotal / numTests);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        puntos(puntosAntes);
        throwIfAnError(toThrow);
    }
    
    protected void checkDataInFirstRow(double valorCadaTest) {
        //11 tests
        AssertionError toThrow = null;
        AssertionError error = null;
        double expDouble;
        String expString;
        String cellId;
        this.checkDoubleInCell("A1", 1.0, valorCadaTest);
        this.checkStringFromFormulaInCell("B1", "1+2", valorCadaTest);
        this.checkStringFromFormulaInCell("C1", "10/(2+3)", valorCadaTest);
        this.checkStringFromFormulaInCell("D1", "A1*10-5", valorCadaTest);
        this.checkStringFromFormulaInCell("E1", "(A5*4)/(A2+A2)", valorCadaTest);
        this.checkStringFromFormulaInCell("F1", "100/(A5+(A5*A5/5))", valorCadaTest);
        this.checkStringFromFormulaInCell("G1", "(A5*4)/(A2+A2)+SUMA(1;2;3;4;5)", valorCadaTest);
        this.checkStringFromFormulaInCell("H1", "(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5)", valorCadaTest);
        this.checkStringFromFormulaInCell("I1", "(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12)", valorCadaTest);
        this.checkStringFromFormulaInCell("J1", "(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12;MIN(A13:A20))", valorCadaTest);
        this.checkEmptyCell("K1", valorCadaTest);
    }

    protected void checkDataInSecondRow(double valorCadaTest) {
        //11 tests
        AssertionError toThrow = null;
        AssertionError error = null;
        double expDouble;
        String expString;
        String cellId;
        this.checkDoubleInCell("A2", 2.0, valorCadaTest);
        this.checkDoubleInCell("B2", 10.0, valorCadaTest);
        this.checkEmptyCell("C2", valorCadaTest);
        this.checkEmptyCell("D2", valorCadaTest);
        this.checkEmptyCell("E2", valorCadaTest);
        this.checkEmptyCell("F2", valorCadaTest);
        this.checkEmptyCell("G2", valorCadaTest);
        this.checkEmptyCell("H2", valorCadaTest);
        this.checkEmptyCell("I2", valorCadaTest);
        this.checkEmptyCell("J2", valorCadaTest);
        this.checkEmptyCell("K2", valorCadaTest);
    }
    
    protected void  checkDataInThirdRow(double valorCadaTest){
        AssertionError toThrow = null;
        AssertionError error = null;
        double expDouble;
        String expString;
        String cellId;
        this.checkDoubleInCell("A3", 3.0, valorCadaTest);
        this.checkEmptyCell("B3", valorCadaTest);
        this.checkDoubleInCell("C3", 30.0, valorCadaTest);
        this.checkEmptyCell("D3", valorCadaTest);
        this.checkEmptyCell("E3", valorCadaTest);
        this.checkEmptyCell("F3", valorCadaTest);
        this.checkEmptyCell("G3", valorCadaTest);
        this.checkEmptyCell("H3", valorCadaTest);
        this.checkEmptyCell("I3", valorCadaTest);
        this.checkEmptyCell("J3", valorCadaTest);
        this.checkEmptyCell("K3", valorCadaTest);
        
    }

    protected void  checkDataInFourthRow(double valorCadaTest){
        AssertionError toThrow = null;
        AssertionError error = null;
        double expDouble;
        String expString;
        String cellId;
        this.checkDoubleInCell("A4", 4.0, valorCadaTest);
        this.checkEmptyCell("B4", valorCadaTest);
        this.checkEmptyCell("C4", valorCadaTest);
        this.checkDoubleInCell("D4", 8.0, valorCadaTest);
        this.checkEmptyCell("E4", valorCadaTest);
        this.checkEmptyCell("F4", valorCadaTest);
        this.checkEmptyCell("G4", valorCadaTest);
        this.checkEmptyCell("H4", valorCadaTest);
        this.checkEmptyCell("I4", valorCadaTest);
        this.checkEmptyCell("J4", valorCadaTest);
        this.checkEmptyCell("K4", valorCadaTest);
        
    }  
    
    protected void  checkDataInFifthRow(double valorCadaTest){
        AssertionError toThrow = null;
        AssertionError error = null;
        double expDouble;
        String expString;
        String cellId;
        this.checkDoubleInCell("A5", 5.0, valorCadaTest);
        this.checkEmptyCell("B5", valorCadaTest);
        this.checkEmptyCell("C5", valorCadaTest);
        this.checkEmptyCell("D5", valorCadaTest);
        this.checkDoubleInCell("E5", 20.0, valorCadaTest);
        this.checkEmptyCell("F5", valorCadaTest);
        this.checkEmptyCell("G5", valorCadaTest);
        this.checkEmptyCell("H5", valorCadaTest);
        this.checkEmptyCell("I5", valorCadaTest);
        this.checkEmptyCell("J5", valorCadaTest);
        this.checkEmptyCell("K5", valorCadaTest);
    }  
    
    protected void  checkDataInSixthRow(double valorCadaTest){
        AssertionError toThrow = null;
        AssertionError error = null;
        double expDouble;
        String expString;
        String cellId;
        this.checkDoubleInCell("A6", 6.0, valorCadaTest);
        this.checkEmptyCell("B6", valorCadaTest);
        this.checkEmptyCell("C6", valorCadaTest);
        this.checkEmptyCell("D6", valorCadaTest);
        this.checkEmptyCell("E6", valorCadaTest);
        this.checkDoubleInCell("F6", 40.0, valorCadaTest);
        this.checkEmptyCell("G6", valorCadaTest);
        this.checkEmptyCell("H6", valorCadaTest);
        this.checkEmptyCell("I6", valorCadaTest);
        this.checkEmptyCell("J6", valorCadaTest);
        this.checkEmptyCell("K6", valorCadaTest);
        
    }  
    
    protected void  checkDataInSeventhRow(double valorCadaTest){
        AssertionError toThrow = null;
        AssertionError error = null;
        double expDouble;
        String expString;
        String cellId;
        this.checkEmptyCell("A7", valorCadaTest);
        this.checkEmptyCell("B7", valorCadaTest);
        this.checkEmptyCell("C7", valorCadaTest);
        this.checkEmptyCell("D7", valorCadaTest);
        this.checkEmptyCell("E7", valorCadaTest);
        this.checkEmptyCell("F7", valorCadaTest);
        this.checkEmptyCell("G7", valorCadaTest);
        this.checkEmptyCell("H7", valorCadaTest);
        this.checkEmptyCell("I7", valorCadaTest);
        this.checkEmptyCell("J7", valorCadaTest);
        this.checkEmptyCell("K7", valorCadaTest);
        
    }  

    protected void checkEmptyCell(String cellId, double valorCadaTest) {
        try {
            System.out.println("\tChecking " + cellId + ":");
            String expString = "";
            String val = this.instance.getCellContentAsString(cellId);
            this.sAssertEquals(expString, val, valorCadaTest,
                    "Error. The content of cell " + cellId + " should be "
                    + expString + ", instead it is " + val);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }

    }

    protected void checkDoubleInCell(String cellId, double expDouble, double valorCadaTest) {
        try {
            System.out.println("\tChecking " + cellId + ":");
            double val = this.instance.getCellContentAsDouble(cellId);
            this.sAssertEquals(expDouble, val, 0.0001, valorCadaTest,
                    "Error. The content of cell " + cellId + " should be "
                    + expDouble + ", instead it is " + val);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
    }

    protected void checkStringFromFormulaInCell(String cellId, String expString, double valorCadaTest) {
        try {
            System.out.println("\tChecking " + cellId + ":");
            String val = this.instance.getCellFormulaExpression(cellId);
            this.sAssertEquals(expString, val, valorCadaTest,
                    "Error. The content of cell " + cellId + " should be "
                    + expString + ", instead it is " + val);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
    }
}
