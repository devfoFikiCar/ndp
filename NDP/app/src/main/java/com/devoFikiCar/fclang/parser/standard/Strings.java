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

public class Strings {

    /**
     * isString checks if token at index is string or is stringStore contains string with tokens name
     *
     * @param index supposed position of string
     * @return Pair with key of 0 if its not a string or index if its a string and value of string
     */
    public static androidx.core.util.Pair<Integer, String> isString(int index) {
        Pair ret = new Pair(0, "");
        if (Parser.tokens.get(index).key.equals("STRING")) {
            return new Pair(index, Parser.tokens.get(index).value);
        }
        if (Parser.stringStore.containsKey(Parser.tokens.get(index).value)) {
            return new Pair(index, Parser.stringStore.get(Parser.tokens.get(index).value));
        }
        if (index + 5 < Parser.tokens.size() && (int) Arrays.getArrayValue(index, 3).first != 0) {
            return new Pair(Arrays.getArrayValue(index, 3).first, Arrays.getArrayValue(index, 3).second);
        }
        if (index + 7 < Parser.tokens.size() && (int) Matrixes.getMatrixValue(index, 3).first != 0) {
            return new Pair(Matrixes.getMatrixValue(index, 3).first, Matrixes.getMatrixValue(index, 3).second);
        }
        return ret;
    }
}
