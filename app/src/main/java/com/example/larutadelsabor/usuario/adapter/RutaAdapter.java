package com.example.larutadelsabor.usuario.adapter; // Ajusta tu package

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
import com.example.larutadelsabor.usuario.models.Ruta; // Importa tu modelo nuevo
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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_ruta, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ruta r = list.get(position);

        // --- AQUÍ EL CAMBIO CLAVE: USAR GETTERS ---
        holder.txtTitulo.setText(r.getTitulo());
        holder.txtDesc.setText(r.getDescripcion());

        Glide.with(context)
                .load(r.getImagen()) // Usar getter
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