package edu.aha.agualimpiafinal.modulos.login.views;

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
import edu.aha.agualimpiafinal.activities.MainActivity;
import edu.aha.agualimpiafinal.databinding.ActivityRegisterBinding;
import edu.aha.helper.validaciones;

public class RegisterActivity extends AppCompatActivity {

    validaciones rules= new validaciones();
    ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //cargar metodo de los datos guardados en el telefono
        cargarPreferencias();
        getPermissionsMaps();
        implementedClickListener();
        putKeywordOverLayout();

    }

    private void putKeywordOverLayout() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
    }

    private void implementedClickListener() {

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToMenuApp();

            }
        });

        binding.contactSupport.setOnClickListener(new View.OnClickListener() {
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
        valid_firstname=rules.checkField(binding.fistname);
        valid_lastname=rules.checkField(binding.lastname);
        //valid_middlename=rules.checkField(BEedtfirstname);
        valid_email=rules.checkField(binding.email);

        if(valid_firstname)
        {
            if(valid_lastname)
            {
                if(isValidEmail(binding.email.getText().toString()))
                {
                    //Guardar las preferencias
                    guardarPreferencias();
                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK); //eliminar activities anteriores
                    startActivity(i);
                }else
                {
                    binding.email.setError("Ingrese un correo válido!");
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
        binding.fistname.setText(firstname);
        binding.middlename.setText(middlename);
        binding.lastname.setText(lastname);
        binding.email.setText(email);

    }


    private void guardarPreferencias(){

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String spfirstname = binding.fistname.getText().toString();
        String spmiddlename = binding.middlename.getText().toString();
        String splastname =  binding.lastname.getText().toString();
        String spEmail=  binding.email.getText().toString();

        //editor permite editar y almacenar las variables
        SharedPreferences.Editor editor=preferences.edit();
        editor.putString("spfirstname",spfirstname);
        editor.putString("spmiddlename",spmiddlename);
        editor.putString("splastname",splastname);
        editor.putString("spEmail",spEmail);

        //asignar datos de los campos a las variables para almacenarlos
        binding.fistname.setText(spfirstname);
        binding.middlename.setText(spmiddlename);
        binding.lastname.setText(splastname);
        binding.email.setText(spEmail);
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