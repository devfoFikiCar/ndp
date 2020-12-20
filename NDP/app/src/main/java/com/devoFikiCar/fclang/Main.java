/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.fclang;


import com.devoFikiCar.fclang.lexer.Lexer;
import com.devoFikiCar.fclang.parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    public static ArrayList<Token> tokens = new ArrayList<>();
    public static ArrayList<String> code = new ArrayList<>();

    public static String data;

    /**
     * @param args filePath required, -dev with -m for minimal debug and -f for full
     */
    public static void main(String[] args) {
        switch (args.length) {
            case 0: {
                System.out.println("Enter the file path");
                break;
            }
            case 1: {
                setData(args[0]);
                setTokens();
                runParser();
                break;
            }
            case 2: {
                System.out.println("Invalid option " + args[0]);
                break;
            }
            case 3: {
                if (args[0].equals("-dev")) {
                    switch (args[1]) {
                        case "-m": {
                            setData(new File(args[2]).getAbsolutePath());
                            setTokens();
                            devM();
                            runParser();
                            break;
                        }
                        case "-f": {
                            setData(new File(args[2]).getAbsolutePath());
                            setTokens();
                            devF();
                            runParser();
                            break;
                        }
                    }
                }
                break;
            }
        }
    }

    /**
     * runParser executes code and checks for errors.
     */
    public static void runParser() {
        try {
            System.out.println("======================================");
            System.out.println("Beginning of FCLang execution: ");
            System.out.println("======================================");
            Parser.parse(tokens, 0, tokens.size());
            if (!Parser.error) {
                System.out.println("======================================");
                System.out.println("Successful execution.");
                System.out.println("======================================");
            } else {
                System.out.println("======================================");
                System.out.println("Error occurred.");
                System.out.println("======================================");
            }
        } catch (Exception ex) {
            System.out.println("======================================");
            System.out.println("Error occurred.");
            System.out.println("======================================");
        }
        clearLists();
    }

    public static void setData(String filePath) {
        Main.data = readFile(filePath);
    }

    public static void setTokens() {
        Main.tokens = Lexer.lexer();
    }

    /**
     * devM developer minimal prints size of tokens.
     */
    public static void devM() {
        System.out.println("Size of tokens after lexing: " + tokens.size());
    }

    /**
     * devF developer full prints size of tokens and each of them.
     */
    public static void devF() {
        System.out.println("======================================");
        System.out.println("Beginning of FCLang dev debug: ");
        System.out.println("======================================");
        System.out.println("Size of tokens after lexing: " + tokens.size());
        for (Token t : tokens) {
            System.out.println(t.toString());
        }
        System.out.println("======================================");
        System.out.println("Ending of FCLang dev debug: ");
        System.out.println("======================================");
    }

    /**
     * readFile safes code in string and ArrayList.
     *
     * @param fileName path relative to jar file
     * @return code backup
     */
    public static String readFile(String fileName) {
        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(new File(fileName)));
            String line = br.readLine();
            while (line != null) {
                code.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * Always clear memory.
     */
    public static void clearLists() {
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
