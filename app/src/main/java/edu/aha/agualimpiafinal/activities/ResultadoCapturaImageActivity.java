package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;

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

        getDataLastIntent();

        SetOnclickListeners();

        goToPoints();


    }

    private void getDataLastIntent() {


        int points = getIntent().getIntExtra("points",100);
        int descripcion = getIntent().getIntExtra("descripcion",100);
        String imageurl = getIntent().getStringExtra("imageurl");
        String title = getIntent().getStringExtra("title");

        Log.e("imageurl","imageurl: "+ imageurl);


        binding.points.setText(String.valueOf(points));
        binding.textViewDescriptionSustantivo.setText(descripcion);
        binding.title.setText(title);

        Glide.with(this)
                .load(imageurl)
                .placeholder(R.drawable.loading_icon)
                .into(binding.roundedImageViewSustantivo);


        /*
       if(position_image != 0)
       {
           switch (position_image)
           {
               case 1:

                   binding.textViewDescriptionSustantivo.setText(R.string.descripcion_mariposa1);

                   Glide.with(this)
                           .load("https://www.nationalgeographic.com.es/medio/2018/03/23/perdida-entre-la-multitud_86c63fc7_1016x648.jpg")
                           .placeholder(R.drawable.loading_icon)
                           .into(binding.roundedImageViewSustantivo);
                   break;
               case 2:
                   binding.textViewDescriptionSustantivo.setText(R.string.descripcion_mariposa2);

                   Glide.with(this)
                           .load("https://www.nationalgeographic.com.es/medio/2021/08/31/apollo-landing_1a9abaa4_1280x853.jpg")
                           .placeholder(R.drawable.loading_icon)
                           .into(binding.roundedImageViewSustantivo);
                   break;

               case 3:

                   binding.textViewDescriptionSustantivo.setText(R.string.descripcion_mariposa3);

                   Glide.with(this)
                           .load("https://www.nationalgeographic.com.es/medio/2019/06/13/danza-de-mariposas_a6a4af54_1280x853.jpg")
                           .placeholder(R.drawable.loading_icon)
                           .into(binding.roundedImageViewSustantivo);
                   break;

               default:
                   Log.e("ERROR","NO CASE FOUND");
                   break;
           }
       }*/


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

        binding.fabHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(ResultadoCapturaImageActivity.this, MainActivity.class);
                startActivity(i);

            }
        });

    }
}