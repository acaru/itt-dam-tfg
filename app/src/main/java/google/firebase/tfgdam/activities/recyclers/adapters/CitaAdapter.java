package google.firebase.tfgdam.activities.recyclers.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import google.firebase.tfgdam.R;
import google.firebase.tfgdam.controller.ViewModel;
import google.firebase.tfgdam.model.Cita;

public class CitaAdapter extends RecyclerView.Adapter<CitaAdapter.CitaViewHolder> {

    ArrayList<Cita> citas = ViewModel.getCitas();
    OnItemClickListener listener;
    Context context;

    public interface OnItemClickListener{
        void onClick(Cita c);
    }

    public CitaAdapter(Context context, OnItemClickListener listener){
        this.listener = listener;
        this.context = context;
    }

    @NonNull
    @Override
    public CitaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cita_card, parent, false);
        return new google.firebase.tfgdam.activities.recyclers.adapters.CitaAdapter.CitaViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CitaViewHolder holder, int position) {
        if (citas != null){
            holder.tvPeluqueroCita.setText(citas.get(position).getIdPeluquero());
            holder.tvHoraCita.setText(citas.get(position).getFechayHora().toString());
            holder.ly.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(citas.get(position));
                    Log.d("check", "Listener");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (citas != null){
            return citas.size();
        }
        return 0;
    }

    public class CitaViewHolder extends RecyclerView.ViewHolder {

        TextView tvPeluqueroCita, tvHoraCita;
        ConstraintLayout ly;

        public CitaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvPeluqueroCita = itemView.findViewById(R.id.tvPeluqueroCita);
            tvHoraCita = itemView.findViewById(R.id.tvHoraCita);
            ly = itemView.findViewById(R.id.lyCitaCard);
        }
    }
}
