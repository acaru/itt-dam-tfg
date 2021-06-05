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
import com.ITTDAM.bookncut.models.Facturas;

import java.util.List;

public class AdapterFacturasAdmin extends ListAdapter<Facturas, AdapterFacturasAdmin.ViewHolder> {

    private MyListener listener;//listener para el click del elemento
    private boolean isSearchList;//si es una lista

    public AdapterFacturasAdmin(List<Facturas> Facturas, MyListener listener) {
        super(new callBack());//lo primero que se ejecuta para comprobar elementos de la factura
        this.submitList(Facturas);//pinta la lista que se va a mostrar
        this.listener = listener;//setea el listener declarado en el activity
        this.isSearchList = false;//inicializa el searchList para hacer el onclick del elemento
    }

    //ViewHolder contenedor que sostiene al item (layout)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //toma la view y adapta el diseño del layout segun los parametros necesarios
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_facturas, parent, false);
        return new ViewHolder(view);//devuelve la view adaptada al padre
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //toma la cita de la factura de la posicion seleccionada
        final Facturas Facturas = getCurrentList().get(position);
        //holder es la view adaptada y usuario es un textview declarado en la clase viewholder, le setea el texto de al textview de usuario con el nombre del usuario
        holder.usuario.setText("Usuario: " + Facturas.getUsuario());
        //Declara las variables usadas como comodines para mostrar los elementos de productos y cantidades
        String productosTodos="";//variable para productos
        String cantidadesTodos="";//variable para cantiodades
        //for que recorre desde 0 hasta el tamaño de la lista de los productos que es igual el tamaño de la lista de cantidades
        for(int k=0;k<Facturas.getProductos().size();k++){
            //añade al string de productosTodos el valor del nombre del producto
            productosTodos+=""+Facturas.getProductos().get(k)+"\n";
            //añade al string de cantidadesTodos el valor de las cantidades del producto
            cantidadesTodos+="X "+Facturas.getCantidades().get(k)+"\n";
        }
        //holder es la view adaptada y productos es un textview declarado en la clase viewholder, le setea el texto de al textview de los productos en la factura
        holder.productos.setText(productosTodos);
        //holder es la view adaptada y cantidades es un textview declarado en la clase viewholder, le setea el texto de al textview de las cantidades de los productos en la factura
        holder.cantidades.setText(cantidadesTodos);
        //holder es la view adaptada y total es un textview declarado en la clase viewholder, le setea el texto del total de la factura
        holder.total.setText("Total: "+Facturas.getTotal()+"€");
        if(!isSearchList)// si no es una lista de busqueda añade le onclick al item del recyclerview
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(Facturas);
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
        TextView productos, usuario,cantidades,total;//declara las variables textview usadas en el item, declaradas anteriormente en un xml en layouts
        View baseview;//baseview es el layout que contiene los items del recyclerview
        ViewHolder(View itemView) {
            super(itemView);
            usuario = itemView.findViewById(R.id.txtVUsuarioFactura);//es el edittext declarado en el xml que contendra el valor de usuario
            cantidades = itemView.findViewById(R.id.txtVCantidadesFactura);//es el edittext declarado en el xml que contendra el valor de las cantidades de los productos
            productos = itemView.findViewById(R.id.txtVProductosFactura);//es el edittext declarado en el xml que contendra el valor de los nombres de los productos
            total=itemView.findViewById(R.id.txtVTotalFactura);//es el edittext declarado en el xml que contendra el valor del total que se pago de la factura
            baseview=itemView.findViewById(R.id.baseView);//el view que contiene los items del recyclerview
        }
    }
    //Clase estática del tipo callback que compara citas las nuevas con las antiguas para ver si existen o no devuelve un boolean
    public static class callBack extends DiffUtil.ItemCallback<Facturas> {
        @Override
        public boolean areItemsTheSame(@NonNull Facturas oldItem, @NonNull Facturas newItem) {
            return oldItem.equals(newItem);//si los items son iguales devuelve true
        }

        @Override
        public boolean areContentsTheSame(@NonNull Facturas oldItem, @NonNull Facturas newItem) {
            return oldItem.getProductos().equals(newItem.getProductos()) &&
                    oldItem.getTotal().equals(newItem.getTotal()) &&
                    oldItem.getUsuario().equals(newItem.getUsuario())&&
                            oldItem.getPeluqueria().equals(newItem.getPeluqueria())&&
                    oldItem.getCantidades().equals(newItem.getCantidades());
        }//hace una comparacion de los items productos, total, usuario, peluqueria y cantidades para comprobar si son los mismos que los anteriores
    }

    //Interfaz que añade clics a los elementos de las facturas
    public interface MyListener
    {
        void onClick(Facturas ca);//metodo que ejecutara cuando se hace click en el elemento necesita de parametro una factura


    }
}
