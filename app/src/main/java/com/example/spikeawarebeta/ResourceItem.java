package com.example.spikeawarebeta;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "resources")
public class ResourceItem {
    @PrimaryKey(autoGenerate = true)
    public int id;

    // Changed from 'final' to standard public so Room can modify them
    public String title;
    public String desc;
    public String url;

    @ColumnInfo(defaultValue = "0")
    public boolean isApproved; // This field enables your "Moderation" requirement

    // 1. Empty constructor required by Room
    public ResourceItem() {}

    // 2. Original constructor (Keeps friend's frontend working)
    public ResourceItem(String title, String desc, String url) {
        this.title = title;
        this.desc = desc;
        this.url = url;
        this.isApproved = true; // Default to true for demo items
    }
}