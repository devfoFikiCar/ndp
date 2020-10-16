package com.devoFikiCar.ndp;

public class User {
    private boolean isTeacher = false;
    private boolean isLight = false;
    private String userID = "";

    public User(boolean isTeacher, String userID, boolean isLight) {
        this.isTeacher = isTeacher;
        this.userID = userID;
        this.isLight = isLight;
    }

    public User(User user) {
        this.isTeacher = user.isTeacher;
        this.userID = user.userID;
        this.isLight = user.isLight;
    }

    public boolean isTeacher() {
        return isTeacher;
    }

    public boolean isLight() {
        return isLight;
    }

    public void setLight(boolean light) {
        isLight = light;
    }

    public String getUserID() {
        return userID;
    }

    @Override
    public String toString() {
        return "User{" +
                "isTeacher=" + isTeacher +
                ", isLight=" + isLight +
                ", userID='" + userID + '\'' +
                '}';
    }
}
