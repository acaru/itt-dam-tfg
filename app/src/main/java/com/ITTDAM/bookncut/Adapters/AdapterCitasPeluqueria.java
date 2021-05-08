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

import java.util.List;

public class AdapterCitasPeluqueria extends ListAdapter<CitasPeluqueria, AdapterCitasPeluqueria.ViewHolder> {

    private MyListener listener;
    private boolean isSearchList;

    public AdapterCitasPeluqueria(List<CitasPeluqueria> listCitas, MyListener listener) {
        super(new callBack());
        this.submitList(listCitas);
        this.listener = listener;
        this.isSearchList = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita_peluqueria, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CitasPeluqueria CitasPeluqueria = getCurrentList().get(position);
        holder.usuario.setText("Cliente :"+CitasPeluqueria.getUsuario().get("nombre")+"");
        holder.diahora.setText("Dia: "+CitasPeluqueria.getDia()+" "+CitasPeluqueria.getHora());
        holder.servicio.setText("Servicio contratado:" +CitasPeluqueria.getServicio());
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(CitasPeluqueria);
                }
            });
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView usuario, diahora,servicio;
        View baseview;
        ViewHolder(View itemView) {
            super(itemView);
            usuario = itemView.findViewById(R.id.txtVUsuarioRVCitaPeluqueria);
            servicio = itemView.findViewById(R.id.txtVServicioRVCitaPeluqueria);
            diahora = itemView.findViewById(R.id.txtVDiaHoraRVCitaPeluqueria);
            baseview=itemView.findViewById(R.id.baseView);
        }
    }

    public static class callBack extends DiffUtil.ItemCallback<CitasPeluqueria> {
        @Override
        public boolean areItemsTheSame(@NonNull CitasPeluqueria oldItem, @NonNull CitasPeluqueria newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull CitasPeluqueria oldItem, @NonNull CitasPeluqueria newItem) {
            return oldItem.getUsuario().equals(newItem.getUsuario()) &&
                    oldItem.getDia().equals(newItem.getDia()) &&
                    oldItem.getHora().equals(newItem.getHora()) &&
                            (oldItem.getFinalizado()&&newItem.getFinalizado());
        }
    }

    public interface MyListener
    {
        void onClick(CitasPeluqueria ca);


    }
}
