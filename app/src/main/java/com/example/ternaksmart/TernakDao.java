package com.example.ternaksmart;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface TernakDao {
    @Insert
    void insert(TernakData data);

    @androidx.room.Update
    void update(TernakData data);

    @androidx.room.Delete
    void delete(TernakData data);

    @Query("SELECT * FROM ternak_data ORDER BY timestamp DESC")
    List<TernakData> getAllData();
}