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
import com.ITTDAM.bookncut.models.CitasPeluqueria;
import com.ITTDAM.bookncut.models.Peluqueria;

import java.util.List;

public class AdapterPeluquerias extends ListAdapter<Peluqueria, AdapterPeluquerias.ViewHolder> {

    private MyListener listener;
    private boolean isSearchList;

    public AdapterPeluquerias(List<Peluqueria> Peluquerias, MyListener listener) {
        super(new callBack()); //lo primero que se ejecuta para comprobar elementos cita
        this.submitList(Peluquerias); //pinta la lista que se va a mostrar
        this.listener = listener;
        this.isSearchList = false;
    }

    //ViewHolder contenedor que sostiene al item (layout)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peluqueria, parent, false);
        return new ViewHolder(view);
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Peluqueria Peluqueria = getCurrentList().get(position);
        holder.nombre.setText(""+Peluqueria.getNombre());
        holder.propietario.setText("Propietario: "+Peluqueria.getPropietario());
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(Peluqueria);
                }
            });
    }

    //Contador de items
    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }



    //Plantilla donde defines que items tiene ViewHolder. A los elementos que les vas a cambiar el valor.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre,propietario;
        View baseview;
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreVRVPeluqueria);
            propietario = itemView.findViewById(R.id.txtVPropietarioRVPeluqueria);
            baseview=itemView.findViewById(R.id.baseView);
        }
    }

    //Clase estática del tipo callback que compara citas las nuevas con las antiguas para ver si existen o no devuelve un boolean
    public static class callBack extends DiffUtil.ItemCallback<Peluqueria> {
        @Override
        public boolean areItemsTheSame(@NonNull Peluqueria oldItem, @NonNull Peluqueria newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Peluqueria oldItem, @NonNull Peluqueria newItem) {
            return oldItem.getPropietario().equals(newItem.getPropietario()) &&
                    oldItem.getHorario().equals(newItem.getPropietario()) &&
                    oldItem.getNombre().equals(newItem.getNombre()) &&
                            (oldItem.getUbicacion().equals(newItem.getUbicacion()));
        }
    }

    //Interfaz que añade clics a los elementos de las CitasPeluqueria
    public interface MyListener
    {
        void onClick(Peluqueria ca);
    }
}
