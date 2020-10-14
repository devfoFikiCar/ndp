package com.devoFikiCar.fclang.parser.math;

import com.devoFikiCar.fclang.parser.Parser;

public class Min extends TwoExpressions {

    /**
     * mathMinInt checks if series of tokens fits type int min function.
     *
     * @param index position of MIN token
     * @return index to continue parsing from and value
     */
    public static int[] mathMinInt(int index) {
        int[] ret = new int[2];
        if (!Parser.tokens.get(index).key.equals("MIN")) {
            return ret;
        }
        index++;
        int[] retInt = getTwoIntExpressions(index);
        ret[0] = retInt[0];
        ret[1] = Math.min(retInt[1], retInt[2]);
        return ret;
    }

    /**
     * mathMinDecimal checks if series of tokens fits type decimal min function.
     *
     * @param index position of MIN token
     * @return index to continue parsing from and value
     */
    public static double[] mathMinDecimal(int index) {
        double[] ret = new double[2];
        if (!Parser.tokens.get(index).key.equals("MIN")) {
            return ret;
        }
        index++;
        double[] retDecimal = getTwoDecimalExpressions(index);
        ret[0] = retDecimal[0];
        ret[1] = Math.min(retDecimal[1], retDecimal[2]);
        return ret;
    }
}
