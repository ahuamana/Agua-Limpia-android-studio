package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.activities.macroinvertebrados.AnacroneuriaActivity;
import edu.aha.agualimpiafinal.activities.plants.GirasolActivity;
import edu.aha.agualimpiafinal.databinding.ActivityPlantsChallenger2Binding;
import edu.aha.agualimpiafinal.databinding.CustomDialogMoreinfoBinding;

public class PlantsChallengerActivity2 extends AppCompatActivity {

    ActivityPlantsChallenger2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantsChallenger2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        setDataChallenges();

        openDialogs();
    }


    private void openDialogs() {

        binding.cardview1.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), GirasolActivity.class); //Girasol Activity
                createDialog(R.drawable.flower_girasol,R.string.text_flower_girasol,intent,"Girasol");
            }
        });

        binding.cardview2.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnacroneuriaActivity.class);
                createDialog(R.drawable.flower_margarita,R.string.text_flower_margarita,intent,"Margarita");
            }
        });

        binding.cardview3.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnacroneuriaActivity.class);
                createDialog(R.drawable.flower_new,R.string.text_flower_lirio,intent,"Lirio");
            }
        });

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void setDataChallenges() {

        binding.cardview1.name.setText("Girasol");
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/agualimpiafinal.appspot.com/o/LaboratorioDigital%2FPlantsChallenge%2Fflower_girasol.png?alt=media&token=e9b3df21-ef52-41da-8161-e5a357423a04")
                .placeholder(R.drawable.loading_icon)
                .into(binding.cardview1.roundedImageView);

        //binding.cardview1.roundedImageView.setImageDrawable(getDrawable(R.drawable.flower_girasol));

        binding.cardview2.name.setText("Margarita");
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/agualimpiafinal.appspot.com/o/LaboratorioDigital%2FPlantsChallenge%2Fflower_margarita.png?alt=media&token=d3daef5d-c724-40dc-8fd5-92c009924631")
                .placeholder(R.drawable.loading_icon)
                .into(binding.cardview2.roundedImageView);
        //binding.cardview2.roundedImageView.setImageDrawable(getDrawable(R.drawable.flower_margarita));

        binding.cardview3.name.setText("Lirio");
        Glide.with(this)
                .load("https://firebasestorage.googleapis.com/v0/b/agualimpiafinal.appspot.com/o/LaboratorioDigital%2FPlantsChallenge%2Fflower_new.png?alt=media&token=5ddd2ad6-a8ef-41b8-a42c-81353b0b59ce")
                .placeholder(R.drawable.loading_icon)
                .into(binding.cardview3.roundedImageView);
        //binding.cardview3.roundedImageView.setImageDrawable(getDrawable(R.drawable.flower_new));

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
                Toast.makeText(getApplicationContext(), "Bien hecho", Toast.LENGTH_SHORT).show();
                startActivity(intentReceiver);

            }
        });

        dialog.show();

    }

}