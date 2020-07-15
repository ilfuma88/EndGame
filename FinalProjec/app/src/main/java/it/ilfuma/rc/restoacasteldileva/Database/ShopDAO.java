package it.ilfuma.rc.restoacasteldileva.Database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ShopDAO {
    @Query("SELECT * FROM shop WHERE categoryId IN (:id)")
    List<Shop> loadAllById(int id);

    @Query("SELECT count(*) FROM shop")
    int size();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Shop... shops);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Shop>shops);

    @Delete
    void delete(Shop shop);
}