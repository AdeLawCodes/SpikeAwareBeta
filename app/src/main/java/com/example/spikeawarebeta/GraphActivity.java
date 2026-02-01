package com.example.spikeawarebeta;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsets;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GraphActivity extends AppCompatActivity {

    private LineChartView lineChart;
    private BarChartViewSimple barChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        // Push content below camera/notch
        LinearLayout root = findViewById(R.id.graphRoot);
        root.setOnApplyWindowInsetsListener((v, insets) -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                int top = insets.getInsets(WindowInsets.Type.systemBars()).top;
                v.setPadding(v.getPaddingLeft(), top, v.getPaddingRight(), v.getPaddingBottom());
            } else {
                v.setPadding(v.getPaddingLeft(), v.getPaddingTop() + 40, v.getPaddingRight(), v.getPaddingBottom());
            }
            return insets;
        });

        TextView title = findViewById(R.id.tvGraphTitle);
        title.setText("Reported spiking incidents (Jan 2021 – Feb 2025)");

        lineChart = findViewById(R.id.lineChart);
        barChart = findViewById(R.id.barChart);

        // Monthly data
        String[] labels = SpikingData.labels();
        int[] values = SpikingData.values();

        lineChart.setData(labels, values);
        barChart.setData(labels, values);

        // Year totals
        BarChartViewSimple yearBar = findViewById(R.id.yearBarChart);
        yearBar.setData(SpikingData.yearLabels(), SpikingData.yearTotals());

        // Toggle buttons
        Button btnLine = findViewById(R.id.btnLine);
        Button btnBar = findViewById(R.id.btnBar);

        btnLine.setOnClickListener(v -> {
            lineChart.setVisibility(View.VISIBLE);
            barChart.setVisibility(View.GONE);
        });

        btnBar.setOnClickListener(v -> {
            lineChart.setVisibility(View.GONE);
            barChart.setVisibility(View.VISIBLE);
        });
    }
}
