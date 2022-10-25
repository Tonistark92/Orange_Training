package com.example.finalorangeproject.screens.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalorangeproject.R;
import com.example.finalorangeproject.database.cart.CartDataBase;
import com.example.finalorangeproject.database.cart.CartEntity;
import com.example.finalorangeproject.model.Product;
import com.example.finalorangeproject.model.ProductViewModle;
import com.example.finalorangeproject.screens.activities.DetailsActivity;
import com.example.finalorangeproject.screens.adapters.CategoryAdapter;
import com.example.finalorangeproject.screens.adapters.custumAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import io.reactivex.CompletableObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ShopFragment extends Fragment {


    androidx.appcompat.widget.SearchView searchView;
    private custumAdapter ProductAdapter;
    private custumAdapter.RecyclerListener listener;
    private CategoryAdapter categoryAdapter;
    private ProductViewModle postViewModle;
    private CartDataBase cartDataBase ;

    public ShopFragment(){

    }


    public static ShopFragment newInstance() {
        Bundle args = new Bundle();

        return new ShopFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setOnclicklistener();
        ProductAdapter =new custumAdapter(listener);
        categoryAdapter =new CategoryAdapter();
        postViewModle = new ViewModelProvider(this).get(ProductViewModle.class);
        postViewModle.getPostsFromRepo();
        postViewModle.getCategorysFromRepo();
        cartDataBase = CartDataBase.getInstance(getContext());
        ProductViewModle.posts().observe(this, products -> {
            ProductAdapter.setList(products,getContext());
            // this function does(notifies the attached observers that the underlying data has been changed
            // and any View reflecting the data set should refresh itself)
            ProductAdapter.notifyDataSetChanged();
        });
        ProductViewModle.categories().observe(this, new Observer<List<java.lang.String>>() {
            @Override
            public void onChanged(List<java.lang.String> categorys) {
                categoryAdapter.setList(categorys);
                categoryAdapter.notifyDataSetChanged();

            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_shop, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        searchView=view.findViewById(R.id.search_view);
        RecyclerView productRecycle=view.findViewById(R.id.recyclerview);
        RecyclerView categoryRecycle=view.findViewById(R.id.recyclerview_category);
        TextView seeAll=view.findViewById(R.id.see_alltext);
        seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ExploreFragment fragment2=new ExploreFragment();
                FragmentManager fragmentManager=getActivity(). getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.myFragment,fragment2);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(java.lang.String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(java.lang.String newText) {
                filterList(newText);
                return true;
            }
        });

        productRecycle.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        productRecycle.setAdapter(ProductAdapter);
        categoryRecycle.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        categoryRecycle.setAdapter(categoryAdapter);
    }


    private void filterList(java.lang.String newText) {
        MutableLiveData<List<Product>> list= postViewModle.posts();
        List<Product> filterList =new ArrayList<>();
        for (Product product : list.getValue()) {
            if(product.getTitle().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)) ||product.getCategory().toLowerCase(Locale.ROOT).contains(newText.toLowerCase(Locale.ROOT)) ){
                filterList.add(product);
            }
            if(list.getValue().isEmpty()){
                Toast.makeText(getContext(), "No Data Found", Toast.LENGTH_SHORT).show();
            }
            else {
                ProductAdapter.setFilterList(filterList);
            }
        }
    }
    private void setOnclicklistener() {
        listener=new custumAdapter.RecyclerListener() {
            @Override
            public void onClick(View v, int Position) {
                MutableLiveData<List<Product>> list= postViewModle.posts();

                v.findViewById(R.id.addImg).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getContext(), "adding ...", Toast.LENGTH_SHORT).show();
                        cartDataBase.Dao().insertProductToCart(new CartEntity(list.getValue().get(Position).getTitle(), list.getValue().get(Position).getImage()
                                        , list.getValue().get(Position).getPrice(), list.getValue().get(Position).getRating().getCount()
                                        , list.getValue().get(Position).getRating().getRate()))
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
                });
                v.findViewById(R.id.img_product).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
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
                });

            }
        };
    }

}