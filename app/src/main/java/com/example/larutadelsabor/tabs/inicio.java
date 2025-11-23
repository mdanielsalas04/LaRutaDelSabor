package com.example.larutadelsabor.tabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.larutadelsabor.R;
import com.example.larutadelsabor.admin.pages.AdminLogin;
import com.example.larutadelsabor.usuario.auth.LoginActivity;

public class inicio extends AppCompatActivity {

    // Variables
    private Button admin, usuario;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio);

        admin = findViewById(R.id.btnEntrarAdmin);
        usuario = findViewById(R.id.btnEntrarUsuario);

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inicio.this, AdminLogin.class);
            }
        });
        usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(inicio.this, LoginActivity.class);
            }
        });



    }
}