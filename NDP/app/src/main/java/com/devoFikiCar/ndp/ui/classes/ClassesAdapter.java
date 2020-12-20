/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> 2020
 */

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
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener (OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ClassesViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvID;


        public ClassesViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitleLineClass);
            tvID = itemView.findViewById(R.id.tvIDLineClass);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public ClassesAdapter(ArrayList<ClassItem> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ClassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.class_item, parent, false);
        ClassesViewHolder classesViewHolder = new ClassesViewHolder(view, mListener);
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
