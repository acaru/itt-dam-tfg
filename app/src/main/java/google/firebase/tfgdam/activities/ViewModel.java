package google.firebase.tfgdam.activities;

import android.util.Log;

import java.util.ArrayList;

public class ViewModel {

    public static ArrayList<String> getLista(){
        ArrayList<String> lista = new ArrayList<>();
        int cont = 0;
        while(cont < 30){
            lista.add(String.valueOf(cont));
            cont++;
            Log.d("check", "lista");
        }
        return lista;

    }

}
