// app/src/main/java/com/example/apphotel/SuitesActivity.java
package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class SuitesActivity extends AppCompatActivity {

    Button btnFazerReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suites);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Nossas Suítes");
        }

        btnFazerReserva = findViewById(R.id.btnFazerReserva);

        btnFazerReserva.setOnClickListener(v -> {
            // Cria um Intent para iniciar a ReservationActivity
            Intent intent = new Intent(SuitesActivity.this, ReservationActivity.class);
            startActivity(intent);
        });
    }
}