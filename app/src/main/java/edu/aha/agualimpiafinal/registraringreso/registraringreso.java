package edu.aha.agualimpiafinal.registraringreso;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import edu.aha.agualimpiafinal.Entidades.Ingreso;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.validaciones.validaciones;

import static android.app.Activity.RESULT_OK;

public class registraringreso extends Fragment implements View.OnClickListener {

    EditText RItvlatitud, RItvlongitud;
    ImageButton RIbtnLocalization;
    String ValorURL;
    Long TimeSTamp;

    ImageView RIimgfoto;
    EditText RICantidad, RIDepartamento, RIProvincia, RIBQV;
    Button RIbtnregistrar,RIbtnlimpiar;
    ImageButton RIbtncargarfoto;

    //Cloud Firestore
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;


    //validaciones
    validaciones rules = new validaciones();
    boolean departamento=false,provincia=false,latitud=false,lontitud=false,cantidad_muestra=false,resultado=false,BQV=false;

    //referencias al storage
    private StorageReference mstorage;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog progressDialog;

    private FusedLocationProviderClient ubicacion;

    //Shared preferences
    String firstname,middlename,lastname, email;

    private RegistraringresoViewModel mViewModel;

    public static registraringreso newInstance() {
        return new registraringreso();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.registraringreso_fragment, container, false);

        //Inicializar firebase
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();
        mstorage=FirebaseStorage.getInstance().getReference();
        /////fin de firebase

        //Cargar SharePreferences
        cargarPreferencias();


        //crear nuevo progressDialog (necesario para mostrar dialogo al cargar la foto)
        progressDialog = new ProgressDialog(getContext());

       //edittext
       RItvlatitud = vista.findViewById(R.id.RIedtLatitud);
       RItvlongitud = vista.findViewById(R.id.RIedtLongitud);
       RICantidad=vista.findViewById(R.id.RIedtcantidadmuestra);
       RIDepartamento=vista.findViewById(R.id.RIedtDepartamento);
       RIProvincia=vista.findViewById(R.id.RIedtProvincia);
       RIBQV=vista.findViewById(R.id.RIedtBQV);

       //Image View
        RIimgfoto= vista.findViewById(R.id.RIimgFoto);

        //botones
       RIbtnregistrar=vista.findViewById(R.id.RIbtnRegistrar);
       RIbtnlimpiar=vista.findViewById(R.id.RIbtnLimpiar);
       RIbtncargarfoto=vista.findViewById(R.id.RIbtncargarfoto);



       //imageboton
       RIbtnLocalization = vista.findViewById(R.id.RIbtngeolocalizacion);

       //clickListenes para botones
       RIbtnLocalization.setOnClickListener(this);
       RIbtnregistrar.setOnClickListener(this);
       RIbtncargarfoto.setOnClickListener(this);
       RIbtnlimpiar.setOnClickListener(this);








        return vista;
    }



    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistraringresoViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {


        switch (v.getId())
        {

            case R.id.RIbtngeolocalizacion:
            {
                //Toast.makeText(getActivity(), "Probando", Toast.LENGTH_SHORT).show();
                getLocation();
                //llamar al metodo para obtener la localizacion
                break;
            }


            case R.id.RIbtnRegistrar:
            {
                //Toast.makeText(getActivity(), "Probando Boton Registro", Toast.LENGTH_SHORT).show();
                ////Registrar Pedido a Firebase
                registrarMuestraAnalizada();
                break;
            }

            case R.id.RIbtncargarfoto:
            {

                ////Cargar Foto a Firebase

                uploadFoto();

                break;
            }

            case R.id.RIbtnLimpiar:
            {

                ////Limpiar Campos
                limpiarcampos();

                break;
            }




        }







    }

    private void uploadFoto() {


        Intent i = new Intent(Intent.ACTION_PICK);
        i.setType("image/*");
        startActivityForResult(i, GALLERY_INTENT);

    }

    private void limpiarcampos() {

        RICantidad.setText("");
        RIDepartamento.setText("");
        RIProvincia.setText("");
        RItvlatitud.setText("");
        RItvlongitud.setText("");
        RIBQV.setText("");
        ValorURL="";
        RIimgfoto.setImageResource(0);


    }

    private void cargarPreferencias() {

        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        firstname= preferences.getString("spfirstname","");
        middlename= preferences.getString("spmiddlename","");
        lastname= preferences.getString("splastname","");
        email= preferences.getString("spEmail","");

    }

    private void registrarMuestraAnalizada() {

        //Validar campos vacios
        latitud=rules.checkField(RItvlatitud);
        lontitud=rules.checkField(RItvlongitud);
        cantidad_muestra=rules.checkField(RICantidad);
        departamento=rules.checkField(RIDepartamento);
        provincia=rules.checkField(RIProvincia);
        BQV=rules.checkField(RIBQV);

        if(latitud)
        {
            if(lontitud)
            {
                if(cantidad_muestra)
                {
                    if(departamento)
                    {
                        if(provincia)
                        {
                            if(BQV)
                            {
                                if(!TextUtils.isEmpty(ValorURL))
                                {
                                    //incia guardado de datos a cloudfirestore
                                    final DocumentReference df = fStore.collection("DatosMuestra").document(); // Genera automaticamente la KEY al almacenar datos con .document()

                                    df.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                            if(task.isSuccessful())
                                            {
                                                Map<String, Object> muestrasData =new HashMap<>();
                                                muestrasData.put("MuestraCantidad",RICantidad.getText().toString());
                                                muestrasData.put("MuestraDepartamento",RIDepartamento.getText().toString());
                                                muestrasData.put("MuestraProvincia",RIProvincia.getText().toString());
                                                muestrasData.put("MuestraFotoLatitud",Double.parseDouble(RItvlatitud.getText().toString()));
                                                muestrasData.put("MuestraFotoLongitud",Double.parseDouble(RItvlongitud.getText().toString()));
                                                muestrasData.put("MuestraResultadoBQV",RIBQV.getText().toString());
                                                muestrasData.put("MuestraFotoPATH",ValorURL);
                                                muestrasData.put("MuestraTimeStamp",System.currentTimeMillis()/1000);
                                                muestrasData.put("AuthorFirstname",firstname);
                                                muestrasData.put("AuthorLastname",lastname);
                                                muestrasData.put("AuthorMiddlename",middlename);
                                                muestrasData.put("AuthorAlias",email);

                                                df.set(muestrasData);
                                                Toast.makeText(getActivity(), "Datos registrados correctamente", Toast.LENGTH_SHORT).show();
                                                limpiarcampos();
                                            }

                                        }
                                    });
                                    //fin de df.onCompleteListener


                                }
                                else {
                                    Toast.makeText(getActivity(), "Agregar la foto", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }
                }
            }
        }

       //fin de validar

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //inicio de if

        if(requestCode==GALLERY_INTENT&& resultCode==RESULT_OK)
        {

            progressDialog.setTitle("Subiendo Imagen");
            progressDialog.setMessage("Subiendo Foto a FireBase");
            progressDialog.setCancelable(false);
            progressDialog.show();

            Uri mipath = data.getData();

            //RIimgfoto.setImageURI(mipath);

            StorageReference filePath = mstorage.child("MuestraFotos").child(mipath.getLastPathSegment());

            filePath.putFile(mipath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                   //inicio on successlistener

                    progressDialog.dismiss();
                    Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();

                    while (!urlTask.isSuccessful());

                    Uri rutaFoto = urlTask.getResult();

                    //Para guardar y mostrar la foto de firebase
                    ValorURL=rutaFoto.toString();
                    //Uri.parse(ValorURL);


                    Glide.with(getActivity())
                            .load(rutaFoto)
                            .placeholder(R.mipmap.ic_launcher)
                            .into(RIimgfoto);


                    Toast.makeText(getContext(), "Foto Agregada", Toast.LENGTH_SHORT).show();
                    Log.e("test","Valor foto: "+ValorURL);


                    //fin on successlistener
                }
            });


            //fin de if
        }



    }

    private void getLocation() {

        ubicacion = LocationServices.getFusedLocationProviderClient(getActivity());

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
           ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            //Capturar ultima ubicacion

            getCurrentLocation();

            //Toast.makeText(getActivity(), "Tenemos Permisos", Toast.LENGTH_SHORT).show();


//            ubicacion.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//                @Override
//                public void onSuccess(Location location) {
//
//                    double latitude = location.getLatitude();
//                    double longitud = location.getLongitude();
//
//                    RItvlatitud.setText("");
//                    RItvlongitud.setText("");
//                    RItvlatitud.setText(String.valueOf(latitude));
//                    RItvlongitud.setText(String.valueOf(longitud));
//
//
//                }
//            });



        } else
        {

            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
            {

                Toast.makeText(getActivity(), "Dio permiso para utilizar su ubicacion", Toast.LENGTH_SHORT).show();

            }
            else {

                ActivityCompat.requestPermissions(getActivity(),new String [] {Manifest.permission.ACCESS_FINE_LOCATION},1 );

            }

        }










    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //when location is enabled
            //get last location

            ubicacion.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Inicialize location
                    final Location location = task.getResult();

                    //check condicion
                    if (location != null) {
                        //set latitude
                        RItvlatitud.setText(String.valueOf(location.getLatitude()));
                        //set longitud
                        RItvlongitud.setText(String.valueOf(location.getLongitude()));

                    } else {

                        final LocationRequest locationRequest = new LocationRequest()
                                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                .setInterval(10000)
                                .setFastestInterval(1000)
                                .setNumUpdates(1);
                        LocationCallback locationCallback = new LocationCallback() {

                            @Override
                            public void onLocationResult(LocationResult locationResult) {
                                super.onLocationResult(locationResult);

                                //Iniciar location
                                Location location1 = locationResult.getLastLocation();
                                //set latitude
                                RItvlatitud.setText(String.valueOf(location.getLatitude()));
                                //set longitud
                                RItvlongitud.setText(String.valueOf(location.getLongitude()));

                            }
                        };

                        //fin else
                        //Solicitar Location updates
                        ubicacion.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


                    }
                    //fin condition


                }
            });

            //fin get last location

        } else {

            //when location services is not enabled
            //Open Location  setting
            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)  );


        }


    }


}