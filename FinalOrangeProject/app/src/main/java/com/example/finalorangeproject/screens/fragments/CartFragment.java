package com.example.finalorangeproject.screens.fragments;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.finalorangeproject.R;
import com.example.finalorangeproject.database.cart.CartDataBase;
import com.example.finalorangeproject.database.cart.CartEntity;
import com.example.finalorangeproject.model.Product;
import com.example.finalorangeproject.model.ProductViewModle;
import com.example.finalorangeproject.screens.activities.DetailsActivity;
import com.example.finalorangeproject.screens.adapters.CartAdapter;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class CartFragment extends Fragment {
    RecyclerView recycler;
    CartAdapter cartAdapter;
    private CartDataBase cartDataBase ;
    private ProductViewModle postViewModle;
    private CartAdapter.RecyclerListener listener;

    public CartFragment() {

    }


    public static CartFragment newInstance() {
        CartFragment fragment = new CartFragment();
        Bundle args = new Bundle();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnclicklistener();
        cartDataBase = CartDataBase.getInstance(getContext());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_my_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cartAdapter=new CartAdapter(listener);
        recycler=view.findViewById(R.id.recyclerView_cart_fragment);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recycler.setHasFixedSize(true);
        cartDataBase.Dao().getProductsFromCart()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<CartEntity>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onSuccess(@NonNull List<CartEntity> entities) {
                        cartAdapter.setList(entities,getContext());

                        // this function does(notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself)
                        cartAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: ");
                    }
                });
        recycler.setAdapter(cartAdapter);



    }
    private void setOnclicklistener() {
        listener=new CartAdapter.RecyclerListener() {
            @Override
            public void onClick(View v, int Position) {
                MutableLiveData<List<Product>> list= postViewModle.posts();
                for (int i=0 ;i<list.getValue().size();i++){
                    if(list.getValue().get(i).getId()==Position){
                        Intent intent =new Intent(getContext(), DetailsActivity.class);
                        //String title, Double price, String img, int count, Double rate
                        intent.putExtra("title", list.getValue().get(Position).getTitle());
                        intent.putExtra("price", list.getValue().get(Position).getPrice());
                        intent.putExtra("img", list.getValue().get(Position).getImage());
                        intent.putExtra("count", list.getValue().get(Position).getRating().getCount());
                        intent.putExtra("rate", list.getValue().get(Position).getRating().getRate());
                        startActivity(intent);
                    }

                }

            }
        };
    }

}