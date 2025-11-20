package com.example.larutadelsabor.admin.pages;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.larutadelsabor.R;
import com.example.larutadelsabor.admin.adapter.AdminRutaAdapter;
import com.example.larutadelsabor.usuario.models.Ruta;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminRutasListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;
    private LinearLayout btnBack;

    private FirebaseFirestore db;
    private List<Ruta> listaRutas = new ArrayList<>();
    private AdminRutaAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_rutas_list);

        // --- 1. Configurar Márgenes y Barra de Estado ---
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_admin_rutas_list), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        WindowInsetsControllerCompat windowInsetsController =
                ViewCompat.getWindowInsetsController(getWindow().getDecorView());
        if (windowInsetsController != null) {
            windowInsetsController.setAppearanceLightStatusBars(true);
        }
        // ------------------------------------------------

        db = FirebaseFirestore.getInstance();

        // Referencias UI
        recyclerView = findViewById(R.id.rvAdminRutas);
        progressBar = findViewById(R.id.progressBarAdminRutas);
        fabAdd = findViewById(R.id.fabAddRuta);
        btnBack = findViewById(R.id.btnBackRutas);

        // Configurar Adapter y RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminRutaAdapter(this, listaRutas, new AdminRutaAdapter.OnItemActionListener() {
            @Override
            public void onEdit(Ruta ruta) {
                Toast.makeText(AdminRutasListActivity.this, "Editar Ruta: " + ruta.getTitulo(), Toast.LENGTH_SHORT).show();
                // TODO: Abrir actividad de edición pasando ruta.getId()
            }

            @Override
            public void onDelete(Ruta ruta) {
                confirmarEliminacion(ruta);
            }
        });
        recyclerView.setAdapter(adapter);

        // Eventos de Botones
        fabAdd.setOnClickListener(v -> {
            Toast.makeText(this, "Crear nueva Ruta", Toast.LENGTH_SHORT).show();
            // TODO: Abrir actividad de creación vacía
        });

        btnBack.setOnClickListener(v -> finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarRutas();
    }

    private void cargarRutas() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("rutas").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaRutas.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Ruta r = doc.toObject(Ruta.class);
                        if (r != null) {
                            r.setId(doc.getId()); // Guardamos el ID de Firestore
                            listaRutas.add(r);
                        }
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar rutas", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });
    }

    private void confirmarEliminacion(Ruta ruta) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Ruta")
                .setMessage("¿Seguro que deseas eliminar '" + ruta.getTitulo() + "'?")
                .setPositiveButton("Eliminar", (dialog, which) -> eliminarRuta(ruta.getId()))
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarRuta(String id) {
        db.collection("rutas").document(id).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Ruta eliminada", Toast.LENGTH_SHORT).show();
                    cargarRutas(); // Recargamos la lista
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show());
    }
}