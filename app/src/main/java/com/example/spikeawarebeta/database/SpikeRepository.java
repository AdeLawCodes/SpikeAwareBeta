package com.example.spikeawarebeta.database;

import android.content.Context;
import androidx.lifecycle.LiveData;

import com.example.spikeawarebeta.ResourceDao;
import com.example.spikeawarebeta.ResourceItem;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SpikeRepository {
    private final ResourceDao resourceDao;
    private final AnalyticsDao analyticsDao;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public SpikeRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        resourceDao = db.resourceDao();
        analyticsDao = db.analyticsDao();
    }

    // --- METHODS FOR FRONTEND TO USE ---

    // 1. Get Resources (Live updates!)
    public LiveData<List<ResourceItem>> getApprovedResources() {
        return resourceDao.getAllApprovedResources();
    }

    public LiveData<List<ResourceItem>> search(String query) {
        return resourceDao.searchResources(query);
    }

    // 2. Suggest a Resource (Moderation)
    public void suggestResource(String title, String desc, String url) {
        executor.execute(() -> {
            ResourceItem item = new ResourceItem(title, desc, url);
            item.isApproved = false; // Pending approval
            resourceDao.insert(item);
        });
    }

    // 3. Analytics Tracking
    public void trackEvent(String eventName) {
        executor.execute(() -> {
            analyticsDao.logEvent(new AnalyticsEvent(eventName));
        });
    }
}