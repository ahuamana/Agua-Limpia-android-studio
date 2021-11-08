package edu.aha.agualimpiafinal.activities.plants;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityGirasolBinding;

public class GirasolActivity extends AppCompatActivity {

    ActivityGirasolBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGirasolBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }
}