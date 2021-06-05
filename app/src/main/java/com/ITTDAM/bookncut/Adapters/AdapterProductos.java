package com.ITTDAM.bookncut.Adapters;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.ITTDAM.bookncut.R;
import com.ITTDAM.bookncut.models.Productos;
import com.ITTDAM.bookncut.models.Productos;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AdapterProductos extends ListAdapter<Productos, AdapterProductos.ViewHolder> {

    private MyListener listener;//listener para el click del elemento
    private boolean isSearchList;//si es una lista

    private int position;//variable que guarda el valor de la posicion de el elemento en la coleccion

    public int getPosition() {
        return position;
    }//devuelve el valor posicio

    public void setPosition(int position) {
        this.position = position;
    }//pone el valor de la posicion donde hace longclick

    public AdapterProductos(List<Productos> productos, MyListener listener) {
        super(new callBack());//lo primero que se ejecuta para comprobar elementos cita
        this.submitList(productos);//pinta la lista que se va a mostrar
        this.listener = listener;//setea el listener declarado en el activity
        this.isSearchList = false;//inicializa el searchList para hacer el onclick del elemento
    }

    //ViewHolder contenedor que sostiene al item (layout)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //toma la view y adapta el diseño del layout segun los parametros necesarios
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto, parent, false);
        return new ViewHolder(view);//devuelve la view adaptada al padre
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //toma el producto de la posicion seleccionada
        final Productos productos = getCurrentList().get(position);
        //holder es la view adaptada y nombre es un textview declarado en la clase viewholder, le setea el texto de al textview de nombre del producto
        holder.nombre.setText(productos.getNombre());
        //holder es la view adaptada y tipo es un textview declarado en la clase viewholder, le setea el texto de al textview de tipo del producto
        holder.tipo.setText("Tipo: "+productos.getTipo());
        //holder es la view adaptada y precio es un textview declarado en la clase viewholder, le setea el texto de al textview de precio del producto
        holder.precio.setText(productos.getPrecio()+"€");

        if(!isSearchList) {
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(productos);
                }//setea el valor del onclick con el valor del item seleccionado


            });
            holder.baseview.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //cuando hace un click largo, osea manteniendo click guarda la posicion
                    setPosition(position);
                    return false;
                }
            });

        }

    }

    //Contador de items
    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    //cuando el viewholder se borre el listener del lonk click se borra
    @Override
    public void onViewRecycled(@NonNull @NotNull ViewHolder holder) {
        holder.baseview.setOnLongClickListener(null);//
        super.onViewRecycled(holder);
    }

    //Plantilla donde defines que items tiene ViewHolder. A los elementos que les vas a cambiar el valor.
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView nombre, tipo,precio;//declara las variables textview usadas en el item, declaradas anteriormente en un xml en layouts
        View baseview;//baseview es el layout que contiene los items del recyclerview
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtVNombreProducto);//es el edittext declarado en el xml que contendra el valor del nombre del producto
            tipo = itemView.findViewById(R.id.txtVTipoProducto);//es el edittext declarado en el xml que contendra el valor del tipo del producto
            precio = itemView.findViewById(R.id.txtVPrecioProducto);//es el edittext declarado en el xml que contendra el valor del precio del producto
            baseview=itemView.findViewById(R.id.baseView);//el view que contiene los items del recyclerview
            itemView.setOnCreateContextMenuListener(this);//setea el listener para el menu para crear el menu cuando se haga long click
        }

        //se crea el menu cuando mantienen presionado el click mucho tiempo
        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            //añade los items al menu
            contextMenu.add(Menu.NONE, R.id.modificarCantidad, Menu.NONE, "Modificar la cantidad");//groupId, itemId, order, title
            contextMenu.add(0, R.id.borraCantidad, Menu.NONE, "Borrar");
        }
    }

    //Clase estática del tipo callback que compara citas las nuevas con las antiguas para ver si existen o no devuelve un boolean
    public static class callBack extends DiffUtil.ItemCallback<Productos> {
        @Override
        public boolean areItemsTheSame(@NonNull Productos oldItem, @NonNull Productos newItem) {
            return oldItem.equals(newItem);//si los items son iguales devuelve true
        }

        @Override
        public boolean areContentsTheSame(@NonNull Productos oldItem, @NonNull Productos newItem) {
            return oldItem.getNombre().equals(newItem.getNombre()) &&
                    oldItem.getTipo().equals(newItem.getTipo()) &&
                    oldItem.getPrecio().equals(newItem.getPrecio());
        }//hace una comparacion de los items nombre, tipo y precio para comprobar si son los mismos que los anteriores
    }

    //Interfaz que añade clics a los elementos de las peluquerias
    public interface MyListener
    {
        void onClick(Productos ca);//metodo que ejecutara cuando se hace click en el elemento necesita de parametro una cita de peluqueria

    }
}
