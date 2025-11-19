package com.example.larutadelsabor.usuario.tabs;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.example.larutadelsabor.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.firebase.firestore.FirebaseFirestore;
import com.example.larutadelsabor.usuario.models.Feria;
import com.example.larutadelsabor.usuario.models.FeriaActividad;
import com.example.larutadelsabor.usuario.models.Ruta;
import com.example.larutadelsabor.usuario.models.RutaPunto;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // subirDatosDePrueba();

        BottomNavigationView bottomNavigation = findViewById(R.id.bottomNavigation);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();

        NavigationUI.setupWithNavController(bottomNavigation, navController);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.activity_home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void subirDatosDePrueba() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // 1. PREPARAR DATOS DE LA FERIA
        List<FeriaActividad> actividades = new ArrayList<>();
        actividades.add(new FeriaActividad("10:00 AM", "Inauguración", "Entrada Principal"));
        actividades.add(new FeriaActividad("02:00 PM", "Concurso de comida", "Escenario B"));

        Calendar cal = Calendar.getInstance();
        Date hoy = cal.getTime();
        cal.add(Calendar.DAY_OF_MONTH, 3);
        Date fin = cal.getTime();

        Feria feriaPrueba = new Feria(
                "Gran Feria del Taco",
                "La mejor feria para probar tacos de todo el país.",
                19.432608, // Latitud ejemplo
                -99.133209, // Longitud ejemplo
                hoy,
                fin,
                actividades
        );

        // SUBIR FERIA
        db.collection("ferias").add(feriaPrueba)
                .addOnSuccessListener(documentReference -> System.out.println("Feria subida ID: " + documentReference.getId()))
                .addOnFailureListener(e -> System.out.println("Error subiendo feria: " + e.getMessage()));

        // 2. PREPARAR DATOS DE LA RUTA
        List<RutaPunto> puntosRuta = new ArrayList<>();
        puntosRuta.add(new RutaPunto(19.4326, -99.1332, 1));
        puntosRuta.add(new RutaPunto(19.4350, -99.1350, 2));
        puntosRuta.add(new RutaPunto(19.4380, -99.1380, 3));

        Ruta rutaPrueba = new Ruta(
                "Ruta Histórica Centro",
                "Recorrido a pie por los sabores del centro.",
                "3 Horas",
                puntosRuta
        );

        // SUBIR RUTA
        db.collection("rutas").add(rutaPrueba)
                .addOnSuccessListener(documentReference -> System.out.println("Ruta subida ID: " + documentReference.getId()));
    }
}
