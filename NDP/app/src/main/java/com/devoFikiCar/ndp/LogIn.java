/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 *
 */

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