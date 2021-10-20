package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.activities.macroinvertebrados.AnacroneuriaActivity;
import edu.aha.agualimpiafinal.databinding.ActivityWaterChallengerBinding;
import edu.aha.agualimpiafinal.databinding.CustomDialogMoreinfoBinding;

public class WaterChallengerActivity extends AppCompatActivity {

    private ActivityWaterChallengerBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWaterChallengerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setPrimaryData();

        openDialogs();



    }

    private void openDialogs() {

        binding.cardview1.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(WaterChallengerActivity.this, AnacroneuriaActivity.class);
                createDialog(R.drawable.macroinvertebrado_anacroneuria,R.string.text_anacroneuria,intent,"Anacroneuria");
            }
        });

        binding.cardview2.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterChallengerActivity.this, AnacroneuriaActivity.class);
                createDialog(R.drawable.macroinvertebrado_naucoridae,R.string.text_anacroneuria,intent,"Naucoridae");
            }
        });

        binding.cardview3.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WaterChallengerActivity.this, AnacroneuriaActivity.class);
                createDialog(R.drawable.macroinvertebrado_caracoldeagua,R.string.text_anacroneuria,intent,"Caracol de agua");
            }
        });
    }

    private void setPrimaryData() {

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        experiemtsWithMicroinvertebrados();

        experimentsWithWater();

    }

    private void experiemtsWithMicroinvertebrados() {

        binding.cardview1.name.setText("Anacroneuria");
        binding.cardview1.roundedImageView.setImageDrawable(getDrawable(R.drawable.macroinvertebrado_anacroneuria));

        binding.cardview2.name.setText("Naucoridae");
        binding.cardview2.roundedImageView.setImageDrawable(getDrawable(R.drawable.macroinvertebrado_naucoridae));

        binding.cardview3.name.setText("Lymnaeidae");
        binding.cardview3.roundedImageView.setImageDrawable(getDrawable(R.drawable.macroinvertebrado_caracoldeagua));

    }

    private void experimentsWithWater() {

        binding.cardviewExperimento1.name.setText("Tension superficial");
        binding.cardviewExperimento1.roundedImageView.setImageDrawable( getDrawable(R.drawable.tensionsuperficial) );

        binding.cardviewExperimento2.name.setText("Difusion");
        binding.cardviewExperimento2.roundedImageView.setImageDrawable( getDrawable(R.drawable.difusion_de_agua) );

        binding.cardviewExperimento3.name.setText("Lampara lava");
        binding.cardviewExperimento3.roundedImageView.setImageDrawable( getDrawable(R.drawable.lampara_lava) );

        binding.cardviewExperimento4.name.setText("Fantasma espumante");
        binding.cardviewExperimento4.roundedImageView.setImageDrawable( getDrawable(R.drawable.fantasmas_espumosos) );

    }

    private void createDialog(int drawable, int textChallenge, Intent intentReceiver, String title)
    {
        CustomDialogMoreinfoBinding customBinding = CustomDialogMoreinfoBinding.inflate(LayoutInflater.from(this));

        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(customBinding.getRoot());

        customBinding.textViewTittle.setText(title);
        customBinding.imageViewChallenge.setImageResource(drawable);
        customBinding.textViewChallenge.setText(textChallenge);

        CardView.LayoutParams params = new CardView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );

        params.setMargins(30,0,30,0);
        customBinding.cardParent.setLayoutParams(params);

        customBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        customBinding.btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(WaterChallengerActivity.this, "Bien hecho", Toast.LENGTH_SHORT).show();
                startActivity(intentReceiver);

            }
        });

        dialog.show();

    }
}