package com.example.finalorangeproject.network;



import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryClient {
    private static final String URL="https://fakestoreapi.com/products/";
    private static CategoryClient categoryClient;
    private CategoryInterface postcategory;

    public CategoryClient() {
        Retrofit retrofit=new Retrofit.Builder().
                baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postcategory =retrofit.create(CategoryInterface.class);
    }
    public static CategoryClient getINSTANCE(){
        if(categoryClient ==null){
            categoryClient =new CategoryClient();
        }
        return categoryClient;
    }
    public Call<List<String>> getALLPosts(){
        return postcategory.get_data();
    }
}
