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
import com.ITTDAM.bookncut.models.CitasUsuario;

import java.util.List;

public class AdapterCitasUsuarios extends ListAdapter<CitasUsuario,AdapterCitasUsuarios.ViewHolder> {

    private MyListener listener;
    private boolean isSearchList;

    public AdapterCitasUsuarios(List<CitasUsuario> listCitas,MyListener listener) {
        super(new callBack()); //lo primero que se ejecuta para comprobar elementos cita
        this.submitList(listCitas); //pinta la lista que se va a mostrar
        this.listener = listener;
        this.isSearchList = false;
    }

    //ViewHolder contenedor que sostiene al item (layout)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita_usuario, parent, false);
        return new ViewHolder(view);
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CitasUsuario citasUsuario = getCurrentList().get(position);
        holder.peluqueria.setText("Peluqueria: "+citasUsuario.getPeluqueria());
        holder.diahora.setText("Dia: "+citasUsuario.getDia()+" a las "+citasUsuario.getHora()+" horas");
        holder.servicio.setText("Servicio: "+citasUsuario.getServicio());
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(citasUsuario);
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
        TextView peluqueria, diahora,servicio;
        View baseview;
        ViewHolder(View itemView) {
            super(itemView);
            peluqueria = itemView.findViewById(R.id.txtVPeluqueriaRVCitaUsuario);
            servicio = itemView.findViewById(R.id.txtVServicioRVCitaUsuario);
            diahora = itemView.findViewById(R.id.txtVDiaHoraRVCitaUsuario);
            baseview=itemView.findViewById(R.id.baseView);
        }
    }

    //Clase estática del tipo callback que compara citas las nuevas con las antiguas para ver si existen o no devuelve un boolean
    public static class callBack extends DiffUtil.ItemCallback<CitasUsuario> {
        @Override
        public boolean areItemsTheSame(@NonNull CitasUsuario oldItem, @NonNull CitasUsuario newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull CitasUsuario oldItem, @NonNull CitasUsuario newItem) {
            return oldItem.getPeluqueria().equals(newItem.getPeluqueria()) &&
                    oldItem.getDia().equals(newItem.getDia()) &&
                    oldItem.getHora().equals(newItem.getHora()) &&
                            (oldItem.getFinalizado()&&newItem.getFinalizado());
        }
    }

    public interface MyListener
    {
        void onClick(CitasUsuario ca);


    }
}
