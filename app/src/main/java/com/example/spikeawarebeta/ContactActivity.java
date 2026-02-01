package com.example.spikeawarebeta;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_contact);
        Button btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
    }
}
