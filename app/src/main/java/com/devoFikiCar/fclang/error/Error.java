/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.fclang.error;


import com.devoFikiCar.fclang.Main;
import com.devoFikiCar.fclang.StartFClang;
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
                StartFClang.getOutput().add("After print method you need to use a string, int, decimal or valid variable name.");
                break;
            }
            case 2: {
                printError(index);
                StartFClang.getOutput().add("Wrong int declaration.");
                break;
            }
            case 3: {
                printError(index);
                StartFClang.getOutput().add("Wrong string declaration.");
                break;
            }
            case 4: {
                printError(index);
                StartFClang.getOutput().add("Wrong decimal declaration.");
                break;
            }
            case 5: {
                printError(index);
                StartFClang.getOutput().add("Wrong expression.");
                break;
            }
            case 6: {
                printError(index);
                StartFClang.getOutput().add("Wrong bool expression.");
                break;
            }
            case 7: {
                printError(index);
                StartFClang.getOutput().add("Wrong if statement.");
                break;
            }
            case 8: {
                printError(index);
                StartFClang.getOutput().add("Wrong logical expression.");
                break;
            }
            case 9: {
                printError(index);
                StartFClang.getOutput().add("Wrong for loop.");
                break;
            }
            case 10: {
                printError(index);
                StartFClang.getOutput().add("Wrong goto statement");
                break;
            }
            case 11: {
                printError(index);
                StartFClang.getOutput().add("Wrong array declaration.");
                break;
            }
            case 12: {
                printError(index);
                StartFClang.getOutput().add("Wrong matrix declaration.");
                break;
            }
            case 13: {
                printError(index);
                StartFClang.getOutput().add("Wrong name usage.");
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
        int lineSize = StartFClang.getCode().get(line).length();
        StartFClang.getOutput().add(Main.code.get(line));
        String tmp = "";
        for (int i = 0; i < lineSize; i++) {
            tmp += "-";
        }
        StartFClang.getOutput().add(tmp);
        StartFClang.getOutput().add("");
        StartFClang.getOutput().add("At the line above you made mistake!");
        StartFClang.getOutput().add("Possible reason:");
    }
}
