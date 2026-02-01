package com.example.spikeawarebeta.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import java.util.List;

@Dao
public interface AnalyticsDao {
    @Insert
    void logEvent(AnalyticsEvent event);

    @Query("SELECT eventName, COUNT(*) as count FROM analytics GROUP BY eventName")
    List<EventCount> getStats();

    // Simple helper class for the result
    static class EventCount {
        public String eventName;
        public int count;
    }
}