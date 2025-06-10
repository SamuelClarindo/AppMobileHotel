// app/src/main/java/com/example/apphotel/MissaoActivity.java
package com.example.apphotel;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MissaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_missao);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Missão");
        }
    }
}