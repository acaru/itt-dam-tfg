package com.ITTDAM.bookncut.Adapters;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
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

import java.io.InputStream;
import java.util.List;

public class AdapterProductosUsuario extends ListAdapter<Productos, AdapterProductosUsuario.ViewHolder> {

    public static final String TAG="ADAPTER PRODUCTO";//tag usada para determinar la posicion y contexto de los logs
    private MyListener listener;//listener para el click del elemento
    private boolean isSearchList;//si es una lista

    public AdapterProductosUsuario(List<Productos> productos, MyListener listener) {
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_producto_usuario, parent, false);
        return new ViewHolder(view);//devuelve la view adaptada al padre
    }

    //Pone los valores al item del ViewHolder, gestiona los items y los recorre internamente
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        //toma el de la posicion seleccionada
        final Productos productos = getCurrentList().get(position);
        //holder es la view adaptada y nombre es un textview declarado en la clase viewholder, le setea el texto de al textview de nombre del producto
        holder.nombre.setText(productos.getNombre());
        //holder es la view e imagen es un Imageview declarado en la clase viewholder, le setea una imagen a partir del valor seleccionado del tipo de producto
        holder.image.setAdjustViewBounds(true);
        //switch que decide que imagen se utiliza a partir del tipo
        switch (productos.getTipo()){
            case "Maquina de afeitar":
                holder.image.setImageBitmap(decodeSampledBitmapFromResource(holder.baseview.getResources(), R.drawable.apurado_maquinas_afeitar, 100, 100));
                break;
            case "Champu":
                holder.image.setImageBitmap(decodeSampledBitmapFromResource(holder.baseview.getResources(), R.drawable.champus, 100, 100));
                break;
            case "Fijador":
                holder.image.setImageBitmap(decodeSampledBitmapFromResource(holder.baseview.getResources(), R.drawable.fijadores, 100, 100));
                break;
            case "Tijera":
                holder.image.setImageBitmap(decodeSampledBitmapFromResource(holder.baseview.getResources(), R.drawable.tijeras, 100, 100));
                break;
            case "Tinte":
                holder.image.setImageBitmap(decodeSampledBitmapFromResource(holder.baseview.getResources(), R.drawable.tintes, 100, 100));
                break;
            case "Peine o Cepillo":
                holder.image.setImageBitmap(decodeSampledBitmapFromResource(holder.baseview.getResources(), R.drawable.peinados, 100, 100));
                break;

        }

        //holder es la view adaptada y precio es un textview declarado en la clase viewholder, le setea el texto de al textview de precio del producto
        holder.precio.setText(productos.getPrecio()+"€");
        if(!isSearchList)
            holder.baseview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(productos);
                }
            });//setea el valor del onclick con el valor del item seleccionado
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {


        //primero de codifica con inJustDecodeBounds=true para verificar las dimensiones de la imagen
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calcula el valor para la toma de ejemplo
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decodifica el bitmap usando el tamaño de ejemplo
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Valores de altura y anchura
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;


            // Calcula el valor mas grande dentro de inSampleSize ques un elevado al cuadrado y mantiene ambos

            //altura y anchura son mas grandes que el necesitado
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    //Contador de items
    @Override
    public int getItemCount() {
        return getCurrentList().size();
    }

    //Plantilla donde defines que items tiene ViewHolder. A los elementos que les vas a cambiar el valor.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, precio;//declara las variables textview usadas en el item, declaradas anteriormente en un xml en layouts
        ImageView image;//declara la variable para el imageview usadas en el item, declarados anteriormente en un xml en layouts
        View baseview;//baseview es el layout que contiene los items del recyclerview
        ViewHolder(View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtVNombreProductoUsuario);//es el edittext declarado en el xml que contendra el valor del nombre del producto
            image = itemView.findViewById(R.id.imgProductoUsuario);//image es el imageview declaradp en el xml que contendra el valor de la imagen bsandose en el tipo de producto seleccionado
            precio = itemView.findViewById(R.id.txtVPrecioProductoUsuario);//es el edittext declarado en el xml que contendra el valor del precio del producto
            baseview=itemView.findViewById(R.id.baseView);//el view que contiene los items del recyclerview
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
