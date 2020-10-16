package com.devoFikiCar.fclang;

import android.graphics.Color;

public class Token {
    /*public static final Color lightVariableNames = new Color(56, 58, 66); #383a42
    public static final Color lightKeywords = new Color(166, 38, 164); #a626a4
    public static final Color lightStrings = new Color(80, 161, 79); #50a14f
    public static final Color lightBID = new Color(152, 104, 1); #986801
    public static final Color lightMethods = new Color(64, 120, 242); #4078f2
    public static final Color lightSigns = new Color(1, 132, 188); #0184bc
    public static final Color lightBP = new Color(56, 58, 66); #383a42
    public static final Color lightComments = new Color(204, 223, 50); #ccdf32
    public static final Color lightBackground = new Color(250, 250, 250); #fafafa

    public static final Color darkVariableNames = new Color(121, 171, 255); #79aaff
    public static final Color darkKeywords = new Color(255, 255, 255); #ffffff
    public static final Color darkStrings = new Color(255, 198, 0); #ffc600
    public static final Color darkBID = new Color(127, 179, 71); #7fb347
    public static final Color darkMethods = new Color(190, 214, 255); #bed6ff
    public static final Color darkSigns = new Color(216, 216, 216); #d8d8d8
    public static final Color darkComments = new Color(204, 223, 50); #ccdf32
    public static final Color darkBackground = new Color(30, 30, 30); #1e1e1e
    */

    public String key;
    public String value;
    public Color colorLight;
    public Color colorDark;
    public int posInLine = -1;
    public int lineNumber = -1;


    public Token(String Key, String Value) {
        key = Key;
        value = Value;
    }

    public Token(String Key, String Value, int LineNumber) {
        key = Key;
        value = Value;
        lineNumber = LineNumber;
    }

    public Token(String key, String value, Color colorLight, Color colorDark, int lineNumber) {
        this.key = key;
        this.value = value;
        this.colorLight = colorLight;
        this.colorDark = colorDark;
        this.lineNumber = lineNumber;
    }

    @Override
    public String toString() {
        return "Token{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", posInLine=" + posInLine +
                ", lineNumber=" + lineNumber +
                '}';
    }
}
