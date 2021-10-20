package edu.aha.agualimpiafinal.activities;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityMapsBinding;
import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.providers.MuestrasProvider;
import edu.aha.agualimpiafinal.utils.RelativeTime;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    MuestrasProvider mMuestrasProvider;
    private Marker myMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mMuestrasProvider = new MuestrasProvider();


        // Obtain the SupportMapFragment and get notified when the MapFragment is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String lat = getIntent().getStringExtra("latitud");
        String lon = getIntent().getStringExtra("longitud");
        String time = getIntent().getStringExtra("timeago");

        Log.e("LAT&LONG",""+ lat+":"+lon);

        // Add a marker in Sydney and move the camera
        LatLng positionReceiver = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        //mMap.addMarker(new MarkerOptions().position(positionReceiver).title("Marker in Sydney"));
        CameraPosition cameraPosition = CameraPosition.builder()
                .target(positionReceiver)
                .zoom(15)
                .build();
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


        settingsMaps(googleMap);

        setPointsOnGoogleMaps(googleMap);

    }

    private void setPointsOnGoogleMaps(GoogleMap googleMap) {


        Log.e("MAP POINTS","METHOD");
        Log.e("MAP POINTS","NO ES NULO");

        List<MoldeMuestra> listaMuestras= new ArrayList<>();


        //crear referencia a Firebase
        mMuestrasProvider.getCollectionDatosMuestra().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot documentSnapshots) {
                //Codigo si obtiene la ListaFragment

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

                        ////Horal Obtenida
                        long time = listaMuestras.get(i).getMuestraTimeStamp()*1000;  //
                        //Date df = new java.util.Date(time);
                        //String vv = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);
                        //String HoraObtenida = new SimpleDateFormat("MM/dd/yyyy hh:mma").format(df);



                        //Validar si la muestra es positivo
                        if(muestraResultado.contains("Negativo"))
                        {
                            //Asignar un punto Azul en google maps con su latitud y longitud
                            LatLng newlat = new LatLng(listaMuestras.get(i).getMuestraLatitud(),listaMuestras.get(i).getMuestraLongitud());
                            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            myMarker=googleMap.addMarker(new MarkerOptions()
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.waterblue64))
                                    .position(newlat)
                                    //.title("Muestra "+ i));
                                    .title(RelativeTime.getTimeAgo(time, getApplicationContext())));

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
                                        .title(RelativeTime.getTimeAgo(time, getApplicationContext())));


                            }

                        }
                        ////Fin de Validar si la muestra es positivo

                    }

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //Codigo si falla al obtener la ListaFragment
                Toast.makeText(MapsActivity.this, "Error getting data!!!", Toast.LENGTH_LONG).show();
            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                //limpiar marker
                //googleMaps.clear();
            }
        });

        //}
    }

    private void settingsMaps(GoogleMap googleMap) {

        //añadir botones de + y - para hacer zoom
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        //habilitar compass o brujula
        //googleMap.setMyLocationEnabled(true);
        //googleMap.getUiSettings().setMyLocationButtonEnabled(false);//ocultar boton de ubicacion
        googleMap.getUiSettings().setCompassEnabled(true);


        googleMap.getUiSettings().setAllGesturesEnabled(true); // habilitar gestos


        //mostrar mi localizacion
        googleMap.getUiSettings().setMyLocationButtonEnabled(true);

        //añadir la brujula
        googleMap.getUiSettings().setCompassEnabled(true);

        //añadir toolbar
        googleMap.getUiSettings().setMapToolbarEnabled(true);


    }
}