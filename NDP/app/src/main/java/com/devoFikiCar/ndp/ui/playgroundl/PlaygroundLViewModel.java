package com.devoFikiCar.ndp.ui.playgroundl;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;

import androidx.lifecycle.ViewModel;

import com.devoFikiCar.fclang.StartFClang;
import com.devoFikiCar.ndp.User;
import com.devoFikiCar.ndp.ui.login.LogInViewModel;

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

    private ForegroundColorSpan lightName = new ForegroundColorSpan(Color.rgb(56, 58, 66));         // default
    private ForegroundColorSpan lightKeywords = new ForegroundColorSpan(Color.rgb(166, 38, 164));
    private ForegroundColorSpan lightStrings = new ForegroundColorSpan(Color.rgb(80, 161, 79));
    private ForegroundColorSpan lightBID = new ForegroundColorSpan(Color.rgb(152, 104, 1));
    private ForegroundColorSpan lightMethods = new ForegroundColorSpan(Color.rgb(64, 120, 242));
    private ForegroundColorSpan lightSigns = new ForegroundColorSpan(Color.rgb(1, 132, 188));
    private ForegroundColorSpan lightBP = new ForegroundColorSpan(Color.rgb(56, 58, 66));
    private ForegroundColorSpan lightComments = new ForegroundColorSpan(Color.rgb(204, 223, 50));

    private ForegroundColorSpan darkName = new ForegroundColorSpan(Color.rgb(121, 171, 255));           // default
    private ForegroundColorSpan darkKeywords = new ForegroundColorSpan(Color.rgb(255, 255, 255));
    private ForegroundColorSpan darkStrings = new ForegroundColorSpan(Color.rgb(255, 198, 0));
    private ForegroundColorSpan darkBID = new ForegroundColorSpan(Color.rgb(127, 179, 71));
    private ForegroundColorSpan darkMethods = new ForegroundColorSpan(Color.rgb(190, 214, 255));
    private ForegroundColorSpan darkSigns = new ForegroundColorSpan(Color.rgb(216, 216, 216));
    private ForegroundColorSpan darkComments = new ForegroundColorSpan(Color.rgb(204, 223, 50));

    public User getUser() {
        return this.user;
    }

    public void setUser() {
        this.user = LogInViewModel.getUser();
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