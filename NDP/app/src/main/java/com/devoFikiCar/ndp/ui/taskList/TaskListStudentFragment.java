/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2021
 */

package com.devoFikiCar.ndp.ui.taskList;

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

import com.devoFikiCar.fclang.parser.math.Abs;
import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.ui.taskEditor.student.TaskEditorStudentFragment;
import com.devoFikiCar.ndp.util.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class TaskListStudentFragment extends Fragment {

    private TaskListStudentViewModel mViewModel;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private TasksAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<TaskItem> taskItems = new ArrayList<>();

    public static TaskListStudentFragment newInstance() {
        return new TaskListStudentFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.task_list_student_fragment, container, false);

        mViewModel = new ViewModelProvider(this).get(TaskListStudentViewModel.class);
        firestore = FirebaseFirestore.getInstance();

        Bundle bundle = this.getArguments();
        int assignmentPosition = (int) bundle.get("position");

        mViewModel.loadTasks(firestore, getContext(), assignmentPosition);

        buildRecyclerView(root);

        mViewModel.getTasks().observe(getViewLifecycleOwner(), tasksList);

        return root;
    }

    private void buildRecyclerView(View root) {
        recyclerView = root.findViewById(R.id.rvTasksList);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new TasksAdapter(taskItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new TasksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("clicked");
                Bundle bundle = new Bundle();
                bundle.putInt("position", position);

                TaskEditorStudentFragment taskEditorStudentFragment = new TaskEditorStudentFragment();
                taskEditorStudentFragment.setArguments(bundle);

                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container, taskEditorStudentFragment).commit();
            }
        });
    }

    final Observer<ArrayList<Task>> tasksList = new Observer<ArrayList<Task>>() {
        @Override
        public void onChanged(ArrayList<Task> tasks) {
            if (taskItems != null) {
                taskItems.clear();
            }
            for (int i = 0; i < tasks.size(); i++) {
                taskItems.add(new TaskItem("Task " + (i + 1), "NO PROGRESS"));
            }
            adapter.notifyDataSetChanged();
        }
    };
}