package com.example.ternaksmart;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.appbar.MaterialToolbar;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle("Pengaturan");
            toolbar.setNavigationOnClickListener(v -> finish());
        }
        
        findViewById(R.id.btnProfile).setOnClickListener(v -> 
            Toast.makeText(this, "Membuka Profil...", Toast.LENGTH_SHORT).show());
            
        findViewById(R.id.btnLogout).setOnClickListener(v -> 
            Toast.makeText(this, "Keluar dari akun...", Toast.LENGTH_SHORT).show());
    }
}