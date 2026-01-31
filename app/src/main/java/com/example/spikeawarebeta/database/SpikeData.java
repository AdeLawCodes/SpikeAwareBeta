package com.example.spikeawarebeta.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "spike_data")
public class SpikeData {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public long timestamp; // Specific time if available
    public int year;       // For easy filtering
    public int month;      // 1-12, for charting months
    public int count;      // Number of incidents in this record
    public String location; // Optional: City or Venue category

    public SpikeData(int year, int month, int count, String location) {
        this.year = year;
        this.month = month;
        this.count = count;
        this.location = location;
        // Optional: you could also calculate a rough timestamp here
    }
}
