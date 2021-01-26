/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.fclang.parser.standard;

import androidx.core.util.Pair;

import com.devoFikiCar.fclang.parser.Parser;

import java.util.ArrayList;
import java.util.Collections;

public class Arrays {

    /**
     * declareIntArray declares int array, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareIntArray(int index) {
        String name;
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("INT_ARRAY")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_BRACES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("R_BRACES")) return 0;
        Parser.intArrayStore.put(name, new Pair<>(new ArrayList<Integer>(ret[1]), ret[1]));
        for (int i = 0; i < ret[1]; i++) Parser.intArrayStore.get(name).first.add(0);
        return index;
    }

    /**
     * declareDecimalArray declares decimal array, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareDecimalArray(int index) {
        String name;
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("DECIMAL_ARRAY")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_BRACES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("R_BRACES")) return 0;
        Parser.decimalArrayStore.put(name, new Pair<>(new ArrayList<Double>(ret[1]), ret[1]));
        for (int i = 0; i < ret[1]; i++) Parser.decimalArrayStore.get(name).first.add(0.0);
        return index;
    }

    /**
     * declareStringArray declares string array, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareStringArray(int index) {
        String name;
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("STRING_ARRAY")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_BRACES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("R_BRACES")) return 0;
        Parser.stringArrayStore.put(name, new Pair<>(new ArrayList<String>(ret[1]), ret[1]));
        for (int i = 0; i < ret[1]; i++) Parser.stringArrayStore.get(name).first.add("");
        return index;
    }

    /**
     * declareBoolArray declares bool array, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareBoolArray(int index) {
        String name;
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("BOOL_ARRAY")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_BRACES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("R_BRACES")) return 0;
        Parser.boolArrayStore.put(name, new Pair<>(new ArrayList<Boolean>(ret[1]), ret[1]));
        for (int i = 0; i < ret[1]; i++) Parser.boolArrayStore.get(name).first.add(false);
        return index;
    }

    /**
     * arraySize parses {Array}.size() requests.
     *
     * @param index position of array name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int[] arraySize(int index) {
        int[] ret = new int[2];
        int store;
        String name = Parser.tokens.get(index).value;
        if (Parser.intArrayStore.containsKey(name)) store = 1;
        else if (Parser.decimalArrayStore.containsKey(name)) store = 2;
        else if (Parser.stringArrayStore.containsKey(name)) store = 3;
        else if (Parser.boolArrayStore.containsKey(name)) store = 4;
        else return ret;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) {
            return ret;
        }
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SIZE")) {
            return ret;
        }
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES") && !Parser.tokens.get(index + 2).key.equals("R_PARENTHESES")) {
            return ret;
        }
        index++;
        ret[0] = index;
        switch (store) {
            case 1: {
                ret[1] = Parser.intArrayStore.get(name).second;
                return ret;
            }
            case 2: {
                ret[1] = Parser.decimalArrayStore.get(name).second;
                return ret;
            }
            case 3: {
                ret[1] = Parser.stringArrayStore.get(name).second;
                return ret;
            }
            case 4: {
                ret[1] = Parser.boolArrayStore.get(name).second;
                return ret;
            }
        }
        return ret;
    }

    /**
     * arraySort parses {Array}.sort() requests.
     *
     * @param index position of array name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int arraySort(int index) {
        int store;
        String name = Parser.tokens.get(index).value;
        if (Parser.intArrayStore.containsKey(name)) store = 1;
        else if (Parser.decimalArrayStore.containsKey(name)) store = 2;
        else if (Parser.stringArrayStore.containsKey(name)) store = 3;
        else if (Parser.boolArrayStore.containsKey(name)) store = 4;
        else return 0;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SORT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES") && !Parser.tokens.get(index + 2).key.equals("R_PARENTHESES"))
            return 0;
        index += 2;
        switch (store) {
            case 1: {
                Collections.sort(Parser.intArrayStore.get(name).first);
                return index;
            }
            case 2: {
                Collections.sort(Parser.decimalArrayStore.get(name).first);
                return index;
            }
            case 3: {
                Collections.sort(Parser.stringArrayStore.get(name).first);
                return index;
            }
            case 4: {
                Collections.sort(Parser.boolArrayStore.get(name).first);
                return index;
            }
        }
        return index;
    }

    /**
     * setArrayValueInt validates expression and sets value to intArrayStore.
     *
     * @param index position of array name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setArrayValueInt(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.intArrayStore.containsKey(name)) return 0;
        int size;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) return 0;
        index++;
        int[] ret1 = Integers.expressionInt(index, 0);
        if (ret1[0] == 0) return 0;
        index = ret1[0];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return 0;
        size = Parser.intArrayStore.get(name).second;
        if (ret[1] >= size) return 0;
        Parser.intArrayStore.get(name).first.set(ret[1], ret1[1]);
        return index;
    }

    /**
     * setArrayValueDecimal validates expression and sets value to decimalArrayStore.
     *
     * @param index position of array name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setArrayValueDecimal(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.decimalArrayStore.containsKey(name)) return 0;
        int size;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) return 0;
        index++;
        double[] ret1 = Decimals.expressionDecimal(index, 0);
        if (ret1[0] == 0) return 0;
        index = (int) ret1[0];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return 0;
        size = Parser.decimalArrayStore.get(name).second;
        if (ret[1] >= size) return 0;
        Parser.decimalArrayStore.get(name).first.set(ret[1], ret1[1]);
        return index;
    }

    /**
     * setArrayValueString validates expression and sets value to stringArrayStore.
     *
     * @param index position of array name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setArrayValueString(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.stringArrayStore.containsKey(name)) return 0;
        int size;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) return 0;
        index++;
        Pair<Integer, String> retPair = Strings.isString(index++);
        if (retPair.first == 0) return 0;
        index = retPair.first;
        if (!Parser.tokens.get(index + 1).key.equals("R_PARENTHESES")) return 0;
        size = Parser.stringArrayStore.get(name).second;
        if (ret[1] >= size) return 0;
        Parser.stringArrayStore.get(name).first.set(ret[1], retPair.second);
        return index;
    }

    /**
     * setArrayValueBool validates expression and sets value to boolArrayStore.
     *
     * @param index position of array name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setArrayValueBool(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.boolArrayStore.containsKey(name)) return 0;
        int size = Parser.boolArrayStore.get(name).second;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return 0;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) return 0;
        index++;
        int[] ret1 = Bools.bool(index);
        if (ret1[0] == 0) return 0;
        index = ret1[0];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return 0;
        size = Parser.boolArrayStore.get(name).second;
        if (ret[1] >= size) return 0;
        Parser.boolArrayStore.get(name).first.set(ret[1], ret1[1] == 1);
        return index;
    }

    /**
     * getArrayValue validates expression and gets value from correct array.
     *
     * @param index position of array name
     * @param store array type 0 if unknown
     * @return pair with key 0 if error or with key index and value correct array value
     */
    public static Pair getArrayValue(int index, int store) {
        Pair<Integer, Integer> retError = new Pair<>(0, 0);
        String name = Parser.tokens.get(index).value;
        if (store == 0) {
            if (Parser.intArrayStore.containsKey(name)) store = 1;
            else if (Parser.decimalArrayStore.containsKey(name)) store = 2;
            else if (Parser.stringArrayStore.containsKey(name)) store = 3;
            else if (Parser.boolArrayStore.containsKey(name)) store = 4;
        }
        if (store == 0) return retError;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return retError;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("GET")) return retError;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return retError;
        index++;
        int[] ret = Integers.expressionInt(index + 1, 0);
        if (ret[0] == 0) return retError;
        index = ret[0];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return retError;
        switch (store) {
            case 1: {
                if (ret[1] < Parser.intArrayStore.get(name).second) {
                    return new Pair<>(index, Parser.intArrayStore.get(name).first.get(ret[1]));
                }
                return retError;
            }
            case 2: {
                if (ret[1] < Parser.decimalArrayStore.get(name).second) {
                    return new Pair<>(index, Parser.decimalArrayStore.get(name).first.get(ret[1]));
                }
                return retError;
            }
            case 3: {
                if (ret[1] < Parser.stringArrayStore.get(name).second) {
                    return new Pair<>(index, Parser.stringArrayStore.get(name).first.get(ret[1]));
                }
                return retError;
            }
            case 4: {
                if (ret[1] < Parser.boolArrayStore.get(name).second) {
                    return new Pair<>(index, Parser.boolArrayStore.get(name).first.get(ret[1]));
                }
                return retError;
            }
        }
        return retError;
    }
}