package google.firebase.tfgdam.activities;

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

public class PeluqueriaAdapter extends RecyclerView.Adapter<PeluqueriaAdapter.PeluqueriaViewHolder> {

    private ArrayList<String> lista;
    private LayoutInflater inflater;
    private Context context;

    public PeluqueriaAdapter(Context context){
        lista = ViewModel.getLista();
        notifyDataSetChanged();
        this.context = context;
        inflater = LayoutInflater.from(context);
        Log.d("aaa", "Adapter");
    }

    public void setData(ArrayList<String> lista){
        this.lista = lista;
    }

    @NonNull
    @Override
    public PeluqueriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.peluqueria_card, parent, false);
        return new PeluqueriaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PeluqueriaViewHolder holder, int position) {
        if (lista != null){
            holder.tvNombrePeluqueria.setText(lista.get(position));
            holder.tvUbicacion.setText("Ubicacion del : " + lista.get(position));
        }
    }

    @Override
    public int getItemCount() {
        Log.d("aaa", "Tamaño lista: " + lista.size());
        int elementos = 0;
        if (lista != null){
            elementos = lista.size();
        }
        return elementos;
    }

    public class PeluqueriaViewHolder extends RecyclerView.ViewHolder{

        private ImageView ivPeluqueria;
        private TextView tvNombrePeluqueria, tvUbicacion;



        public PeluqueriaViewHolder(@NonNull View itemView) {
            super(itemView);

            ivPeluqueria = itemView.findViewById(R.id.ivPeluqueria);
            tvNombrePeluqueria = itemView.findViewById(R.id.tvNombrePeluqueria);
            tvUbicacion = itemView.findViewById(R.id.tvUbicacion);
            Log.d("aaaa", "ViewHolder");
        }
    }
}
