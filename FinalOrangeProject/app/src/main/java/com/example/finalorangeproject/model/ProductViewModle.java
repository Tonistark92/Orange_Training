package com.example.finalorangeproject.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalorangeproject.network.Repostry;

import java.util.List;


public class ProductViewModle extends ViewModel {

    private static final Repostry repo =new Repostry();


    public static MutableLiveData<List<Product>> posts(){
        return repo.posts();
    }
    public static MutableLiveData<List<java.lang.String>> categories(){
        return repo.categoryPost();
    }

    public static LiveData<java.lang.String> error(){
        return repo.error();
    }
    public void getPostsFromRepo(){
        repo.getAllPostsFromServer();
    }



    public static LiveData<java.lang.String> errorCat(){
        return repo.error();
    }
    public void getCategorysFromRepo(){
        repo.getAllCategoriesFromServer();
    }

}
