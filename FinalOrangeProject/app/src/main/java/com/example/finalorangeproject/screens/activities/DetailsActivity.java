package com.example.finalorangeproject.screens.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.finalorangeproject.R;
import com.example.finalorangeproject.database.cart.CartDataBase;
import com.example.finalorangeproject.database.cart.CartEntity;
import com.example.finalorangeproject.database.favourite.FavouriteDataBase;
import com.example.finalorangeproject.database.favourite.FavouriteEntity;

import io.reactivex.CompletableObserver;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailsActivity extends AppCompatActivity {
    TextView productDesc, productPrice, productTitle, productRate, productNo;
    java.lang.String title, img;
    int count;
    double rate, price;
    ImageView productImg, heart, plus, minus;
    Button addToBasket;
    boolean addedToFav=false;
    boolean addedToCart=false;
    FavouriteDataBase favdataBase;
    CartDataBase cartDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_screen);
        //String title, Double price, String img, int count, Double rate
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("title");
        img = bundle.getString("img");
        rate = bundle.getDouble("rate");
        price = bundle.getDouble("price");
        count = bundle.getInt("count");
        favdataBase = FavouriteDataBase.getInstance(this);
        cartDataBase = CartDataBase.getInstance(this);

        productDesc = findViewById(R.id.productdesc);
        productPrice = findViewById(R.id.productprice);
        productRate = findViewById(R.id.productrate);
        productTitle = findViewById(R.id.producttitle);
        productNo = findViewById(R.id.count_added_for_product);
        productImg = findViewById(R.id.productimg);
        plus = findViewById(R.id.plusproduct);
        minus = findViewById(R.id.minuseproduct);
        productNo.setText("1");
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.valueOf(productNo.getText().toString());
                ++n;
                productNo.setText(java.lang.String.valueOf(n));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n = Integer.valueOf(productNo.getText().toString());
                if (n != 1) {
                    --n;
                }
                productNo.setText(java.lang.String.valueOf(n));
            }
        });
        heart = findViewById(R.id.addtofav);
        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //insert data and choose the thread
                //String title, Double price, String img, int count, Double rate
               if(addedToFav==false){
                   heart.setColorFilter(Color.RED);
                   Toast.makeText(DetailsActivity.this, "Added to Favourit", Toast.LENGTH_SHORT).show();
                   addedToFav=true;
               }
                favdataBase.Dao().insertProductToFavorite(new FavouriteEntity(title, price, img, Integer.valueOf(productNo.getText().toString()), rate))
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {
                                Log.i("help", "onSubscribe: inserted the data in cart");

                            }

                            @Override
                            public void onComplete() {
                                Log.i("help", "onComplete: inserted the data in cart");

                            }

                            @Override
                            public void onError(@NonNull Throwable e) {

                            }
                        });
            }
        });
        addToBasket = findViewById(R.id.add_to_basket_button);
        addToBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(addedToCart==false){
                    Toast.makeText(DetailsActivity.this, "Added to Cart", Toast.LENGTH_SHORT).show();
                    addedToCart=true;
                }

                //String title, String img, Double price, int count, Double rate
                cartDataBase.Dao().insertProductToCart(new CartEntity(title, img, price, Integer.valueOf(productNo.getText().toString()), rate))
                        .subscribeOn(Schedulers.computation())
                        .subscribe(new CompletableObserver() {
                            @Override
                            public void onSubscribe(@NonNull Disposable d) {

                            }

                            @Override
                            public void onComplete() {
                                Log.i("help", "onComplete: inserted the data in cart");
                            }

                            @Override
                            public void onError(@NonNull Throwable e) {
                                Log.i("help", "onComplete: didnt insert in cart");

                            }
                        });
            }
        });
        productDesc.setText("product.getDescription()");
        productPrice.setText(java.lang.String.valueOf(price));
        productTitle.setText(title);
        productRate.setText(java.lang.String.valueOf(rate));
        Glide.with(this).load(img).into(productImg);
    }
}