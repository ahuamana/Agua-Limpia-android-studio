package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityRegistrarMuestraMicrobiologicaBinding;

public class RegistrarMuestraMicrobiologicaActivity extends AppCompatActivity {

    private ActivityRegistrarMuestraMicrobiologicaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrarMuestraMicrobiologicaBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //all code here

        showBackActivity();


    }

    private void showBackActivity() {

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}