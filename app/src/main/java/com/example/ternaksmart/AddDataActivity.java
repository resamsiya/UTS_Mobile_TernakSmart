package com.example.ternaksmart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Locale;
import java.util.Random;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDataActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_LOCATION_PERMISSION = 101;
    private ImageView ivTernakPhoto;
    private TextView tvCurrentWeight, tvLocationCoords;
    private Handler handler = new Handler();
    private boolean isMonitoring = true;
    private FusedLocationProviderClient fusedLocationClient;
    private double currentLatitude = 0.0, currentLongitude = 0.0;
    private String currentPhotoPath = "";
    private TextInputEditText etNamaHewan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            toolbar.setNavigationOnClickListener(v -> finish());
        }

        ivTernakPhoto = findViewById(R.id.ivTernakPhoto);
        tvCurrentWeight = findViewById(R.id.tvCurrentWeight);
        tvLocationCoords = findViewById(R.id.tvLocationCoords);
        etNamaHewan = findViewById(R.id.etNamaHewan);
        MaterialButton btnTakePhoto = findViewById(R.id.btnTakePhoto);
        MaterialButton btnSaveData = findViewById(R.id.btnSaveData);

        btnTakePhoto.setOnClickListener(v -> dispatchTakePictureIntent());

        btnSaveData.setOnClickListener(v -> saveData());

        // Simulasi Sensor Berat
        startWeightSensorSimulation();
        
        // Ambil Lokasi
        checkLocationPermission();
    }

    private void checkLocationPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION_PERMISSION);
        } else {
            getLastLocation();
        }
    }

    private void getLastLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
            if (location != null) {
                currentLatitude = location.getLatitude();
                currentLongitude = location.getLongitude();
                String coords = String.format(Locale.getDefault(), "Lat: %.5f, Long: %.5f", 
                        currentLatitude, currentLongitude);
                tvLocationCoords.setText(coords);
            } else {
                tvLocationCoords.setText("Lokasi tidak ditemukan (Cek GPS)");
            }
        });
    }

    private void saveData() {
        String nama = etNamaHewan.getText().toString().trim();
        String weightStr = tvCurrentWeight.getText().toString();
        double berat = Double.parseDouble(weightStr.replace(",", "."));

        if (nama.isEmpty()) {
            etNamaHewan.setError("Nama tidak boleh kosong");
            return;
        }

        TernakData data = new TernakData(nama, berat, currentPhotoPath, currentLatitude, currentLongitude, System.currentTimeMillis());

        // Simpan ke Room Database (Offline)
        Executors.newSingleThreadExecutor().execute(() -> {
            AppDatabase.getInstance(this).ternakDao().insert(data);
            runOnUiThread(() -> {
                Toast.makeText(this, R.string.msg_data_saved, Toast.LENGTH_SHORT).show();
            });
        });

        // Simpan ke REST API (Online)
        saveDataToServer(data);
    }

    private void saveDataToServer(TernakData data) {
        TernakApiService apiService = ApiClient.getApiService();
        Call<TernakData> call = apiService.createTernak(data);
        call.enqueue(new Callback<TernakData>() {
            @Override
            public void onResponse(Call<TernakData> call, Response<TernakData> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddDataActivity.this, "Data berhasil disinkronkan ke server", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddDataActivity.this, "Gagal sinkron server: " + response.code(), Toast.LENGTH_SHORT).show();
                    finish(); // Tetap finish karena sudah tersimpan di lokal
                }
            }

            @Override
            public void onFailure(Call<TernakData> call, Throwable t) {
                Toast.makeText(AddDataActivity.this, "Kesalahan jaringan: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish(); // Tetap finish karena sudah tersimpan di lokal
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            } else {
                tvLocationCoords.setText("Izin lokasi ditolak");
            }
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        } else {
            Toast.makeText(this, "Tidak ada aplikasi kamera ditemukan", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ivTernakPhoto.setImageBitmap(imageBitmap);
            ivTernakPhoto.setAlpha(1.0f);
            Toast.makeText(this, R.string.msg_photo_captured, Toast.LENGTH_SHORT).show();
        }
    }

    private void startWeightSensorSimulation() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isMonitoring) return;

                // Simulasi fluktuasi berat antara 1.50 - 2.50 kg
                double min = 1.50;
                double max = 2.50;
                double randomWeight = min + (max - min) * new Random().nextDouble();
                
                tvCurrentWeight.setText(String.format("%.2f", randomWeight));

                // Jalankan lagi setelah 2 detik
                handler.postDelayed(this, 2000);
            }
        }, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isMonitoring = false;
        handler.removeCallbacksAndMessages(null);
    }
}
