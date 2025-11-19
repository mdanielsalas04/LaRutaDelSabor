package com.example.larutadelsabor.detalle;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.larutadelsabor.R;

public class DetalleFeriaActivity extends AppCompatActivity {

    ImageView imgFeria;
    TextView txtTitulo, txtFecha, txtDescripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_feria);

        // 🔗 Referencias del XML
        imgFeria = findViewById(R.id.imgFeriaDetalle);
        txtTitulo = findViewById(R.id.txtFeriaTituloDetalle);
        txtFecha = findViewById(R.id.txtFeriaFechaDetalle);
        txtDescripcion = findViewById(R.id.txtFeriaDescDetalle);

        // 📩 RECIBIR DATOS DEL INTENT
        String titulo = getIntent().getStringExtra("titulo");
        String fecha = getIntent().getStringExtra("fecha");
        String descripcion = getIntent().getStringExtra("descripcion");
        String imagen = getIntent().getStringExtra("imagen");

        // 🖼 Mostrar datos
        txtTitulo.setText(titulo);
        txtFecha.setText(fecha);
        txtDescripcion.setText(descripcion);

        Glide.with(this).load(imagen).into(imgFeria);
    }
}
