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
import com.ITTDAM.bookncut.models.Servicios;
import com.ITTDAM.bookncut.models.Servicios;

import java.util.List;

public class AdapterServicios extends ListAdapter<Servicios, AdapterServicios.ViewHolder> {

    private MyListener listener;//listener para el click del elemento
    private boolean isSearchList;//si es una lista

    public AdapterServicios(List<Servicios> servicios, MyListener listener) {
        super(new callBack());//lo primero que se ejecuta para comprobar elementos cita
        this.submitList(servicios);//pinta la lista que se va a mostrar
        this.listener = listener;//setea el listener declarado en el activity
        this.isSearchList = false;//inicializa el searchList para hacer el onclick del elemento
    }

    //ViewHolder contenedor que sostiene al item (layout)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //toma la view y adapta el diseño del layout segun los parametros necesarios
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_servicio, parent, false);
        return new ViewHolder(view);//devuelve la view adaptada al padre
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //toma el valor de la posicion seleccionada
        final Servicios servicio = getCurrentList().get(position);
        //holder es la view adaptada y nombre es un textview declarado en la clase viewholder, le setea el texto de al textview de nombre del servicio
        holder.nombre.setText(servicio.getNombre());
        //holder es la view e imagen es un Imageview declarado en la clase viewholder, le setea el precio del producto
        holder.precio.setText("Precio: "+servicio.getPrecio()+"€");
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(servicio);
                }
            });//setea el valor del onclick con el valor del item seleccionado
    }

    //Contador de items
    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }


    //Plantilla donde defines que items tiene ViewHolder. A los elementos que les vas a cambiar el valor.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, duracion,precio;//declara las variables textview usadas en el item, declaradas anteriormente en un xml en layouts
        View baseview;//baseview es el layout que contiene los items del recyclerview
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtVNombreServicio);//es el edittext declarado en el xml que contendra el valor del nombre del servicio
            precio = itemView.findViewById(R.id.txtVPrecioServicio);//es el edittext declarado en el xml que contendra el valor del precio del servicio
            baseview=itemView.findViewById(R.id.baseView);//el view que contiene los items del recyclerview
        }
    }

    //Clase estática del tipo callback que compara citas las nuevas con las antiguas para ver si existen o no devuelve un boolean
    public static class callBack extends DiffUtil.ItemCallback<Servicios> {
        @Override
        public boolean areItemsTheSame(@NonNull Servicios oldItem, @NonNull Servicios newItem) {
            return oldItem.equals(newItem);//si los items son iguales devuelve true
        }

        @Override
        public boolean areContentsTheSame(@NonNull Servicios oldItem, @NonNull Servicios newItem) {
            return oldItem.getNombre().equals(newItem.getNombre()) &&
                    oldItem.getPrecio().equals(newItem.getPrecio());
        }//hace una comparacion de los items nombre, tipo y precio para comprobar si son los mismos que los anteriores
    }

    //Interfaz que añade clics a los elementos de las peluquerias
    public interface MyListener
    {
        void onClick(Servicios ca);//metodo que ejecutara cuando se hace click en el elemento necesita de parametro una cita de peluqueria


    }
}
