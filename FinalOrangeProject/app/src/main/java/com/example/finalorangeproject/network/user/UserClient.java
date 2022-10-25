package com.example.finalorangeproject.network.user;

import com.example.finalorangeproject.model.UserResponseModel;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserClient {
    private static final String BASE_URL = "https://bego8889.000webhostapp.com/php/";
    private IUser mIUser;
    private static UserClient INSTANCE;

    public UserClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        mIUser = retrofit.create(IUser.class);
    }

    public static UserClient getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UserClient();
        return INSTANCE;
    }

    public Call<UserResponseModel> signUp(String name, String email, String password) {
        return mIUser.signUp(name, email, password);
    }

    public Call<UserResponseModel> signIn(String email, String password) {
        return mIUser.signIn(email, password);
    }
}
