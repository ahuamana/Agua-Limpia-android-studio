package edu.aha.agualimpiafinal.begining;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.aha.agualimpiafinal.MainActivity;
import edu.aha.agualimpiafinal.R;

public class begining extends AppCompatActivity {

    EditText BEedtEmail, BEfullname;
    Button btnbegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begining);

        BEedtEmail= findViewById(R.id.BEemail);
        BEfullname=findViewById(R.id.BEfullname);

        btnbegin= findViewById(R.id.btnbegining);

        cargarPreferencias();

        btnbegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //if(!TextUtils.isEmpty(RICantidad.getText().toString()) ||  !TextUtils.isEmpty(RITiempo.getText().toString())  )

                if(!TextUtils.isEmpty(BEedtEmail.getText().toString())) {


                    if (!TextUtils.isEmpty(BEfullname.getText().toString()))
                    {
                        guardarPreferencias();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                    } else {Toast.makeText(begining.this, "Rellene los campos vacios", Toast.LENGTH_SHORT).show();}


                } else {Toast.makeText(begining.this, "Rellene los campos vacios", Toast.LENGTH_SHORT).show();}

            }
        });


    }

    private void cargarPreferencias() {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String fullname= preferences.getString("spfullname","");
        String email= preferences.getString("spEmail","");

        BEfullname.setText(fullname);
        BEedtEmail.setText(email);

    }


    private void guardarPreferencias(){

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String spfullname = BEfullname.getText().toString();
        String spEmail= BEedtEmail.getText().toString();

        //editor permite editar y almacenar las variables
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("spfullname",spfullname);
        editor.putString("spEmail",spEmail);
        //ejecutar estas lineas

        BEfullname.setText(spfullname);
        BEedtEmail.setText(spEmail);

        editor.commit();




    }



}