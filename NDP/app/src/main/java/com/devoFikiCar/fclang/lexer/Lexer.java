package com.devoFikiCar.fclang.lexer;



import com.devoFikiCar.fclang.Main;
import com.devoFikiCar.fclang.Token;

import java.util.ArrayList;

public class Lexer {
    public static int lineNumber = -1;
    private static int curSum = 0;
    private static int oldSum = 0;

    /**
     * lexer converts string to meaningful tokens with colors that later get used in parser.
     *
     * @return ArraysList filled with tokens
     */
    public static ArrayList<Token> lexer() {
        ArrayList<Token> tokens = new ArrayList<>();
        for (int line = 0; line < Main.code.size(); line++) {
            lineNumber++;
            String currentLine = Main.code.get(line) + " ";
            StringBuilder temp = new StringBuilder();
            boolean skip;
            oldSum = tokens.size();
            for (int i = 0; i < currentLine.length(); i++) {
                curSum = tokens.size();
                skip = false;
                switch (currentLine.charAt(i)) {
                    case ' ': {
                        tokens.add(lexTemp(temp.toString()));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case '=': {
                        if (currentLine.charAt(i + 1) == '=') {
                            i++;
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("EQUAL_TO", "==", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        } else {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("EQUALS", "=", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '+': {
                        tokens.add(lexTemp(temp.toString()));
                        tokens.add(new Token("ADDITION", "+", lineNumber));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case '-': {
                        if (currentLine.charAt(i - 1) != 'L') {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("SUBTRACTION", "-", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '*': {
                        tokens.add(lexTemp(temp.toString()));
                        tokens.add(new Token("MULTIPLICATION", "*", lineNumber));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case '/': {
                        if (currentLine.charAt(i + 1) == '/') {
                            i = currentLine.length() - 1;
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("COMMENT", "", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        } else {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("DIVISION", "/", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '.': {
                        if (!temp.toString().matches("\\d+")) {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("DOT", ".", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '(': {
                        tokens.add(lexTemp(temp.toString()));
                        tokens.add(new Token("L_PARENTHESES", "(", lineNumber));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case ')': {
                        tokens.add(lexTemp(temp.toString()));
                        tokens.add(new Token("R_PARENTHESES", ")", lineNumber));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case '{': {
                        tokens.add(lexTemp(temp.toString()));
                        tokens.add(new Token("L_BRACES", "{", lineNumber));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case '}': {
                        tokens.add(lexTemp(temp.toString()));
                        tokens.add(new Token("R_BRACES", "}", lineNumber));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case '&': {
                        if (currentLine.charAt(i + 1) == '&') {
                            i++;
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("AND", "&&", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case ',': {
                        tokens.add(lexTemp(temp.toString()));
                        tokens.add(new Token("COMMA", ",", lineNumber));
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                    case '>': {
                        if (currentLine.charAt(i + 1) == '=') {
                            i++;
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("GREATER_EQUAL", ">=", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        } else {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("GREATER_THAN", ">", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '<': {
                        if (currentLine.charAt(i + 1) == '=') {
                            i++;
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("LESS_EQUAL", "<=", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        } else {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("LESS_THAN", "<", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '!': {
                        if (currentLine.charAt(i + 1) == '=') {
                            i++;
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("NOT_EQUAL", "!=", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        } else {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("NOT", "!", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '|': {
                        if (currentLine.charAt(i + 1) == '|') {
                            i++;
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("OR", "||", lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        } else {
                            tokens.add(lexTemp(temp.toString()));
                            tokens.add(new Token("SPLIT", "|", Token.lineNumber));
                            temp = new StringBuilder();
                            skip = true;
                        }
                        break;
                    }
                    case '"': {
                        tokens.add(lexTemp(temp.toString()));
                        temp = new StringBuilder("\"");
                        for (int j = i + 1; j < currentLine.length(); j++) {
                            if (currentLine.charAt(j) == '"') {
                                tokens.add(new Token("STRING", temp + "\"", lineNumber));
                                i = j;
                            } else temp.append(currentLine.charAt(j));
                        }
                        temp = new StringBuilder();
                        skip = true;
                        break;
                    }
                }
                if (!skip)
                    temp.append(currentLine.charAt(i));
            }
        }
        lineNumber = -1;
        checkForEmptyTokens(tokens);
        return tokens;
    }

    /**
     * checkFotEmptyTokens removes all the tokens from token list that are empty which is crucial
     * so that later in parser all the indexes and tokens align correctly.
     *
     * @param tokens list to be checked for errors
     */
    public static void checkForEmptyTokens(ArrayList<Token> tokens) {
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).key.equals("")) {
                tokens.remove(i);
                i--;
            }
        }
    }

    /**
     * lexTemp checks if the current string is or a keyword, value or name.
     *
     * @param temp string that is in between special characters
     * @return token with correct key and colors.
     */
    public static Token lexTemp(String temp) {
        Token token = new Token("", "");
        if (temp.equals("print")) {
            return new Token("PRINT", "print", lineNumber);
        } else if (temp.equals("int")) {
            return new Token("INT_T", "int", lineNumber);
        } else if (temp.equals("decimal")) {
            return new Token("DECIMAL_T", "decimal", lineNumber);
        } else if (temp.equals("string")) {
            return new Token("STRING_T", "string", lineNumber);
        } else if (temp.equals("max")) {
            return new Token("MAX", "max", lineNumber);
        } else if (temp.equals("min")) {
            return new Token("MIN", "min", lineNumber);
        } else if (temp.equals("get")) {
            return new Token("GET", "get", lineNumber);
        } else if (temp.equals("bool")) {
            return new Token("BOOL_T", "bool", lineNumber);
        } else if (temp.equals("pow")) {
            return new Token("POW", "pow", lineNumber);
        } else if (temp.equals("sqrt")) {
            return new Token("SQRT", "sqrt", lineNumber);
        } else if (temp.equals("if")) {
            return new Token("IF", "if", lineNumber);
        } else if (temp.equals("size")) {
            return new Token("SIZE", "size", lineNumber);
        } else if (temp.equals("set")) {
            return new Token("SET", "set", lineNumber);
        } else if (temp.equals("else")) {
            return new Token("ELSE", "else", lineNumber);
        } else if (temp.equals("for")) {
            return new Token("FOR", "for", lineNumber);
        } else if (temp.equals("sort")) {
            return new Token("SORT", "sort", lineNumber);
        } else if (temp.equals("goto")) {
            return new Token("GOTO", "goto", lineNumber);
        } else if (temp.equals("getInt")) {
            return new Token("GET_INT", "getInt", lineNumber);
        } else if (temp.equals("getDecimal")) {
            return new Token("GET_DECIMAL", "getDecimal", lineNumber);
        } else if (temp.equals("IntMatrix")) {
            return new Token("INT_MATRIX", "IntMatrix", lineNumber);
        } else if (temp.equals("DecimalMatrix")) {
            return new Token("DECIMAL_MATRIX", "DecimalMatrix", lineNumber);
        } else if (temp.equals("StringMatrix")) {
            return new Token("STRING_MATRIX", "StringMatrix", lineNumber);
        } else if (temp.equals("BoolMatrix")) {
            return new Token("BOOL_MATRIX", "BoolMatrix", lineNumber);
        } else if (temp.equals("rowSize")) {
            return new Token("ROW_SIZE", "rowSize", lineNumber);
        } else if (temp.equals("columnSize")) {
            return new Token("COLUMN_SIZE", "columnSize", lineNumber);
        } else if (temp.equals("IntArray")) {
            return new Token("INT_ARRAY", "IntArray", lineNumber);
        } else if (temp.equals("abs")) {
            return new Token("ABS", "abs", lineNumber);
        } else if (temp.equals("DecimalArray")) {
            return new Token("DECIMAL_ARRAY", "DecimalArray", lineNumber);
        } else if (temp.equals("StringArray")) {
            return new Token("STRING_ARRAY", "StringArray", lineNumber);
        } else if (temp.equals("BoolArray")) {
            return new Token("BOOL_ARRAY", "BoolArray", lineNumber);
        } else if (temp.equals("new")) {
            return new Token("NEW", "new", lineNumber);
        } else if (temp.equals("getString")) {
            return new Token("GET_STRING", "getString", lineNumber);
        } else if (temp.equals("getBool")) {
            return new Token("GET_BOOL", "getBool", lineNumber);
        } else if (temp.matches("(\".*\")")) {
            return new Token("STRING", temp, lineNumber);
        } else if (temp.equals("true") || temp.equals("false")) {
            return new Token("BOOL", temp, lineNumber);
        } else if (temp.matches("\\d+")) {
            return new Token("INT", temp, lineNumber);
        } else if (temp.matches("\\d+(\\.\\d{1,2})?")) {
            return new Token("DECIMAL", temp, lineNumber);
        } else if (temp.matches("^L-.*")) {
            return new Token("L_GOTO", temp, lineNumber);
        } else if (temp.matches(".*") && !temp.equals("")) {
            Token token1 = new Token("NAME", temp, lineNumber);
            if (Math.abs(curSum - oldSum) == 0) token1.posInLine = 0;
            else token1.posInLine = 1;
            return token1;
        }
        return token;
    }
}