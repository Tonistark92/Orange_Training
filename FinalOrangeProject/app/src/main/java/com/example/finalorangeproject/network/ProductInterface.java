package com.example.finalorangeproject.network;

import com.example.finalorangeproject.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductInterface {
    @GET("products")
    public Call<List<Product>> get_data();

}
