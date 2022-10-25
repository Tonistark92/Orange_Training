package com.example.finalorangeproject.network.user;

import com.example.finalorangeproject.model.UserResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUser {

    @FormUrlEncoded
    @POST("signup.php")
    Call<UserResponseModel> signUp(@Field("name") String name,
                                   @Field("email") String email,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("login.php")
    Call<UserResponseModel> signIn(@Field("email") String email,
                                   @Field("password") String password);
}
