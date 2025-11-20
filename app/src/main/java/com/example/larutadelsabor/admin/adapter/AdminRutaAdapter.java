package com.example.larutadelsabor.admin.adapter;

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
import com.example.larutadelsabor.usuario.models.Ruta;

import java.util.List;

public class AdminRutaAdapter extends RecyclerView.Adapter<AdminRutaAdapter.ViewHolder> {

    private Context context;
    private List<Ruta> lista;
    private OnItemActionListener listener;

    public interface OnItemActionListener {
        void onEdit(Ruta ruta);
        void onDelete(Ruta ruta);
    }

    public AdminRutaAdapter(Context context, List<Ruta> lista, OnItemActionListener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ruta_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ruta ruta = lista.get(position);

        holder.txtTitulo.setText(ruta.getTitulo());

        // Mostramos la duración si existe, o una descripción corta
        String subtexto = (ruta.getDuracion() != null && !ruta.getDuracion().isEmpty())
                ? ruta.getDuracion()
                : "Sin duración especificada";

        holder.txtDuracion.setText(subtexto);

        Glide.with(context)
                .load(ruta.getImagen())
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.img);

        holder.btnEdit.setOnClickListener(v -> listener.onEdit(ruta));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(ruta));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, btnEdit, btnDelete;
        TextView txtTitulo, txtDuracion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgRutaAdmin);
            txtTitulo = itemView.findViewById(R.id.txtTituloRutaAdmin);
            txtDuracion = itemView.findViewById(R.id.txtDuracionRutaAdmin);
            btnEdit = itemView.findViewById(R.id.btnEditRuta);
            btnDelete = itemView.findViewById(R.id.btnDeleteRuta);
        }
    }
}