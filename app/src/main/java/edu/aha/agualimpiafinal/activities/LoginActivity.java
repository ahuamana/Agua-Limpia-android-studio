package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.utils.validaciones;

public class LoginActivity extends AppCompatActivity {

    EditText BEedtfirstname, BEedtlastname,BEedtmiddlename,BEedtEmail;
    TextView BEtxtcontactSupport;
    validaciones rules= new validaciones();

    Button btnbegin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Hide action bar
        getSupportActionBar().hide();
        //Vincular variables con ids del activity
        BEedtfirstname=findViewById(R.id.BEfirstname);
        BEedtmiddlename=findViewById(R.id.BEmiddlename);
        BEedtlastname=findViewById(R.id.BElastname);
        BEedtEmail= findViewById(R.id.BEemail);
        btnbegin= findViewById(R.id.btnbegining);
        BEtxtcontactSupport = findViewById(R.id.BEtxtcontactSupport);

        //cargar metodo de los datos guardados en el telefono
        cargarPreferencias();

        getPermissionsMaps();

        implementedClickListener();


        putKeywordOverLayout();

    }

    private void putKeywordOverLayout() {

        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void implementedClickListener() {

        btnbegin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToMenuApp();

            }
        });



        BEtxtcontactSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Inicio de contacte soporte
                goToWhatsapp();

            }
        });
        //
    }

    private void goToMenuApp() {

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

                if(isValidEmail(BEedtEmail.getText().toString()))
                {
                    //Guardar las preferencias
                    guardarPreferencias();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //eliminar activities anteriores
                    startActivity(i);

                }else
                {
                    BEedtEmail.setError("Ingrese un correo válido!");
                }
            }

        }
        
    }

    private void goToWhatsapp() {
        String phoneNumberWithCountryCode = "+51930292619";
        String message = "Hola, te contactaste con Agua Limpia en una APP, \n¿En que te podemos ayudar?";
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("https://api.whatsapp.com/send?phone=%s&text=%s", phoneNumberWithCountryCode, message)));
        startActivity(i);
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    private void getPermissionsMaps()
    {
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            buildAlertMessageNoGps();
        }

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("El aplicativo hace uso de GPS para el registro microbiológico, desear habilitarlo?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();

    }





}