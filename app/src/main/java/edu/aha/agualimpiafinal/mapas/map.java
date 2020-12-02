package edu.aha.agualimpiafinal.mapas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.aha.agualimpiafinal.R;

public class map extends Fragment {

    private GoogleMap mMap;

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
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

            mMap = googleMap;

            //permisos
            if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                //añadir botones de + y - para hacer zoom
                mMap.getUiSettings().setZoomControlsEnabled(true);

                //mostrar mi localizacion
                mMap.getUiSettings().setMyLocationButtonEnabled(true);

                //añadir la brujula
                mMap.getUiSettings().setCompassEnabled(true);

                //añadir toolbar
                mMap.getUiSettings().setMapToolbarEnabled(true);

            } else
            {

                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION))
                {

                    Toast.makeText(getContext(), "Dio permiso para utilizar su ubicacion", Toast.LENGTH_SHORT).show();

                }
                else {

                    ActivityCompat.requestPermissions(getActivity(),new String [] {Manifest.permission.ACCESS_FINE_LOCATION},1 );

                }

            }

            //fin permisos



            //inicio onMapClickListener
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {

                    //when click on map
                    //Inicializar marker options
                    MarkerOptions markerOptions = new MarkerOptions();
                    //Set position of marker
                    markerOptions.position(latLng);

                    //set tittle of marker
                    markerOptions.title(latLng.latitude+ " : "+latLng.longitude);
                    //remove all marker
                    googleMap.clear();
                    //animate to zoom the marker
                    googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
                    //add marker on map
                    googleMap.addMarker(markerOptions);

                }
            });

            //fin onMapClickListener








        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
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
}