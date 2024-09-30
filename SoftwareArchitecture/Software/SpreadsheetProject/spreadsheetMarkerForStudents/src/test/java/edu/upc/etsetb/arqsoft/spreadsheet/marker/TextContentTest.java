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
import java.util.List;
import java.util.Map;

// KEEP THESE IMPORTS (for JUnit 4.12)
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
public class TextContentTest extends SuperClassForTests {

    private static int numErrorsBefore;

    private static int numInstances = 0;

    private static List<AssertionError> indErrors;

    private static Map<String, Double> nota;

    private ISpreadsheetControllerForChecker instance;

    public TextContentTest(){
        super();
        numInstances++;
        if (numInstances == 1) {
            numErrorsBefore = SuperClassForTests.indErrors.size();
        }
        TextContentTest.indErrors = SuperClassForTests.indErrors;
        this.instance = ISpreadsheetFactoryForChecker
                .createSpreadsheetController();
    }

    @BeforeClass
    public static void setUpClass() {
        nota = TestAll.notas;
    }

    @AfterClass
    public static void tearDownClass() {
        showErrors(indErrors, "TextContentTest");
        nota.put("TextContentTest", puntosTotales);
        puntosTotales = 0;
    }

    @Test
    public void testSetCellContent_TextContent() throws Exception {
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
                + "testSetCellContent() with text content. Value: " + valorTotal);
        try {
            instance.setCellContent("C1", "This is a string");
            String content = this.instance.getCellContentAsString("C1");
            error = this.sAssertEquals("This is a string", content, valorTotal, "The cell "
                    + "should contain the string: \'This is a string\'. Instead "
                    + "it contains \'" + content + "\'");
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
