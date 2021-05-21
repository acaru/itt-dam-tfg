package com.ITTDAM.bookncut.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Productos;

import java.util.List;

public class AdapterProductosUsuario extends ListAdapter<Productos, AdapterProductosUsuario.ViewHolder> {

    private MyListener listener;
    private boolean isSearchList;

    public AdapterProductosUsuario(List<Productos> productos, MyListener listener) {
        super(new callBack());
        this.submitList(productos);
        this.listener = listener;
        this.isSearchList = false;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_usuario, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Productos productos = getCurrentList().get(position);
        holder.nombre.setText(productos.getNombre());
        //holder.image.setText("Tipo: "+productos.getTipo());
        holder.precio.setText(productos.getPrecio()+"€");
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(productos);
                }
            });
    }

    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, precio;
        ImageView image;
        View baseview;
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtVNombreProductoUsuario);
            image = itemView.findViewById(R.id.imgProductoUsuario);
            precio = itemView.findViewById(R.id.txtVPrecioProductoUsuario);
            baseview=itemView.findViewById(R.id.baseView);
        }
    }

    public static class callBack extends DiffUtil.ItemCallback<Productos> {
        @Override
        public boolean areItemsTheSame(@NonNull Productos oldItem, @NonNull Productos newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Productos oldItem, @NonNull Productos newItem) {
            return oldItem.getNombre().equals(newItem.getNombre()) &&
                    oldItem.getTipo().equals(newItem.getTipo()) &&
                    oldItem.getPrecio().equals(newItem.getPrecio());
        }
    }

    public interface MyListener
    {
        void onClick(Productos ca);


    }
}
