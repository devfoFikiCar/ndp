/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.classes.teacher;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.classes.ClassItem;
import com.devoFikiCar.ndp.ui.classes.ClassesAdapter;
import com.devoFikiCar.ndp.ui.lectureassignment.LectureAssignmentFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassesTeacherFragment extends Fragment {

    final Observer<Integer> change = new Observer<Integer>() {
        @Override
        public void onChanged(Integer integer) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new LectureAssignmentFragment())
                    .addToBackStack(null)
                    .commit();
        }
    };
    private ClassesTeacherViewModel mViewModel;
    private Button btCreateClass;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private ClassesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private final ArrayList<ClassItem> classItems = new ArrayList<>();
    final Observer<ArrayList<HashMap<String, String>>> classesList = new Observer<ArrayList<HashMap<String, String>>>() {
        @Override
        public void onChanged(ArrayList<HashMap<String, String>> strings) {
            if (classItems != null) {
                classItems.clear();
            }
            for (int i = 0; i < strings.size(); i++) {
                classItems.add(new ClassItem(strings.get(i).get("classTitle"), "Class code: " + strings.get(i).get("classID")));
            }
            adapter.notifyDataSetChanged();
        }
    };

    public static ClassesTeacherFragment newInstance() {
        return new ClassesTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.classes_teacher_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(ClassesTeacherViewModel.class);

        btCreateClass = root.findViewById(R.id.createClass);

        firestore = FirebaseFirestore.getInstance();

        buildRecyclerView(root);

        btCreateClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setTitle("Create new class");

                final EditText classTitle = new EditText(getActivity());
                classTitle.setMaxLines(1);
                classTitle.setHint("Class title");
                alertDialog.setView(classTitle);

                alertDialog.setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mViewModel.createClass(firestore, classTitle.getText().toString(), getContext());
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity(), "Cancel class creation", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
            }
        });

        mViewModel.getIdTitles().observe(getViewLifecycleOwner(), classesList);

        mViewModel.getChange().observe(getViewLifecycleOwner(), change);

        return root;
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvClassesTeacher);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ClassesAdapter(classItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new ClassesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("Clicked: " + position);
                mViewModel.loadClass(firestore, position, getContext());
            }
        });
    }
}