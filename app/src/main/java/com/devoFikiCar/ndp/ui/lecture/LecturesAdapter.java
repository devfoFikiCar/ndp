/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.lecture;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;

import java.util.ArrayList;

public class LecturesAdapter extends RecyclerView.Adapter<LecturesAdapter.LecturesViewHolder> {
    ArrayList<LectureItem> arrayList;
    private OnItemClickListener mListener;

    public LecturesAdapter(ArrayList<LectureItem> arrayList) {
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public LecturesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lecture_item, parent, false);
        LecturesViewHolder lecturesViewHolder = new LecturesViewHolder(view, mListener);
        return lecturesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LecturesViewHolder holder, int position) {
        LectureItem lectureItem = arrayList.get(position);

        holder.tvTitle.setText(lectureItem.getLectureTitle());
    }

    @Override
    public int getItemCount() {
        if (arrayList == null)
            return 0;
        return arrayList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public static class LecturesViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvTitle;

        public LecturesViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvLectureTitle);

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
}
