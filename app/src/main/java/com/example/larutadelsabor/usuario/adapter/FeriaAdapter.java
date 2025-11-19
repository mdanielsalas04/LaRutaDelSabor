package com.example.larutadelsabor.usuario.adapter;

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
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_feria, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Feria f = list.get(position);

        // USAR GETTERS
        holder.txtTitulo.setText(f.getTitulo());

        // Formatear fecha (Porque ahora son tipo Date, no String)
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String inicio = (f.getFechaInicio() != null) ? sdf.format(f.getFechaInicio()) : "--";
        String fin = (f.getFechaFin() != null) ? sdf.format(f.getFechaFin()) : "--";

        holder.txtFecha.setText(inicio + " - " + fin);

        Glide.with(context)
                .load(f.getImagen())
                .placeholder(R.drawable.ic_image_placeholder)
                .into(holder.img);

        holder.itemView.setOnClickListener(v -> listener.onClick(f));
    }

    @Override
    public int getItemCount() { return list.size(); }

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