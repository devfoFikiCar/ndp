package com.devoFikiCar.ndp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devoFikiCar.ndp.ui.info.InfoFragment;

public class Info extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, InfoFragment.newInstance())
                    .commitNow();
        }
    }
}