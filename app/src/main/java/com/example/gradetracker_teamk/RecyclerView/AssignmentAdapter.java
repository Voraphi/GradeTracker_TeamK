package com.example.gradetracker_teamk.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gradetracker_teamk.R;

import java.util.ArrayList;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.ViewHolder> {
    private ArrayList<AssignmentItems> list;
    private OnItemClickedListener listener;

    public interface OnItemClickedListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickedListener listener) {
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView assignmentView;
        public TextView dueDateView;
        public TextView gradeView;
        public ImageView info_icon;
        public ImageView edit_icon;
        public ImageView delete_icon;

        public ViewHolder(@NonNull View itemView, final OnItemClickedListener listener) {
            super(itemView);
            assignmentView = itemView.findViewById(R.id.assignment);
            dueDateView = itemView.findViewById(R.id.dueDate);
            gradeView = itemView.findViewById(R.id.grade);
            info_icon = itemView.findViewById(R.id.image_edit);
            edit_icon = itemView.findViewById(R.id.image_info);
            delete_icon = itemView.findViewById(R.id.image_delete);

            info_icon.setOnClickListener(new View.OnClickListener() {
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

            edit_icon.setOnClickListener(new View.OnClickListener() {
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

            delete_icon.setOnClickListener(new View.OnClickListener() {
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

    public AssignmentAdapter(ArrayList<AssignmentItems> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.assignment_items, parent, false);
        ViewHolder vh = new ViewHolder(v, listener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AssignmentItems currentItem = list.get(position);

        holder.assignmentView.setText(currentItem.getAssignment());
        holder.dueDateView.setText(currentItem.getDateDue());

        double score = Double.parseDouble(currentItem.getEarnedScore()) / Double.parseDouble(currentItem.getMaxScore());
        String scoreString = "%" + score;
        holder.gradeView.setText(scoreString);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
