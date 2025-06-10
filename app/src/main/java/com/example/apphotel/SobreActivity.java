// app/src/main/java/com/example/apphotel/SobreActivity.java
package com.example.apphotel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre);

        // Opcional: Adicionar um título à barra de ação
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sobre");
        }
    }
}