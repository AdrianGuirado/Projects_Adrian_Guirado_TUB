package edu.upc.etsetb.arqsoft.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import edu.upc.etsetb.arqsoft.controller.SpreadsheetController;
import edu.upc.etsetb.arqsoft.domain.Spreadsheet;
import edu.upc.etsetb.arqsoft.exceptions.BadFormulaException;
import edu.upc.etsetb.arqsoft.exceptions.BadPostfixerImplementationException;
import edu.upc.etsetb.arqsoft.exceptions.CircularDependenciesException;
import edu.upc.etsetb.arqsoft.exceptions.IncorrectFormatException;
import edu.upc.etsetb.arqsoft.exceptions.IncorrectPathException;
import edu.upc.etsetb.arqsoft.exceptions.SyntaxErrorException;
import edu.upc.etsetb.arqsoft.exceptions.TokenNotMatchedException;
import edu.upc.etsetb.arqsoft.exceptions.ZeroDivisionException;

public class SpreadsheetUI {
    private SpreadsheetController controller;

    public SpreadsheetUI(SpreadsheetController controller) {
        this.controller = controller;
    }

    private void executeCommandsFromFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            System.out.println("The specified file doesn't exit");
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String command;
            while ((command = reader.readLine()) != null) {
                executeCommand(command);
            }
        } catch (IOException e) {
            System.out.println("Error when reading the file " + e.getMessage());
        }
    }

    private void executeCommand(String input) {
        String[] parts = input.toUpperCase().split(" ");
        String command = parts[0];
        
        switch (command) {
            case "C":
                controller.createSpreadsheet();
                break;
                
            case "E":
                parts = input.toUpperCase().split(" ", 3);
                if (parts.length == 3) {
                    try {
                        controller.editCell(parts[1], parts[2]);
                    } catch (BadFormulaException | TokenNotMatchedException | CircularDependenciesException
                            | ZeroDivisionException | BadPostfixerImplementationException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Invalid format for edit command. Usage: E <cell coordinate> <new content>");
                }
                break;
            case "L":
                parts = input.split(" ", 2);
                if (parts.length > 1) {
                    filePath = parts[1]; 
                    try {
                        controller.setSpreadsheet(controller.getLoader().computeFile(filePath));
                    } catch (IncorrectPathException | IncorrectFormatException | IOException
                            | SyntaxErrorException e) {
                        e.printStackTrace();
                    }
                    controller.getPrinter().displaySpreadsheet(controller.getSpreadsheet());
                } else {
                    System.out.println("Path required for loading");
                }
                break;

            case "S":
                parts = input.split(" ", 2);
                if (parts.length > 1) {
                    filePath = parts[1];
                    try {
                        controller.getSaver().saveSpreadSheet(controller.getSpreadsheet(), filePath);
                    } catch (IncorrectPathException | IncorrectFormatException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Path required for saving");
                }
                break;
                
            default:
                System.out.println("Invalid option, try again.");
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    private String filePath;


    public void runMainMenu() {
        while (true) {
            System.out.println("Enter command:");
            String input = scanner.nextLine();
            String[] parts = input.toUpperCase().split(" "); 
            String command = parts[0];

            switch (command) {
                case "RF":
                parts = input.split(" ", 2);
                filePath = parts[1];
                executeCommandsFromFile(filePath);
                    break;
                case "C":
                    controller.createSpreadsheet();
                    break;
                case "E":
                    parts = input.toUpperCase().split(" ", 3);
                    if (parts.length == 3) {
                        try {
                            controller.editCell(parts[1], parts[2]);
                        } catch (BadFormulaException | TokenNotMatchedException | CircularDependenciesException
                                | ZeroDivisionException | BadPostfixerImplementationException e) {
                            e.printStackTrace();
                        } 
                    } else {
                        System.out.println("Invalid format for edit command. Usage: E <cell coordinate> <new content>");
                    }
                    break;
                case "L":
                    parts = input.split(" ", 2);
                    if (parts.length > 1) {
                        filePath = parts[1]; 
                        try {
                            controller.setSpreadsheet(controller.getLoader().computeFile(filePath));
                        } catch (IncorrectPathException | IncorrectFormatException | IOException
                                | SyntaxErrorException e) {
                            e.printStackTrace();
                        }
                        controller.getPrinter().displaySpreadsheet(controller.getSpreadsheet());
                    } else {
                        System.out.println("Path required for loading");
                    }
                    break;

                case "S":
                    parts = input.split(" ", 2);
                    if (parts.length > 1) {
                        filePath = parts[1];
                        try {
                            controller.getSaver().saveSpreadSheet(controller.getSpreadsheet(), filePath);
                        } catch (IncorrectPathException | IncorrectFormatException e) {
                            e.printStackTrace();
                        } 
                    } else {
                        System.out.println("Path required for saving");
                    }
                    break;
                default:
                    System.out.println("Invalid option, try again.");
            }
        }
    }
}