package google.firebase.tfgdam.activities.recyclers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import google.firebase.tfgdam.R;
import google.firebase.tfgdam.controller.ViewModel;
import google.firebase.tfgdam.model.Servicio;

public class ServiciosAdapter extends RecyclerView.Adapter<ServiciosAdapter.ServiciosViewHolder> {

    private ArrayList<Servicio> lista;
    private LayoutInflater inflater;
    private Context context;
    OnItemClickListener listener;

    public ServiciosAdapter(Context context, OnItemClickListener listener){
        this.listener = listener;
        lista = ViewModel.getServicios();
        notifyDataSetChanged();
        this.context = context;
        inflater = LayoutInflater.from(context);
        Log.d("aaa", "Adapter");
    }

    public interface OnItemClickListener{
        void onItemClickListener(Servicio s);
    }

    public void setData(ArrayList<Servicio> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public ServiciosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.peluqueria_card, parent, false);
        return new ServiciosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiciosViewHolder holder, int position) {
        if (lista != null){
            holder.tvNombreServicio.setText(lista.get(position).getNombre());
            holder.tvPrecioServicio.setText(String.valueOf(lista.get(position).getPrecio()));
        }
    }

    @Override
    public int getItemCount() {
        Log.d("aaa", "Tamaño lista: " + lista.size());
        int elementos = 0;
        if (lista != null){
            elementos = lista.size();
        }
        Log.d("aa", "ELEMENTOS FJASPOF: " + String.valueOf(elementos));
        return elementos;
    }

    public class ServiciosViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivServicio;
        private TextView tvNombreServicio, tvPrecioServicio;



        public ServiciosViewHolder(@NonNull View itemView) {
            super(itemView);

            ivServicio = itemView.findViewById(R.id.ivServicio);
            tvNombreServicio = itemView.findViewById(R.id.tvNombreServicio);
            tvPrecioServicio = itemView.findViewById(R.id.tvPrecioServicio);
            Log.d("aaaa", "ViewHolder");
        }
    }
}