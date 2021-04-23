package google.firebase.tfgdam.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import google.firebase.tfgdam.R;

public class RecyclerPeluqueriasActivity extends AppCompatActivity {

    private RecyclerView rvPeluquerias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_peluquerias);

        Log.d("check", "Ha entrado");
        PeluqueriaAdapter peluqueriaAdapter = new PeluqueriaAdapter(this.getApplicationContext());
        peluqueriaAdapter.setData(ViewModel.getLista());
        rvPeluquerias = findViewById(R.id.rvPeluquerias);
        rvPeluquerias.setAdapter(peluqueriaAdapter);
    }
}