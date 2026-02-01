package com.example.spikeawarebeta.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SpikeDao {
    @Insert
    void insertAll(List<SpikeData> data);

    @Query("SELECT * FROM spike_data WHERE year = :year ORDER BY month ASC")
    List<SpikeData> getDataByYear(int year);

    @Query("SELECT SUM(count) FROM spike_data WHERE year = :year")
    int getTotalForYear(int year);

    @Query("SELECT SUM(maleCount) FROM spike_data WHERE year = :year")
    int getMaleTotalForYear(int year);

    @Query("SELECT SUM(femaleCount) FROM spike_data WHERE year = :year")
    int getFemaleTotalForYear(int year);

    @Query("SELECT DISTINCT year FROM spike_data ORDER BY year DESC")
    List<Integer> getAvailableYears();

    @Query("DELETE FROM spike_data")
    void deleteAll();

    // Add inside SpikeDao interface
    @Query("SELECT * FROM spike_data WHERE month > 0 ORDER BY year, month")
    List<SpikeData> getMonthlyStats();

    @Query("SELECT * FROM spike_data WHERE month = 0 ORDER BY year")
    List<SpikeData> getYearlyGenderStats();
}
