package com.example.larutadelsabor.admin.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.larutadelsabor.R;

public class AdminActivity extends AppCompatActivity {

    private View cardFerias, cardRutas, btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        // Referencias
        cardFerias = findViewById(R.id.cardManageFerias);
        cardRutas = findViewById(R.id.cardManageRutas);
        btnExit = findViewById(R.id.btnExitAdmin);

        // Eventos
        cardFerias.setOnClickListener(v -> {
            // TODO: Navegar a la lista de gestión de ferias
            Toast.makeText(this, "Abriendo gestión de Ferias...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AdminActivity.this, AdminFeriasListActivity.class);
            startActivity(intent);
        });

        cardRutas.setOnClickListener(v -> {
            // TODO: Navegar a la lista de gestión de rutas
            Toast.makeText(this, "Abriendo gestión de Rutas...", Toast.LENGTH_SHORT).show();
            // Intent intent = new Intent(this, AdminRutasListActivity.class);
            // startActivity(intent);
        });

        btnExit.setOnClickListener(v -> {
            finish(); // Cierra la actividad y vuelve al perfil
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_admin), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(true);
        }
    }
}