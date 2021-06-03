package edu.aha.agualimpiafinal.mapas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import edu.aha.agualimpiafinal.Entidades.MoldeMuestra;
import edu.aha.agualimpiafinal.R;

public class map extends Fragment implements GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private FusedLocationProviderClient ubicacion;
    double latitudActual, longitudActual;
    LocationManager locationManager;

    private Marker myMarker;

    //Firebase
    //DatabaseReference ref;
    //Cloud Firestore
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    //guardar datos en esta lista

    //guardar datos en esta lista los nuevos datos de Firestore
    List<MoldeMuestra> listaMuestras= new ArrayList<>();


    GoogleMap googleMaps;
    //Marker Global de posicion
    Marker currentmarker=null;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(final GoogleMap googleMap) {



            //Obtener Permisos geolzalizacion
            ubicacion = LocationServices.getFusedLocationProviderClient(getActivity());
            //permisos
            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                //añadir botones de + y - para hacer zoom
                googleMap.getUiSettings().setZoomControlsEnabled(true);

                //mostrar mi localizacion
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);

                //añadir la brujula
                googleMap.getUiSettings().setCompassEnabled(true);

                //añadir toolbar
                googleMap.getUiSettings().setMapToolbarEnabled(true);

                ObtenerUbicationActual(googleMap);

            } else
            {

//                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
//                {
//
//                    Toast.makeText(getActivity(), "Dio permiso para utilizar su ubicacion", Toast.LENGTH_SHORT).show();
//
//                }
//                else {

                    ActivityCompat.requestPermissions(getActivity(),new String [] {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},100 );

                }

//            }

            //fin Permisos geolzalizacion

            //asginarle variable para el mejor manejo
            mMap = googleMap;



            //inicio onMapClickListener
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    if(currentmarker != null) {
                        currentmarker.remove();
                    }

                    //when click on map
                    //Inicializar marker options
                    MarkerOptions markerOptions = new MarkerOptions();
                    //Set position of marker
                    markerOptions.position(latLng);

                    //asignar tipo de mapa
                    googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    //set tittle of marker
                    markerOptions.title(latLng.latitude+ " : "+latLng.longitude);
                    //remove all marker


                    //animate to zoom the marker
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    //add marker on map
                    currentmarker=googleMap.addMarker(markerOptions);



                    //fin para remover marker

                }
            });

            //fin onMapClickListener








        }
    };



    @SuppressLint("MissingPermission")
    private void ObtenerUbicationActual(final GoogleMap googleMap) {


        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //when location is enabled
            //get last location

            ubicacion.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @SuppressLint("MissingPermission")
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //Inicialize location
                    final Location location = task.getResult();

                    //check condicion
                    if (location != null) {
                        //set latitude
                        latitudActual= location.getLatitude();
                        //set longitud
                        longitudActual=location.getLongitude();


                        /////LLevarme a mi ubicacion Actual
                        Log.e( "DatosLatitud",String.valueOf(latitudActual)+" : "+String.valueOf(longitudActual));

                                      //Longitud inicial
                                      LatLng pichanaki = new LatLng(latitudActual,longitudActual);
                                      googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        currentmarker=googleMap.addMarker(new MarkerOptions().position(pichanaki).title("Oficina Principal"));

                        //Ingresa a la posicion actual
                        CameraPosition cameraPosition = CameraPosition.builder().target(pichanaki).zoom(10).build();
                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(pichanaki));

                        /////fin de llevarme a mi ubicacion Actual



                    } else {


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
                                latitudActual=location1.getLatitude();
                                //set longitud
                                longitudActual=location1.getLongitude();

                                /////LLevarme a mi ubicacion Actual
                                        Log.e( "DatosLatitud",String.valueOf(latitudActual)+" : "+String.valueOf(longitudActual));

                                              //Longitud inicial
                                              LatLng pichanaki = new LatLng(latitudActual,longitudActual);
                                              googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                currentmarker=googleMap.addMarker(new MarkerOptions().position(pichanaki).title("Oficina Principal"));

                                        //Ingresa a la posicion actual
                                        CameraPosition cameraPosition = CameraPosition.builder().target(pichanaki).zoom(10).build();
                                        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                                        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(pichanaki));

                                /////fin de llevarme a mi ubicacion Actual

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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition

        if(requestCode==100 &&(grantResults.length>0) && (grantResults[0] + grantResults[1] ==PackageManager.PERMISSION_GRANTED))
        {
            ObtenerUbicationActual(googleMaps);

        }else {

            //Permisos denegados
            Toast.makeText(getActivity(), "Permisos denied", Toast.LENGTH_SHORT).show();
        }

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_map, container, false);


        //Inicializar firestore
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();


        //crear referencia a Firebase
        //ref= FirebaseDatabase.getInstance().getReference().child("Muestrass");
        fStore.collection("DatosMuestra").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                //Codigo si obtiene la lista

                if (documentSnapshots.isEmpty()) {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                } else
                    {
                        //Asignar Datos de Firestore al molde
                        List<MoldeMuestra> types = documentSnapshots.toObjects(MoldeMuestra.class);
                        listaMuestras.addAll(types);
                        Log.d("TAG", "onSuccess: " + listaMuestras);

                        //Obtener datos del molde
                        for (int i=0;i<listaMuestras.size();i++)
                        {
                            Log.e("Latitud "+i, String.valueOf(listaMuestras.get(i).getMuestraLatitud()));
                            Log.e("Longitud" +i,String.valueOf(listaMuestras.get(i).getMuestraLongitud()));
                            Log.e("unixtime" +i,String.valueOf(listaMuestras.get(i).getMuestraTimeStamp()));
                            //Obtengo el Resultado de la muestra
                            String muestraResultado= listaMuestras.get(i).getMuestraResultado();

                            //UnixTime to Date
                            java.util.Date dateTime=new java.util.Date(listaMuestras.get(i).getMuestraTimeStamp()*1000);

                            //Validar si la muestra es positivo
                            if(muestraResultado.contains("Negativo"))
                            {
                                //Asignar un punto Azul en google maps con su latitud y longitud
                                LatLng newlat = new LatLng(listaMuestras.get(i).getMuestraLatitud(),listaMuestras.get(i).getMuestraLongitud());
                                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                myMarker=mMap.addMarker(new MarkerOptions()
                                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.waterblue64))
                                                .position(newlat)
                                                //.title("Muestra "+ i));
                                                .title( String.valueOf(dateTime)));

                            }
                            else {
                                if(muestraResultado.contains("Positivo"))
                                {
                                    //Asignar un punto Rojo en google maps con su latitud y longitud

                                    LatLng newlats = new LatLng(listaMuestras.get(i).getMuestraLatitud(), listaMuestras.get(i).getMuestraLongitud());
                                    mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                                    mMap.addMarker(new MarkerOptions()
                                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.waterred64))
                                            .position(newlats)
                                            //.title("Muestra "+ dateTime));
                                            .title(String.valueOf(dateTime)));


                                }

                            }
                            ////Fin de Validar si la muestra es positivo

                        }

                    }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Codigo si falla al obtener la lista
                Toast.makeText(getActivity(), "Error getting data!!!", Toast.LENGTH_LONG).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //limpiar marker
               //googleMaps.clear();
            }
        });

        //inicio evento para capturar valores
//        datosEmpresa.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                for (DataSnapshot postSnapshot : snapshot.getChildren())
//                {
//                    Ingreso datos = postSnapshot.getValue(Ingreso.class);
//                    listaDatos.add(datos);
//
//                }
//
//                for (int i=0;i<listaDatos.size();i++)
//                {
//                    Log.e("Latitud "+i, String.valueOf(listaDatos.get(i).getLatitudDB()));
//                    Log.e("Longitud" +i,String.valueOf(listaDatos.get(i).getLongitudDB()));
//
//                    //Obtengo el valor del BQV
//                    int valorBQV= Integer.parseInt(listaDatos.get(i).getBqvDB());
//                    if(valorBQV <=50)
//                    {
//
//                        LatLng newlat = new LatLng(listaDatos.get(i).getLatitudDB(),listaDatos.get(i).getLongitudDB());
//                        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                        mMap.addMarker(new MarkerOptions()
//                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.waterblue64))
//                                        .position(newlat)
//                                        .title("Muestra "+ i));
//                    }
//                    else {
//
//                        LatLng newlat = new LatLng(listaDatos.get(i).getLatitudDB(),listaDatos.get(i).getLongitudDB());
//                                      mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
//                        mMap.addMarker(new MarkerOptions()
//                                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.waterred64))
//                                        .position(newlat)
//                                        .title("Muestra "+ i));
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        //fin evento para capturar valores


        return vista;
    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }


    @Override
    public boolean onMarkerClick(final Marker marker) {
        if (marker.equals(myMarker))
        {
            //handle click here
            Toast.makeText(getActivity(), "Hiciste click a myMarker", Toast.LENGTH_SHORT).show();
        }

        return false;
    }
}