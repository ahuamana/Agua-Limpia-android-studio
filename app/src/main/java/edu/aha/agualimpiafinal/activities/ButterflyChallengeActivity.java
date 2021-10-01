package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityButterflyChallengeBinding;

public class ButterflyChallengeActivity extends AppCompatActivity {

    private ActivityButterflyChallengeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityButterflyChallengeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //All code here

    }
}