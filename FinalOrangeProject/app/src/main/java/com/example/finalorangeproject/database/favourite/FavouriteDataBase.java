package com.example.finalorangeproject.database.favourite;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = FavouriteEntity.class,version = 2)
public abstract class FavouriteDataBase  extends RoomDatabase {
    //using singlton patern
    private static FavouriteDataBase instance;

    public abstract FavouriteDao Dao();

    public synchronized static FavouriteDataBase getInstance(Context context){

        if(instance == null){
            instance= Room.databaseBuilder(context.getApplicationContext(),FavouriteDataBase.class,"product_fav_database")
                    // use this when u update version to sync the new data with the old one
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


}
