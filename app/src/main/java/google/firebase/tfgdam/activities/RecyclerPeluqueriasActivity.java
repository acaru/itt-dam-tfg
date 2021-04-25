package google.firebase.tfgdam.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import google.firebase.tfgdam.R;

public class RecyclerPeluqueriasActivity extends AppCompatActivity {

    private RecyclerView rvPeluquerias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_peluquerias);

        Log.d("check", "Ha entrado");
        google.firebase.tfgdam.activities.PeluqueriaAdapter peluqueriaAdapter = new google.firebase.tfgdam.activities.PeluqueriaAdapter(this.getApplicationContext());
        peluqueriaAdapter.setData(ViewModel.getLista());
        rvPeluquerias = findViewById(R.id.rvPeluquerias);
        rvPeluquerias.setAdapter(peluqueriaAdapter);
    }
}