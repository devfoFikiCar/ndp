/*
 * Copyright (C) devfoFikiCar - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Filip Obradović <fiki.obradovic@gmail.com> [2020-2021]
 */

package com.devoFikiCar.ndp.ui.statistics.overall;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devoFikiCar.ndp.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class UserStatsAdapter extends RecyclerView.Adapter<UserStatsAdapter.UserStatsViewHolder> {
    ArrayList<UserStatsItem> arrayList;
    private OnItemClickListener mListener;

    public UserStatsAdapter(ArrayList<UserStatsItem> arrayList) {
        this.arrayList = arrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public UserStatsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_stats_item, parent, false);
        return new UserStatsViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UserStatsViewHolder holder, int position) {
        UserStatsItem userStatsItem = arrayList.get(position);

        holder.tvTitle.setText(userStatsItem.getAssignmentTitle());
        holder.tvScore.setText(userStatsItem.getScore());
    }

    @Override
    public int getItemCount() {
        if (arrayList == null) {
            return 0;
        } else return arrayList.size();
    }

    public interface OnItemClickListener {
        void OnItemClick(int position);
    }

    public static class UserStatsViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvScore;

        public UserStatsViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvAssignmentTitleUser);
            tvScore = itemView.findViewById(R.id.tvScoreStatsUser);

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnItemClick(position);
                    }
                }
            });
        }
    }
}
