package com.example.larutadelsabor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.larutadelsabor.R;
import com.example.larutadelsabor.models.Feria;
import com.example.larutadelsabor.models.Ruta;

import java.util.List;

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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TIPO_FERIA) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_feria, parent, false);
            return new FeriaHolder(v);
        } else {
            View v = LayoutInflater.from(context).inflate(R.layout.item_ruta, parent, false);
            return new RutaHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
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
            titulo.setText(f.titulo);
            fecha.setText(f.fecha_inicio + " - " + f.fecha_fin);

            Glide.with(context)
                    .load(f.imagen)
                    .into(img);
        }
    }

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
            titulo.setText(r.titulo);
            desc.setText(r.descripcion);

            Glide.with(context)
                    .load(r.imagen)
                    .into(img);
        }
    }
}
