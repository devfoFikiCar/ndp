/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.fclang.parser.standard;

public class Names {

    /**
     * redeclareNames detects the correct datatype and reassigns value
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int redeclareNames(int index) {
        --index;
        int intReturn = Declaration.declareInt(index);
        int decimalReturn = Declaration.declareDecimal(index);
        int boolReturn = Declaration.declareBool(index);
        int stringReturn = Declaration.declareString(index);
        if (intReturn != 0) return intReturn;
        if (decimalReturn != 0) return decimalReturn;
        if (boolReturn != 0) return boolReturn;
        if (stringReturn != 0) return stringReturn;
        ++index;
        int arraySortReturn = Arrays.arraySort(index);
        int setIntArray = Arrays.setArrayValueInt(index);
        int setDecimalArray = Arrays.setArrayValueDecimal(index);
        int setStringArray = Arrays.setArrayValueString(index);
        int setBoolArray = Arrays.setArrayValueBool(index);
        if (arraySortReturn != 0) return arraySortReturn;
        if (setIntArray != 0) return setIntArray;
        if (setDecimalArray != 0) return setDecimalArray;
        if (setStringArray != 0) return setStringArray;
        if (setBoolArray != 0) return setBoolArray;
        int setIntMatrix = Matrixes.setMatrixValueInt(index);
        int setDecimalMatrix = Matrixes.setMatrixValueDecimal(index);
        int setStringMatrix = Matrixes.setMatrixValueString(index);
        int setBoolMatrix = Matrixes.setMatrixValueBool(index);
        if (setIntMatrix != 0) return setIntMatrix;
        if (setDecimalMatrix != 0) return setDecimalMatrix;
        if (setStringMatrix != 0) return setStringMatrix;
        return setBoolMatrix;
    }
}
