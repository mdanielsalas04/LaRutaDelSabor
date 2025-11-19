package com.example.larutadelsabor.usuario.detalle;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.larutadelsabor.R;

public class DetalleRutaActivity extends AppCompatActivity {

    ImageView imgRuta;
    TextView txtTitulo, txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_ruta);

        // 🔗 IDs del XML
        imgRuta = findViewById(R.id.imgRutaDetalle);
        txtTitulo = findViewById(R.id.txtRutaTituloDetalle);
        txtDescripcion = findViewById(R.id.txtRutaDescripcionDetalle);

        // 📩 Datos del Intent
        String titulo = getIntent().getStringExtra("titulo");
        String descripcion = getIntent().getStringExtra("descripcion");
        String imagen = getIntent().getStringExtra("imagen");

        // 🖼 Mostrar datos
        txtTitulo.setText(titulo);
        txtDescripcion.setText(descripcion);

        Glide.with(this).load(imagen).into(imgRuta);
    }
}
