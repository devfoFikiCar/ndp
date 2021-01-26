/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.fclang.parser.math;

import com.devoFikiCar.fclang.parser.Parser;
import com.devoFikiCar.fclang.parser.standard.Decimals;
import com.devoFikiCar.fclang.parser.standard.Integers;

public class OneExpression {

    /**
     * getOneIntExpression is helper function that checks if '(' INT ')' is next
     * sequence of tokens.
     *
     * @param index position of (
     * @return index to continue parsing from and value
     */
    public static int[] getOneIntExpression(int index) {
        int[] ret = new int[2];
        if (!Parser.tokens.get(index).key.equals("L_PARENTHESES")) {
            return ret;
        }
        index++;
        int[] retV = Integers.expressionInt(index, 0);
        if (retV[0] == 0) {
            return ret;
        }
        index = retV[0];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) {
            return ret;
        }
        ret[0] = index;
        ret[1] = retV[1];
        return ret;
    }

    /**
     * getOneDecimalExpression is helper function that checks if '(' DECIMAL ')' is next
     * sequence of tokens.
     *
     * @param index position of (
     * @return index to continue parsing from and value
     */
    public static double[] getOneDecimalExpression(int index) {
        double[] ret = new double[2];
        if (!Parser.tokens.get(index).key.equals("L_PARENTHESES")) {
            return ret;
        }
        index++;
        double[] retV = Decimals.expressionDecimal(index, 0);
        if (retV[0] == 0) {
            return ret;
        }
        index = (int) retV[0];
        if (!Parser.tokens.get(index).key.equals("R_PARENTHESES")) {
            return ret;
        }
        ret[0] = index;
        ret[1] = retV[1];
        return ret;
    }
}
