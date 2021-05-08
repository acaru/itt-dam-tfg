package com.ITTDAM.bookncut.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.models.Servicios;

import java.util.List;

public class AdapterServicios extends ListAdapter<Servicios, AdapterServicios.ViewHolder> {

    private MyListener listener;
    private boolean isSearchList;

    public AdapterServicios(List<Servicios> servicios, MyListener listener) {
        super(new callBack());
        this.submitList(servicios);
        this.listener = listener;
        this.isSearchList = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Servicios servicio = getCurrentList().get(position);
        holder.nombre.setText(servicio.getNombre());
        holder.duracion.setText(servicio.getDuracion()+"");
        holder.precio.setText(servicio.getPrecio()+"");
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(servicio);
                }
            });
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, duracion,precio;
        View baseview;
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtVNombreServicio);
            duracion = itemView.findViewById(R.id.txtVDuracionServicio);
            precio = itemView.findViewById(R.id.txtVPrecioServicio);
            baseview=itemView.findViewById(R.id.baseView);
        }
    }

    public static class callBack extends DiffUtil.ItemCallback<Servicios> {
        @Override
        public boolean areItemsTheSame(@NonNull Servicios oldItem, @NonNull Servicios newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Servicios oldItem, @NonNull Servicios newItem) {
            return oldItem.getNombre().equals(newItem.getNombre()) &&
                    oldItem.getDuracion().equals(newItem.getDuracion()) &&
                    oldItem.getPrecio().equals(newItem.getPrecio());
        }
    }

    public interface MyListener
    {
        void onClick(Servicios ca);


    }
}
