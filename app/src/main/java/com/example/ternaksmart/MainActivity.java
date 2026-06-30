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
    private TextView tvUmurTernak;
    private TextView tvPrediksiKesehatan;

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
            TextView tvWelcome = findViewById(R.id.tvWelcome);
            if (tvWelcome != null) {
                tvWelcome.setText(getString(R.string.hello_user, userName));
            }
        }

        tvStatusKesehatan = findViewById(R.id.tvStatusKesehatan);
        tvUmurTernak = findViewById(R.id.tvUmurTernak);
        tvPrediksiKesehatan = findViewById(R.id.tvPrediksiKesehatan);
        
        // Animasi Masuk Dashboard
        View mainContent = findViewById(R.id.main);
        if (mainContent != null) {
            mainContent.startAnimation(android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in_up));
        }
        
        setupDashboard();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateHealthAlgorithm();
    }

    private void updateHealthAlgorithm() {
        SharedPreferences sharedPref = getSharedPreferences("TernakData", Context.MODE_PRIVATE);
        
        // Simulasi Umur Ternak (bisa diambil dari database/input)
        int umurHari = sharedPref.getInt("UMUR_TERNAK", 12); 
        int selesai = sharedPref.getInt("VAKSIN_SELESAI", 0);
        int total = sharedPref.getInt("TOTAL_VAKSIN", 6); // Total vaksin nyata kita ada 6

        if (tvUmurTernak != null) {
            tvUmurTernak.setText(getString(R.string.value_umur_ternak, umurHari));
        }

        // Algoritma Deteksi Perawatan & Kesehatan
        // Skenario: Idealnya pada hari ke-12, ternak harus sudah 3 kali vaksin (ND-IB Tetes, Gumboro 1, ND-IB Injeksi)
        int targetVaksinSesuaiUmur = 0;
        if (umurHari >= 28) targetVaksinSesuaiUmur = 6;
        else if (umurHari >= 18) targetVaksinSesuaiUmur = 5;
        else if (umurHari >= 14) targetVaksinSesuaiUmur = 4;
        else if (umurHari >= 10) targetVaksinSesuaiUmur = 3;
        else if (umurHari >= 7) targetVaksinSesuaiUmur = 2;
        else if (umurHari >= 4) targetVaksinSesuaiUmur = 1;

        if (tvStatusKesehatan != null) {
            if (selesai >= targetVaksinSesuaiUmur) {
                tvStatusKesehatan.setText(R.string.status_healthy);
                tvStatusKesehatan.setTextColor(getResources().getColor(R.color.primary_green));
            } else if (selesai >= targetVaksinSesuaiUmur - 1) {
                tvStatusKesehatan.setText(R.string.status_at_risk);
                tvStatusKesehatan.setTextColor(getResources().getColor(R.color.accent_orange));
            } else {
                tvStatusKesehatan.setText(R.string.status_needs_care);
                tvStatusKesehatan.setTextColor(getResources().getColor(R.color.error_red));
            }
        }

        // Algoritma Prediksi Kesehatan Masa Depan
        if (tvPrediksiKesehatan != null) {
            if (selesai >= targetVaksinSesuaiUmur && (umurHari + 7 < 14 || selesai > 3)) {
                tvPrediksiKesehatan.setText(R.string.prediction_safe);
                tvPrediksiKesehatan.setTextColor(getResources().getColor(R.color.text_dark));
            } else {
                tvPrediksiKesehatan.setText(R.string.prediction_warning);
                tvPrediksiKesehatan.setTextColor(getResources().getColor(R.color.error_red));
            }
        }
    }

    private void setupDashboard() {
        MaterialButton btnAddData = findViewById(R.id.btnAddData);
        MaterialButton btnLihatData = findViewById(R.id.btnLihatData);
        MaterialButton btnStats = findViewById(R.id.btnStats);
        MaterialButton btnSettings = findViewById(R.id.btnSettings);

        // Tambahkan animasi tekan untuk semua tombol
        if (btnAddData != null) {
            addPressAnimation(btnAddData);
            btnAddData.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddDataActivity.class);
                startActivity(intent);
            });
        }
        
        if (btnLihatData != null) {
            addPressAnimation(btnLihatData);
            btnLihatData.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, LihatDataActivity.class);
                startActivity(intent);
            });
        }
        
        if (btnStats != null) {
            addPressAnimation(btnStats);
            btnStats.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, StatisticsActivity.class);
                startActivity(intent);
            });
        }
        
        if (btnSettings != null) {
            addPressAnimation(btnSettings);
            btnSettings.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(intent);
            });
        }
    }

    private void addPressAnimation(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    v.animate().scaleX(0.95f).scaleY(0.95f).setDuration(100).start();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    v.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    if (event.getAction() == MotionEvent.ACTION_UP) {
                        v.performClick();
                    }
                    break;
            }
            return true;
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}