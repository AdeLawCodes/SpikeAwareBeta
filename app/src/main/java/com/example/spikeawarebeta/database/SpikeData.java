package com.example.spikeawarebeta.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "spike_data")
public class SpikeData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int year;
    public int month;
    public int count;
    public int maleCount;
    public int femaleCount;

    public SpikeData(int year, int month, int count, int maleCount, int femaleCount) {
        this.year = year;
        this.month = month;
        this.count = count;
        this.maleCount = maleCount;
        this.femaleCount = femaleCount;
    }
}
