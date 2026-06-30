package com.example.ternaksmart;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class ItemVaksinActivity extends AppCompatActivity {

    private RecyclerView rvItemVaksin;
    private VaksinAdapter adapter;
    private List<Vaksin> itemVaksinList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_vaksin);

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

        rvItemVaksin = findViewById(R.id.rvItemVaksin);
        rvItemVaksin.setLayoutManager(new LinearLayoutManager(this));

        prepareData();

        adapter = new VaksinAdapter(itemVaksinList);
        rvItemVaksin.setAdapter(adapter);

        MaterialButton btnSimpan = findViewById(R.id.btnSimpanItem);
        btnSimpan.setOnClickListener(v -> {
            Toast.makeText(this, "Data Item Vaksin Berhasil Disimpan!", Toast.LENGTH_SHORT).show();
            finish(); // Sesuai flowchart: Selesai (End)
        });
    }

    private void prepareData() {
        itemVaksinList = new ArrayList<>();
        // Menggunakan nama vaksin nyata untuk stok
        itemVaksinList.add(new Vaksin(getString(R.string.vax_nd_lasota), 0, true));
        itemVaksinList.add(new Vaksin(getString(R.string.vax_gumboro_1), 0, false));
        itemVaksinList.add(new Vaksin(getString(R.string.vax_ai_h5n1), 0, true));
        itemVaksinList.add(new Vaksin("Medivac Coryza B", 0, false));
    }
}