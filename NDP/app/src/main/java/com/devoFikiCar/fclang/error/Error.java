package com.devoFikiCar.fclang.error;


import com.devoFikiCar.fclang.Main;
import com.devoFikiCar.fclang.parser.Parser;

public class Error {

    /**
     * FatalError stops program from executing, usually when syntax error occurs.
     *
     * @param code  error id
     * @param index index of error so that the line can be found
     */
    public static void FatalError(int code, int index) {
        switch (code) {
            case 1: {
                printError(index);
                System.out.println("After print method you need to use a string, int, decimal or valid variable name.");
                break;
            }
            case 2: {
                printError(index);
                System.out.println("Wrong int declaration.");
                break;
            }
            case 3: {
                printError(index);
                System.out.println("Wrong string declaration.");
                break;
            }
            case 4: {
                printError(index);
                System.out.println("Wrong decimal declaration.");
                break;
            }
            case 5: {
                printError(index);
                System.out.println("Wrong expression.");
                break;
            }
            case 6: {
                printError(index);
                System.out.println("Wrong bool expression.");
                break;
            }
            case 7: {
                printError(index);
                System.out.println("Wrong if statement.");
                break;
            }
            case 8: {
                printError(index);
                System.out.println("Wrong logical expression.");
                break;
            }
            case 9: {
                printError(index);
                System.out.println("Wrong for loop.");
                break;
            }
            case 10: {
                printError(index);
                System.out.println("Wrong goto statement");
                break;
            }
            case 11: {
                printError(index);
                System.out.println("Wrong array declaration.");
                break;
            }
            case 12: {
                printError(index);
                System.out.println("Wrong matrix declaration.");
                break;
            }
            case 13: {
                printError(index);
                System.out.println("Wrong name usage.");
            }
        }
    }

    /**
     * printError finds the line in question based on index provided. Prints the line and
     * suggests the error.
     *
     * @param index position of token
     */
    private static void printError(int index) {
        int line = Parser.tokens.get(index).lineNumber;
        int lineSize = Main.code.get(line).length();
        System.out.println(Main.code.get(line));
        for (int i = 0; i < lineSize; i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println("At the line above you made mistake!");
        System.out.println("Possible reason:");
    }
}
