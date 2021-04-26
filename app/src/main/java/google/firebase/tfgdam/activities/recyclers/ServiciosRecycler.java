package google.firebase.tfgdam.activities.recyclers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import google.firebase.tfgdam.R;
import google.firebase.tfgdam.activities.recyclers.adapters.ServiciosAdapter;
import google.firebase.tfgdam.controller.ViewModel;

public class ServiciosRecycler extends AppCompatActivity {

    RecyclerView rvServicios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_recycler);
        final ServiciosAdapter serviciosAdapter = new ServiciosAdapter(this.getApplicationContext());
        serviciosAdapter.setData(ViewModel.getServicios());
        rvServicios = findViewById(R.id.rvServicios);
        rvServicios.setLayoutManager(new LinearLayoutManager(this));
        rvServicios.setAdapter(serviciosAdapter);
    }
}