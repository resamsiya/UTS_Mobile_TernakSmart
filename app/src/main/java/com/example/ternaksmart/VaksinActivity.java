package com.example.ternaksmart;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
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
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());

        rvVaksin = findViewById(R.id.rvVaksin);
        rvVaksin.setLayoutManager(new LinearLayoutManager(this));

        prepareData();

        adapter = new VaksinAdapter(vaksinList);
        rvVaksin.setAdapter(adapter);
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
        // Mengambil data awal atau dari database (simulasi)
        vaksinList.add(new Vaksin("Vaksin ND-IB (Tetes)", "Hari ke-4", false));
        vaksinList.add(new Vaksin("Vaksin Gumboro", "Hari ke-7", false));
        vaksinList.add(new Vaksin("Vaksin AI", "Hari ke-10", false));
        vaksinList.add(new Vaksin("Vaksin ND-IB (Minum)", "Hari ke-18", false));
    }
}