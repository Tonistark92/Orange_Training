package com.example.finalorangeproject.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.finalorangeproject.model.Product;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repostry {

    private MutableLiveData<List<Product>> responsePost =new MutableLiveData<List<Product>>();
    private MutableLiveData<java.lang.String>errorPost =new MutableLiveData<>();
    public LiveData<java.lang.String> error() {
        return errorPost;
    }
    public MutableLiveData<List<Product>> posts() {
        return responsePost;
    }


    private MutableLiveData<List<java.lang.String>> responsePostCat =new MutableLiveData<List<java.lang.String>>();
    private MutableLiveData<java.lang.String>errorPostCat =new MutableLiveData<>();
    public LiveData<java.lang.String> error2() {
        return errorPostCat;
    }
    public MutableLiveData<List<java.lang.String>> categoryPost() {
        return responsePostCat;
    }



    public  void getAllPostsFromServer(){
        ProductClient.getINSTANCE().getALLPosts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(response.isSuccessful()){
                    responsePost.setValue(response.body());
                }
                else{
                    errorPost.setValue(response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                errorPost.setValue(t.getMessage());
            }
        });
    }
    public void getAllCategoriesFromServer(){
        CategoryClient.getINSTANCE().getALLPosts().enqueue(new Callback<List<java.lang.String>>() {
            @Override
            public void onResponse(Call<List<java.lang.String>> call, Response<List<java.lang.String>> response) {
                responsePostCat.setValue(response.body());
            }
            @Override
            public void onFailure(Call<List<java.lang.String>> call, Throwable t) {
                errorPostCat.setValue(t.getMessage());
            }
        });
    }
}
