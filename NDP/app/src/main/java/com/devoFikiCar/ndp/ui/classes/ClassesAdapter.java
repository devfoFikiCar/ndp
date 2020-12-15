package com.devoFikiCar.ndp.ui.classes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class ClassesAdapter extends RecyclerView.Adapter<ClassesAdapter.ClassesViewHolder> {
    ArrayList<ClassItem> arrayList;

    public static class ClassesViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvID;


        public ClassesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleLineClass);
            tvID = itemView.findViewById(R.id.tvIDLineClass);
        }
    }

    public ClassesAdapter(ArrayList<ClassItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        ClassesViewHolder classesViewHolder = new ClassesViewHolder(view);
        return classesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ClassesViewHolder holder, int position) {
        ClassItem classItem = arrayList.get(position);

        holder.tvTitle.setText(classItem.getClassTitle());
        holder.tvID.setText(classItem.getClassID());
    }

    @Override
    public int getItemCount() {
        if (arrayList == null)
            return 0;
        return arrayList.size();
    }
}
