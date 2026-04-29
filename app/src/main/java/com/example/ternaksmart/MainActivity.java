package com.example.ternaksmart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

public class MainActivity extends AppCompatActivity {

    private TextView tvStatusKesehatan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ambil data nama dari intent
        String userName = getIntent().getStringExtra("USER_NAME");
        if (userName != null && !userName.isEmpty()) {
            TextView tvTitle = findViewById(R.id.tvTitle);
            if (tvTitle != null) {
                tvTitle.setText("Halo, " + userName + "!");
            }
        }

        tvStatusKesehatan = findViewById(R.id.tvStatusKesehatan); // Kita perlu ID ini di XML
        
        setupDashboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateVaksinStatus();
    }

    private void updateVaksinStatus() {
        SharedPreferences sharedPref = getSharedPreferences("TernakData", Context.MODE_PRIVATE);
        int selesai = sharedPref.getInt("VAKSIN_SELESAI", 0);
        int total = sharedPref.getInt("TOTAL_VAKSIN", 0);

        if (tvStatusKesehatan != null && total > 0) {
            if (selesai == total) {
                tvStatusKesehatan.setText("Sangat Baik");
                tvStatusKesehatan.setTextColor(getResources().getColor(R.color.primary_green));
            } else if (selesai > 0) {
                tvStatusKesehatan.setText("Dalam Proses");
                tvStatusKesehatan.setTextColor(getResources().getColor(android.R.color.holo_orange_dark));
            } else {
                tvStatusKesehatan.setText("Belum Vaksin");
                tvStatusKesehatan.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
            }
        }
    }

    private void setupDashboard() {
        MaterialButton btnAddData = findViewById(R.id.btnAddData);
        MaterialButton btnVaksin = findViewById(R.id.btnVaksin);
        MaterialButton btnStats = findViewById(R.id.btnStats);
        MaterialButton btnSettings = findViewById(R.id.btnSettings);

        // Tombol Tambah Data
        if (btnAddData != null) {
            btnAddData.setOnClickListener(v -> showToast("Membuka Fitur Input Data..."));
        }
        
        // Tombol Vaksinasi -> Buka halaman Vaksinasi
        if (btnVaksin != null) {
            btnVaksin.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, VaksinActivity.class);
                startActivity(intent);
            });
        }
        
        // Tombol Statistik
        if (btnStats != null) {
            btnStats.setOnClickListener(v -> showToast("Membuka Statistik Ternak..."));
        }
        
        // Tombol Pengaturan
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> showToast("Membuka Pengaturan..."));
        }
    }

    private void addPressAnimation(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick(); 
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    break;
            }
            return true;
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}