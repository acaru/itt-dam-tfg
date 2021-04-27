package google.firebase.tfgdam.activities.recyclers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import google.firebase.tfgdam.R;
import google.firebase.tfgdam.activities.recyclers.adapters.ServiciosAdapter;
import google.firebase.tfgdam.controller.ViewModel;
import google.firebase.tfgdam.model.Servicio;

public class ServiciosRecycler extends AppCompatActivity {

    RecyclerView rvServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_recycler);
        final ServiciosAdapter serviciosAdapter = new ServiciosAdapter(this.getApplicationContext(), new ServiciosAdapter.OnItemClickListener(){
            @Override
            public void onItemClickListener(Servicio s) {

                Log.d("check", "Va por aqui");

                Intent intent = new Intent(ServiciosRecycler.this, CitasRecyclerActivity.class);
                intent.putExtra("servicio", s);
                startActivity(intent);
            }
        });
        serviciosAdapter.setData(ViewModel.getServicios());
        rvServicios = findViewById(R.id.rvServicios);
        rvServicios.setLayoutManager(new LinearLayoutManager(this));
        rvServicios.setAdapter(serviciosAdapter);
    }
}