package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import edu.aha.agualimpiafinal.R;

public class FirstStepsActivity extends AppCompatActivity {

    TextView txtSaltarFirstSteps;
    Button btnStartFirstSteps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_steps);

        setStatusBarColor();

        txtSaltarFirstSteps = findViewById(R.id.txtsaltarFirstSteps);
        btnStartFirstSteps = findViewById(R.id.btnIniciarFirstSteps);

        btnStartFirstSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToInicio();

            }
        });

        txtSaltarFirstSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToLogin();

            }
        });


    }

    private void goToLogin() {
        Intent intent = new Intent(FirstStepsActivity.this, begining.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToInicio()
    {
        Intent intent = new Intent(FirstStepsActivity.this, inicioApp.class);
        startActivity(intent);
    }

    private void setStatusBarColor()
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            getWindow().setStatusBarColor(getResources().getColor(R.color.greeLight, this.getTheme()));
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            {
                getWindow().setStatusBarColor(getResources().getColor(R.color.greeLight));
            }
        }
    }
}