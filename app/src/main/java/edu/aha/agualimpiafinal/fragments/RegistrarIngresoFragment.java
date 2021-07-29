package edu.aha.agualimpiafinal.fragments;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.providers.ImageProvider;
import edu.aha.agualimpiafinal.providers.MuestrasProvider;
import edu.aha.agualimpiafinal.utils.arrays;
import edu.aha.agualimpiafinal.viewModels.RegistraringresoViewModel;
import edu.aha.agualimpiafinal.utils.validaciones;

import static android.app.Activity.RESULT_CANCELED;

public class RegistrarIngresoFragment extends Fragment implements LocationListener {

    EditText RItvlatitud, RItvlongitud;
    ImageButton RIbtnLocalization;
    CircleImageView mCircleImagePhoto;
    EditText RICantidad;
    Button RIbtnregistrar,RIbtnlimpiar;
    FloatingActionButton fabActionButton;


    Spinner RIDepartamento, RIProvincia;
    Spinner RIResultadoMuestra;


    String ValorURL;
    LocationManager locationManager;

    Context mContext;
    Options mOptions;
    File mImageFile;
    ArrayList<String> mReturnValues = new ArrayList<>();


    MuestrasProvider user;
    ImageProvider mImageProvider;

    ProgressDialog mDialog;
    ProgressDialog mDialogLocation;

    //validaciones
    validaciones rules = new validaciones();
    boolean departamento=false,provincia=false,latitud=false,lontitud=false,cantidad_muestra=false,resultado=false, ResultadoMuestra =false;

    //referencias al storage
    private StorageReference mstorage;
    private static final int GALLERY_INTENT = 1;
    private ProgressDialog progressDialog;

    private FusedLocationProviderClient ubicacion;

    //Shared preferences
    String firstname,middlename,lastname, email;

    private RegistraringresoViewModel mViewModel;

    public static RegistrarIngresoFragment newInstance() {
        return new RegistrarIngresoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.registraringreso_fragment, container, false);

        mContext = vista.getContext();
        //Inicializar firebase
        user = new MuestrasProvider();
        mstorage=FirebaseStorage.getInstance().getReference();
        /////fin de firebase

        //Cargar SharePreferences
        cargarPreferencias();


        //crear nuevo progressDialog (necesario para mostrar dialogo al cargar la foto)
        progressDialog = new ProgressDialog(getContext());
        mImageProvider = new ImageProvider();

        mDialog = new ProgressDialog(getContext());
        mDialog.setTitle("Espere un momento");
        mDialog.setMessage("Guardando Información");

        mDialogLocation = new ProgressDialog(getContext());
        mDialogLocation.setTitle("Espere un momento");
        mDialogLocation.setMessage("Obteniendo localización");

        //ImagePicker
        mOptions = Options.init()
                .setRequestCode(100)                                           //Request code for activity results
                .setCount(1)                                                   //Number of images to restict selection count
                .setFrontfacing(false)                                         //Front Facing camera on start
                .setPreSelectedUrls(mReturnValues)                               //Pre selected Image Urls
                .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                .setMode(Options.Mode.Picture)                                     //Option to select only pictures or videos or both
                .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/pix/images");                                       //Custom Path For media Storage

       //edittext
       RItvlatitud = vista.findViewById(R.id.RIedtLatitud);
       RItvlongitud = vista.findViewById(R.id.RIedtLongitud);
       RICantidad=vista.findViewById(R.id.RIedtcantidadmuestra);
       RIDepartamento=vista.findViewById(R.id.RIedtDepartamento);
       RIProvincia=vista.findViewById(R.id.RIedtProvincia);
       RIResultadoMuestra =vista.findViewById(R.id.RIedtBQV);

       //Image View
        mCircleImagePhoto = vista.findViewById(R.id.RIimgFoto);
        fabActionButton = vista.findViewById(R.id.fabSelectImage);

        //botones
       RIbtnregistrar=vista.findViewById(R.id.RIbtnRegistrar);
       RIbtnlimpiar=vista.findViewById(R.id.RIbtnLimpiar);


       //imageboton
       RIbtnLocalization = vista.findViewById(R.id.RIbtngeolocalizacion);
       ubicacion = LocationServices.getFusedLocationProviderClient(getActivity());

       //clickListenes para botones
       RIbtnLocalization.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               //Toast.makeText(getActivity(), "Probando", Toast.LENGTH_SHORT).show();
               getLocation();
               //llamar al metodo para obtener la localizacion

           }
       });


       RIbtnregistrar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //Toast.makeText(getActivity(), "Probando Boton Registro", Toast.LENGTH_SHORT).show();
               ////Registrar Pedido a Firebase
               registrarMuestraAnalizada();

           }
       });

       RIbtnlimpiar.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ////Limpiar Campos
               limpiarcampos();
           }
       });

        fabActionButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               ////Cargar Foto a Firebase
               startCamera();
           }
       });

       RIDepartamento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               mostrarProvincia(position);

           }

           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });


        return vista;
    }

    private void mostrarProvincia(int position) {

        arrays datos = new arrays();
        ArrayAdapter<String> spinnerArrayAdapter;
        switch (position)
        {
            case 0:{
                Log.e("Seleccionaste",": nada");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.Seleccione);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }

            case 1:{
                Log.e("Seleccionaste",": Amazonas");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasAmazonas);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;

            }
            case 2:{
                Log.e("Seleccionaste",": ancash");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasAncash);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 3:{
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasApurimac);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }

            case 4:{
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasArequipa);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }

            case 5:{
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasAyacucho);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 6:{
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasCajamarca);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 7:{
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasCallao);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 8:{
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasCusco);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 9:{
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasHuancavelica);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 10:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasHuanuco);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 11:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasIca);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 12:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasJunín);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 13:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasLibertad);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 14:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasLambayeque);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 15:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasLima);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 16:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasLoreto);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 17:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasMadreDeDios);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 18:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasMoquegua);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 19:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasPasco);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 20:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasPiura);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 21:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasPuno);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 22:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasSanMartin);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 23:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasTacna);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 24:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasTumbes);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }
            case 25:{
                Log.e("Seleccionaste",": apurimac");
                spinnerArrayAdapter=datos.ObtenerArray(getContext(),R.array.provinciasUcayali);
                RIProvincia.setAdapter(spinnerArrayAdapter);
                break;
            }


        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistraringresoViewModel.class);
        // TODO: Use the ViewModel
    }

    private void startCamera()
    {
        Pix.start(RegistrarIngresoFragment.this, mOptions);
    }

    private void limpiarcampos() {

        RICantidad.setText("");
        RIDepartamento.setSelection(0,true);
        RIProvincia.setSelection(0,true);
        RItvlatitud.setText("");
        RItvlongitud.setText("");
        RIResultadoMuestra.setSelection(0,true);
        ValorURL="";
        mCircleImagePhoto.setImageResource(0);


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
        departamento=rules.checkSpinner(RIDepartamento,"Seleccione");
        provincia=rules.checkSpinner(RIProvincia,"Seleccione");
        ResultadoMuestra =rules.checkSpinner(RIResultadoMuestra,"Seleccione Resultado");

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
                            if(ResultadoMuestra)
                            {
                                if(mImageFile != null)
                                {

                                    mDialog.show();
                                    mImageProvider.save(getContext(), mImageFile).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {


                                            if(task.isSuccessful())
                                            {
                                                //Inicia otra tarea para descargar la URL que se subira a firestorage
                                                mImageProvider.getDownloadUri().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {
                                                        String url = uri.toString();
                                                        saveDataOnFirestore(url); //ACtualiza la informacion en firestorage
                                                    }
                                                });
                                                //Fin de tarea descargar la URL que se subira a firestorage
                                            } else {
                                                mDialog.dismiss();
                                                Toast.makeText(getContext(), "No se pudo almacenar la imagen", Toast.LENGTH_SHORT).show();
                                            }

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            mDialog.dismiss();
                                            Toast.makeText(getContext(), "No se cargar la tarea para subir la imagen", Toast.LENGTH_SHORT).show();
                                        }
                                    });





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

    private void saveDataOnFirestore(String url) {

        MoldeMuestra mMoldeMuestra = new MoldeMuestra();
        mMoldeMuestra.setAuthorFirstname(firstname.toLowerCase());
        mMoldeMuestra.setAuthorLastname(lastname.toLowerCase());
        mMoldeMuestra.setAuthorAlias(middlename.toLowerCase());
        mMoldeMuestra.setAuthorEmail(email.toLowerCase());
        mMoldeMuestra.setMuestraCantidad(RICantidad.getText().toString());
        mMoldeMuestra.setMuestraDepartamento(RIDepartamento.getSelectedItem().toString().toLowerCase());
        mMoldeMuestra.setMuestraProvincia(RIProvincia.getSelectedItem().toString().toLowerCase());
        mMoldeMuestra.setMuestraLatitud(Double.parseDouble(RItvlatitud.getText().toString()));
        mMoldeMuestra.setMuestraLongitud(Double.parseDouble(RItvlongitud.getText().toString()));
        mMoldeMuestra.setMuestraResultado(RIResultadoMuestra.getSelectedItem().toString());
        mMoldeMuestra.setMuestraFotoPATH(url);
        mMoldeMuestra.setMuestraTimeStamp(System.currentTimeMillis()/1000);


        user.create(mMoldeMuestra).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(getActivity(), "Datos registrados correctamente", Toast.LENGTH_SHORT).show();
                    limpiarcampos();
                    mDialog.dismiss();
                }else {
                    mDialog.dismiss();
                    Toast.makeText(mContext, "No se pudieron almacenar los datos", Toast.LENGTH_SHORT).show();
                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(mContext, "Error al al crear la tarea", Toast.LENGTH_SHORT).show();
                mDialog.dismiss();
            }
        });


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        Log.e("DATA", "camara resultado");

        if (resultCode != RESULT_CANCELED)
        {
            if (data != null)
            {
                if (resultCode == Activity.RESULT_OK && requestCode == 100)
                {
                    Log.e("DATA INGRESASTE: ", "RequestCode: " + requestCode + " & resultacode: "+resultCode);

                    mReturnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    mImageFile = new File(mReturnValues.get(0)); // Guardar en File la imagen recibida si el usuario selecciono una imagen
                    mCircleImagePhoto.setBorderColor(0);//eliminar border color del XML para que se vea mas agradable
                    mCircleImagePhoto.setBorderWidth(0);//eliminar ancho de border del XML para que se vea mas agradable
                    mCircleImagePhoto.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath())); //Asignar la imagen al id del xml
                } else {
                    Toast.makeText(getContext(), "error al seleccionar la foto", Toast.LENGTH_SHORT).show();
                }
            }

        }else { Toast.makeText(getContext(), "operacion Cancelado!", Toast.LENGTH_SHORT).show(); }


    }

    private void getLocation()
    {


        Log.e("Mensaje","Entraste a getLocation");
        ubicacion = LocationServices.getFusedLocationProviderClient(getActivity());

        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
           ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {

            //Capturar ultima ubicacion
            getCurrentLocation();

        }
        else
        {
                //ActivityCompat.requestPermissions(getActivity(),new String [] {Manifest.permission.ACCESS_FINE_LOCATION},1 );
                ActivityCompat.requestPermissions(getActivity(),new String [] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100 );
        }

    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {



        Log.e("Mensaje","Entraste a Current Location");
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            mDialogLocation.show();
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

                        mDialogLocation.dismiss();

                    } else {

                        //String bestProvider;
                        //Criteria criteria = new Criteria();
                        //bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();
                        //locationManager.requestLocationUpdates(bestProvider, 1000, 0, (LocationListener) locationManager);

                        LocationRequest locationRequest = new LocationRequest()
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
                                RItvlatitud.setText(String.valueOf(location1.getLatitude()));
                                //set longitud
                                RItvlongitud.setText(String.valueOf(location1.getLongitude()));

                                mDialogLocation.dismiss();


                            }
                        };

                        //fin else
                        //Solicitar Location updates
                        ubicacion.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());


                    }
                    //fin condition


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getContext(), "No se pudo ejecutar la tarea", Toast.LENGTH_SHORT).show();
                    mDialogLocation.dismiss();
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition

        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(getActivity(), mOptions);
                } else {
                    Toast.makeText(getActivity(), "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }

    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        locationManager.removeUpdates((LocationListener) getActivity());

        //open the map:
        //set latitude
        RItvlatitud.setText(String.valueOf(location.getLatitude()));
        //set longitud
        RItvlongitud.setText(String.valueOf(location.getLongitude()));
        Toast.makeText(getContext(), "latitude:" + String.valueOf(location.getLatitude()) + " longitude:" + String.valueOf(location.getLongitude()), Toast.LENGTH_SHORT).show();
    }
}