package com.example.finalorangeproject.database.cart;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;





@Database(entities = CartEntity.class,version = 2)
public abstract class CartDataBase extends RoomDatabase {
    //using singlton patern
    private static CartDataBase instance;

    public abstract CartDao Dao();

    public synchronized static CartDataBase getInstance(Context context){

        if(instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),CartDataBase.class,"product_cart_database")
                    // use this when u update version to sync the new data with the old one
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


}