/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
// CHANGE THE PACKAGE AT YOUR CONVENIENCE
package edu.upc.etsetb.arqsoft.spreadsheet.marker;

// CHANGE THESE IMPORTS AS PER YOUR OWN PACKAGES
import edu.upc.ac.corrector.SuperClassForTests;
import edu.upc.arqsoft.marker.TestAll;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ISpreadsheetControllerForChecker;
import edu.upc.etsetb.arqsoft.spreadsheet.usecases.marker.ISpreadsheetFactoryForChecker;
import java.util.ArrayList;
import java.util.Map;
import org.junit.After;

// KEEP THESE IMPORTS (for JUnit 4.12)
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DependentCellsTest extends SuperClassForTests {

    private static int numErrorsBefore;

    private static int numInstances = 0;

    private static Map<String, Double> nota;

    private ISpreadsheetControllerForChecker instance;

    public DependentCellsTest() {
        super();
        numInstances++;
        if (numInstances == 1) {
            numErrorsBefore = SuperClassForTests.indErrors.size();
        }
        if (DependentCellsTest.indErrors == null) {
            DependentCellsTest.indErrors = new ArrayList<>();
        }
        this.instance = ISpreadsheetFactoryForChecker
                .createSpreadsheetController();
        try {
            instance.setCellContent("A1", "1");
            instance.setCellContent("A2", "2");
            instance.setCellContent("A3", "3");
            instance.setCellContent("A4", "4");
            instance.setCellContent("A5", "5");
            instance.setCellContent("A6", "6");
            instance.setCellContent("A7", "7");
            instance.setCellContent("A8", "8");
            instance.setCellContent("A9", "9");
            instance.setCellContent("A10", "10");
            instance.setCellContent("A11", "11");
            instance.setCellContent("A12", "12");
            instance.setCellContent("A13", "13");
            instance.setCellContent("A14", "14");
            instance.setCellContent("A15", "15");
            instance.setCellContent("A16", "16");
            instance.setCellContent("A17", "17");
            instance.setCellContent("A18", "18");
            instance.setCellContent("A19", "19");
            instance.setCellContent("A20", "20");
            instance.setCellContent("A21", "21");
            instance.setCellContent("A22", "22");
            instance.setCellContent("A23", "23");
            //
            //Now set formulas that depend on some of the cells above
            //
            instance.setCellContent("B1", "=A1+2-A2");
            instance.setCellContent("B2", "=1+SUMA(A3;A4;A5)");
            instance.setCellContent("C1", "=2+SUMA(A6:A10)");
        } catch (Exception ex) {
            System.out.println("An error has occurred while trying to set either "
                    + "a numerical or a text content in one cell. You should "
                    + "review your code as this should not happen. Details "
                    + "of the exception follow: " + ex.getMessage());

        }
    }

    @BeforeClass
    public static void setUpClass() {
        nota = TestAll.notas;
    }

    @AfterClass
    public static void tearDownClass() {
        showErrors(SuperClassForTests.indErrors, "DependentCellsTest");
        nota.put("DependentCellsTest", puntosTotales);
        puntosTotales = 0;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_01_DependentCellDirectDependency() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 2.5;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nChecking the proper update of cells that contain "
                + "formulas that contain an operand that is a reference to the "
                + "cell whose content is modified. Total value (over 10): " + valorTotal);
        try {
            this.printlnAlways("\n\tCase 1: modifying one cell that is "
                    + "directly referenced as an operand in the formula: " + valorTotal * 0.5);
            double content = this.instance.getCellContentAsDouble("B1");
            error = this.sAssertEquals(1, content, 0.00001, 0, "The cell "
                    + "should contain the number: 1 -result of formula =A1+2-A2, when "
                    + "A1=1 and A2=2- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
            instance.setCellContent("A1", "2");
            content = this.instance.getCellContentAsDouble("B1");
            error = this.sAssertEquals(2, content, 0.00001, valorTotal * 0.5, "The cell "
                    + "should contain the number: 2 -result of formula =A1+2-A2, when "
                    + "A1=2 and A2=2- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        try {
            this.printlnAlways("\n\tCase 2: modifying a second cell that is "
                    + "directly referenced as an operand in the formula: " + valorTotal * 0.5);
            double content = this.instance.getCellContentAsDouble("B1");
            instance.setCellContent("A2", "4");
            content = this.instance.getCellContentAsDouble("B1");
            error = this.sAssertEquals(0, content, 0.00001, valorTotal * 0.5, "The cell "
                    + "should contain the number: 0 -result of formula =A1+2-A2, when "
                    + "A1=2 and A2=4- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        //AÑADIR SIEMPRE PARA ACUMULAR LOS PUNTOS
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_02_DependentCellReferencedAsFunctionArgument() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 3.5;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nChecking the proper update of cells that contain "
                + "formulas that contain a function with an argument that is a reference to the "
                + "cell whose content is modified. Total value (over 10): " + valorTotal);
        try {
            this.printlnAlways("\n\tCase 1: modifying one cell whose reference is "
                    + "one of the arguments of a function in another cell: " + valorTotal * 0.5);
            double content = this.instance.getCellContentAsDouble("B2");
            error = this.sAssertEquals(13, content, 0.00001, valorTotal * 0.25, "The cell "
                    + "should contain the number: 13 -result of formula =1+SUMA(A3;A4;A5), when "
                    + "A3=3, A4=4 , and A5=5- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
            instance.setCellContent("A3", "4");
            content = this.instance.getCellContentAsDouble("B2");
            error = this.sAssertEquals(14, content, 0.00001, valorTotal * 0.25, "The cell "
                    + "should contain the number: 14 -result of formula =1+SUMA(A3;A4;A5), when "
                    + "A3=4, A4=4 , and A5=5- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        try {
            this.printlnAlways("\n\tCase 2: modifying a second cell whose reference is "
                    + "one of the arguments of a function in another cell: " + valorTotal * 0.5);
            instance.setCellContent("A4", "5");
            double content = this.instance.getCellContentAsDouble("B2");
            error = this.sAssertEquals(15, content, 0.00001, valorTotal * 0.5, "The cell "
                    + "should contain the number: 15 -result of formula =1+SUMA(A3;A4;A5), when "
                    + "A3=4, A4=5 , and A5=5- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        //AÑADIR SIEMPRE PARA ACUMULAR LOS PUNTOS
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_03_DependentCellReferencedAsFunctionRangeArgument() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 4;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nChecking the proper update of cells that contain "
                + "formulas that contain a function with an argument that is a range "
                + "that contains the cell whose content is modified. Total value (over 10): " + valorTotal);
        try {
            this.printlnAlways("\n\tCase 1: modifying one cell that appears in "
                    + "the range that is one of the arguments of a function in another cell: " + valorTotal * 0.5);
            double content = this.instance.getCellContentAsDouble("C1");
            error = this.sAssertEquals(42, content, 0.00001, 0, "The cell "
                    + "should contain the number: 42 -result of formula =2+SUMA(A6:A10), when "
                    + "A6=6, A7=7, A8=8, A9=9, and A10=10- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
            instance.setCellContent("A6", "7");
            content = this.instance.getCellContentAsDouble("C1");
            error = this.sAssertEquals(43, content, 0.00001, valorTotal * 0.5, "The cell "
                    + "should contain the number: 43 -result of formula =2+SUMA(A6:A10), when "
                    + "A6=7, A7=7, A8=8, A9=9, and A10=10- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        try {
            this.printlnAlways("\n\tCase 2: modifying a second cell that appears in "
                    + "the range that is one of the arguments of a function in another cell: " + valorTotal * 0.5);
            instance.setCellContent("A7", "8");
            double content = this.instance.getCellContentAsDouble("C1");
            error = this.sAssertEquals(44, content, 0.00001, valorTotal * 0.5, "The cell "
                    + "should contain the number: 44 -result of formula =2+SUMA(A6:A10), when "
                    + "A6=7, A7=8, A8=8, A9=9, and A10=10- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        //AÑADIR SIEMPRE PARA ACUMULAR LOS PUNTOS
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

}
