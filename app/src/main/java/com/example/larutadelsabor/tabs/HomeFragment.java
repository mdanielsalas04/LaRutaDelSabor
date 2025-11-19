package com.example.larutadelsabor.tabs;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.larutadelsabor.R;
import com.example.larutadelsabor.adapter.FeriaAdapter;
import com.example.larutadelsabor.adapter.RutaAdapter;
import com.example.larutadelsabor.adapter.HomeMixedAdapter;
import com.example.larutadelsabor.models.Feria;
import com.example.larutadelsabor.models.Ruta;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private RecyclerView rvHome;

    private View cardTodo, cardFerias, cardRutas;
    private LinearLayout layoutTodo, layoutFerias, layoutRutas;

    FirebaseFirestore db;

    List<Feria> ferias = new ArrayList<>();
    List<Ruta> rutas = new ArrayList<>();

    public HomeFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // ---- REFERENCIAS ----
        rvHome = view.findViewById(R.id.rvHome);

        cardTodo = view.findViewById(R.id.cardTodo);
        cardFerias = view.findViewById(R.id.cardFerias);
        cardRutas = view.findViewById(R.id.cardRutas);

        layoutTodo = view.findViewById(R.id.layoutTodo);
        layoutFerias = view.findViewById(R.id.layoutFerias);
        layoutRutas = view.findViewById(R.id.layoutRutas);

        rvHome.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();

        // Cargar desde Firestore al iniciar
        cargarFerias();
        cargarRutas();

        // Eventos
        cardTodo.setOnClickListener(v -> {
            cargarCategoria("todo");
            marcarSeleccion("todo");
        });

        cardFerias.setOnClickListener(v -> {
            cargarCategoria("ferias");
            marcarSeleccion("ferias");
        });

        cardRutas.setOnClickListener(v -> {
            cargarCategoria("rutas");
            marcarSeleccion("rutas");
        });

        return view;
    }

    // --------------------------- FIRESTORE ------------------------------

    private void cargarFerias() {
        db.collection("ferias").get().addOnSuccessListener(q -> {
            ferias.clear();
            for (DocumentSnapshot d : q) {
                Feria f = d.toObject(Feria.class);
                f.id = d.getId();
                ferias.add(f);
            }
            cargarCategoria("todo");
        });
    }

    private void cargarRutas() {
        db.collection("rutas").get().addOnSuccessListener(q -> {
            rutas.clear();
            for (DocumentSnapshot d : q) {
                Ruta r = d.toObject(Ruta.class);
                r.id = d.getId();
                rutas.add(r);
            }
            cargarCategoria("todo");
        });
    }

    // --------------------------- ADAPTADORES ------------------------------

    private void cargarCategoria(String categoria) {

        switch (categoria) {

            case "ferias":
                rvHome.setAdapter(new FeriaAdapter(
                        getContext(),
                        ferias,
                        feria -> { /* evento click */ }
                ));
                break;

            case "rutas":
                rvHome.setAdapter(new RutaAdapter(
                        getContext(),
                        rutas,
                        ruta -> { /* evento click */ }
                ));
                break;

            case "todo":
            default:
                List<Object> mixto = new ArrayList<>();
                mixto.addAll(ferias);
                mixto.addAll(rutas);

                rvHome.setAdapter(new HomeMixedAdapter(
                        getContext(),
                        mixto
                ));
                break;
        }
    }

    // --------------------------- ESTILOS ------------------------------

    private void marcarSeleccion(String categoria) {

        int selectedColor = getResources().getColor(R.color.colorSelectedTab);
        int normalColor = getResources().getColor(R.color.colorSecondaryLight);

        layoutTodo.setBackgroundColor(normalColor);
        layoutFerias.setBackgroundColor(normalColor);
        layoutRutas.setBackgroundColor(normalColor);

        switch (categoria) {
            case "todo":
                layoutTodo.setBackgroundColor(selectedColor);
                break;

            case "ferias":
                layoutFerias.setBackgroundColor(selectedColor);
                break;

            case "rutas":
                layoutRutas.setBackgroundColor(selectedColor);
                break;
        }
    }
}
