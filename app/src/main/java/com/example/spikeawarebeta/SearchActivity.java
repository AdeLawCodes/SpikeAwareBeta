package com.example.spikeawarebeta;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private EditText etKeywordTop, etKeywordBottom;
    private RecyclerView recyclerResults;
    private ResourceAdapter adapter;

    private final ArrayList<ResourceItem> allItems = new ArrayList<>();
    private final ArrayList<ResourceItem> filteredItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Analytics.track(this, "search_opened");

        etKeywordTop = findViewById(R.id.etKeywordTop);
        etKeywordBottom = findViewById(R.id.etKeywordBottom);
        recyclerResults = findViewById(R.id.recyclerResults);
        Button btnBack = findViewById(R.id.btnBack);

        recyclerResults.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ResourceAdapter(this, filteredItems);
        recyclerResults.setAdapter(adapter);

        seedDemoData();
        filter("");

        // Keep both inputs in sync and filter as user types
        TextWatcher watcher = new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String text = s.toString();
                // sync both fields without infinite loop
                if (!etKeywordTop.getText().toString().equals(text)) etKeywordTop.setText(text);
                if (!etKeywordBottom.getText().toString().equals(text)) etKeywordBottom.setText(text);

                Analytics.track(SearchActivity.this, "search_used");
                filter(text);
            }
        };

        etKeywordTop.addTextChangedListener(watcher);
        etKeywordBottom.addTextChangedListener(watcher);

        btnBack.setOnClickListener(v -> finish());
    }

    private void seedDemoData() {
        // Research resources
        allItems.add(new ResourceItem(
                "Understanding Drink Spiking (Research, 2022)",
                "Academic overview of substances, reporting and prevention. Keywords: spiking.",
                "https://www.ncbi.nlm.nih.gov/"
        ));

        allItems.add(new ResourceItem(
                "Needle Spiking Reports Summary (Research, 2023)",
                "Summary of reports and safety discussion. Keywords: needling, spiking.",
                "https://www.gov.uk/"
        ));

        // Public resources
        allItems.add(new ResourceItem(
                "Spike Aware UK – Safety & Support",
                "Public-facing information, awareness material and support links.",
                "https://www.spikeaware.co.uk/"
        ));

        allItems.add(new ResourceItem(
                "Health guidance – reporting and support",
                "General health advice and what to do after an incident.",
                "https://www.nhs.uk/"
        ));
    }

    private void filter(String query) {
        String q = query == null ? "" : query.trim().toLowerCase();

        filteredItems.clear();

        if (q.isEmpty()) {
            filteredItems.addAll(allItems);
        } else {
            for (ResourceItem item : allItems) {
                String combined = (item.title + " " + item.desc).toLowerCase();
                if (combined.contains(q)) {
                    filteredItems.add(item);
                }
            }
        }

        adapter.notifyDataSetChanged();
    }
}
