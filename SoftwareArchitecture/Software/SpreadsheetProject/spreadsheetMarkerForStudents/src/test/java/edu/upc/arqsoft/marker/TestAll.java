/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.upc.arqsoft.marker;

import edu.upc.ac.corrector.SuperClassForTests;
import java.util.HashMap;
import java.util.Map;
import org.junit.rules.ErrorCollector;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

/**
 *
 * @author Juan Carlos Cruellas at Universidad Politécnica de Cataluña
 */
public class TestAll {

    private static String[] clases = {
        "TextContentTest", "NumberContentTest", "FormulaContentTest",
         "DependentCellsTest", "CircularDependenciesTest", "SaveTest",
        "LoadTest"};

    public static final double[] tantosPorCiento = {
        1.5, //TextContentTest
        1.5, //NumberContentTest
        64.5, //FormulaTest. Edition and computation of formulas
        12.5, //UpdateTest. Update of cells with formulas that depend on the modified cell
        7.5, //DependenciesTest. Circular dependencies
        5, //SaveTest
        5, //LoadTest
     };

    public static Map<String, Double> notas;
    public static Map<String, Double> porcentajes;

    public static ErrorCollector allCollector;

    static {
        notas = new HashMap<>();
        porcentajes = new HashMap<>();
//        allCollector = SuperClassForTests.allCollector;
        int i = -1;
        for (String className : clases) {
            i++;
            notas.put(className, 0.0);
            porcentajes.put(className, tantosPorCiento[i]);
        }
    }

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(TestSuite.class);
        System.out.println("\nBriefing of marks after automatic marker operation:\n");
        double notaFinal = 0;
        double notaParcial;
        for (Map.Entry<String, Double> nota : notas.entrySet()) {
            String className = nota.getKey();
            notaParcial = nota.getValue() * porcentajes.get(className) / 100;
            notaFinal += notaParcial;
            System.out.println("Mark in " + className
                    + ": " + SuperClassForTests.withMathRound(nota.getValue(), 3) + " (Percentage: "
                    + porcentajes.get(className) + "%). Contribution to final mark in "
                            + "\"features\" section of the project: " + notaParcial);
        }
        System.out.println("\nFinal mark of automatic marker: " + SuperClassForTests.withMathRound(notaFinal, 3));
    }
}
