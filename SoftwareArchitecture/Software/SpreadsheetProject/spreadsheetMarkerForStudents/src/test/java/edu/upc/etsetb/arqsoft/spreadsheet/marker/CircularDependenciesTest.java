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
import edu.upc.etsetb.arqsoft.spreadsheet.entities.CircularDependencyException;
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
public class CircularDependenciesTest extends SuperClassForTests {

    private static int numErrorsBefore;

    private static int numInstances = 0;

    private static Map<String, Double> nota;

    private ISpreadsheetControllerForChecker instance;

    public CircularDependenciesTest() {
        super();
        numInstances++;
        if (numInstances == 1) {
            numErrorsBefore = SuperClassForTests.indErrors.size();
        }
        if (CircularDependenciesTest.indErrors == null) {
            CircularDependenciesTest.indErrors = new ArrayList<>();
        }
        this.instance = ISpreadsheetFactoryForChecker
                .createSpreadsheetController();
        try {
            instance.setCellContent("A6", "1");
            instance.setCellContent("A7", "2");
            instance.setCellContent("A8", "3");
            instance.setCellContent("A9", "4");
            instance.setCellContent("A10", "5");
            instance.setCellContent("A11", "6");
            instance.setCellContent("A12", "7");
            instance.setCellContent("A13", "8");
            instance.setCellContent("A14", "9");
            instance.setCellContent("A1", "=A2+A3+A4+A5");
            instance.setCellContent("A2", "=A6+A7+A8");
            instance.setCellContent("A3", "=A9+A10+A11");
            instance.setCellContent("A4", "=A12+A13");
            instance.setCellContent("A5", "=A14+1");
        } catch (Exception ex) {
            System.out.println("An error has occurred while trying to set either "
                    + "a numerical, a text, or a formula content in one cell. You should "
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
        showErrors(SuperClassForTests.indErrors, "CircularDependenciesTest");
        nota.put("CircularDependenciesTest", puntosTotales);
        puntosTotales = 0;
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void test_01_DirectCircularDependency() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 4;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nChecking that the program detects a direct "
                + "circular dependency: " + valorTotal);
        try {
            double content = this.instance.getCellContentAsDouble("A2");
            error = this.sAssertEquals(6, content, 0.00001, 0, "The cell A2"
                    + "should contain the number: 6 -result of formula =A6+A7+A8, "
                    + "initialized to the values indicated in the constructor. "
                    + "Instead, it contains the value " + content);
            toThrow = toThrow(error, toThrow);

            try {
                instance.setCellContent("A2", "=A1+A7+A8");
                content = this.instance.getCellContentAsDouble("B1");
                error = this.sAssertTrue(false, valorTotal, "Cell A1 contains "
                        + "the formula =A2+A3+A4+A5, and now a try has been done to "
                        + "set cell A2 to =A1+A7+A8. This introduces a direct circular "
                        + "dependency that your program should have detected and an exception "
                        + "should have been trhown. Instead, no exception has been thrown.");
                toThrow = toThrow(error, toThrow);
            } catch (CircularDependencyException ex) {
                error = this.sAssertTrue(true, valorTotal, "");
                toThrow = toThrow(error, toThrow);
            } catch (Throwable ex) {
                error = this.sAssertTrue(false, valorTotal, "Cell A1 contains "
                        + "the formula =A2+A3+A4+A5, and now a try has been done to "
                        + "set cell A2 to =A1+A7+A8. This introduces a direct circular "
                        + "dependency that your program should have detected and the "
                        + "corresponding type of exception "
                        + "should have been trhown. Instead, a " + ex.getClass().getName()
                        + " has been thrown. You should review your code");
                toThrow = toThrow(error, toThrow);
//                ex.printStackTrace();
            }
        } catch (Exception ex) {
            System.out.println("*****Some error has been thrown by your code. Take "
                    + "a look to the trace in order to find out what has happened and "
                    + "fix it.");
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
    public void test_02_IndirectCircularDependency() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 6;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nChecking that the program properly detects an indirect "
                + "circular dependency: " + valorTotal);
        try {
            this.printlnAlways("\tA change is introduced in a cell that does not "
                    + "introduce a circular dependency");
            try {
                instance.setCellContent("A11", "=A2+A5");
                double content = this.instance.getCellContentAsDouble("B1");
                error = this.sAssertTrue(true, valorTotal * 0.3, "");
                toThrow = toThrow(error, toThrow);
            } catch (CircularDependencyException ex) {
                error = this.sAssertTrue(false, 0, "Cell A2 contains "
                        + "the formula =A6+A7+A8, and now a try has been done to "
                        + "set cell A11 to =A2+A5. This does not introduce any circular "
                        + "dependency, BUT your program has thrown an exception notifying "
                        + "a circular dependency");
                toThrow = toThrow(error, toThrow);
            } catch (Throwable ex) {
                error = this.sAssertTrue(false, 0, "Cell A2 contains "
                        + "the formula =A6+A7+A8, and now a try has been done to "
                        + "set cell A11 to =A2+A5. This does not introduce any circular "
                        + "dependency, and everything should have gone fine, BUT "
                        + "your program has thrown an exception " + ex.getClass().getName()
                        + ". You should review your code");
                toThrow = toThrow(error, toThrow);
                //ex.printStackTrace();
            }
        } catch (Exception ex) {
            this.printlnAlways("*** An exception has been thrown by your code. Very likely "
                    + "it is because your code does detect circular dependencies when there is not"
                    + "any. You should review your code.");
            ex.printStackTrace();
        }
        try {
            try {
                this.printlnAlways("\n\tCase 2: modifying a cell in such a way that "
                        + "it introduces an indirect circular dependency: " + valorTotal * 0.35);
                instance.setCellContent("A11", "=A1+5");
                error = this.sAssertTrue(false, valorTotal * 0.35, "Cell A1 contains "
                        + "the formula =A2+A3+A4+A5, cell A3 contains the formula "
                        + "=A9+A10+A11, and now a try has been done to "
                        + "set cell A11 to =A1+5. This introduces any circular "
                        + "dependency, BUT your program has not thrown an exception notifying "
                        + "this circular dependency");
                toThrow = toThrow(error, toThrow);
            } catch (CircularDependencyException ex) {
                error = this.sAssertTrue(true, valorTotal * 0.35, "");
                toThrow = toThrow(error, toThrow);
            } catch (Throwable ex) {
                error = this.sAssertTrue(true, 0, "Cell A1 contains "
                        + "the formula =A2+A3+A4+A5, cell A3 contains the formula "
                        + "=A9+A10+A11, and now a try has been done to "
                        + "set cell A11 to =A1+5. This introduces any circular "
                        + "dependency, BUT your program has not thrown an exception notifying "
                        + "this circular dependency. Instead it has thrown a "
                        + ex.getClass().getName() + ". Review your code");
                toThrow = toThrow(error, toThrow);
//                ex.printStackTrace();
            }
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
        try {
            try {
                this.printlnAlways("\n\tCase 3: modifying another cell in such a way that "
                        + "it introduces an indirect circular dependency: " + valorTotal * 0.35);
                instance.setCellContent("A6", "=A1+5");
                error = this.sAssertTrue(false, 0, "Cell A1 contains "
                        + "the formula =A2+A3+A4+A5, cell A2 contains the formula "
                        + "=A6+A7+A8, and now a try has been done to "
                        + "set cell A6 to =A1+5. This introduces a circular "
                        + "dependency, BUT your program has not thrown an exception notifying "
                        + "this circular dependency");
                toThrow = toThrow(error, toThrow);
            } catch (CircularDependencyException ex) {
                error = this.sAssertTrue(true, valorTotal * 0.35, "");
                toThrow = toThrow(error, toThrow);
            } catch (Throwable ex) {
                error = this.sAssertTrue(false, 0, "Cell A1 contains "
                        + "the formula =A2+A3+A4+A5, cell A2 contains the formula "
                        + "=A6+A7+A8, and now a try has been done to "
                        + "set cell A6 to =A1+5. This introduces a circular "
                        + "dependency, BUT your program has not thrown an exception notifying "
                        + "this circular dependency. Instead it has thrown a "
                        + ex.getClass().getName() + ". Review your code");
                toThrow = toThrow(error, toThrow);
//                ex.printStackTrace();
            }
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
