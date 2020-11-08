package com.devoFikiCar.ndp.ui.login;

public class CheckUserDB {
    // Implement users from db
    public static boolean checkCredentials(String user, String password, boolean isTeacher) {
        return user.equals("test") && password.equals("1234");
    }
}
