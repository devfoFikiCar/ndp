package com.devoFikiCar.fclang.parser.IO;



import com.devoFikiCar.fclang.parser.Parser;

import java.util.Scanner;

public class GetInput {
    static final Scanner scanner = new Scanner(System.in);

    /**
     * Single int input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int getInputInt(int index) {
        if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index).key.equals("GET_INT") && Parser.tokens.get(index + 1).key.equals("LESS_THAN")) {
            int input = scanner.nextInt();
            Parser.intStore.put(Parser.tokens.get(index - 2).value, input);
            return ++index;
        }
        return 0;
    }

    /**
     * Single double/decimal input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int getInputDecimal(int index) {
        if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index).key.equals("GET_DECIMAL") && Parser.tokens.get(index + 1).key.equals("LESS_THAN")) {
            double input = scanner.nextDouble();
            Parser.decimalStore.put(Parser.tokens.get(index - 2).value, input);
            return ++index;
        }
        return 0;
    }

    /**
     * Single string input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int getInputString(int index) {
        if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index).key.equals("GET_STRING") && Parser.tokens.get(index + 1).key.equals("LESS_THAN")) {
            String input = scanner.nextLine();
            String ret = "\"" + input + "\"";
            Parser.stringStore.put(Parser.tokens.get(index - 2).value, ret);
            return ++index;
        }
        return 0;
    }

    /**
     * Single bool input.
     *
     * @param index begin position for parsing
     * @return index to continue parsing from
     */
    public static int getInputBool(int index) {
        if (index + 1 < Parser.tokens.size() && Parser.tokens.get(index).key.equals("GET_BOOL") && Parser.tokens.get(index + 1).key.equals("LESS_THAN")) {
            boolean input = scanner.nextBoolean();
            Parser.boolStore.put(Parser.tokens.get(index - 2).value, input);
            return ++index;
        }
        return 0;
    }
}
