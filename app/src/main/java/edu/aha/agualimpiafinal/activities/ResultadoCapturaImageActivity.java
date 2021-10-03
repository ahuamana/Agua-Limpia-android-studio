package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityResultadoCapturaImageBinding;

public class ResultadoCapturaImageActivity extends AppCompatActivity {

    private ActivityResultadoCapturaImageBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultadoCapturaImageBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);



        SetOnclickListeners();

        goToPoints();


    }

    private void goToPoints() {

        binding.fabPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ResultadoCapturaImageActivity.this, PointsActivity.class);
                startActivity(i);

            }
        });

    }

    private void SetOnclickListeners() {

        binding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }
}