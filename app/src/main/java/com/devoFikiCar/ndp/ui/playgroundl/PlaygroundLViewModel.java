/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.playgroundl;

import androidx.lifecycle.ViewModel;

import com.devoFikiCar.fclang.StartFClang;
import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.util.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PlaygroundLViewModel extends ViewModel {
    private final ArrayList<String> inputCode = new ArrayList<>();
    private ArrayList<String> output = new ArrayList<>();
    private final Queue<Object> inputData = new LinkedList<>();
    private User user;
    private String text = "";
    private String stdin = "";

    public PlaygroundLViewModel() {
        init();
    }

    private void init() {
        setUser();
    }

    public User getUser() {
        return this.user;
    }

    public void setUser() {
        this.user = new User(userSave.user);
    }

    public Queue<Object> getInputData() {
        return inputData;
    }

    public void setInputData(String text) {
        inputData.clear();
        setStdin(text);
        for (String s1 : text.split("\n"))
            inputData.add(s1);
    }

    public String getStdin() {
        return stdin;
    }

    private void setStdin(String stdin) {
        this.stdin = stdin;
    }

    public void clearPlaygroundL() {
        inputCode.clear();
        output.clear();
    }

    public void setInputCode() {
        this.inputCode.addAll(Arrays.asList(text.split("\n")));
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }

    public String runCode(String text) {
        clearPlaygroundL();
        setText(text);
        setInputCode();
        setOutput(StartFClang.startFClang(inputCode, new LinkedList<>(inputData)));
        String out = "";
        for (String s : output) {
            out += s;
            out += "\n";
        }
        return out;
    }

    public String setInputText() {
        String inputText = "";

        for (Object o : inputData) {
            inputText += (String) o;
            inputText += "\n";
        }

        return inputText;
    }
}