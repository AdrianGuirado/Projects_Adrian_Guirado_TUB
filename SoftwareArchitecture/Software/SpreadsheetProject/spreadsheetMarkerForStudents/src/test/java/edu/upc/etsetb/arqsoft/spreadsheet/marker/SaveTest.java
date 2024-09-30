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
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

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
public class SaveTest extends SuperClassForTests {

    private static int numErrorsBefore;

    private static int numInstances = 0;

    private static Map<String, Double> nota;

    private ISpreadsheetControllerForChecker instance;

    public SaveTest() {
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
            instance.setCellContent("B1", "=1+2");
            instance.setCellContent("B2", "10");
            instance.setCellContent("C1", "=10/(2+3)");
            instance.setCellContent("C3", "30");
            instance.setCellContent("D1", "=A1*10-5");
            instance.setCellContent("D4", "8");
            instance.setCellContent("E1", "=(A5*4)/(A2+A2)");
            instance.setCellContent("E5", "20");
            instance.setCellContent("F6", "40");
            instance.setCellContent("F1", "=100/(A5+(A5*A5/5))");
            instance.setCellContent("G1", "=(A5*4)/(A2+A2)+SUMA(1;2;3;4;5)");
            instance.setCellContent("H1", "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5)");
            instance.setCellContent("I1", "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12)");
            instance.setCellContent("J1", "=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12;MIN(A13:A20))");
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
        showErrors(SuperClassForTests.indErrors, "SaveTest");
        nota.put("SaveTest", puntosTotales);
        puntosTotales = 0;
    }

    @Test
    public void test_01_save() throws Exception {
        //PONER EN TODOS: ES EL VALOR DEL MÉTODO QUE SE PRUEBA
        double valorTotal = 10;
        //PONER EN TODOS: PARA GESTIONAR LOS ERRORES
        AssertionError toThrow = null;
        AssertionError error = null;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: UN MENSAJE QUE DIGA QUÉ MÉTODO SE PRUEBA Y
        //QUÉ VALOR TIENE
        this.printlnAlways("\nTesting save spreadsheet. "
                + "Total value (over 10): " + valorTotal);
        try {
            this.printlnAlways("\nThe spreadsheet shall be saved in file . "
                    + "marker_save_test.s2v" + valorTotal);
            instance.saveSpreadSheetToFile("marker_save_test.s2v");
            this.checkS2VFileContents("marker_save_test.s2v", valorTotal);
        } catch (Exception ex) {
            this.printlnAlways("*** Se ha capturado una excepción que probablemente "
                    + "ha sido lanzada por tu código. Mira la traza para "
                    + "detectar en qué punto ha sido creada y lanzada...");
            ex.printStackTrace();
        }
    }

    private void checkS2VFileContents(String nameInNetBeansFolder, double puntosArg)
            throws Exception {
        AssertionError toThrow = null;
        AssertionError error = null;
        double puntosAntes = puntosTotales;
        //PONER EN TODOS: SON LOS PUNTOS ASIGNADOS ANTES DE EJECUTAR ESTE TEST       

        Scanner reader = new Scanner(new FileInputStream(nameInNetBeansFolder));
        int numLineas = 0;
        ArrayList<String> lineas = new ArrayList<>();
        while (reader.hasNextLine() && numLineas != 6) {
            numLineas++;
            lineas.add(reader.nextLine());
        }
        this.println("\tTesting the number of lines corresponding to non-empty "
                + "rows of the spreadsheet");
        //Check the number of rows saved
        if (!reader.hasNextLine()) {
            if (numLineas == 6) {
                this.sAssertTrue(true, puntosArg * 0.05, "");
            } else if (numLineas < 6) {
                this.sAssertTrue(false, 0.0, "Error detected in the sv2 file. The spreadsheet "
                        + "should contain non empty cells in the first 6 rows. However, "
                        + "the number of lines in the file are lower than 6");
            }
        } else if (!RestOfFileHasNonEmptyCells(reader)) {
            this.println("\tChecking that the number lines "
                    + "representing non-empty rows is 6");
            this.sAssertTrue(true, puntosArg * 0.05, "");
        } else {
            this.sAssertTrue(false, 0.0, "Error detected in the sv2 file. The spreadsheet "
                    + "only contained non empty cells in the first 6 rows. However, "
                    + "the number of lines read are higher than 6, and some of the following rows "
                    + "there are cells that are not empty");
        }
        this.acumula(puntos);
        puntos(puntosAntes);
        //Check contents of every row
        if (numLineas >= 1) {
            this.checkRow1(lineas.get(0), 0.7 * puntosArg);
        }
        if (numLineas >= 2) {
            this.checkRow2(lineas.get(1), 0.05 * puntosArg);
        }
        if (numLineas >= 3) {
            this.checkRow3(lineas.get(2), 0.05 * puntosArg);
        }
        if (numLineas >= 4) {
            this.checkRow4(lineas.get(3), 0.05 * puntosArg);
        }
        if (numLineas >= 5) {
            this.checkRow5(lineas.get(4), 0.05 * puntosArg);
        }
        if (numLineas >= 6) {
            this.checkRow6(lineas.get(5), 0.05 * puntosArg);
        }

    }

    private void checkRow1(String line, double puntosArg) {
        if (line == null || line.isEmpty()) {
            this.sAssertTrue(false, 0, "Line 1 of the file is empty. It should not be the case");
            return;
        }
        String linea = line.replaceAll("\\s+", "");
        String[] parts = linea.split(";");
        AssertionError toThrow = null;
        AssertionError error = null;
        double puntosAntes = puntosTotales;
        try {
            this.println("\tChecking line 1 of file");
            //Check contents of A1
            error = this.sAssertEquals("1", parts[0], 0.09090909090 * puntosArg, "A1 contained \'1\', "
                    + "but the file contains '" + parts[0] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of B1
            error = this.sAssertEquals("=1+2", parts[1], 0.09090909090 * puntosArg,
                    "B1 contained \'=1+2\', "
                    + "but the file contains '" + parts[1] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of C1
            error = this.sAssertEquals("=10/(2+3)", parts[2], 0.09090909090 * puntosArg, "C1 contained \'=10/(2+3)\', "
                    + "but the file contains '" + parts[2] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of D1
            error = this.sAssertEquals("=A1*10-5", parts[3], 0.09090909090 * puntosArg,
                    "D1 contained \'=A1*10-5\', "
                    + "but the file contains '" + parts[3] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of E1
            error = this.sAssertEquals("=(A5*4)/(A2+A2)", parts[4], 0.09090909090 * puntosArg,
                    "E1 contained \'=(A5*4)/(A2+A2)\', "
                    + "but the file contains '" + parts[4] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of F1
            error = this.sAssertEquals("=100/(A5+(A5*A5/5))", parts[5], 0.09090909090 * puntosArg,
                    "F1 contained \'=100/(A5+(A5*A5/5))\', "
                    + "but the file contains '" + parts[5] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of G1
            error = this.sAssertEquals("=(A5*4)/(A2+A2)+SUMA(1,2,3,4,5)",
                    parts[6], 0.09090909090 * puntosArg, "G1 contained "
                    + "\'=(A5*4)/(A2+A2)+SUMA(1;2;3;4;5)\', "
                    + "but the file contains '" + parts[6] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of H1
            error = this.sAssertEquals("=(A5*4)/(A2+A2)+SUMA(A1,A2,3,4,5)", parts[7],
                    0.09090909090 * puntosArg,
                    "H1 contained \'=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5)\', "
                    + "but the file contains '" + parts[7] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of I1
            error = this.sAssertEquals("=(A5*4)/(A2+A2)+SUMA(A1,A2,3,4,5,A6:A12)",
                    parts[8], 0.09090909090 * puntosArg,
                    "I1 contained \'=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12)\', "
                    + "but the file contains '" + parts[8] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of J1
            error = this.sAssertEquals("=(A5*4)/(A2+A2)+SUMA(A1,A2,3,4,5,A6:A12,MIN(A13:A20))",
                    parts[9], 0.09090909090 * puntosArg,
                    "J1 contained \'=(A5*4)/(A2+A2)+SUMA(A1;A2;3;4;5;A6:A12;MIN(A13:A20))\', "
                    + "but the file contains '" + parts[9] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check that there is not any other empty cell in further columns
            if (!this.lineHasNonEmptyCellsFromCellOnwards(parts, 10)) {
                this.sAssertTrue(true, 0.09090909090 * puntosArg, "");
            } else {
                this.sAssertTrue(false, 0, "Last non empty cell in row 1 should be "
                        + "in column 10; however there are non-empty cells in higher columns");
            }

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

    private void checkRow2(String line, double puntosArg) {
        if (line == null || line.isEmpty()) {
            this.sAssertTrue(false, 0, "Line 1 of the file is empty. It should not be the case");
            return;
        }
        String linea = line.replaceAll("\\s+", "");
        String[] parts = linea.split(";");
        AssertionError toThrow = null;
        AssertionError error = null;
        double puntosAntes = puntosTotales;
        try {
            this.println("\tChecking line 2 of file");
            //Check contents of A2
            error = this.sAssertEquals("2", parts[0], 0.333333 * puntosArg, "A2 contained \'2\', "
                    + "but the file contains '" + parts[0] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of B2
            error = this.sAssertEquals("10", parts[1], 0.333333 * puntosArg,
                    "B2 contained \'10\', "
                    + "but the file contains '" + parts[1] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check that there is not any other empty cell in further columns
            if (!this.lineHasNonEmptyCellsFromCellOnwards(parts, 2)) {
                this.sAssertTrue(true, 0.333333 * puntosArg, "");
            } else {
                this.sAssertTrue(false, 0, "Last non empty cell in row 2 should be "
                        + "in column 2; however there are non-empty cells in higher columns");
            }

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

    private void checkRow3(String line, double puntosArg) {
        if (line == null || line.isEmpty()) {
            this.sAssertTrue(false, 0, "Line 1 of the file is empty. It should not be the case");
            return;
        }
        String linea = line.replaceAll("\\s+", "");
        String[] parts = linea.split(";");
        AssertionError toThrow = null;
        AssertionError error = null;
        double puntosAntes = puntosTotales;
        try {
            this.println("\tChecking line 3 of file");
            //Check contents of A3
            error = this.sAssertEquals("3", parts[0], 0.25 * puntosArg,
                    "A3 contained \'3\', "
                    + "but the file contains '" + parts[0] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of B3
            error = this.sAssertEquals("", parts[1], 0.25 * puntosArg,
                    "B3 was empty, "
                    + "but the file contains '" + parts[1] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of C3
            error = this.sAssertEquals("30", parts[2], 0.25 * puntosArg,
                    "C3 contained \'30\', "
                    + "but the file contains '" + parts[2] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check that there is not any other empty cell in further columns
            if (!this.lineHasNonEmptyCellsFromCellOnwards(parts, 3)) {
                this.sAssertTrue(true, 0.25 * puntosArg, "");
            } else {
                this.sAssertTrue(false, 0, "Last non empty cell in row 3 should be "
                        + "in column 3; however there are non-empty cells in higher columns");
            }
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

    private void checkRow4(String line, double puntosArg) {
        if (line == null || line.isEmpty()) {
            this.sAssertTrue(false, 0, "Line 1 of the file is empty. It should not be the case");
            return;
        }
        String linea = line.replaceAll("\\s+", "");
        String[] parts = linea.split(";");
        AssertionError toThrow = null;
        AssertionError error = null;
        double puntosAntes = puntosTotales;
        try {
            this.println("\tChecking line 4 of file");
            //Check contents of A4
            error = this.sAssertEquals("4", parts[0], 0.2 * puntosArg,
                    "A4 contained \'4\', "
                    + "but the file contains '" + parts[0] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of B4
            error = this.sAssertEquals("", parts[1], 0.2 * puntosArg,
                    "B4 was empty, "
                    + "but the file contains '" + parts[1] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of C4
            error = this.sAssertEquals("", parts[2], 0.2 * puntosArg,
                    "C4 was empty, "
                    + "but the file contains '" + parts[2] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of D4
            error = this.sAssertEquals("8", parts[3], 0.2 * puntosArg,
                    "D4 contained \'8\', "
                    + "but the file contains '" + parts[3] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check that there is not any other empty cell in further columns
            if (!this.lineHasNonEmptyCellsFromCellOnwards(parts, 4)) {
                this.sAssertTrue(true, 0.2 * puntosArg, "");
            } else {
                this.sAssertTrue(false, 0, "Last non empty cell in row 4 should be "
                        + "in column 4; however there are non-empty cells in higher columns");
            }
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

    private void checkRow5(String line, double puntosArg) {
        if (line == null || line.isEmpty()) {
            this.sAssertTrue(false, 0, "Line 1 of the file is empty. It should not be the case");
            return;
        }
        String linea = line.replaceAll("\\s+", "");
        String[] parts = linea.split(";");
        AssertionError toThrow = null;
        AssertionError error = null;
        double puntosAntes = puntosTotales;
        try {
            this.println("\tChecking line 5 of file");
            //Check contents of A5
            error = this.sAssertEquals("5", parts[0], 0.16666666 * puntosArg,
                    "A5 contained \'5\', "
                    + "but the file contains '" + parts[0] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of B5
            error = this.sAssertEquals("", parts[1], 0.16666666 * puntosArg,
                    "B5 was empty, "
                    + "but the file contains '" + parts[1] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of C5
            error = this.sAssertEquals("", parts[2], 0.16666666 * puntosArg,
                    "C5 was empty, "
                    + "but the file contains '" + parts[2] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of D5
            error = this.sAssertEquals("", parts[3], 0.16666666 * puntosArg,
                    "D5 was empty, "
                    + "but the file contains '" + parts[3] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of E5
            error = this.sAssertEquals("20", parts[4], 0.16666666 * puntosArg,
                    "E5 contains \'20\', "
                    + "but the file contains '" + parts[4] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check that there is not any other empty cell in further columns
            if (!this.lineHasNonEmptyCellsFromCellOnwards(parts, 5)) {
                this.sAssertTrue(true, 0.16666666 * puntosArg, "");
            } else {
                this.sAssertTrue(false, 0, "Last non empty cell in row 5 should be "
                        + "in column 5; however there are non-empty cells in higher columns");
            }
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

    private void checkRow6(String line, double puntosArg) {
        if (line == null || line.isEmpty()) {
            this.sAssertTrue(false, 0, "Line 1 of the file is empty. It should not be the case");
            return;
        }
        String linea = line.replaceAll("\\s+", "");
        String[] parts = linea.split(";");
        AssertionError toThrow = null;
        AssertionError error = null;
        double puntosAntes = puntosTotales;
        try {
            this.println("\tChecking line 6 of file");
            //Check contents of A6
            error = this.sAssertEquals("6", parts[0], 0.14285714 * puntosArg,
                    "A6 contained \'6\', "
                    + "but the file contains '" + parts[0] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of B6
            error = this.sAssertEquals("", parts[1], 0.14285714 * puntosArg,
                    "B6 was empty, "
                    + "but the file contains '" + parts[1] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of C6
            error = this.sAssertEquals("", parts[2], 0.14285714 * puntosArg,
                    "C6 was empty, "
                    + "but the file contains '" + parts[2] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of D6
            error = this.sAssertEquals("", parts[3], 0.14285714 * puntosArg,
                    "D6 was empty, "
                    + "but the file contains '" + parts[3] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of E6
            error = this.sAssertEquals("", parts[4], 0.14285714 * puntosArg,
                    "E6 was empty, "
                    + "but the file contains '" + parts[4] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check contents of F6
            error = this.sAssertEquals("40", parts[5], 0.14285714 * puntosArg,
                    "F6 contains \'40\', "
                    + "but the file contains '" + parts[5] + "\'");
            toThrow = toThrow(error, toThrow);
            //Check that there is not any other empty cell in further columns
            if (!this.lineHasNonEmptyCellsFromCellOnwards(parts, 6)) {
                this.sAssertTrue(true, 0.14285714 * puntosArg, "");
            } else {
                this.sAssertTrue(false, 0, "Last non empty cell in row 6 should be "
                        + "in column 6; however there are non-empty cells in higher columns");
            }

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

    private boolean lineHasNonEmptyCells(String line) {
        String[] parts = line.replaceAll("\\s+", "").split(";");
        for (int i = 0; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private boolean RestOfFileHasNonEmptyCells(Scanner reader) {
        while (reader.hasNextLine()) {
            if (lineHasNonEmptyCells(reader.nextLine())) {
                return true;
            }
        }
        return false;
    }

    private boolean lineHasNonEmptyCellsFromCellOnwards(String[] parts,
            int iOnwardsIncluded) {
        if (iOnwardsIncluded == parts.length) {
            return false;
        }
        for (int i = iOnwardsIncluded; i < parts.length; i++) {
            if (!parts[i].isEmpty()) {
                return true;
            }
        }
        return false;
    }

}
