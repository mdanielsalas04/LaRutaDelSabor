package com.example.larutadelsabor.admin.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.larutadelsabor.R;
import com.example.larutadelsabor.admin.adapter.AdminFeriaAdapter;
import com.example.larutadelsabor.usuario.models.Feria;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class AdminFeriasListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private FloatingActionButton fabAdd;
    private FirebaseFirestore db;
    private List<Feria> listaFerias = new ArrayList<>();
    private AdminFeriaAdapter adapter;
    private LinearLayout btnVolver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ferias_list);

        db = FirebaseFirestore.getInstance();

        // Referencias
        recyclerView = findViewById(R.id.rvFeriasAdmin);
        progressBar = findViewById(R.id.progressBarAdmin);
        fabAdd = findViewById(R.id.fabAddFeria);
        btnVolver = findViewById(R.id.btnVolver);

        // Configurar Recycler
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AdminFeriaAdapter(this, listaFerias, new AdminFeriaAdapter.OnItemActionListener() {
            @Override
            public void onEdit(Feria feria) {
                Intent intent = new Intent(AdminFeriasListActivity.this, FormularioFeriaActivity.class);
                intent.putExtra("FERIA_ID", feria.getId());
                startActivity(intent);
            }

            @Override
            public void onDelete(Feria feria) {
                confirmarEliminacion(feria);
            }
        });

        recyclerView.setAdapter(adapter);

        // Botón Agregar
        fabAdd.setOnClickListener(v -> {
            Intent intent = new Intent(this, FormularioFeriaActivity.class);
            startActivity(intent);
        });

        // Evento volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Cierra esta actividad y vuelve al Login
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_admin_ferias_list), (v, insets) -> {
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

    @Override
    protected void onResume() {
        super.onResume();
        cargarFerias(); // Recargar lista al volver (por si editamos algo)
    }

    private void cargarFerias() {
        progressBar.setVisibility(View.VISIBLE);
        db.collection("ferias").get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    listaFerias.clear();
                    for (DocumentSnapshot doc : queryDocumentSnapshots) {
                        Feria f = doc.toObject(Feria.class);
                        f.setId(doc.getId()); // Importante guardar el ID
                        listaFerias.add(f);
                    }
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Error al cargar", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                });
    }

    private void confirmarEliminacion(Feria feria) {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar Feria")
                .setMessage("¿Estás seguro de borrar '" + feria.getTitulo() + "'? No se puede deshacer.")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    eliminarFeria(feria.getId());
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarFeria(String id) {
        db.collection("ferias").document(id).delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Feria eliminada", Toast.LENGTH_SHORT).show();
                    cargarFerias(); // Refrescar lista
                })
                .addOnFailureListener(e -> Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show());
    }
}