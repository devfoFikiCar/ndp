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
import com.devoFikiCar.fclang.parser.math.Abs;
import com.devoFikiCar.fclang.parser.math.Max;
import com.devoFikiCar.fclang.parser.math.Min;
import com.devoFikiCar.fclang.parser.math.Pow;
import com.devoFikiCar.fclang.parser.math.Sqrt;

public class Integers {

    /**
     * expressionInt validates and calculates int expressions.
     * expression: term (('-' | '+') term)?
     *
     * @param index begin position for parsing
     * @param value beginning value for operations to start on
     * @return index to continue parsing from and value of expression
     */
    public static int[] expressionInt(int index, int value) {
        int[] ret = {index, value};
        if (termInt(index, value)[0] != 0) {
            int[] retV = termInt(index, value);
            index = retV[0];
            value = retV[1];
            if (index < Parser.tokens.size() && (Parser.tokens.get(index).key.equals("ADDITION") || Parser.tokens.get(index).key.equals("SUBTRACTION"))) {
                retV = expressionInt(index + 1, value);
                switch (Parser.tokens.get(index).key) {
                    case "SUBTRACTION": {
                        value -= retV[1];
                        break;
                    }
                    case "ADDITION": {
                        value += retV[1];
                        break;
                    }
                }
                index = retV[0];
            }
            ret[0] = index;
            ret[1] = value;
            return ret;
        } else {
            ret[0] = 0;
            return ret;
        }
    }

    /**
     * termInt divides and multiplies ints.
     * term: factor (('/' | '*') factor)?
     *
     * @param index begin position for parsing
     * @param value beginning value for operations to start on
     * @return index to continue parsing from and value of expression
     */
    private static int[] termInt(int index, int value) {
        int[] ret = {index, value};
        if (factorInt(index, value)[0] != 0) {
            int[] retV = factorInt(index, value);
            index = retV[0];
            value = retV[1];
            if (index < Parser.tokens.size() && (Parser.tokens.get(index).key.equals("MULTIPLICATION") || Parser.tokens.get(index).key.equals("DIVISION"))) {
                retV = expressionInt(index + 1, value);
                switch (Parser.tokens.get(index).key) {
                    case "DIVISION": {
                        value /= retV[1];
                        break;
                    }
                    case "MULTIPLICATION": {
                        value *= retV[1];
                        break;
                    }
                }
                index = retV[0];
            }
            ret[0] = index;
            ret[1] = value;
            return ret;
        } else {
            ret[0] = 0;
            return ret;
        }
    }

    /**
     * factorInt checks for int and base of int expressions.
     * factor: NUMBER | '(' expression ')'
     *
     * @param index begin position for parsing
     * @param value beginning value for operations to start on
     * @return index to continue parsing from and value of expression
     */
    private static int[] factorInt(int index, int value) {
        int[] ret = {index, value};
        if (Parser.tokens.get(index).key.equals("INT") || Parser.tokens.get(index).key.equals("NAME")) {
            if (Parser.intStore.containsKey(Parser.tokens.get(index).value)) {
                ret[1] = Parser.intStore.get(Parser.tokens.get(index).value);
            } else if (Arrays.arraySize(index)[0] != 0) {
                ret = Arrays.arraySize(index);
            } else if (Matrixes.matrixRowSize(index)[0] != 0) {
                ret = Matrixes.matrixRowSize(index);
            } else if (Matrixes.matrixColumnSize(index)[0] != 0) {
                ret = Matrixes.matrixColumnSize(index);
            } else if (index + 5 < Parser.tokens.size() && (int) Arrays.getArrayValue(index, 1).first != 0) {
                Pair<Integer, Integer> retPair = Arrays.getArrayValue(index, 1);
                index = retPair.first;
                ret[1] = retPair.second;
            } else if (index + 7 < Parser.tokens.size() && (int) Matrixes.getMatrixValue(index, 1).first != 0) {
                Pair<Integer, Integer> retPair = Matrixes.getMatrixValue(index, 1);
                index = retPair.first;
                ret[1] = retPair.second;
            } else {
                if (Parser.tokens.get(index).value.chars().allMatch(Character::isDigit)) {
                    ret[1] = Integer.parseInt(Parser.tokens.get(index).value);
                }
            }
            ret[0] = index + 1;
            return ret;
        } else if (Parser.tokens.get(index).key.equals("MAX")) {
            ret = Max.mathMaxInt(index);
            return ret;
        } else if (Parser.tokens.get(index).key.equals("MIN")) {
            ret = Min.mathMinInt(index);
            return ret;
        } else if (Parser.tokens.get(index).key.equals("SQRT")) {
            ret = Sqrt.mathSqrtInt(index);
            return ret;
        } else if (Parser.tokens.get(index).key.equals("POW")) {
            ret = Pow.mathPowInt(index);
            return ret;
        } else if (Parser.tokens.get(index).key.equals("ABS")) {
            ret = Abs.mathAbsInt(index);
            return ret;
        } else if (Parser.tokens.get(index).key.equals("L_PARENTHESES")) {
            int[] retV = expressionInt(index + 1, ret[1]);
            index = retV[0];
            value = retV[1];
            if (index == 0) {
                ret[0] = 0;
                return ret;
            } else {
                if (index < Parser.tokens.size() && Parser.tokens.get(index).key.equals("R_PARENTHESES")) {
                    ret[0] = index + 1;
                    ret[1] = value;
                    return ret;
                } else {
                    ret[0] = 0;
                    return ret;
                }
            }
        } else if (!Parser.tokens.get(index).key.equals("EQUAL_TO") && !Parser.tokens.get(index).key.equals("NOT_EQUAL")
                && !Parser.tokens.get(index).key.equals("GREATER_EQUAL") && !Parser.tokens.get(index).key.equals("LESS_EQUAL")
                && !Parser.tokens.get(index).key.equals("LESS_THAN") && !Parser.tokens.get(index).key.equals("GREATER_THAN")) {
            ret[0] = 0;
            return ret;
        } else {
            return ret;
        }
    }
}
