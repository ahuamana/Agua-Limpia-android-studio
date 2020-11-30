package edu.aha.agualimpiafinal.registraringreso;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.Objects;

import edu.aha.agualimpiafinal.R;

public class registraringreso extends Fragment implements View.OnClickListener {

    EditText RItvlatitud, RItvlongitud;
    ImageButton RIbtnLocalization;



    private FusedLocationProviderClient ubicacion;
    double longitudeBest, latitudeBest;
    double longitudeGPS, latitudeGPS;
    double longitudeNetwork, latitudeNetwork;

    private RegistraringresoViewModel mViewModel;

    public static registraringreso newInstance() {
        return new registraringreso();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.registraringreso_fragment, container, false);

       RItvlatitud = vista.findViewById(R.id.RIedtLatitud);
       RItvlongitud = vista.findViewById(R.id.RIedtLongitud);
       RIbtnLocalization = vista.findViewById(R.id.RIbtngeolocalizacion);

       RIbtnLocalization.setOnClickListener(this);

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



        }







    }

    private void getLocation() {


        if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            //Capturar ultima ubicacion
            //Toast.makeText(getActivity(), "Tenemos Permisos", Toast.LENGTH_SHORT).show();

            ubicacion = LocationServices.getFusedLocationProviderClient(getActivity());
            ubicacion.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                    double latitude = location.getLatitude();
                    double longitud = location.getLongitude();

                    RItvlatitud.setText("");
                    RItvlongitud.setText("");
                    RItvlatitud.setText(String.valueOf(latitude));
                    RItvlongitud.setText(String.valueOf(longitud));


                }
            });



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



}