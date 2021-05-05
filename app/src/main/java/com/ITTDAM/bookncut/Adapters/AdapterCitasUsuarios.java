package com.ITTDAM.bookncut.Adapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ITTDAM.bookncut.models.CitasUsuario;

import java.util.List;

public class AdapterCitasUsuarios extends RecyclerView.Adapter<AdapterCitasUsuarios.ViewHolderCitas> {

    List<CitasUsuario> listCitas;

    public AdapterCitasUsuarios(List<CitasUsuario> listCitas) {
        this.listCitas = listCitas;
    }

    @NonNull
    @Override
    public AdapterCitasUsuarios.ViewHolderCitas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCitasUsuarios.ViewHolderCitas holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    public class ViewHolderCitas extends RecyclerView.ViewHolder {
        public ViewHolderCitas(View itemView){
            super(itemView);
        }
    }
}
