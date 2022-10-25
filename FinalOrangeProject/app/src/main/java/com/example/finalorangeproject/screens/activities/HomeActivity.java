package com.example.finalorangeproject.screens.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.finalorangeproject.R;
import com.example.finalorangeproject.screens.fragments.CartFragment;
import com.example.finalorangeproject.screens.fragments.AccountFragment;
import com.example.finalorangeproject.screens.fragments.ExploreFragment;
import com.example.finalorangeproject.screens.fragments.FavouriteFragment;
import com.example.finalorangeproject.screens.fragments.ShopFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    Fragment selectedFragment=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        selectedFragment= ShopFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.myFragment, selectedFragment)
                .commit();


        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemReselectedListener(item -> {

            int id=item.getItemId();
            if(id==R.id.shop){
                selectedFragment=ShopFragment.newInstance();
            }
            else if(id== R.id.cart){
                selectedFragment= CartFragment.newInstance();

            }
            else if(id == R.id.favourite){
                selectedFragment= FavouriteFragment.newInstance();
            }
            else if(id == R.id.explore){
                selectedFragment= ExploreFragment.newInstance();
            }
            else if(id==R.id.account){
                selectedFragment= AccountFragment.newInstance();
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.myFragment, selectedFragment)
                    .commit();
        });
    }
}