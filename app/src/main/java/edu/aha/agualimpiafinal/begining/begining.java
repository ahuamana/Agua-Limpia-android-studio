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
import edu.aha.agualimpiafinal.validaciones.validaciones;

public class begining extends AppCompatActivity {

    EditText BEedtfirstname, BEedtlastname,BEedtmiddlename,BEedtEmail;

    validaciones rules= new validaciones();

    Button btnbegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begining);

        //Vincular variables con ids del activity
        BEedtfirstname=findViewById(R.id.BEfirstname);
        BEedtmiddlename=findViewById(R.id.BEmiddlename);
        BEedtlastname=findViewById(R.id.BElastname);
        BEedtEmail= findViewById(R.id.BEemail);
        btnbegin= findViewById(R.id.btnbegining);

        //cargar metodo de los datos guardados en el telefono
        cargarPreferencias();

        btnbegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                boolean valid_firstname=false, valid_middlename=false,valid_lastname=false, valid_email=false;

                //Validar los campos vacios
                valid_firstname=rules.checkField(BEedtfirstname);
                valid_lastname=rules.checkField(BEedtlastname);
                //valid_middlename=rules.checkField(BEedtfirstname);
                valid_email=rules.checkField(BEedtEmail);

                if(valid_firstname)
                {

                    if(valid_lastname)
                    {

                        if(valid_email)
                        {
                            //Guardar las preferencias
                            guardarPreferencias();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);

                        }
                    }

                }



            }
        });


    }

    private void cargarPreferencias() {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        String firstname= preferences.getString("spfirstname","");
        String middlename= preferences.getString("spmiddlename","");
        String lastname= preferences.getString("splastname","");
        String email= preferences.getString("spEmail","");

        //asignar datos guardados a los respectivos campos
        BEedtfirstname.setText(firstname);
        BEedtmiddlename.setText(middlename);
        BEedtlastname.setText(lastname);
        BEedtEmail.setText(email);

    }


    private void guardarPreferencias(){

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String spfirstname = BEedtfirstname.getText().toString();
        String spmiddlename = BEedtmiddlename.getText().toString();
        String splastname = BEedtlastname.getText().toString();
        String spEmail= BEedtEmail.getText().toString();

        //editor permite editar y almacenar las variables
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("spfirstname",spfirstname);
        editor.putString("spmiddlename",spmiddlename);
        editor.putString("splastname",splastname);
        editor.putString("spEmail",spEmail);

        //asignar datos de los campos a las variables para almacenarlos
        BEedtfirstname.setText(spfirstname);
        BEedtmiddlename.setText(spmiddlename);
        BEedtlastname.setText(splastname);
        BEedtEmail.setText(spEmail);

        editor.commit();

    }



}