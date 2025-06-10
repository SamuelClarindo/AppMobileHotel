package com.example.apphotel;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.apphotel.adapters.ImagePagerAdapter;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    // Componentes da tela principal
    ViewPager2 viewPager;
    ImagePagerAdapter adapter;
    int[] images = {
            R.drawable.copa_palace_noite,
            R.drawable.copa_palace_dia,
            R.drawable.main_pool_hotel,
            R.drawable.quadra_basket,
            R.drawable.quadra_tenis_praia
    };
    private final Handler sliderHandler = new Handler();
    private Runnable sliderRunnable;
    Button btnExploreHotel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // --- 1. Configuração do Navigation Drawer ---
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // --- 2. Lógica da tela principal (slideshow e botão) ---
        viewPager = findViewById(R.id.viewPager);
        adapter = new ImagePagerAdapter(images);
        viewPager.setAdapter(adapter);
        btnExploreHotel = findViewById(R.id.btnExploreHotel);

        btnExploreHotel.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, SuitesActivity.class);
            startActivity(intent);
        });

        sliderRunnable = new Runnable() {
            @Override
            public void run() {
                if (adapter.getItemCount() > 0) {
                    int currentItem = viewPager.getCurrentItem();
                    int nextItem = (currentItem + 1) % adapter.getItemCount();
                    viewPager.setCurrentItem(nextItem, true);
                }
                sliderHandler.postDelayed(this, 3000);
            }
        };
    }

    // --- 3. Método para lidar com cliques no menu lateral ---
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.nav_suites) {
            startActivity(new Intent(this, SuitesActivity.class));
        } else if (itemId == R.id.nav_missao) {
            startActivity(new Intent(this, MissaoActivity.class));
        } else if (itemId == R.id.nav_sobre) {
            startActivity(new Intent(this, SobreActivity.class));
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // --- 4. Lógica do botão "voltar" e do ciclo de vida para o slideshow ---
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        sliderHandler.postDelayed(sliderRunnable, 3000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sliderHandler.removeCallbacks(sliderRunnable);
    }
}