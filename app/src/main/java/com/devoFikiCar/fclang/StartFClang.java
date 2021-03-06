/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.fclang;

import com.devoFikiCar.fclang.lexer.Lexer;
import com.devoFikiCar.fclang.parser.Parser;

import java.util.ArrayList;
import java.util.Queue;

public class StartFClang {
    private static final ArrayList<String> output = new ArrayList<>();
    private static ArrayList<String> code = new ArrayList<>();
    private static ArrayList<Token> tokens = new ArrayList<>();
    private static Queue<Object> input;

    public static ArrayList<String> startFClang(ArrayList<String> code, Queue<Object> input) {
        try {
            setCode(code);
            setInput(input);
            setTokens();
            Parser.parse(tokens, 0, tokens.size());
            clearLists();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return output;
    }

    public static ArrayList<String> getOutput() {
        return output;
    }

    public static ArrayList<String> getCode() {
        return code;
    }

    public static void setCode(ArrayList<String> code) {
        StartFClang.code = code;
    }

    public static Queue<Object> getInput() {
        return input;
    }

    public static void setInput(Queue<Object> input) {
        StartFClang.input = input;
    }

    public static ArrayList<Token> getTokens() {
        return tokens;
    }

    public static void setTokens() {
        StartFClang.tokens = Lexer.lexer();
    }

    private static void clearLists() {
        tokens.clear();
        code.clear();
        Parser.tokens.clear();
        Parser.skip.clear();
        Parser.intStore.clear();
        Parser.decimalStore.clear();
        Parser.stringStore.clear();
        Parser.boolStore.clear();
        Parser.gotoStore.clear();
        Parser.skipStore.clear();
        Parser.intArrayStore.clear();
        Parser.decimalArrayStore.clear();
        Parser.stringArrayStore.clear();
        Parser.boolArrayStore.clear();
        Parser.intMatrixStore.clear();
        Parser.decimalMatrixStore.clear();
        Parser.stringMatrixStore.clear();
        Parser.boolMatrixStore.clear();
        Parser.error = false;
    }
}
