package com.example.finalorangeproject.database.favourite;


import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;
import io.reactivex.Completable;
import io.reactivex.Single;


@androidx.room.Dao
public interface FavouriteDao {
    @Insert
    Completable insertProductToFavorite(FavouriteEntity entity );

    @Query("select * from favtable")
    Single<List<FavouriteEntity>> getProductsFromFavorite();

    @Query("DELETE FROM favtable")
    Completable DeletAllData();

}
