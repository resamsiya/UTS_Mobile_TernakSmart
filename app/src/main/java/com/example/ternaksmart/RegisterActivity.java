package com.example.ternaksmart;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etRegName, etRegEmail, etRegFarmName, etRegPassword;
    private Button btnRegisterSubmit;
    private TextView tvBackToLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etRegName = findViewById(R.id.etRegName);
        etRegEmail = findViewById(R.id.etRegEmail);
        etRegFarmName = findViewById(R.id.etRegFarmName);
        etRegPassword = findViewById(R.id.etRegPassword);
        btnRegisterSubmit = findViewById(R.id.btnRegisterSubmit);
        tvBackToLogin = findViewById(R.id.tvBackToLogin);

        btnRegisterSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etRegName.getText().toString().trim();
                String email = etRegEmail.getText().toString().trim();
                String farm = etRegFarmName.getText().toString().trim();
                String password = etRegPassword.getText().toString().trim();

                if (name.isEmpty() || email.isEmpty() || farm.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Mohon lengkapi semua data", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Akun Berhasil Dibuat!", Toast.LENGTH_SHORT).show();
                    
                    // Langsung hubungkan ke Dashboard (MainActivity)
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    // Kirim nama peternak untuk ditampilkan di dashboard
                    intent.putExtra("USER_NAME", name);
                    startActivity(intent);
                    finish(); // Tutup halaman register agar tidak bisa kembali dengan tombol back
                }
            }
        });

        tvBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Sesuai permintaan, jika sudah punya akun bisa kembali ke login
                // Namun untuk sementara kita anggap login ada di flow sebelumnya
                onBackPressed();
            }
        });
    }
}