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
import com.example.larutadelsabor.usuario.models.Feria;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class AdminFeriaAdapter extends RecyclerView.Adapter<AdminFeriaAdapter.ViewHolder> {

    private Context context;
    private List<Feria> lista;
    private OnItemActionListener listener;

    // Interfaz para comunicar clicks a la actividad
    public interface OnItemActionListener {
        void onEdit(Feria feria);
        void onDelete(Feria feria);
    }

    public AdminFeriaAdapter(Context context, List<Feria> lista, OnItemActionListener listener) {
        this.context = context;
        this.lista = lista;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_feria_admin, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feria f = lista.get(position);

        holder.txtTitulo.setText(f.getTitulo());

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String fecha = (f.getFechaInicio() != null) ? sdf.format(f.getFechaInicio()) : "Sin fecha";
        holder.txtFecha.setText(fecha);

        Glide.with(context).load(f.getImagen()).placeholder(R.drawable.ic_image_placeholder).into(holder.img);

        // Clicks de botones
        holder.btnEdit.setOnClickListener(v -> listener.onEdit(f));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(f));
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, btnEdit, btnDelete;
        TextView txtTitulo, txtFecha;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imgFeriaAdmin);
            txtTitulo = itemView.findViewById(R.id.txtTituloAdmin);
            txtFecha = itemView.findViewById(R.id.txtFechaAdmin);
            btnEdit = itemView.findViewById(R.id.btnEditFeria);
            btnDelete = itemView.findViewById(R.id.btnDeleteFeria);
        }
    }
}