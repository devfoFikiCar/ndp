/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.fclang.parser;

import androidx.core.util.Pair;

import com.devoFikiCar.fclang.Token;
import com.devoFikiCar.fclang.error.Error;
import com.devoFikiCar.fclang.parser.IO.Print;
import com.devoFikiCar.fclang.parser.standard.Arrays;
import com.devoFikiCar.fclang.parser.standard.Declaration;
import com.devoFikiCar.fclang.parser.standard.ForLoop;
import com.devoFikiCar.fclang.parser.standard.Goto;
import com.devoFikiCar.fclang.parser.standard.IfStatement;
import com.devoFikiCar.fclang.parser.standard.Matrixes;
import com.devoFikiCar.fclang.parser.standard.Names;

import java.util.ArrayList;
import java.util.HashMap;

public class Parser {
    public static boolean error = false;

    public static ArrayList<Token> tokens = new ArrayList<>();
    public static ArrayList<Integer> skip = new ArrayList<>();

    public static HashMap<String, Integer> intStore = new HashMap<>();
    public static HashMap<String, Double> decimalStore = new HashMap<>();
    public static HashMap<String, String> stringStore = new HashMap<>();
    public static HashMap<String, Boolean> boolStore = new HashMap<>();

    public static HashMap<Integer, Integer> skipStore = new HashMap<>();
    public static HashMap<String, Integer> gotoStore = new HashMap<>();

    public static HashMap<String, Pair<ArrayList<Integer>, Integer>> intArrayStore = new HashMap<>();
    public static HashMap<String, Pair<ArrayList<Double>, Integer>> decimalArrayStore = new HashMap<>();
    public static HashMap<String, Pair<ArrayList<String>, Integer>> stringArrayStore = new HashMap<>();
    public static HashMap<String, Pair<ArrayList<Boolean>, Integer>> boolArrayStore = new HashMap<>();

    public static HashMap<String, Pair<ArrayList<ArrayList<Integer>>, Pair<Integer, Integer>>> intMatrixStore = new HashMap<>();
    public static HashMap<String, Pair<ArrayList<ArrayList<Double>>, Pair<Integer, Integer>>> decimalMatrixStore = new HashMap<>();
    public static HashMap<String, Pair<ArrayList<ArrayList<String>>, Pair<Integer, Integer>>> stringMatrixStore = new HashMap<>();
    public static HashMap<String, Pair<ArrayList<ArrayList<Boolean>>, Pair<Integer, Integer>>> boolMatrixStore = new HashMap<>();

    /**
     * parse parses tokens and manages memory
     *
     * @param tokenData tokens to parse
     * @param begin     starting index
     * @param end       ending index
     * @return size of tokens if everything was fine
     */
    public static int parse(ArrayList<Token> tokenData, int begin, int end) {
        tokens = tokenData;
        int indexR = 0;
        Goto.safeGoto();
        for (int index = begin; index < end; index++) {
            if (skipStore.containsKey(index)) {
                index = skipStore.get(index);
                continue;
            }
            if (skip.contains(index)) continue;
            String key = tokens.get(index).key;
            switch (key) {
                case "PRINT": {
                    int result = Print.printFunction(index);
                    if (result == 0) {
                        Error.FatalError(1, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    System.out.println();
                    break;
                }
                case "INT_T": {
                    int result = Declaration.declareInt(index);
                    if (result == 0) {
                        Error.FatalError(2, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "STRING_T": {
                    int result = Declaration.declareString(index);
                    if (result == 0) {
                        Error.FatalError(3, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "DECIMAL_T": {
                    int result = Declaration.declareDecimal(index);
                    if (result == 0) {
                        Error.FatalError(4, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "BOOL_T": {
                    int result = Declaration.declareBool(index);
                    if (result == 0) {
                        Error.FatalError(6, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "IF": {
                    int[] ret_v = IfStatement.ifStatement(index);
                    if (ret_v[0] == 0) {
                        Error.FatalError(7, index);
                        index = tokens.size();
                        error = true;
                    } else {
                        if (ret_v[2] == 0) {
                            skip.add(ret_v[1]);
                            if (ret_v[5] != 0) {
                                skipStore.put(ret_v[1], ret_v[5]);
                            }
                            index = ret_v[0];
                        } else {
                            if (ret_v[4] != 0) {
                                index = ret_v[4];
                                skip.add(ret_v[5]);
                            } else index = ret_v[1];
                        }
                    }
                    break;
                }
                case "FOR": {
                    int result = ForLoop.forLoop(index);
                    if (result == 0) {
                        Error.FatalError(9, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "GOTO": {
                    int result = Goto.gotoFunction(index);
                    if (result == -1) {
                        Error.FatalError(10, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "INT_ARRAY": {
                    int result = Arrays.declareIntArray(index);
                    if (result == 0) {
                        Error.FatalError(11, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "DECIMAL_ARRAY": {
                    int result = Arrays.declareDecimalArray(index);
                    if (result == 0) {
                        Error.FatalError(11, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "STRING_ARRAY": {
                    int result = Arrays.declareStringArray(index);
                    if (result == 0) {
                        Error.FatalError(11, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "BOOL_ARRAY": {
                    int result = Arrays.declareBoolArray(index);
                    if (result == 0) {
                        Error.FatalError(11, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "INT_MATRIX": {
                    int result = Matrixes.declareIntMatrix(index);
                    if (result == 0) {
                        Error.FatalError(12, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "DECIMAL_MATRIX": {
                    int result = Matrixes.declareDecimalMatrix(index);
                    if (result == 0) {
                        Error.FatalError(12, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "STRING_MATRIX": {
                    int result = Matrixes.declareStringMatrix(index);
                    if (result == 0) {
                        Error.FatalError(12, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "BOOL_MATRIX": {
                    int result = Matrixes.declareBoolMatrix(index);
                    if (result == 0) {
                        Error.FatalError(12, index);
                        index = tokens.size();
                        error = true;
                    } else index = result;
                    break;
                }
                case "NAME": {
                    if (tokens.get(index).posInLine == 0) {
                        int result = Names.redeclareNames(index);
                        if (result == 0) {
                            Error.FatalError(13, index);
                            index = tokens.size();
                            error = true;
                        } else index = result;
                    }
                    break;
                }
            }
            indexR = index;
        }
        return indexR;
    }
}
