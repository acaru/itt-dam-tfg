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
import com.ITTDAM.bookncut.models.EncargosAdmin;

import java.util.List;

public class AdapterEncargosAdmin extends ListAdapter<EncargosAdmin, AdapterEncargosAdmin.ViewHolder> {

    private MyListener listener;
    private boolean isSearchList;

    public AdapterEncargosAdmin(List<EncargosAdmin> EncargosAdmin, MyListener listener) {
        super(new callBack());
        this.submitList(EncargosAdmin);
        this.listener = listener;
        this.isSearchList = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_encargo_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EncargosAdmin EncargosAdmin = getCurrentList().get(position);
        holder.producto.setText("Encargo de: " +EncargosAdmin.getUsuarioEmail());
        holder.precio.setText("Total: "+ EncargosAdmin.getPrecioTotal()+"€");
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(EncargosAdmin);
                }
            });
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView producto, precio,cantidad;
        View baseview;
        ViewHolder(View itemView) {
            super(itemView);
            producto = itemView.findViewById(R.id.txtVNombreProductoEncargo);
            cantidad = itemView.findViewById(R.id.txtVCantidadProductoEncargo);
            precio = itemView.findViewById(R.id.txtVPrecioProductoEncargo);
            baseview=itemView.findViewById(R.id.baseView);
        }
    }

    public static class callBack extends DiffUtil.ItemCallback<EncargosAdmin> {
        @Override
        public boolean areItemsTheSame(@NonNull EncargosAdmin oldItem, @NonNull EncargosAdmin newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull EncargosAdmin oldItem, @NonNull EncargosAdmin newItem) {
            return oldItem.getUsuarioEmail().equals(newItem.getUsuarioEmail()) &&
                    oldItem.getProductos().equals(newItem.getProductos()) &&
                    oldItem.getPrecioTotal().equals(newItem.getPrecioTotal());
        }
    }

    public interface MyListener
    {
        void onClick(EncargosAdmin ca);


    }
}
