/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

package com.devoFikiCar.fclang.parser.standard;

import com.devoFikiCar.fclang.parser.Parser;

public class IfStatement {

    /**
     * ifStatement decides to execute if or not.
     *
     * @param index begin position for parsing
     * @return 0: index 1: index of right bracket 2: skip right bracket 3: execute else 4: begin else 5: end else
     */
    public static int[] ifStatement(int index) {
        int[] ret = new int[6];
        if (Parser.tokens.get(index + 1).key.equals("L_PARENTHESES")) {
            index += 2;
            int[] ret_v = Bools.bool(index);
            index = ret_v[0];
            if (index != 0) {
                if (index < Parser.tokens.size() && Parser.tokens.get(index).key.equals("R_PARENTHESES")) {
                    if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index + 1).key.equals("L_BRACES")) {
                        index++;
                        int r_pos = HelperFunctions.searchRightBracket(index);
                        int[] ret_1 = elseStatement(r_pos);
                        if (r_pos != 0) {
                            if (ret_v[1] == 1) {
                                ret[0] = index;
                                ret[1] = r_pos;
                                ret[2] = 0;
                                if (ret_1[0] != 0) {
                                    ret[3] = 0;
                                    ret[4] = ret_1[0];
                                    ret[5] = ret_1[1];
                                } else {
                                    ret[3] = 0;
                                    ret[4] = 0;
                                    ret[5] = 0;
                                }
                            } else {
                                ret[0] = index;
                                ret[1] = r_pos;
                                ret[2] = 1;
                                if (ret_1[0] != 0) {
                                    ret[3] = 1;
                                    ret[4] = ret_1[0];
                                    ret[5] = ret_1[1];
                                } else {
                                    ret[3] = 0;
                                    ret[4] = 0;
                                    ret[5] = 0;
                                }
                            }
                        } else {
                            ret[0] = 0;
                            return ret;
                        }
                    } else {
                        ret[0] = 0;
                        return ret;
                    }
                } else {
                    ret[0] = 0;
                    return ret;
                }
            } else {
                ret[0] = 0;
                return ret;
            }
        } else {
            ret[0] = 0;
        }
        return ret;
    }

    /**
     * elseStatement safes start and end of else block.
     *
     * @param index begin position for parsing
     * @return index of left bracket and right bracket
     */
    public static int[] elseStatement(int index) {
        int[] ret = {0, 0};
        if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index + 1).key.equals("ELSE")) {
            index++;
            if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index + 1).key.equals("L_BRACES")) {
                index++;
                int r_pos = HelperFunctions.searchRightBracket(index);
                if (r_pos != 0) {
                    ret[0] = index;
                    ret[1] = r_pos;
                } else {
                    return ret;
                }
            } else {
                return ret;
            }
        }
        return ret;
    }
}
