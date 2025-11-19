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
import com.example.larutadelsabor.models.Ruta;

import java.util.List;

public class RutaAdapter extends RecyclerView.Adapter<RutaAdapter.ViewHolder> {

    Context context;
    List<Ruta> list;
    OnRutaClick listener;

    public interface OnRutaClick {
        void onClick(Ruta ruta);
    }

    public RutaAdapter(Context context, List<Ruta> list, OnRutaClick listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ruta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Ruta r = list.get(position);

        holder.txtTitulo.setText(r.titulo);
        holder.txtDesc.setText(r.descripcion);

        Glide.with(context)
                .load(r.imagen)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> listener.onClick(r));
    }

    @Override
    public int getItemCount() { return list.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView txtTitulo, txtDesc;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgRuta);
            txtTitulo = itemView.findViewById(R.id.txtRutaTitulo);
            txtDesc = itemView.findViewById(R.id.txtRutaDesc);
        }
    }
}
