package com.devoFikiCar.ndp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.devoFikiCar.ndp.ui.playgroundl.PlaygroundLFragment;

public class PlaygroundL extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playground_l_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PlaygroundLFragment.newInstance())
                    .commitNow();
        }
    }
}