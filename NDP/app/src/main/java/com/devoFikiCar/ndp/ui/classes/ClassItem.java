package com.devoFikiCar.ndp.ui.classes;

public class ClassItem {
    private String classTitle;
    private String classID;

    public ClassItem(String classTitle, String classID) {
        this.classTitle = classTitle;
        this.classID = classID;
    }

    public String getClassTitle() {
        return classTitle;
    }

    public String getClassID() {
        return classID;
    }
}
