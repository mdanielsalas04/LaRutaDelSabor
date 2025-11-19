package com.example.larutadelsabor.usuario.adapter; // Ajusta esto si tu carpeta es diferente

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.larutadelsabor.R;
// IMPORTANTE: Importar los modelos desde la nueva ubicación
import com.example.larutadelsabor.usuario.models.Feria;
import com.example.larutadelsabor.usuario.models.Ruta;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class HomeMixedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    List<Object> items;

    private static final int TIPO_FERIA = 1;
    private static final int TIPO_RUTA = 2;

    public HomeMixedAdapter(Context context, List<Object> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position) instanceof Feria) return TIPO_FERIA;
        else return TIPO_RUTA;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TIPO_FERIA) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_feria, parent, false);
            return new FeriaHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_ruta, parent, false);
            return new RutaHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FeriaHolder) {
            Feria f = (Feria) items.get(position);
            ((FeriaHolder) holder).bind(f);

        } else {
            Ruta r = (Ruta) items.get(position);
            ((RutaHolder) holder).bind(r);
        }
    }

    @Override
    public int getItemCount() { return items.size(); }

    // --- HOLDER PARA FERIAS ---
    class FeriaHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView titulo, fecha;

        FeriaHolder(View item) {
            super(item);
            img = item.findViewById(R.id.imgFeria);
            titulo = item.findViewById(R.id.txtFeriaTitulo);
            fecha = item.findViewById(R.id.txtFeriaFecha);
        }

        void bind(Feria f) {
            // USO DE GETTERS (Aquí estaba el error)
            titulo.setText(f.getTitulo());

            // Formatear las fechas correctamente
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            String inicio = (f.getFechaInicio() != null) ? sdf.format(f.getFechaInicio()) : "--";
            String fin = (f.getFechaFin() != null) ? sdf.format(f.getFechaFin()) : "--";

            fecha.setText(inicio + " - " + fin);

            Glide.with(context)
                    .load(f.getImagen()) // Getter
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(img);
        }
    }

    // --- HOLDER PARA RUTAS ---
    class RutaHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView titulo, desc;

        RutaHolder(View item) {
            super(item);
            img = item.findViewById(R.id.imgRuta);
            titulo = item.findViewById(R.id.txtRutaTitulo);
            desc = item.findViewById(R.id.txtRutaDesc);
        }

        void bind(Ruta r) {
            // USO DE GETTERS
            titulo.setText(r.getTitulo());
            desc.setText(r.getDescripcion());

            Glide.with(context)
                    .load(r.getImagen()) // Getter
                    .placeholder(R.drawable.ic_image_placeholder)
                    .into(img);
        }
    }
}