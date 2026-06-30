package com.example.ternaksmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class VaksinActivity extends AppCompatActivity {

    private RecyclerView rvVaksin;
    private VaksinAdapter adapter;
    private List<Vaksin> vaksinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaksin);

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

        rvVaksin = findViewById(R.id.rvVaksin);
        rvVaksin.setLayoutManager(new LinearLayoutManager(this));

        prepareData();

        adapter = new VaksinAdapter(vaksinList);
        rvVaksin.setAdapter(adapter);

        MaterialButton btnSimpan = findViewById(R.id.btnSimpanJadwal);
        btnSimpan.setOnClickListener(v -> {
            saveVaksinProgress();
            Toast.makeText(this, "Data Jadwal Vaksin Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
            finish(); // Sesuai flowchart: Selesai (End) dan kembali
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveVaksinProgress();
    }

    private void saveVaksinProgress() {
        int completed = 0;
        for (Vaksin v : vaksinList) {
            if (v.isDone()) completed++;
        }

        SharedPreferences sharedPref = getSharedPreferences("TernakData", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("VAKSIN_SELESAI", completed);
        editor.putInt("TOTAL_VAKSIN", vaksinList.size());
        editor.apply();
    }

    private void prepareData() {
        vaksinList = new ArrayList<>();
        // Menggunakan nama vaksin nyata dan jadwal hari yang umum dalam peternakan ayam/ternak
        vaksinList.add(new Vaksin(getString(R.string.vax_nd_ib_tetes), 4, false));
        vaksinList.add(new Vaksin(getString(R.string.vax_gumboro_1), 7, false));
        vaksinList.add(new Vaksin(getString(R.string.vax_nd_ib_vax), 10, false));
        vaksinList.add(new Vaksin(getString(R.string.vax_ai_h5n1), 14, false));
        vaksinList.add(new Vaksin(getString(R.string.vax_gumboro_2), 18, false));
        vaksinList.add(new Vaksin(getString(R.string.vax_nd_lasota), 28, false));
    }
}