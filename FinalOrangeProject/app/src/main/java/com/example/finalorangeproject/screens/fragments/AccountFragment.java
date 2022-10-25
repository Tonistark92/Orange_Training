package com.example.finalorangeproject.screens.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.finalorangeproject.R;
import com.example.finalorangeproject.database.SaveData;
import com.example.finalorangeproject.screens.activities.HomeActivity;
import com.example.finalorangeproject.screens.activities.LoginActivity;


public class AccountFragment extends Fragment {


    SaveData myData;
    public AccountFragment() {
        // Required empty public constructor
    }



    public static AccountFragment newInstance() {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myData=new SaveData(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_account, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Button b= view.findViewById(R.id.logout_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SaveData(getContext()).updateUserStatus(false);
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        TextView name=view.findViewById(R.id.username);
        name.setText(myData.getUserName());
        TextView email=view.findViewById(R.id.useremail);
        email.setText(myData.getUserEmail());
    }
}