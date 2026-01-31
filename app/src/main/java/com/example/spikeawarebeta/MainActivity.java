package com.example.spikeawarebeta;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {

    private VideoView videoBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Make the activity fullscreen
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
        WindowInsetsControllerCompat insetsController = new WindowInsetsControllerCompat(getWindow(), getWindow().getDecorView());
        insetsController.hide(WindowInsetsCompat.Type.statusBars());
        insetsController.setSystemBarsBehavior(WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE);

        setContentView(R.layout.activity_main);

        ImageView imgLogo = findViewById(R.id.imgLogo);
        videoBackground = findViewById(R.id.videoBackground);

        // Path to res/raw/home_bg.mp4
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.home_bg);
        videoBackground.setVideoURI(videoUri);

        // Loop + mute when prepared
        videoBackground.setOnPreparedListener(mp -> {
            mp.setLooping(true);
            mp.setVolume(0f, 0f); // mute
            videoBackground.start();
        });

        // start invisible
        imgLogo.setAlpha(0f);

        // fade in smoothly
        imgLogo.animate()
                .alpha(1f)
                .setDuration(800)   // 0.8 seconds
                .setStartDelay(150) // slight delay
                .start();

        Button btnContact = findViewById(R.id.btnContact);
        Button btnSearch = findViewById(R.id.btnSearch);

        btnContact.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, ContactActivity.class)));

        btnSearch.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SearchActivity.class)));

        Button btnSpiking = findViewById(R.id.btnSpiking);
        btnSpiking.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, SpikingIncidentActivity.class)));


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (videoBackground != null) videoBackground.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (videoBackground != null) videoBackground.pause();
    }
}