/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.fclang.parser.standard;

import androidx.core.util.Pair;

import com.devoFikiCar.fclang.parser.IO.GetInput;
import com.devoFikiCar.fclang.parser.Parser;

public class Declaration {

    /**
     * declareInt declares new int variable by expression or input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int declareInt(int index) {
        if (Parser.tokens.get(index + 1).key.equals("NAME")) {
            if (Parser.tokens.get(index + 2).key.equals("EQUALS")) {
                int[] retV = Integers.expressionInt(index + 3, 0);
                if (retV[0] != 0) {
                    Parser.intStore.put(Parser.tokens.get(index + 1).value, retV[1]);
                    index = retV[0];
                    return --index;
                } else if (Parser.tokens.get(index + 3).key.equals("GET_INT")) {
                    return GetInput.getInputInt(index + 3);
                } else {
                    index = declareValue(index + 3, Parser.tokens.get(index).key, Parser.tokens.get(index + 1).value);
                    return index;
                }
            } else return 0;
        }
        return 0;
    }

    /**
     * declareString declares new string variable by code or input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int declareString(int index) {
        if (Parser.tokens.get(index + 1).key.equals("NAME")) {
            if (Parser.tokens.get(index + 2).key.equals("EQUALS")) {
                if (Parser.tokens.get(index + 3).key.equals("GET_STRING")) {
                    return GetInput.getInputString(index + 3);
                }
                if (index + 7 < Parser.tokens.size()) {
                    Pair<Integer, String> retPair = Arrays.getArrayValue(index + 3, 3);
                    if (retPair.first != 0) {
                        Parser.stringStore.put(Parser.tokens.get(index + 1).value, retPair.second);
                        return retPair.first;
                    }
                }
                if (index + 8 < Parser.tokens.size()) {
                    Pair<Integer, String> retPair = Matrixes.getMatrixValue(index + 3, 3);
                    if (retPair.first != 0) {
                        Parser.stringStore.put(Parser.tokens.get(index + 1).value, retPair.second);
                        return retPair.first;
                    }
                }
                index = declareValue(index + 3, Parser.tokens.get(index).key, Parser.tokens.get(index + 1).value);
                return index;
            } else return 0;
        }
        return 0;
    }

    /**
     * declareBool declares new boolean variable by expression or input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int declareBool(int index) {
        if (Parser.tokens.get(index + 1).key.equals("NAME")) {
            if (Parser.tokens.get(index + 2).key.equals("EQUALS")) {
                int[] retV = Bools.bool(index + 3);
                if (retV[0] != 0) {
                    Parser.boolStore.put(Parser.tokens.get(index + 1).value, retV[1] == 1);
                    index = retV[0];
                    return --index;
                } else if (Parser.tokens.get(index + 3).key.equals("GET_BOOL")) {
                    return GetInput.getInputBool(index + 3);
                } else {
                    index = declareValue(index + 3, Parser.tokens.get(index).key, Parser.tokens.get(index + 1).value);
                    return index;
                }
            } else return 0;
        } else return 0;
    }

    /**
     * declareDecimal declares new decimal variable by expression or input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int declareDecimal(int index) {
        if (Parser.tokens.get(index + 1).key.equals("NAME")) {
            if (Parser.tokens.get(index + 2).key.equals("EQUALS")) {
                double[] retV = Decimals.expressionDecimal(index * 1.0 + 3.0, 0);
                if (retV[0] != 0) {
                    Parser.decimalStore.put(Parser.tokens.get(index + 1).value, retV[1]);
                    index = (int) retV[0];
                    return --index;
                } else if (Parser.tokens.get(index + 3).key.equals("GET_DECIMAL")) {
                    return GetInput.getInputDecimal(index + 3);
                } else {
                    index = declareValue(index + 3, Parser.tokens.get(index).key, Parser.tokens.get(index + 1).value);
                    return index;
                }
            } else return 0;
        } else return 0;
    }

    /**
     * declareValue declares simple new variables using correct functions.
     *
     * @param index begin position for parsing
     * @param type  new variable type
     * @param name  new variable name
     * @return index to continue parsing from
     */
    public static int declareValue(int index, String type, String name) {
        String valueType = Parser.tokens.get(index).key + "_T";
        if (type.equals(valueType)) {
            switch (type) {
                case "INT_T": {
                    Parser.intStore.put(name, Integer.valueOf(Parser.tokens.get(index).value));
                    break;
                }
                case "STRING_T": {
                    Parser.stringStore.put(name, String.valueOf(Parser.tokens.get(index).value));
                    break;
                }
                case "DECIMAL_T": {
                    Parser.decimalStore.put(name, Double.valueOf(Parser.tokens.get(index).value));
                    break;
                }
                case "BOOL_T": {
                    Parser.boolStore.put(name, Boolean.valueOf(Parser.tokens.get(index).value));
                }
            }
            return index;
        }
        return 0;
    }

}
