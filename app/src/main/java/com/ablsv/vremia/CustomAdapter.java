package com.ablsv.vremia;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<String> task_id, task_name, task_desc, task_date, task_time, task_color, task_image;

    CustomAdapter(Context context,
                  ArrayList<String> task_id,
                  ArrayList<String> task_name,
                  ArrayList<String> task_desc,
                  ArrayList<String> task_date,
                  ArrayList<String> task_time,
                  ArrayList<String> task_color,
                  ArrayList<String> task_image) {
        this.context = context;
        this.task_id = task_id;
        this.task_name = task_name;
        this.task_desc = task_desc;
        this.task_date = task_date;
        this.task_time = task_time;
        this.task_color = task_color;
        this.task_image = task_image;
    }

    public CustomAdapter(HomeActivity context, ArrayList<String> task_id, ArrayList<String> task_name, ArrayList<String> task_date, ArrayList<String> task_time, ArrayList<String> task_color, ArrayList<String> task_image) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_view_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        holder.task_id_txt.setText(task_id.get(position));
        holder.task_name_txt.setText(task_name.get(position));
        holder.task_desc_txt.setText(task_desc.get(position));
        holder.task_date_txt.setText(task_date.get(position));
        holder.task_time_txt.setText(task_time.get(position));
        holder.task_color_txt.setText(task_color.get(position));
        holder.task_image_txt.setText(task_image.get(position));
    }

    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView task_id_txt, task_name_txt, task_desc_txt, task_date_txt,
                task_time_txt, task_color_txt, task_image_txt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            task_id_txt = itemView.findViewById(R.id.task_id_txt);
            task_name_txt = itemView.findViewById(R.id.task_name_txt);
            task_desc_txt = itemView.findViewById(R.id.task_desc_txt);
            task_date_txt = itemView.findViewById(R.id.task_date_txt);
            task_time_txt = itemView.findViewById(R.id.task_time_txt);
            task_color_txt = itemView.findViewById(R.id.task_color_txt);
            task_image_txt = itemView.findViewById(R.id.task_image_txt);
        }
    }
}