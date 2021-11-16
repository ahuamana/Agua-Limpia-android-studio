package edu.aha.agualimpiafinal.activities.Challenges;

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
import com.bumptech.glide.request.RequestOptions;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.activities.ButterflyChallengeActivity;
import edu.aha.agualimpiafinal.activities.macroinvertebrados.AnacroneuriaActivity;
import edu.aha.agualimpiafinal.databinding.ActivityAnimalsChallengerBinding;
import edu.aha.agualimpiafinal.databinding.CustomDialogMoreinfoBinding;

public class AnimalsChallengerActivity extends AppCompatActivity {

    private ActivityAnimalsChallengerBinding binding;

    String challenge_butterfly = "https://firebasestorage.googleapis.com/v0/b/agualimpiafinal.appspot.com/o/LaboratorioDigital%2FAnimalsChallenge%2FButterfly%2Fmariposa_icon.png?alt=media&token=deb8226a-40b1-4cde-b300-0c37b7cfb66e";
    String challenge_butterfly_name="Mariposa";
    String challenge_spider = "https://firebasestorage.googleapis.com/v0/b/agualimpiafinal.appspot.com/o/LaboratorioDigital%2FAnimalsChallenge%2FSpider%2Ftarantula.png?alt=media&token=b28df6b7-2412-4769-ad79-2076a5e63f31";
    String challenge_spider_name="Araña";
    String challenge_worm = "https://firebasestorage.googleapis.com/v0/b/agualimpiafinal.appspot.com/o/LaboratorioDigital%2FAnimalsChallenge%2Fworm%2Fgusano.png?alt=media&token=b2fc2e49-06bc-48a5-a52a-cf1a0011b6ae";
    String challenge_worm_name="Lombriz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimalsChallengerBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //set all code here
        setDataChallenges();

        openDialogs();
    }


    private void openDialogs() {

        binding.cardview1.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), ButterflyChallengeActivity.class); //Girasol Activity
                createDialog(challenge_butterfly,R.string.text_flower_girasol,intent,challenge_butterfly_name);
            }
        });

        binding.cardview2.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnacroneuriaActivity.class);
                createDialog(challenge_spider,R.string.text_flower_margarita,intent,challenge_spider_name);
            }
        });

        binding.cardview3.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AnacroneuriaActivity.class);
                createDialog(challenge_worm,R.string.text_flower_lirio,intent,challenge_worm_name);
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

        RequestOptions options = new RequestOptions();
        options.centerCrop();
        options.fitCenter();

        binding.cardview1.name.setText(challenge_butterfly_name);
        Glide.with(this)
                .load(challenge_butterfly)
                .apply(options)
                .placeholder(R.drawable.loading_icon)
                .into(binding.cardview1.roundedImageView);

        //binding.cardview1.roundedImageView.setImageDrawable(getDrawable(R.drawable.flower_girasol));

        binding.cardview2.name.setText(challenge_spider_name);
        Glide.with(this)
                .load(challenge_spider)
                .placeholder(R.drawable.loading_icon)
                .apply(options)
                .into(binding.cardview2.roundedImageView);
        //binding.cardview2.roundedImageView.setImageDrawable(getDrawable(R.drawable.flower_margarita));

        binding.cardview3.name.setText(challenge_worm_name);
        Glide.with(this)
                .load(challenge_worm)
                .placeholder(R.drawable.loading_icon)
                .apply(options)
                .into(binding.cardview3.roundedImageView);
        //binding.cardview3.roundedImageView.setImageDrawable(getDrawable(R.drawable.flower_new));

    }



    private void createDialog(String image, int textChallenge, Intent intentReceiver, String title)
    {
        CustomDialogMoreinfoBinding customBinding = CustomDialogMoreinfoBinding.inflate(LayoutInflater.from(this));

        Dialog dialog = new Dialog(this);
        dialog.setCancelable(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(customBinding.getRoot());

        customBinding.textViewTittle.setText(title);
        //customBinding.imageViewChallenge.setImageResource(drawable);
        Glide.with(this)
                .load(image)
                .placeholder(R.drawable.loading_icon)
                .into(customBinding.imageViewChallenge);

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