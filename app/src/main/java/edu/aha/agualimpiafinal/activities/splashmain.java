package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.activities.begining;

public class splashmain extends AppCompatActivity {

    ImageView logo;
    Animation animacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashmain);

        setFullStatusBarTransparent();//set status bar

        logo = findViewById(R.id.imglogo);


        animacion = AnimationUtils.loadAnimation(this ,R.anim.animacionsplash);

        logo.startAnimation(animacion);


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), FirstStepsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //Remove activities that have been created before
                startActivity(intent);
            }
        },2000);


    }

    private void setFullStatusBarTransparent()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }


}