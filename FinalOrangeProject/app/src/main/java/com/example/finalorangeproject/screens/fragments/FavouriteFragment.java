package com.example.finalorangeproject.screens.fragments;

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
import android.widget.Button;

import com.example.finalorangeproject.R;
import com.example.finalorangeproject.database.cart.CartDataBase;
import com.example.finalorangeproject.database.cart.CartEntity;
import com.example.finalorangeproject.database.favourite.FavouriteDataBase;
import com.example.finalorangeproject.database.favourite.FavouriteEntity;
import com.example.finalorangeproject.model.Product;
import com.example.finalorangeproject.model.ProductViewModle;
import com.example.finalorangeproject.screens.activities.DetailsActivity;
import com.example.finalorangeproject.screens.adapters.FavAdapter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class FavouriteFragment extends Fragment {
    RecyclerView recycler;
    FavAdapter favAdapter;
    private FavouriteDataBase favouriteDataBase;
    private CartDataBase cartDataBase ;
    private FavAdapter.RecyclerListener listener;
    private ProductViewModle postViewModle;
    private List<FavouriteEntity> myList = new ArrayList<>();


    public FavouriteFragment() {
        // Required empty public constructor
    }


    public static FavouriteFragment newInstance() {
        FavouriteFragment fragment = new FavouriteFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnclicklistener();
        favouriteDataBase = FavouriteDataBase.getInstance(getContext());
        cartDataBase = CartDataBase.getInstance(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        favAdapter = new FavAdapter(listener);
        recycler = view.findViewById(R.id.recyclerView_fav);
        recycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recycler.setHasFixedSize(true);
        favouriteDataBase.Dao().getProductsFromFavorite()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<FavouriteEntity>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<FavouriteEntity> favouriteEntities) {
                        favAdapter.setList(favouriteEntities, getContext());
                        myList=favouriteEntities;
                        // this function does(notifies the attached observers that the underlying data has been changed and any View reflecting the data set should refresh itself)
                        favAdapter.notifyDataSetChanged();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        Button addAllToCart = view.findViewById(R.id.add_all_to_cart);
        addAllToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favAdapter.deletList();
                favouriteDataBase.Dao().DeletAllData()
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

                            }
                        });
                for(int i =0 ; i<myList.size();i++){
                    cartDataBase.Dao().insertProductToCart(new CartEntity(myList.get(i).getTitle(),myList.get(i).getImg()
                                    , myList.get(i).getPrice(), myList.get(i).getCount()
                                    , myList.get(i).getRate()))
                            .subscribeOn(Schedulers.computation())
                            .subscribe(new CompletableObserver() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onComplete() {
                                    Log.i("help", "onComplete: inserted the data in cart from shop fragment");
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }
                            });
                }
            }
        });
        recycler.setAdapter(favAdapter);
    }

    private void setOnclicklistener() {
        listener = new FavAdapter.RecyclerListener(){
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