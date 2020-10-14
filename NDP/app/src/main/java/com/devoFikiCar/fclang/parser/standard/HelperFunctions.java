package com.devoFikiCar.fclang.parser.standard;

import com.devoFikiCar.fclang.parser.Parser;

public class HelperFunctions {

    /**
     * searchRightBracket is a helper function used to detect right bracket
     *
     * @param index position to start the search from
     * @return position of last correct right bracket
     */
    public static int searchRightBracket(int index) {
        int rPos = 0;
        int nSkip = 0;
        for (int i = ++index; i < Parser.tokens.size(); i++) {
            if (Parser.tokens.get(i).key.equals("L_BRACES")) nSkip++;
            else if (Parser.tokens.get(i).key.equals("R_BRACES")) {
                if (nSkip == 0) {
                    return i;
                } else {
                    nSkip--;
                }
            }
        }
        return rPos;
    }
}
