/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

package com.devoFikiCar.ndp.ui.assignments.teacher;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.assignments.AssignmentItem;
import com.devoFikiCar.ndp.ui.assignments.AssignmentsAdapter;
import com.devoFikiCar.ndp.ui.create.assignment.CreateAssignmentTeacherFragment;
import com.devoFikiCar.ndp.ui.create.lecture.CreateLectureTeacherFragment;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class AssignmentsTeacherFragment extends Fragment {

    private AssignmentsTeacherModel mViewModel;
    private RecyclerView recyclerView;
    private AssignmentsAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<AssignmentItem> assignmentItems = new ArrayList<>();
    private FirebaseFirestore firestore;
    private Button btCreateAssignment;

    public static AssignmentsTeacherFragment newInstance() {
        return new AssignmentsTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.assignments_teacher_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(AssignmentsTeacherModel.class);
        firestore = FirebaseFirestore.getInstance();
        mViewModel.assignmentUpdate(firestore);

        buildRecyclerView(root);

        setUpButton(root);

        mViewModel.getAssignmentsIDs().observe(getViewLifecycleOwner(), assignmentsList);

        return root;
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvAssignmentListTeacher);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new AssignmentsAdapter(assignmentItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AssignmentsAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                System.out.println("clicked");
            }
        });
    }

    private void setUpButton(View root) {
        btCreateAssignment = (Button) root.findViewById(R.id.btCreateAssignment);
        btCreateAssignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("clicked");
                getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new CreateAssignmentTeacherFragment())
                        .addToBackStack("AssignmentTeacherFragment")
                        .commit();
            }
        });
    }

    final Observer<ArrayList<HashMap<String, String>>> assignmentsList = new Observer<ArrayList<HashMap<String, String>>>() {
        @Override
        public void onChanged(ArrayList<HashMap<String, String>> hashMaps) {
            if (assignmentItems != null) {
                assignmentItems.clear();
            }
            for (int i = 0; i < hashMaps.size(); i++) {
                assignmentItems.add(new AssignmentItem(hashMaps.get(i).get("assignmentTitle") , hashMaps.get(i).get("assignmentID")));
            }
            adapter.notifyDataSetChanged();
        }
    };
}