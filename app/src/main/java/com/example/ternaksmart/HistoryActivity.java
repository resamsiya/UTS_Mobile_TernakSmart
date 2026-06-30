package com.example.ternaksmart;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView rvHistory;
    private HistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        rvHistory = findViewById(R.id.rvHistory);
        rvHistory.setLayoutManager(new GridLayoutManager(this, 2));
        adapter = new HistoryAdapter(new ArrayList<>());
        rvHistory.setAdapter(adapter);

        findViewById(R.id.fabSync).setOnClickListener(v -> loadApiData());

        loadLocalData();
    }

    private void loadLocalData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<TernakData> allData = AppDatabase.getInstance(this).ternakDao().getAllData();
            runOnUiThread(() -> {
                adapter.setData(allData);
                if (allData.isEmpty()) {
                    Toast.makeText(this, "Belum ada data lokal", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }

    private void loadApiData() {
        Toast.makeText(this, "Sinkronisasi dengan server...", Toast.LENGTH_SHORT).show();
        TernakApiService apiService = ApiClient.getApiService();
        Call<List<TernakData>> call = apiService.getAllTernak();
        call.enqueue(new Callback<List<TernakData>>() {
            @Override
            public void onResponse(Call<List<TernakData>> call, Response<List<TernakData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.setData(response.body());
                    Toast.makeText(HistoryActivity.this, "Data server berhasil dimuat", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(HistoryActivity.this, "Gagal ambil data server", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TernakData>> call, Throwable t) {
                Toast.makeText(HistoryActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}