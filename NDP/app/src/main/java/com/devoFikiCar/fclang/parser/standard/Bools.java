/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.fclang.parser.standard;

import androidx.core.util.Pair;

import com.devoFikiCar.fclang.parser.Parser;

public class Bools {

    /**
     * bool alidates and calculates boolean expressions.
     * smallBool (('||' | '&&') smallBool)?
     *
     * @param index begin position for parsing
     * @return index to continue parsing from and value of expression
     */
    public static int[] bool(int index) {
        int[] ret = new int[2];
        if (smallBool(index)[0] != 0) {
            int[] retV = smallBool(index);
            ret[0] = retV[0];
            ret[1] = retV[1];
            index = ret[0];
            if (index < Parser.tokens.size() && (Parser.tokens.get(index).key.equals("OR") || Parser.tokens.get(index).key.equals("AND"))) {
                switch (Parser.tokens.get(index).key) {
                    case "OR": {
                        index++;
                        retV = bool(index);
                        index = retV[0];
                        ret[0] = index;
                        boolean a = ret[1] == 1;
                        boolean b = retV[1] == 1;
                        ret[1] = (a || b) ? 1 : 0;
                        break;
                    }
                    case "AND": {
                        index++;
                        retV = bool(index);
                        index = retV[0];
                        ret[0] = index;
                        boolean a = ret[1] == 1;
                        boolean b = retV[1] == 1;
                        ret[1] = (a && b) ? 1 : 0;
                        break;
                    }
                }
                return ret;
            }
            return ret;
        }
        return ret;
    }

    /**
     * smallBool simplifies parts of bool expressions and calculates them to true or false.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from and value of smallBool expression
     */
    public static int[] smallBool(int index) {
        int[] ret = new int[2];
        if (index < Parser.tokens.size() && (Parser.tokens.get(index).key.equals("BOOL") || Parser.boolStore.containsKey(Parser.tokens.get(index).value) ||
                Parser.boolArrayStore.containsKey(Parser.tokens.get(index).value) || Parser.boolMatrixStore.containsKey(Parser.tokens.get(index).value))) {
            if (Parser.boolStore.containsKey(Parser.tokens.get(index).value)) {
                ret[1] = (Parser.boolStore.containsKey(Parser.tokens.get(index).value)) ? 1 : 0;
            } else if (index + 5 < Parser.tokens.size() && (int) Arrays.getArrayValue(index, 4).first != 0) {
                Pair<Integer, Boolean> retPair = Arrays.getArrayValue(index, 4);
                ret[0] = retPair.first;
                ret[1] = (retPair.second) ? 1 : 0;
                ret[0]++;
                return ret;
            } else if (index + 7 < Parser.tokens.size() && (int) Matrixes.getMatrixValue(index, 4).first != 0) {
                Pair<Integer, Boolean> retPair = Matrixes.getMatrixValue(index, 4);
                ret[0] = retPair.first;
                ret[1] = (retPair.second) ? 1 : 0;
                ret[0]++;
                return ret;
            } else {
                if (Parser.tokens.get(index).value.equals("true")) {
                    ret[1] = 1;
                }
            }
            ret[0] = ++index;
            return ret;
        } else if (fcompare(index)[0] != 0) {
            int[] retV = fcompare(index);
            ret[0] = retV[0];
            ret[1] = retV[1];
            return ret;
        } else if (Parser.tokens.get(index).key.equals("L_PARENTHESES")) {
            int[] retV = bool(index + 1);
            ret[0] = retV[0];
            ret[1] = retV[1];
            index = ret[0];
            if (index == 0) {
                return ret;
            } else {
                if (index < Parser.tokens.size() && Parser.tokens.get(index).key.equals("R_PARENTHESES")) {
                    ret[0] = ++index;
                    return ret;
                } else {
                    ret[0] = 0;
                    return ret;
                }
            }
        } else return ret;
    }

    /**
     * fcompare compares int expressions and decimal expressions.
     * expresion_int | expresion_decimal ( < | > | <= | >= | == | != ) expresion_int | expresion_decimal
     *
     * @param index begin position for parsing
     * @return index to continue parsing from and value of compared ints or decimals
     */
    public static int[] fcompare(int index) {
        int[] ret = new int[2];
        int signPos = 0;
        for (int i = index + 1; i < Parser.tokens.size(); i++) {
            if (Parser.tokens.get(i).key.equals("EQUAL_TO") || Parser.tokens.get(i).key.equals("NOT_EQUAL")
                    || Parser.tokens.get(i).key.equals("GREATER_EQUAL") || Parser.tokens.get(i).key.equals("LESS_EQUAL")
                    || Parser.tokens.get(i).key.equals("LESS_THAN") || Parser.tokens.get(i).key.equals("GREATER_THAN")) {
                signPos = i;
                break;
            }
        }
        if (signPos == 0) return ret;
        switch (Parser.tokens.get(signPos).key) {
            case "EQUAL_TO": {
                int[] retInt1 = Integers.expressionInt(index, 0);
                if (retInt1[0] != 0) {
                    int[] retInt2 = Integers.expressionInt(signPos + 1, 0);
                    if (retInt2[0] != 0) {
                        if (retInt1[1] == retInt2[1]) {
                            ret[0] = retInt2[0];
                            ret[1] = 1;
                        } else {
                            ret[0] = retInt2[0];
                        }
                    }
                } else {
                    double[] retdouble1 = Decimals.expressionDecimal(index * 1.0, 0.0);
                    if (retdouble1[0] != 0) {
                        double[] retdouble2 = Decimals.expressionDecimal(signPos * 1.0 + 1.0, 0.0);
                        if (retdouble2[0] != 0) {
                            if (retdouble1[1] == retdouble2[1]) {
                                ret[0] = (int) retdouble2[0];
                                ret[1] = 1;
                            } else {
                                ret[0] = (int) retdouble2[0];

                            }
                        } else {
                            return ret;
                        }
                    }
                }
                break;
            }
            case "NOT_EQUAL": {
                int[] retInt1 = Integers.expressionInt(index, 0);
                if (retInt1[0] != 0) {
                    int[] retInt2 = Integers.expressionInt(signPos + 1, 0);
                    if (retInt2[0] != 0) {
                        if (retInt1[1] != retInt2[1]) {
                            ret[0] = retInt2[0];
                            ret[1] = 1;
                        } else {
                            ret[0] = retInt2[0];
                        }
                    }
                } else {
                    double[] retdouble1 = Decimals.expressionDecimal(index * 1.0, 0.0);
                    if (retdouble1[0] != 0) {
                        double[] retdouble2 = Decimals.expressionDecimal(signPos * 1.0 + 1.0, 0.0);
                        if (retdouble2[0] != 0) {
                            if (retdouble1[1] != retdouble2[1]) {
                                ret[0] = (int) retdouble2[0];
                                ret[1] = 1;
                            } else {
                                ret[0] = (int) retdouble2[0];

                            }
                        } else {
                            return ret;
                        }
                    }
                }
                break;
            }
            case "GREATER_EQUAL": {
                int[] retInt1 = Integers.expressionInt(index, 0);
                if (retInt1[0] != 0) {
                    int[] retInt2 = Integers.expressionInt(signPos + 1, 0);
                    if (retInt2[0] != 0) {
                        if (retInt1[1] >= retInt2[1]) {
                            ret[0] = retInt2[0];
                            ret[1] = 1;
                        } else {
                            ret[0] = retInt2[0];
                        }
                    }
                } else {
                    double[] retdouble1 = Decimals.expressionDecimal(index * 1.0, 0.0);
                    if (retdouble1[0] != 0) {
                        double[] retdouble2 = Decimals.expressionDecimal(signPos * 1.0 + 1.0, 0.0);
                        if (retdouble2[0] != 0) {
                            if (retdouble1[1] >= retdouble2[1]) {
                                ret[0] = (int) retdouble2[0];
                                ret[1] = 1;
                            } else {
                                ret[0] = (int) retdouble2[0];

                            }
                        } else {
                            return ret;
                        }
                    }
                }
                break;
            }
            case "LESS_EQUAL": {
                int[] retInt1 = Integers.expressionInt(index, 0);
                if (retInt1[0] != 0) {
                    int[] retInt2 = Integers.expressionInt(signPos + 1, 0);
                    if (retInt2[0] != 0) {
                        if (retInt1[1] <= retInt2[1]) {
                            ret[0] = retInt2[0];
                            ret[1] = 1;
                        } else {
                            ret[0] = retInt2[0];
                        }
                    }
                } else {
                    double[] retdouble1 = Decimals.expressionDecimal(index * 1.0, 0.0);
                    if (retdouble1[0] != 0) {
                        double[] retdouble2 = Decimals.expressionDecimal(signPos * 1.0 + 1.0, 0.0);
                        if (retdouble2[0] != 0) {
                            if (retdouble1[1] <= retdouble2[1]) {
                                ret[0] = (int) retdouble2[0];
                                ret[1] = 1;
                            } else {
                                ret[0] = (int) retdouble2[0];

                            }
                        } else {
                            return ret;
                        }
                    }
                }
                break;
            }
            case "LESS_THAN": {
                int[] retInt1 = Integers.expressionInt(index, 0);
                if (retInt1[0] != 0) {
                    int[] retInt2 = Integers.expressionInt(signPos + 1, 0);
                    if (retInt2[0] != 0) {
                        if (retInt1[1] < retInt2[1]) {
                            ret[0] = retInt2[0];
                            ret[1] = 1;
                        } else {
                            ret[0] = retInt2[0];
                        }
                    }
                } else {
                    double[] retdouble1 = Decimals.expressionDecimal(index * 1.0, 0.0);
                    if (retdouble1[0] != 0) {
                        double[] retdouble2 = Decimals.expressionDecimal(signPos * 1.0 + 1.0, 0.0);
                        if (retdouble2[0] != 0) {
                            if (retdouble1[1] < retdouble2[1]) {
                                ret[0] = (int) retdouble2[0];
                                ret[1] = 1;
                            } else {
                                ret[0] = (int) retdouble2[0];

                            }
                        } else {
                            return ret;
                        }
                    }
                }
                break;
            }
            case "GREATER_THAN": {
                int[] retInt1 = Integers.expressionInt(index, 0);
                if (retInt1[0] != 0) {
                    int[] retInt2 = Integers.expressionInt(signPos + 1, 0);
                    if (retInt2[0] != 0) {
                        if (retInt1[1] > retInt2[1]) {
                            ret[0] = retInt2[0];
                            ret[1] = 1;
                        } else {
                            ret[0] = retInt2[0];
                        }
                    }
                } else {
                    double[] retdouble1 = Decimals.expressionDecimal(index * 1.0, 0.0);
                    if (retdouble1[0] != 0) {
                        double[] retdouble2 = Decimals.expressionDecimal(signPos * 1.0 + 1.0, 0.0);
                        if (retdouble2[0] != 0) {
                            if (retdouble1[1] > retdouble2[1]) {
                                ret[0] = (int) retdouble2[0];
                                ret[1] = 1;
                            } else {
                                ret[0] = (int) retdouble2[0];

                            }
                        } else {
                            return ret;
                        }
                    }
                }
                break;
            }
        }
        return ret;
    }
}