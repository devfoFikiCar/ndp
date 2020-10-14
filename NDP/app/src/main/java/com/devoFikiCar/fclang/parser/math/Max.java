package com.devoFikiCar.fclang.parser.math;

import com.devoFikiCar.fclang.parser.Parser;

public class Max extends TwoExpressions {

    /**
     * mathMaxInt checks if series of tokens fits type int max function.
     *
     * @param index position of MAX token
     * @return index to continue parsing from and value
     */
    public static int[] mathMaxInt(int index) {
        int[] ret = new int[2];
        if (!Parser.tokens.get(index).key.equals("MAX")) {
            return ret;
        }
        index++;
        int[] retInt = getTwoIntExpressions(index);
        ret[0] = retInt[0];
        ret[1] = Math.max(retInt[1], retInt[2]);
        return ret;
    }

    /**
     * mathMaxDecimal checks if series of tokens fits type decimal max function.
     *
     * @param index position of MAX token
     * @return index to continue parsing from and value
     */
    public static double[] mathMaxDecimal(int index) {
        double[] ret = new double[2];
        if (!Parser.tokens.get(index).key.equals("MAX")) {
            return ret;
        }
        index++;
        double[] retDecimal = getTwoDecimalExpressions(index);
        ret[0] = retDecimal[0];
        ret[1] = Math.max(retDecimal[1], retDecimal[2]);
        return ret;
    }
}