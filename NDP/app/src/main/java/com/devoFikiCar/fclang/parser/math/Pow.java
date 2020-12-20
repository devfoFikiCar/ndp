/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.fclang.parser.math;

import com.devoFikiCar.fclang.parser.Parser;

public class Pow extends TwoExpressions {

    /**
     * mathPowInt checks if series of tokens fits type int pow function.
     *
     * @param index position of POW token
     * @return index to continue parsing from and value
     */
    public static int[] mathPowInt(int index) {
        int[] ret = new int[2];
        if (!Parser.tokens.get(index).key.equals("POW")) {
            return ret;
        }
        index++;
        int[] retInt = getTwoIntExpressions(index);
        ret[0] = retInt[0];
        ret[1] = (int) Math.pow(retInt[1], retInt[2]);
        return ret;
    }

    /**
     * mathPowDecimal checks if series of tokens fits type decimal pow function.
     *
     * @param index position of POW token
     * @return index to continue parsing from and value
     */
    public static double[] mathPowDecimal(int index) {
        double[] ret = new double[2];
        if (!Parser.tokens.get(index).key.equals("POW")) {
            return ret;
        }
        index++;
        double[] retDecimal = getTwoDecimalExpressions(index);
        ret[0] = retDecimal[0];
        ret[1] = Math.pow(retDecimal[1], retDecimal[2]);
        return ret;
    }
}
