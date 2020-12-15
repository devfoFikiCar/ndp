package com.devoFikiCar.ndp.ui.classes.teacher;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.devoFikiCar.ndp.R;
import com.devoFikiCar.ndp.User;
import com.devoFikiCar.ndp.helper.userSave;
import com.devoFikiCar.ndp.ui.classes.ClassItem;
import com.devoFikiCar.ndp.ui.classes.ClassesAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;

public class ClassesTeacherFragment extends Fragment {

    private ClassesTeacherViewModel mViewModel;
    private FloatingActionButton btCreateClass;
    private FirebaseFirestore firestore;
    private RecyclerView recyclerView;
    private static RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private static ArrayList<ClassItem> classItems = new ArrayList<>();

    public static ClassesTeacherFragment newInstance() {
        return new ClassesTeacherFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.classes_teacher_fragment, container, false);
        mViewModel = new ViewModelProvider(this).get(ClassesTeacherViewModel.class);

        btCreateClass = (FloatingActionButton) root.findViewById(R.id.createClass);

        firestore = FirebaseFirestore.getInstance();

        recyclerView = root.findViewById(R.id.rvClassesTeacher);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        adapter = new ClassesAdapter(classItems);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

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
                        Toast.makeText(getActivity(), "Cancel class creation", Toast.LENGTH_SHORT).show();
                    }
                });

                alertDialog.show();
            }
        });

        mViewModel.getIdTitles().observe(getViewLifecycleOwner(), classesList);

        return root;
    }

    final Observer<ArrayList<HashMap<String, String>>> classesList = new Observer<ArrayList<HashMap<String, String>>>() {
        @Override
        public void onChanged(ArrayList<HashMap<String, String>> strings) {
            if (classItems != null) {
                classItems.clear();
            }
            for (int i = 0; i < strings.size(); i++)
                classItems.add(new ClassItem(strings.get(i).get("classTitle"), strings.get(i).get("classID")));
            adapter.notifyDataSetChanged();
            System.out.println("test");
        }
    };
}