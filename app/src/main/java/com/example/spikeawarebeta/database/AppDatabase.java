package com.example.spikeawarebeta.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.spikeawarebeta.ResourceDao;
import com.example.spikeawarebeta.ResourceItem;

// Add your entities here
@Database(entities = {ResourceItem.class, AnalyticsEvent.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;

    public abstract ResourceDao resourceDao();
    public abstract AnalyticsDao analyticsDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "spike_aware_db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Simplifies data loading for your project
                    .build();
        }
        return instance;
    }

    public SpikeDao spikeDao() {
        return null;
    }
}