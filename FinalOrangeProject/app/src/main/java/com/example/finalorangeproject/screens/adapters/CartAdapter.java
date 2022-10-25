package com.example.finalorangeproject.screens.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalorangeproject.R;
import com.example.finalorangeproject.database.cart.CartDataBase;
import com.example.finalorangeproject.database.cart.CartEntity;
import com.example.finalorangeproject.database.favourite.FavouriteEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private CartAdapter.RecyclerListener recyclerListener;
    private List<CartEntity> list = new ArrayList<>();
    private Context c;

    public List<CartEntity> getList() {
        return list;
    }
    public CartAdapter(CartAdapter.RecyclerListener recyclerListener) {
        this.recyclerListener = recyclerListener;
    }
    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.ViewHolder(v,recyclerListener);
    }



    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.rate.setText(java.lang.String.valueOf(list.get(position).getRate()));
        holder.price.setText("$ " + java.lang.String.valueOf(list.get(position).getPrice()));
        Glide.with(c).load(list.get(position).getImg()).into(holder.productImage);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                int n = Integer.valueOf(holder.productNo.getText().toString());
                if (n != 1) {
                    --n;
                }
                holder.productNo.setText(java.lang.String.valueOf(n));
            }
        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)  {
                int n = Integer.valueOf(holder.productNo.getText().toString());
                ++n;
                holder.productNo.setText(java.lang.String.valueOf(n));
            }
        });

        holder.exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 CartDataBase cartDataBase =CartDataBase.getInstance(c);
//                for (int i=0 ;i<list.size();i++){
//                    if(list.get(i).getId()==position){
                        cartDataBase.Dao().deletOneElement(list.get(position).getId())
                                .subscribeOn(Schedulers.computation())
                                .subscribe(new CompletableObserver() {
                                    @Override
                                    public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {
                                        Log.i("help", "onSubscribe: inserted the data in cart");

                                    }

                                    @Override
                                    public void onComplete() {
                                        Log.i("help", "onComplete: inserted the data in cart");

                                    }

                                    @Override
                                    public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                                        Log.e("errorrr", "onComplete: inserted the data in cart");

                                    }
                                });
//                    }}

                list.remove(position);
                notifyDataSetChanged();


            }
        });
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    public void setList(List<CartEntity> list1, Context context) {
        this.list = list1;
        this.c=context;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        CartAdapter.RecyclerListener onContactClickListener;
        private TextView title ,rate,price ,productNo;
        private ImageView productImage,add,minus,exit;
        private LinearLayout item;
        public ViewHolder(@NonNull View itemView , CartAdapter.RecyclerListener onContact) {
            super(itemView);
            title=itemView.findViewById(R.id.title_product);
            rate=itemView.findViewById(R.id.rate_product);
            price=itemView.findViewById(R.id.price_product);
            productNo=itemView.findViewById(R.id.no_of_product);
            productImage=itemView.findViewById(R.id.cartimg);
            add=itemView.findViewById(R.id.add_to_product);
            minus=itemView.findViewById(R.id.minus_to_product);
            exit=itemView.findViewById(R.id.exitimg);
            item=itemView.findViewById(R.id.cart_item);
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