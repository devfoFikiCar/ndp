package com.devoFikiCar.ndp.ui.classes.teacher;

import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.helper.userSave;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

public class ClassesTeacherFragment extends Fragment {

    private MainViewModel mViewModel;
    private FloatingActionButton btCreateClass;
    private FirebaseFirestore firestore;

    public static ClassesTeacherFragment newInstance() {
        return new ClassesTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.classes_teacher_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        btCreateClass = (FloatingActionButton) root.findViewById(R.id.createClass);

        mViewModel.setUser(userSave.user);

        firestore = FirebaseFirestore.getInstance();

        btCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Creating new class");

                final EditText classTitle = new EditText(getActivity());
                classTitle.setMaxLines(1);
                alertDialog.setView(classTitle);

                alertDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.createClass(firestore, classTitle.getText().toString(), getContext());
                        Toast.makeText(getActivity(), "Class created", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // todo
                        Toast.makeText(getActivity(), "Cancel class creation", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
            }
        });

        return root;
    }
}