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
import com.example.finalorangeproject.database.favourite.FavouriteEntity;

import java.util.ArrayList;
import java.util.List;

public class FavAdapter extends RecyclerView.Adapter<FavAdapter.ViewHolder> {
    private FavAdapter.RecyclerListener recyclerListener;
    private List<FavouriteEntity> list = new ArrayList<>();
    private Context c;

    public List<FavouriteEntity> getList() {
        return list;
    }
    public FavAdapter(FavAdapter.RecyclerListener recyclerListener) {
        this.recyclerListener = recyclerListener;
    }
    @NonNull
    @Override
    public FavAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_item, parent, false);
        return new FavAdapter.ViewHolder(v,recyclerListener);
    }



    @Override
    public void onBindViewHolder(@NonNull FavAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.rate.setText(java.lang.String.valueOf(list.get(position).getRate()));
        holder.price.setText("$ " + java.lang.String.valueOf(list.get(position).getPrice()));
        Glide.with(c).load(list.get(position).getImg()).into(holder.productImage);

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setList(List<FavouriteEntity> list1, Context context) {
        this.list = list1;
        this.c=context;
        notifyDataSetChanged();
    }
    public void deletList(){
        this.list=new ArrayList<>();
        notifyDataSetChanged();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        FavAdapter.RecyclerListener onContactClickListener;
        private TextView title ,rate,price ;
        private ImageView productImage;
        public ViewHolder(@NonNull View itemView , FavAdapter.RecyclerListener onContact) {
            super(itemView);
            title=itemView.findViewById(R.id.title_product_fav);
            rate=itemView.findViewById(R.id.rate_produc_fav);
            price=itemView.findViewById(R.id.price_product_fav);
            productImage=itemView.findViewById(R.id.fav_img);
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
