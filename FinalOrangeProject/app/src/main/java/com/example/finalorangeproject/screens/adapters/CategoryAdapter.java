package com.example.finalorangeproject.screens.adapters;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalorangeproject.R;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {
    private List<java.lang.String> list = new ArrayList<>();

    public List<String> getList() {
        return list;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(list.get(position));
        if (position % 2 == 0)
            holder.c.setBackgroundColor(Color.parseColor("#53B175"));
        else
            holder.c.setBackgroundColor(Color.parseColor("#99351F"));
    }

    @Override
    public int getItemCount() {

        return list.size();
    }



    public void setList(List<String> categorys) {
        this.list = categorys;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        CardView c;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.text_category);
            c = itemView.findViewById(R.id.cardholder);
        }

    }

}
