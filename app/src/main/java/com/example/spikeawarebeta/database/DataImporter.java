package com.example.spikeawarebeta.database;

import android.content.Context;
import android.util.Log;
import com.example.spikeawarebeta.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DataImporter {
    private final AppDatabase db;
    private final Context context;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    public DataImporter(Context context) {
        this.context = context;
        this.db = AppDatabase.getInstance(context);
    }

    public void importFromCsv() {
        executor.execute(() -> {
            List<SpikeData> allData = new ArrayList<>();

            // 1. Import Monthly Data (Month, Year, Total)
            allData.addAll(parseMonthlyCsv());

            // 2. Import Gender Data (Year, Male, Female, Total)
            allData.addAll(parseGenderCsv());

            if (!allData.isEmpty()) {
                db.spikeDao().deleteAll();
                db.spikeDao().insertAll(allData);
                Log.d("DataImporter", "Successfully imported " + allData.size() + " records.");
            }
        });
    }

    private List<SpikeData> parseMonthlyCsv() {
        List<SpikeData> list = new ArrayList<>();
        // Helper to convert Month Name to Int
        Map<String, Integer> monthMap = new HashMap<>();
        monthMap.put("January", 1); monthMap.put("February", 2); monthMap.put("March", 3);
        monthMap.put("April", 4); monthMap.put("May", 5); monthMap.put("June", 6);
        monthMap.put("July", 7); monthMap.put("August", 8); monthMap.put("September", 9);
        monthMap.put("October", 10); monthMap.put("November", 11); monthMap.put("December", 12);

        try {
            // Ensure this matches your actual filename in res/raw/
            InputStream is = context.getResources().openRawResource(R.raw.monthly_spiking);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Expected: MonthName, Year, Total (3 columns)
                if (parts.length >= 3) {
                    String monthName = parts[0].trim();
                    int month = monthMap.containsKey(monthName) ? monthMap.get(monthName) : 0;
                    int year = Integer.parseInt(parts[1].trim());
                    int total = Integer.parseInt(parts[2].trim());

                    // Monthly data has no gender split, so set 0
                    list.add(new SpikeData(year, month, total, 0, 0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<SpikeData> parseGenderCsv() {
        List<SpikeData> list = new ArrayList<>();
        try {
            InputStream is = context.getResources().openRawResource(R.raw.gender_spiking);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            reader.readLine(); // Skip header
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                // Expected: Year, Male, Female, Total (4 columns)
                if (parts.length >= 4) {
                    int year = Integer.parseInt(parts[0].trim());
                    int male = Integer.parseInt(parts[1].trim());
                    int female = Integer.parseInt(parts[2].trim());
                    int total = Integer.parseInt(parts[3].trim());

                    // Gender data is yearly, so set Month to 0
                    list.add(new SpikeData(year, 0, total, male, female));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}