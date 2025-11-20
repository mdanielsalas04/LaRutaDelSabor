package com.example.larutadelsabor.admin.pages;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

import com.example.larutadelsabor.R;
import com.example.larutadelsabor.usuario.models.Feria;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FormularioFeriaActivity extends AppCompatActivity {

    // UI
    private EditText etTitulo, etDescripcion, etImagen;
    private Button btnFechaInicio, btnFechaFin, btnUbicacion;
    private TextView tvTituloForm, tvCoordenadas;
    private ExtendedFloatingActionButton btnGuardar;
    private LinearLayout btnBack;

    // Data
    private FirebaseFirestore db;
    private String feriaId = null; // Si es null, creamos. Si tiene ID, editamos.
    private Date fechaInicioSeleccionada = new Date();
    private Date fechaFinSeleccionada = new Date();

    // Coordenadas (Por defecto CDMX o 0,0)
    private double latitud = 19.4326;
    private double longitud = -99.1332;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_formulario_feria);

        // Ajustes visuales (Padding y Status Bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainFormFeria), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        WindowInsetsControllerCompat windowInsetsController = ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) windowInsetsController.setAppearanceLightStatusBars(true);

        db = FirebaseFirestore.getInstance();
        initViews();

        // Checar si venimos a EDITAR
        if (getIntent().hasExtra("FERIA_ID")) {
            feriaId = getIntent().getStringExtra("FERIA_ID");
            cargarDatosFeria(feriaId);
            tvTituloForm.setText("Editar Feria");
            btnGuardar.setText("Actualizar");
        }

        setupListeners();
    }

    private void initViews() {
        etTitulo = findViewById(R.id.etTitulo);
        etDescripcion = findViewById(R.id.etDescripcion);
        etImagen = findViewById(R.id.etImagenUrl);
        btnFechaInicio = findViewById(R.id.btnFechaInicio);
        btnFechaFin = findViewById(R.id.btnFechaFin);
        btnUbicacion = findViewById(R.id.btnSeleccionarUbicacion);
        tvCoordenadas = findViewById(R.id.tvCoordenadas);
        tvTituloForm = findViewById(R.id.tvTituloForm);
        btnGuardar = findViewById(R.id.btnGuardarFeria);
        btnBack = findViewById(R.id.btnBackForm);
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        // Pickers de Fecha
        btnFechaInicio.setOnClickListener(v -> mostrarDatePicker(true));
        btnFechaFin.setOnClickListener(v -> mostrarDatePicker(false));

        // Selección de Mapa (Pendiente para el siguiente paso)
        btnUbicacion.setOnClickListener(v -> {
            Toast.makeText(this, "Próximamente: Selector de Mapa", Toast.LENGTH_SHORT).show();
            // Aquí abriremos la actividad del mapa para seleccionar punto
        });

        // Guardar
        btnGuardar.setOnClickListener(v -> guardarFeria());
    }

    private void mostrarDatePicker(boolean esInicio) {
        Calendar cal = Calendar.getInstance();
        if (esInicio && fechaInicioSeleccionada != null) cal.setTime(fechaInicioSeleccionada);
        if (!esInicio && fechaFinSeleccionada != null) cal.setTime(fechaFinSeleccionada);

        DatePickerDialog picker = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            Calendar selectedCal = Calendar.getInstance();
            selectedCal.set(year, month, dayOfMonth);
            Date date = selectedCal.getTime();

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String textoFecha = sdf.format(date);

            if (esInicio) {
                fechaInicioSeleccionada = date;
                btnFechaInicio.setText(textoFecha);
            } else {
                fechaFinSeleccionada = date;
                btnFechaFin.setText(textoFecha);
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

        picker.show();
    }

    private void cargarDatosFeria(String id) {
        // Bloquear UI mientras carga...
        db.collection("ferias").document(id).get().addOnSuccessListener(document -> {
            Feria f = document.toObject(Feria.class);
            if (f != null) {
                etTitulo.setText(f.getTitulo());
                etDescripcion.setText(f.getDescripcion());
                etImagen.setText(f.getImagen()); // Asegúrate que tu modelo tenga getImagen() o getImagenUrl()

                // Cargar fechas
                if (f.getFechaInicio() != null) {
                    fechaInicioSeleccionada = f.getFechaInicio();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    btnFechaInicio.setText(sdf.format(fechaInicioSeleccionada));
                }
                if (f.getFechaFin() != null) {
                    fechaFinSeleccionada = f.getFechaFin();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    btnFechaFin.setText(sdf.format(fechaFinSeleccionada));
                }

                // Cargar Coordenadas
                latitud = f.getLatitud();
                longitud = f.getLongitud();
                tvCoordenadas.setText("Lat: " + latitud + ", Lng: " + longitud);
            }
        }).addOnFailureListener(e -> Toast.makeText(this, "Error al cargar datos", Toast.LENGTH_SHORT).show());
    }

    private void guardarFeria() {
        String titulo = etTitulo.getText().toString().trim();
        String desc = etDescripcion.getText().toString().trim();
        String img = etImagen.getText().toString().trim();

        if (titulo.isEmpty() || desc.isEmpty()) {
            Toast.makeText(this, "Título y Descripción son obligatorios", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear Mapa de datos (Ojo: Usa Map para ser flexibles, o usa tu objeto Feria)
        Map<String, Object> data = new HashMap<>();
        data.put("titulo", titulo);
        data.put("descripcion", desc);
        data.put("imagen", img); // O "imagenUrl" revisa tu base de datos
        data.put("latitud", latitud);
        data.put("longitud", longitud);
        data.put("fechaInicio", fechaInicioSeleccionada);
        data.put("fechaFin", fechaFinSeleccionada);

        // OJO: Asegúrate que los nombres de campos ("titulo", "imagen", etc.) coinciden EXACTO con Firebase

        if (feriaId == null) {
            // CREAR
            db.collection("ferias").add(data)
                    .addOnSuccessListener(doc -> {
                        Toast.makeText(this, "Feria Creada!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error al crear", Toast.LENGTH_SHORT).show());
        } else {
            // ACTUALIZAR
            db.collection("ferias").document(feriaId).update(data)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(this, "Feria Actualizada!", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(this, "Error al actualizar", Toast.LENGTH_SHORT).show());
        }
    }
}