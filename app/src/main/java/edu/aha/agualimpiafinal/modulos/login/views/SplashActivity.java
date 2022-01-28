package edu.aha.agualimpiafinal.modulos.login.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

import edu.aha.agualimpiafinal.R;

public class SplashActivity extends AppCompatActivity {

    ImageView logo;
    Animation animacion;

    boolean skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setFullStatusBarTransparent();//set status bar

        logo = findViewById(R.id.imglogo);
        animacion = AnimationUtils.loadAnimation(this ,R.anim.animacionsplash);
        logo.startAnimation(animacion);

        skip = cargarPreferencias();




        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {

                if(skip != false)
                {
                    goToLogin();
                }else
                {
                    Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //Remove activities that have been created before
                    startActivity(intent);
                }




            }
        },2000);


    }

    private void setFullStatusBarTransparent()
    {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    private void goToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private boolean cargarPreferencias() {

        SharedPreferences preferences = getSharedPreferences("SaltarBienvenida", Context.MODE_PRIVATE);

        boolean bypass= preferences.getBoolean("bypass",false);

        return bypass;

    }


}