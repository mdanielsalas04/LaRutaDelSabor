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

import java.util.List;

public class FeriaAdapter extends RecyclerView.Adapter<FeriaAdapter.ViewHolder> {
    Context context;
    List<Feria> list;
    OnFeriaClick listener;

    public interface OnFeriaClick {
        void onClick(Feria feria);
    }

    public FeriaAdapter(Context context, List<Feria> list, OnFeriaClick listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_feria, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Feria f = list.get(position);

        holder.txtTitulo.setText(f.titulo);
        holder.txtFecha.setText(f.fecha_inicio + " - " + f.fecha_fin);

        // Imagen
        Glide.with(context)
                .load(f.imagen)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> listener.onClick(f));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTitulo, txtFecha;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgFeria);
            txtTitulo = itemView.findViewById(R.id.txtFeriaTitulo);
            txtFecha = itemView.findViewById(R.id.txtFeriaFecha);
        }
    }
}
