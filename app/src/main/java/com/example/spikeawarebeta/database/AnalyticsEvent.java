package com.example.spikeawarebeta.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "analytics")
public class AnalyticsEvent {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String eventName; // e.g., "search_used", "contact_opened"
    public long timestamp;

    public AnalyticsEvent(String eventName) {
        this.eventName = eventName;
        this.timestamp = System.currentTimeMillis();
    }
}
