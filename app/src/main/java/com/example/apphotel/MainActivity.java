package com.example.apphotel;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnSuites, btnMissao, btnSobre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout que será usado por esta activity
        setContentView(R.layout.activity_main);

        // Associa as variáveis aos botões do layout pelo ID
        btnSuites = findViewById(R.id.btnSuites);
        btnMissao = findViewById(R.id.btnMissao);
        btnSobre = findViewById(R.id.btnSobre);

        // Ação do botão "Suítes"
        btnSuites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a tela que lista as suítes (SuiteActivity)
                Intent intent = new Intent(MainActivity.this, SuitesActivity.class);
                startActivity(intent);
            }
        });

        // Ação do botão "Missão"
        btnMissao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a tela de Missão (MissaoActivity)
                Intent intent = new Intent(MainActivity.this, MissaoActivity.class);
                startActivity(intent);
            }
        });

        // Ação do botão "Sobre"
        btnSobre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre a tela Sobre (SobreActivity)
                Intent intent = new Intent(MainActivity.this, SobreActivity.class);
                startActivity(intent);
            }
        });
    }
}