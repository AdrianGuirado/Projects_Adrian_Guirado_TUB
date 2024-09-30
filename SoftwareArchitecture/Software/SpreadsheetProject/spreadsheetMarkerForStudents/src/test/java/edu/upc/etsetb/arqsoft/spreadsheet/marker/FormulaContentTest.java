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
import java.util.Map;

// KEEP THESE IMPORTS (for JUnit 4.12)
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
public class FormulaContentTest extends SuperClassForTests {
   
    private static int numErrorsBefore ;

    private static int numInstances = 0;
    
    private static Map<String, Double> nota;

    private ISpreadsheetControllerForChecker instance;

    public FormulaContentTest() {
        super();
        numInstances++;
        if (numInstances == 1) {
            numErrorsBefore = SuperClassForTests.indErrors.size();
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
            instance.setCellContent("A24", "24");
            instance.setCellContent("A25", "This is a string");
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
        showErrors(SuperClassForTests.indErrors, "FormulaContentTest");
        nota.put("FormulaContentTest", puntosTotales);
        puntosTotales = 0;
    }

    @Test
    public void test_01_SetCellContent_FormulaOnlyNumbers() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 0.465116279069767;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with only numbers as operands in "
                + "a cell. Total value (over 10): " + valorTotal);
        try {
            this.printlnAlways("\n\tCase 1: a sum of two numbers. Value: " + valorTotal * 0.25);
            instance.setCellContent("B1", "=1+2");
            double content = this.instance.getCellContentAsDouble("B1");
            error = this.sAssertEquals(3, content, 0.00001, valorTotal * 0.25, "The cell "
                    + "should contain the number: 3 -result of formula =1+2- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        try {
            this.printlnAlways("\n\tCase 2: a substraction of two numbers. Value: " + valorTotal * 0.25);
            instance.setCellContent("B2", "=1-3");
            double content = this.instance.getCellContentAsDouble("B2");
            error = this.sAssertEquals(-2, content, 0.00001, valorTotal * 0.25, "The cell "
                    + "should contain the number: -2 with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        try {
            this.printlnAlways("\n\tCase 3: a multiplication of two numbers. Value: " + valorTotal * 0.25);
            instance.setCellContent("B3", "=2*3");
            double content = this.instance.getCellContentAsDouble("B3");
            error = this.sAssertEquals(6, content, 0.00001, valorTotal * 0.25, "The cell "
                    + "should contain the number: 6 with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        try {
            this.printlnAlways("\n\tCase 4: a division of two numbers. Value: " + valorTotal * 0.25);
            instance.setCellContent("B4", "=8/4");
            double content = this.instance.getCellContentAsDouble("B4");
            error = this.sAssertEquals(2, content, 0.00001, valorTotal * 0.25, "The cell "
                    + "should contain the number: 2 with a margin of 0.00001. "
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
    public void test_02_SetCellContent_FormulaNumbersAndOneLevelOfParenthesis() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 0.62015503875969;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with only numbers and one level "
                + "of parenthesis in a cell");
        try {
            instance.setCellContent("C1", "=10/(2+3)");
            double content = this.instance.getCellContentAsDouble("C1");
            error = this.sAssertEquals(2, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 2 -result of formula 10/(2+3)- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_03_SetCellContent_FormulaNumbersAndTwoLevelsOfParenthesis() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 0.775193798449612;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with only numbers and two levels "
                + "of parenthesis in a cell");
        try {
            instance.setCellContent("C2", "=100/(5+(25/5))");
            double content = this.instance.getCellContentAsDouble("C2");
            error = this.sAssertEquals(10, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 10 -result of formula 100/(5+(25/5))- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_04_SetCellContent_FormulaNumbersCellsRefs() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 0.775193798449612;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with numbers and references to cells "
                + "as operands");
        try {
            instance.setCellContent("D1", "=A1*10-5");
            double content = this.instance.getCellContentAsDouble("D1");
            error = this.sAssertEquals(5, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 5 -result of formula A1*10-5 - with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_05_SetCellContent_FormulaNumbersCellsRefsOneLevelParenthesis() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 0.852713178294574;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with numbers, references to cells, "
                + "and one level of parenthesis");
        try {
            instance.setCellContent("E1", "=(A5*4)/(A2+A2)");
            double content = this.instance.getCellContentAsDouble("E1");
            error = this.sAssertEquals(5, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 5 -result of formula =(A5*4)/(A2+A2)- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_06_SetCellContent_FormulaNumbersCellsRefsTwoLevelsParenthesis() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 1.0077519379845;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with numbers, references to cells, "
                + "and one level of parenthesis");
        try {
            instance.setCellContent("F1", "=100/(A5+(A5*A5/5))");
            double content = this.instance.getCellContentAsDouble("F1");
            error = this.sAssertEquals(10, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 10 -result of formula =100/(A5+(A5*A5/5))- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_07_SetCellContent_FormulaNumbersCellsRefsFunctionsNumbArgs() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 1.16279069767442;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with numbers, references to cells, "
                + "and functions that have only numbers as args");
        try {
            instance.setCellContent("G1", "=(A5*4)/(A2+A2)+SUMA(1;2;3;4;5)");
            double content = this.instance.getCellContentAsDouble("G1");
            error = this.sAssertEquals(20, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 20 -result of formula "
                    + "=(A5*4)/(A2+A2)+SUMA(1;2;3;4;5)- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_08_SetCellContent_FormulaNumbersCellsRefsFunctionsNumbCellRefsArgs() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 1.31782945736434;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with numbers, references to cells, "
                + "and functions that have numbers and references to cells as args");
        try {
            instance.setCellContent("H1", "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5)");
            double content = this.instance.getCellContentAsDouble("H1");
            error = this.sAssertEquals(20, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 20 -result of formula "
                    + "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5)- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_09_SetCellContent_FormulaNumbersCellsRefsFunctionsNumbCellRefsRangesArgs() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 1.47286821705426;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with numbers, references to cells, "
                + "and functions that have numbers,references to cells, and "
                + "ranges as args");
        try {
            instance.setCellContent("I1", "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12)");
            double content = this.instance.getCellContentAsDouble("I1");
            error = this.sAssertEquals(83, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 83 -result of formula "
                    + "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12)- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

    @Test
    public void test_10_SetCellContent_FormulaNumbersCellsRefsFunctionsNumbCellRefsRangesFuncsArgs() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 1.55038759689922;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSetting a formula with numbers, references to cells, "
                + "and functions that have numbers,references to cells, "
                + "ranges, and other functions as args");
        try {
            instance.setCellContent("J1", "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12;MIN(A13:A20))");
            double content = this.instance.getCellContentAsDouble("J1");
            error = this.sAssertEquals(96, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number 96 -result of formula "
                    + "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12;MIN(A13:A20))- with a margin of 0.00001. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        this.acumula(puntos);
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //AÑADIR SIEMPRE PARA MOSTRAR LOS PUNTOS OBTENIDOS Y LOS ACUMULADOS 
        //EN LA CLASE
        puntos(puntosAntes);
        //AÑADIR SIEMPRE PARA ENCADENAR MENSAJES DE ERROR
        throwIfAnError(toThrow);
    }

}
