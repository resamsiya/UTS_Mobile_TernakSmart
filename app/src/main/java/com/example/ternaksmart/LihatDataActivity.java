package com.example.ternaksmart;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

public class LihatDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_data);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        // Animasi Masuk
        findViewById(android.R.id.content).startAnimation(
                android.view.animation.AnimationUtils.loadAnimation(this, R.anim.fade_in_up)
        );

        MaterialButton btnJadwalVaksin = findViewById(R.id.btnJadwalVaksin);
        MaterialButton btnItemVaksin = findViewById(R.id.btnItemVaksin);
        MaterialButton btnHistoryTernak = findViewById(R.id.btnHistoryTernak);

        btnJadwalVaksin.setOnClickListener(v -> {
            Intent intent = new Intent(LihatDataActivity.this, VaksinActivity.class);
            startActivity(intent);
        });

        btnItemVaksin.setOnClickListener(v -> {
            Intent intent = new Intent(LihatDataActivity.this, ItemVaksinActivity.class);
            startActivity(intent);
        });

        btnHistoryTernak.setOnClickListener(v -> {
            Intent intent = new Intent(LihatDataActivity.this, HistoryActivity.class);
            startActivity(intent);
        });
    }
}