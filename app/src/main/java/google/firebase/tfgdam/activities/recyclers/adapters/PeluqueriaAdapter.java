package google.firebase.tfgdam.activities.recyclers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import google.firebase.tfgdam.R;
import google.firebase.tfgdam.controller.ViewModel;
import google.firebase.tfgdam.model.Peluqueria;

public class PeluqueriaAdapter extends RecyclerView.Adapter<PeluqueriaAdapter.PeluqueriaViewHolder> {

    private ArrayList<Peluqueria> lista;
    private LayoutInflater inflater;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener{
        void onClick(Peluqueria p);
    }

    public PeluqueriaAdapter(Context context, OnItemClickListener listener){
        lista = ViewModel.getPeluquerias();
        notifyDataSetChanged();
        this.context = context;
        this.onItemClickListener = listener;
        inflater = LayoutInflater.from(context);
        Log.d("aaa", "Adapter");
    }



    public void setData(ArrayList<Peluqueria> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public PeluqueriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.peluqueria_card, parent, false);
        return new PeluqueriaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeluqueriaViewHolder holder, int position) {
        if (lista != null){
            holder.tvNombrePeluqueria.setText(lista.get(position).getNombreTienda());
            holder.tvUbicacion.setText(lista.get(position).getUbicacion());
            holder.ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClickListener.onClick(lista.get(position));
                }
            });
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

    public class PeluqueriaViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPeluqueria;
        private TextView tvNombrePeluqueria, tvUbicacion;
        private ConstraintLayout ly;


        public PeluqueriaViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPeluqueria = itemView.findViewById(R.id.ivServicio);
            tvNombrePeluqueria = itemView.findViewById(R.id.tvNombreServicio);
            tvUbicacion = itemView.findViewById(R.id.tvPrecioServicio);
            ly = itemView.findViewById(R.id.lyPeluqueria);
            Log.d("aaaa", "ViewHolder");
        }
    }
}
