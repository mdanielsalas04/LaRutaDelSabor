package com.example.larutadelsabor.admin.pages;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.larutadelsabor.R;

public class PrivacidadYTerminos extends AppCompatActivity {

    private TextView txtContenido;
    private Button btnAceptar, btnCancelar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacidad_yterminos);

        txtContenido = findViewById(R.id.txtContenido);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnCancelar = findViewById(R.id.btnCancelar);

        // Texto de privacidad
        String texto = "Última actualización: 2025\n\n" +
                "1. Introducción\n" +
                "La Ruta del Sabor recopila información personal para mejorar tu experiencia. " +
                "Al usar la app aceptas estos términos.\n\n" +

                "2. Información que recopilamos\n" +
                "- Nombre\n" +
                "- Correo electrónico\n" +
                "- Foto de perfil\n" +
                "- Datos de uso de la aplicación\n\n" +

                "3. Uso de la información\n" +
                "Tus datos se utilizan para administrar tu cuenta, personalizar la aplicación " +
                "y mejorar la experiencia del usuario.\n\n" +

                "4. Almacenamiento\n" +
                "Los datos se almacenan de forma segura en Firebase Authentication y Firebase Storage.\n\n" +

                "5. Permisos\n" +
                "La app solicita acceso a cámara o galería para actualizar tu foto de perfil.\n\n" +

                "6. Derechos del usuario\n" +
                "Puedes solicitar modificación o eliminación de tu información en cualquier momento.\n\n" +

                "7. Uso aceptable\n" +
                "No está permitido subir contenido ofensivo o ilegal.\n\n" +

                "8. Cambios\n" +
                "Podremos modificar estos términos y serán notificados en la app.\n";

        txtContenido.setText(texto);

        btnAceptar.setOnClickListener(v -> {
            // Aquí puedes guardar en SharedPreferences que el usuario aceptó
            finish();
        });

        btnCancelar.setOnClickListener(v -> finish());
    }
}
