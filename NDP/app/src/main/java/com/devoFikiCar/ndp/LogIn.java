package com.devoFikiCar.ndp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.devoFikiCar.ndp.ui.login.LogInFragment;

public class LogIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, LogInFragment.newInstance())
                    .commitNow();
        }
    }
}