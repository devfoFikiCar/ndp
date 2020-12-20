/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.fclang.parser.standard;

import androidx.core.util.Pair;

import com.devoFikiCar.fclang.parser.Parser;

import java.util.ArrayList;

public class Matrixes {

    /**
     * getTwoIntExpression is helper function that checks if '(' INT ',' INT ')' is next
     * sequence of tokens.
     *
     * @param index position of (
     * @return index to continue parsing from and values
     */
    public static int[] getTwoIntExpressions(int index) {
        int[] ret = new int[3];
        if (!Parser.tokens.get(index).key.equals("L_BRACES")) {
            return ret;
        }
        index++;
        int[] retV = Integers.expressionInt(index, 0);
        if (retV[0] == 0) {
            return ret;
        }
        index = retV[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) {
            return ret;
        }
        index++;
        int[] retV2 = Integers.expressionInt(index, 0);
        if (retV2[0] == 0) {
            return ret;
        }
        index = retV2[0];
        if (!Parser.tokens.get(index).key.equals("R_BRACES")) {
            return ret;
        }
        ret[0] = index;
        ret[1] = retV[1];
        ret[2] = retV2[1];
        return ret;
    }

    /**
     * checkPosition checks if syntax is INT ',' INT ','
     *
     * @param index position of first int
     * @return index to continue parsing from or 0 if error occurred and correct values
     */
    public static int[] checkPosition(int index) {
        int[] ret = new int[3];
        int[] retV = Integers.expressionInt(index, 0);
        if (retV[0] == 0) {
            return ret;
        }
        index = retV[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) {
            return ret;
        }
        index++;
        int[] retV2 = Integers.expressionInt(index, 0);
        if (retV2[0] == 0) {
            return ret;
        }
        index = retV2[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) {
            return ret;
        }
        index++;
        ret[0] = index;
        ret[1] = retV[1];
        ret[2] = retV2[1];
        return ret;
    }

    /**
     * checkPositionNoComma checks if syntax is INT ',' INT
     *
     * @param index position of first int
     * @return index to continue parsing from or 0 if error occurred and correct values
     */
    public static int[] checkPositionNoComma(int index) {
        int[] ret = new int[3];
        int[] retV = Integers.expressionInt(index, 0);
        if (retV[0] == 0) {
            return ret;
        }
        index = retV[0];
        if (!Parser.tokens.get(index).key.equals("COMMA")) {
            return ret;
        }
        index++;
        int[] retV2 = Integers.expressionInt(index, 0);
        if (retV2[0] == 0) {
            return ret;
        }
        index = retV2[0];
        ret[0] = index;
        ret[1] = retV[1];
        ret[2] = retV2[1];
        return ret;
    }

    /**
     * declareIntMatrix declares int matrix, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareIntMatrix(int index) {
        String name = "";
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("INT_MATRIX")) return 0;
        index++;
        int[] ret = getTwoIntExpressions(++index);
        if (ret[0] == 0) return 0;
        index = ret[0];
        Parser.intMatrixStore.put(name, new Pair<>(new ArrayList<>(), new Pair<Integer, Integer>(ret[1], ret[2])));
        for (int i = 0; i < ret[1]; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            for (int j = 0; j < ret[2]; j++) {
                tmp.add(0);
            }
            Parser.intMatrixStore.get(name).first.add(new ArrayList<>(tmp));
            tmp.clear();
        }
        return index;
    }

    /**
     * declareDecimalMatrix declares decimal matrix, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareDecimalMatrix(int index) {
        String name = "";
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("DECIMAL_MATRIX")) return 0;
        index++;
        int[] ret = getTwoIntExpressions(++index);
        if (ret[0] == 0) return 0;
        index = ret[0];
        Parser.decimalMatrixStore.put(name, new Pair<>(new ArrayList<>(), new Pair<>(ret[1], ret[2])));
        for (int i = 0; i < ret[1]; i++) {
            ArrayList<Double> tmp = new ArrayList<>();
            for (int j = 0; j < ret[2]; j++) {
                tmp.add(0.0);
            }
            Parser.decimalMatrixStore.get(name).first.add(new ArrayList<>(tmp));
            tmp.clear();
        }
        return index;
    }

    /**
     * declareBoolMatrix declares bool matrix, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareBoolMatrix(int index) {
        String name = "";
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("BOOL_MATRIX")) return 0;
        index++;
        int[] ret = getTwoIntExpressions(++index);
        if (ret[0] == 0) return 0;
        index = ret[0];
        Parser.boolMatrixStore.put(name, new Pair<>(new ArrayList<>(), new Pair<>(ret[1], ret[2])));
        for (int i = 0; i < ret[1]; i++) {
            ArrayList<Boolean> tmp = new ArrayList<>();
            for (int j = 0; j < ret[2]; j++) {
                tmp.add(false);
            }
            Parser.boolMatrixStore.get(name).first.add(new ArrayList<>(tmp));
            tmp.clear();
        }
        return index;
    }

    /**
     * declareStringMatrix declares string matrix, checks syntax and ensures capacity.
     *
     * @param index position to start parsing from
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int declareStringMatrix(int index) {
        String name = "";
        if (!Parser.tokens.get(index + 1).key.equals("NAME")) return 0;
        name = Parser.tokens.get(index + 1).value;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("EQUALS")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("NEW")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("STRING_MATRIX")) return 0;
        index++;
        int[] ret = getTwoIntExpressions(++index);
        if (ret[0] == 0) return 0;
        index = ret[0];
        Parser.stringMatrixStore.put(name, new Pair<>(new ArrayList<>(), new Pair<>(ret[1], ret[2])));
        for (int i = 0; i < ret[1]; i++) {
            ArrayList<String> tmp = new ArrayList<>();
            for (int j = 0; j < ret[2]; j++) {
                tmp.add("");
            }
            Parser.stringMatrixStore.get(name).first.add(new ArrayList<>(tmp));
            tmp.clear();
        }
        return index;
    }

    /**
     * setMatrixValueInt validates expression and sets value to intMatrixStore.
     *
     * @param index position of matrix name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setMatrixValueInt(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.intMatrixStore.containsKey(name)) return 0;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] retV = checkPosition(++index);
        if (retV[0] == 0) return 0;
        index = retV[0];
        int[] retV2 = Integers.expressionInt(index, 0);
        if (retV2[0] == 0) return 0;
        index = retV2[0];
        int value = retV2[1];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return 0;
        Parser.intMatrixStore.get(name).first.get(retV[1]).set(retV[2], value);
        return index;
    }

    /**
     * setMatrixValueDecimal validates expression and sets value to decimalMatrixStore.
     *
     * @param index position of matrix name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setMatrixValueDecimal(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.decimalMatrixStore.containsKey(name)) return 0;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] retV = checkPosition(++index);
        if (retV[0] == 0) return 0;
        index = retV[0];
        double[] retV2 = Decimals.expressionDecimal(index, 0);
        if (retV2[0] == 0) return 0;
        index = (int) retV2[0];
        double value = retV2[1];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return 0;
        Parser.decimalMatrixStore.get(name).first.get(retV[1]).set(retV[2], value);
        return index;
    }

    /**
     * setMatrixValueString validates expression and sets value to stringMatrixStore.
     *
     * @param index position of matrix name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setMatrixValueString(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.stringMatrixStore.containsKey(name)) return 0;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] retV = checkPosition(++index);
        if (retV[0] == 0) return 0;
        index = retV[0];
        Pair<Integer, String> retPair = Strings.isString(index);
        if (retPair.first == 0) return 0;
        index = retPair.first;
        String value = retPair.second;
        if (!Parser.tokens.get(++index).key.equals("R_PARENTHESES")) return 0;
        Parser.stringMatrixStore.get(name).first.get(retV[1]).set(retV[2], value);
        return index;
    }

    /**
     * setMatrixValueBool validates expression and sets value to boolMatrixStore.
     *
     * @param index position of matrix name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int setMatrixValueBool(int index) {
        String name = Parser.tokens.get(index).value;
        if (!Parser.boolMatrixStore.containsKey(name)) return 0;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("SET")) return 0;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return 0;
        index++;
        int[] retV = checkPosition(++index);
        if (retV[0] == 0) return 0;
        index = retV[0];
        int[] retV2 = Bools.bool(index);
        if (retV2[0] == 0) return 0;
        index = retV2[0];
        int value = retV2[1];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return 0;
        Parser.boolMatrixStore.get(name).first.get(retV[1]).set(retV[2], value == 1);
        return index;
    }

    /**
     * matrixRowSize parses {Matrix}.rowSize() requests.
     *
     * @param index position of matrix name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int[] matrixRowSize(int index) {
        int[] ret = new int[2];
        int store = 0;
        String name = Parser.tokens.get(index).value;
        if (Parser.intMatrixStore.containsKey(name)) store = 1;
        else if (Parser.decimalMatrixStore.containsKey(name)) store = 2;
        else if (Parser.stringMatrixStore.containsKey(name)) store = 3;
        else if (Parser.boolMatrixStore.containsKey(name)) store = 4;
        else return ret;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) {
            return ret;
        }
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("ROW_SIZE")) {
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
                ret[1] = Parser.intMatrixStore.get(name).first.size();
                return ret;
            }
            case 2: {
                ret[1] = Parser.decimalMatrixStore.get(name).first.size();
                return ret;
            }
            case 3: {
                ret[1] = Parser.stringMatrixStore.get(name).first.size();
                return ret;
            }
            case 4: {
                ret[1] = Parser.boolMatrixStore.get(name).first.size();
                return ret;
            }
        }
        return ret;
    }

    /**
     * matrixColumnSize parses {Matrix}.columnSize() requests.
     *
     * @param index position of matrix name
     * @return index to continue parsing from or 0 if error occurred
     */
    public static int[] matrixColumnSize(int index) {
        int[] ret = new int[2];
        int store = 0;
        String name = Parser.tokens.get(index).value;
        if (Parser.intMatrixStore.containsKey(name)) store = 1;
        else if (Parser.decimalMatrixStore.containsKey(name)) store = 2;
        else if (Parser.stringMatrixStore.containsKey(name)) store = 3;
        else if (Parser.boolMatrixStore.containsKey(name)) store = 4;
        else return ret;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) {
            return ret;
        }
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("COLUMN_SIZE")) {
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
                ret[1] = Parser.intMatrixStore.get(name).first.get(0).size();
                return ret;
            }
            case 2: {
                ret[1] = Parser.decimalMatrixStore.get(name).first.get(0).size();
                return ret;
            }
            case 3: {
                ret[1] = Parser.stringMatrixStore.get(name).first.get(0).size();
                return ret;
            }
            case 4: {
                ret[1] = Parser.boolMatrixStore.get(name).first.get(0).size();
                return ret;
            }
        }
        return ret;
    }


    /**
     * getMatrixValue validates expression and gets value from correct matrix.
     *
     * @param index position of matrix name
     * @param store matrix type 0 if unknown
     * @return pair with key 0 if error or with key index and value correct matrix value
     */
    public static androidx.core.util.Pair getMatrixValue(int index, int store) {
        Pair<Integer, Integer> retError = new Pair<>(0, 0);
        String name = Parser.tokens.get(index).value;
        if (store == 0) {
            if (Parser.intMatrixStore.containsKey(name)) store = 1;
            else if (Parser.decimalMatrixStore.containsKey(name)) store = 2;
            else if (Parser.stringMatrixStore.containsKey(name)) store = 3;
            else if (Parser.boolMatrixStore.containsKey(name)) store = 4;
        }
        if (store == 0) return retError;
        if (!Parser.tokens.get(index + 1).key.equals("DOT")) return retError;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("GET")) return retError;
        index++;
        if (!Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) return retError;
        index++;
        index++;
        int[] retV = checkPositionNoComma(index);
        if (retV[0] == 0) return retError;
        index = retV[0];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) return retError;
        switch (store) {
            case 1: {
                return new Pair<>(index, Parser.intMatrixStore.get(name).first.get(retV[1]).get(retV[2]));
            }
            case 2: {
                return new Pair<>(index, Parser.decimalMatrixStore.get(name).first.get(retV[1]).get(retV[2]));
            }
            case 3: {
                return new Pair<>(index, Parser.stringMatrixStore.get(name).first.get(retV[1]).get(retV[2]));
            }
            case 4: {
                return new Pair<>(index, Parser.boolMatrixStore.get(name).first.get(retV[1]).get(retV[2]));
            }
        }
        return retError;
    }
}