package com.example.spikeawarebeta.database;

import android.content.Context;
import com.example.spikeawarebeta.R;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
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
            List<SpikeData> list = new ArrayList<>();
            try {
                InputStream is = context.getResources().openRawResource(R.raw.monthly_spiking);
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                
                // Skip header line
                reader.readLine();
                
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length >= 5) {
                        int year = Integer.parseInt(parts[0].trim());
                        int month = Integer.parseInt(parts[1].trim());
                        int total = Integer.parseInt(parts[2].trim());
                        int male = Integer.parseInt(parts[3].trim());
                        int female = Integer.parseInt(parts[4].trim());
                        list.add(new SpikeData(year, month, total, male, female));
                    }
                }
                
                if (!list.isEmpty()) {
                    db.spikeDao().deleteAll();
                    db.spikeDao().insertAll(list);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
