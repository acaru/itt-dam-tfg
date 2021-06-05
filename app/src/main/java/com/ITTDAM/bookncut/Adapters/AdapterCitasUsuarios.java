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
import com.ITTDAM.bookncut.models.CitasUsuario;

import java.util.List;

public class AdapterCitasUsuarios extends ListAdapter<CitasUsuario,AdapterCitasUsuarios.ViewHolder> {

    private MyListener listener;//listener para el click del elemento
    private boolean isSearchList;//si es una lista

    public AdapterCitasUsuarios(List<CitasUsuario> listCitas,MyListener listener) {
        super(new callBack()); //lo primero que se ejecuta para comprobar elementos cita
        this.submitList(listCitas); //pinta la lista que se va a mostrar
        this.listener = listener;//setea el listener declarado en el activity
        this.isSearchList = false;//inicializa el searchList para hacer el onclick del elemento
    }

    //ViewHolder contenedor que sostiene al item (layout)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //toma la view y adapta el diseño del layout segun los parametros necesarios
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cita_usuario, parent, false);
        return new ViewHolder(view);//devuelve la view adaptada al padre
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //toma la cita del usuario de la posicion seleccionada
        final CitasUsuario citasUsuario = getCurrentList().get(position);
        //holder es la view adaptada y peluqueria es un textview declarado en la clase viewholder, le setea el texto de al textview de la peluqueria con el nombre de la peluqueria
        holder.peluqueria.setText(citasUsuario.getPeluqueria());
        //holder es la view adaptada y diahora es un textview declarado en la clase viewholder, le setea el texto de al textview del dia y la hora de la cita en la peluqueria
        holder.diahora.setText("El "+citasUsuario.getDia()+" a las "+citasUsuario.getHora()+" horas");
        //holder es la view adaptada y servicio es un textview declarado en la clase viewholder, le setea el texto de al textview del servicio de la cita en la peluqueria
        holder.servicio.setText("Se hara "+citasUsuario.getServicio());
        if(!isSearchList)// si no es una lista de busqueda añade le onclick al item del recyclerview
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(citasUsuario);
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
        TextView peluqueria, diahora,servicio;//declara las variables textview usadas en el item, declaradas anteriormente en un xml en layouts
        View baseview;//baseview es el layout que contiene los items del recyclerview
        ViewHolder(View itemView) {//declara el constructor de la clase viewholder
            super(itemView);
            peluqueria = itemView.findViewById(R.id.txtVPeluqueriaRVCitaUsuario);//es el edittext declarado en el xml que contendra el valor del nombre de la peluqueria
            servicio = itemView.findViewById(R.id.txtVServicioRVCitaUsuario);//es el edittext declarado en el xml que contendra el valor del servicio
            diahora = itemView.findViewById(R.id.txtVDiaHoraRVCitaUsuario);//es el edittext declarado en el xml que contendra el valor de la dia y la hora
            baseview=itemView.findViewById(R.id.baseView);//el view que contiene los items del recyclerview
        }
    }

    //Clase estática del tipo callback que compara citas las nuevas con las antiguas para ver si existen o no devuelve un boolean
    public static class callBack extends DiffUtil.ItemCallback<CitasUsuario> {
        @Override
        public boolean areItemsTheSame(@NonNull CitasUsuario oldItem, @NonNull CitasUsuario newItem) {
            return oldItem.equals(newItem);//si los items son iguales devuelve true
        }

        @Override
        public boolean areContentsTheSame(@NonNull CitasUsuario oldItem, @NonNull CitasUsuario newItem) {
            return oldItem.getPeluqueria().equals(newItem.getPeluqueria()) &&
                    oldItem.getDia().equals(newItem.getDia()) &&
                    oldItem.getHora().equals(newItem.getHora()) &&
                            (oldItem.getFinalizado()&&newItem.getFinalizado());
        }//hace una comparacion de los items peluqueria, dia, hora y finalizado para comprobar si son los mismos que los anteriores
    }

    //Interfaz que añade clics a los elementos de las CitasPeluqueria
    public interface MyListener
    {
        void onClick(CitasUsuario ca);//metodo que ejecutara cuando se hace click en el elemento necesita de parametro una cita de peluqueria


    }
}
