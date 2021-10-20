package edu.aha.agualimpiafinal.activities;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String lat = getIntent().getStringExtra("latitud");
        String lon = getIntent().getStringExtra("longitud");

        Log.e("CREATE-LAT&LONG",""+ lat+":"+lon);

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
    }
}