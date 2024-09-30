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
import org.junit.Test;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
public class NumberContentTest extends SuperClassForTests {

    private static int numErrorsBefore;

    private static int numInstances = 0;

    private static Map<String, Double> nota;

    private ISpreadsheetControllerForChecker instance;

    public NumberContentTest(){
        super();
        numInstances++;
        if (numInstances == 1) {
            numErrorsBefore = SuperClassForTests.indErrors.size();
        }
        this.instance = ISpreadsheetFactoryForChecker
                .createSpreadsheetController();
    }

    @BeforeClass
    public static void setUpClass() {
        nota = TestAll.notas;
    }

    @AfterClass
    public static void tearDownClass() {
        showErrors(indErrors, "NumberContentTest");
        nota.put("NumberContentTest", puntosTotales);
        puntosTotales = 0;
    }

    @Test
    public void testSetCellContent_NumberContent() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 10;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nSpreadsheetControllerForChecker::"
                + "testSetCellContent() with numerical content. Value: " + valorTotal);
        try {

            instance.setCellContent("A1", "27.5");
            double content = this.instance.getCellContentAsDouble("A1");
            error = this.sAssertEquals(27.5, content, 0.00001, valorTotal, "The cell "
                    + "should contain the number: 27.5 with a margin of 0.00001. "
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
