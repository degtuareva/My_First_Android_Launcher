package com.example.myapplication.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CalculationDao {

    @Insert
    void insert(CalculationEntity calculation);

    @Query("SELECT * FROM calculations ORDER BY createdAt DESC")
    List<CalculationEntity> getAllCalculations();

    @Delete
    void delete(CalculationEntity calculation);

    @Query("DELETE FROM calculations")
    void deleteAll();
}