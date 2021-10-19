package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityWaterChallengerBinding;

public class WaterChallengerActivity extends AppCompatActivity {

    private ActivityWaterChallengerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaterChallengerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.cardview1.name.setText("Anacroneuria");
        binding.cardview1.roundedImageView.setImageDrawable(getDrawable(R.drawable.macroinvertebrado_anacroneuria));

        binding.cardview2.name.setText("Naucoridae");
        binding.cardview2.roundedImageView.setImageDrawable(getDrawable(R.drawable.macroinvertebrado_naucoridae));

        binding.cardview3.name.setText("Lymnaeidae");
        binding.cardview3.roundedImageView.setImageDrawable(getDrawable(R.drawable.macroinvertebrado_caracoldeagua));

    }
}