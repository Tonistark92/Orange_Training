package com.example.finalorangeproject.screens.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalorangeproject.R;
import com.example.finalorangeproject.model.Product;

import java.util.ArrayList;
import java.util.List;

public class custumAdapter extends RecyclerView.Adapter<custumAdapter.ViewHolder> {
    private RecyclerListener recyclerListener;
    private List<Product> list = new ArrayList<>();
    private Context c;

    public List<Product> getList() {
        return list;
    }
    public custumAdapter(RecyclerListener recyclerListener) {
        this.recyclerListener = recyclerListener;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_viewholder, parent, false);
        return new ViewHolder(v,recyclerListener);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.rate.setText(String.valueOf(list.get(position).getRating().getRate()));
        holder.price.setText("$ " + java.lang.String.valueOf(list.get(position).getPrice()));
        Glide.with(c).load(list.get(position).getImage()).into(holder.productImage);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(List<Product> list1, Context context) {
        this.list = list1;
        this.c=context;
        notifyDataSetChanged();
    }
    public void setFilterList(List<Product> list1) {
        this.list = list1;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        RecyclerListener onContactClickListener;
        private TextView title ,count,rate,price ;
        private ImageView productImage,add;
        public ViewHolder(@NonNull View itemView , RecyclerListener onContact) {
            super(itemView);
            title=itemView.findViewById(R.id.title_product);
            price=itemView.findViewById(R.id.price_product);
            rate=itemView.findViewById(R.id.rate_product);
            productImage=itemView.findViewById(R.id.img_product);
            add=itemView.findViewById(R.id.addImg);
            itemView.setOnClickListener(this);
            this.onContactClickListener=onContact;

        }


        @Override
        public void onClick(View v) {
            recyclerListener.onClick(v,getAdapterPosition());
        }
    }

    public interface  RecyclerListener {
        void onClick(View v ,int Position);
    }
}
