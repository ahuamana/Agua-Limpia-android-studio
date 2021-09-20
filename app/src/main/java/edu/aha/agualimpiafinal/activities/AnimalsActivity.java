package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityAnimalsBinding;

public class AnimalsActivity extends AppCompatActivity {

   private ActivityAnimalsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimalsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);




    }
}