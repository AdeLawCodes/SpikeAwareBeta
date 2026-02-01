package com.example.spikeawarebeta;

import android.content.Context;
import android.util.Log;

public class Analytics {
    private static final String TAG = "SpikeAwareAnalytics";

    public static void track(Context context, String eventName) {
        // In a real app, this would send data to an analytics service.
        // For now, we just log it.
        Log.d(TAG, "Event tracked: " + eventName + " from " + context.getClass().getSimpleName());
    }
}
