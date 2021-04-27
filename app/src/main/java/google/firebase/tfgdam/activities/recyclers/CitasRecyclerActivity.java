package google.firebase.tfgdam.activities.recyclers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import google.firebase.tfgdam.R;
import google.firebase.tfgdam.activities.ConfirmarCitaActivity;
import google.firebase.tfgdam.activities.recyclers.adapters.CitaAdapter;
import google.firebase.tfgdam.model.Cita;

public class CitasRecyclerActivity extends AppCompatActivity {


    private RecyclerView rvCitas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_citas_recycler);

        final CitaAdapter citaAdapter = new CitaAdapter(this.getApplicationContext(), new CitaAdapter.OnItemClickListener() {
            @Override
            public void onClick(Cita c) {
                Log.d("check", "Listener");
                Intent intent = new Intent(CitasRecyclerActivity.this, ConfirmarCitaActivity.class);
                intent.putExtra("cita", c);
                startActivity(intent);
            }
        });
        rvCitas = findViewById(R.id.rvPeluquerias);
        rvCitas.setLayoutManager(new LinearLayoutManager(this));
        rvCitas.setAdapter(citaAdapter);
    }
}