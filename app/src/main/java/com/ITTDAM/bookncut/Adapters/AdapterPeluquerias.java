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
import com.ITTDAM.bookncut.models.Peluqueria;

import java.util.List;
import java.util.Random;

public class AdapterPeluquerias extends ListAdapter<Peluqueria, AdapterPeluquerias.ViewHolder> {

    private MyListener listener;//listener para el click del elemento
    private boolean isSearchList;//si es una lista

    public AdapterPeluquerias(List<Peluqueria> peluqueria, MyListener listener) {
        super(new callBack());//lo primero que se ejecuta para comprobar elementos cita
        this.submitList(peluqueria);//pinta la lista que se va a mostrar
        this.listener = listener;//setea el listener declarado en el activity
        this.isSearchList = false;//inicializa el searchList para hacer el onclick del elemento
    }

    //ViewHolder contenedor que sostiene al item (layout)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //toma la view y adapta el diseño del layout segun los parametros necesarios
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peluquerias, parent, false);
        return new ViewHolder(view);//devuelve la view adaptada al padre
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //toma la peluqueria de la posicion seleccionada
        final Peluqueria Peluqueria = getCurrentList().get(position);
        //holder es la view adaptada y nombre es un textview declarado en la clase viewholder, le setea el texto de al textview de nombre de la peluqueria
        holder.nombre.setText(Peluqueria.getNombre());
        //holder es la view adaptada e imagen es un imageview declarado en la clase viewholder, le setea la imagen a mostrar para la peluqueria
        holder.image.setAdjustViewBounds(true);
        //obtiene un numero random entre 0 y 1
        final int random = new Random().nextInt() + 1;
        switch (random){
            case 0://si es 0 pone una imagen
                holder.image.setImageResource(R.drawable.peluquerias1);
            case 1://si es 1 otra
                holder.image.setImageResource(R.drawable.peluquerias2);
            default://imagen default
                holder.image.setImageResource(R.drawable.fondo1);
        }

        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(Peluqueria);
                }//setea el valor del onclick con el valor del item seleccionado
            });
    }


    //Contador de items
    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    //Plantilla donde defines que items tiene ViewHolder. A los elementos que les vas a cambiar el valor.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre;//declara las variables textview usadas en el item, declaradas anteriormente en un xml en layouts
        ImageView image;//declara la imagen que aparecera
        View baseview;//baseview es el layout que contiene los items del recyclerview
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtVNombrePeluqueria);//es el edittext declarado en el xml que contendra el valor del nombre de la peluqueria
            image = itemView.findViewById(R.id.imgPeluqueria);//es la imagen que aparecera en el recyclerview
            baseview=itemView.findViewById(R.id.baseView);//el view que contiene los items del recyclerview
        }
    }

    //Clase estática del tipo callback que compara citas las nuevas con las antiguas para ver si existen o no devuelve un boolean
    public static class callBack extends DiffUtil.ItemCallback<Peluqueria> {
        @Override
        public boolean areItemsTheSame(@NonNull Peluqueria oldItem, @NonNull Peluqueria newItem) {
            return oldItem.equals(newItem);//si los items son iguales devuelve true
        }

        @Override
        public boolean areContentsTheSame(@NonNull Peluqueria oldItem, @NonNull Peluqueria newItem) {
            return oldItem.getNombre().equals(newItem.getNombre()) &&
                    oldItem.getHorario().equals(newItem.getHorario()) &&
                    oldItem.getPropietario().equals(newItem.getPropietario());
        }//hace una comparacion de los items usuario, dia, hora y finalizado para comprobar si son los mismos que los anteriores
    }

    //Interfaz que añade clics a los elementos de las peluquerias
    public interface MyListener
    {
        void onClick(Peluqueria ca);//metodo que ejecutara cuando se hace click en el elemento necesita de parametro una cita de peluqueria


    }
}
