package com.devoFikiCar.ndp.ui.login;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.devoFikiCar.ndp.LogIn;
import com.devoFikiCar.ndp.MainActivity;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.User;

public class LogInFragment extends Fragment {

    private LogInViewModel mViewModel;
    private Button btLogIn;
    private EditText etUser;
    private EditText etPassword;
    private CheckBox cbTeacher;
    private TextView tvPlayInPlaygroundNL;

    public static LogInFragment newInstance() {
        return new LogInFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(LogInViewModel.class);

        btLogIn = (Button) root.findViewById(R.id.btLogIn);
        etUser = (EditText) root.findViewById(R.id.etUsername);
        etPassword = (EditText) root.findViewById(R.id.etPassword);
        cbTeacher = (CheckBox) root.findViewById(R.id.cbTeacher);
        tvPlayInPlaygroundNL = (TextView) root.findViewById(R.id.tvPlayInPlaygroundNL);

        btLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.setUser(cbTeacher.isChecked(), etUser.getText().toString(), false);
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                root.getContext().startActivity(intent);
            }
        });

        return root;
    }
}