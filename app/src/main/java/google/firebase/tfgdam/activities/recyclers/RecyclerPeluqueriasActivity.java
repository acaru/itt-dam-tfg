package google.firebase.tfgdam.activities.recyclers;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import google.firebase.tfgdam.R;
import google.firebase.tfgdam.activities.recyclers.adapters.PeluqueriaAdapter;
import google.firebase.tfgdam.controller.ViewModel;
import google.firebase.tfgdam.model.Peluqueria;

public class RecyclerPeluqueriasActivity extends AppCompatActivity {

    private RecyclerView rvPeluquerias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_peluquerias);

        Log.d("manuel", "Ha entrado");
        final PeluqueriaAdapter peluqueriaAdapter = new PeluqueriaAdapter(this.getApplicationContext(), new PeluqueriaAdapter.OnItemClickListener() {
            @Override
            public void onClick(Peluqueria p) {
                Intent intent = new Intent(google.firebase.tfgdam.activities.recyclers.RecyclerPeluqueriasActivity.this, google.firebase.tfgdam.activities.recyclers.ServiciosRecycler.class);
                intent.putExtra("peluqueria", p);
                startActivity(intent);
            }
        });
        peluqueriaAdapter.setData(ViewModel.getPeluquerias());
        rvPeluquerias = findViewById(R.id.rvPeluquerias);
        rvPeluquerias.setLayoutManager(new LinearLayoutManager(this));
        rvPeluquerias.setAdapter(peluqueriaAdapter);

    }
}