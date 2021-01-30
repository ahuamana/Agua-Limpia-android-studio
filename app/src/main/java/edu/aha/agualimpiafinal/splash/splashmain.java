package edu.aha.agualimpiafinal.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.begining.begining;

public class splashmain extends AppCompatActivity {

    ImageView logo;
    TextView textoLogo;
    Animation animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashmain);


        textoLogo= findViewById(R.id.txtlogo);
        logo = findViewById(R.id.imglogo);


        animacion = AnimationUtils.loadAnimation(this ,R.anim.animacionsplash);

        logo.startAnimation(animacion);
        textoLogo.startAnimation(animacion);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                Intent vtn01 = new Intent(getApplicationContext(), begining.class);
                vtn01.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(vtn01);
            }
        },4000);





    }
}