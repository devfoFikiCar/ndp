package com.devoFikiCar.ndp.ui.playgroundl;

import androidx.lifecycle.ViewModel;

import com.devoFikiCar.fclang.StartFClang;
import com.devoFikiCar.ndp.util.User;
import com.devoFikiCar.ndp.helper.userSave;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class PlaygroundLViewModel extends ViewModel {
    private ArrayList<String> inputCode = new ArrayList<>();
    private ArrayList<String> output = new ArrayList<>();
    private Queue<Object> inputData = new LinkedList<>();
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
            inputData.add((Object) s1);
    }

    private void setStdin(String stdin) {
        this.stdin = stdin;
    }

    public String getStdin() {
        return stdin;
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