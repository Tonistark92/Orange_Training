package com.example.finalorangeproject.database.cart;


import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@androidx.room.Dao
public interface CartDao  {
    @Insert
    Completable insertProductToCart(CartEntity entity );

    @Query("select * FROM carttable ")
    Single<List<CartEntity>> getProductsFromCart();

    @Query("DELETE FROM carttable WHERE id = :id")
    Completable deletOneElement(int id);
}
