package edu.aha.agualimpiafinal.rasberry;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapter.GaleriaImagenesAdapter;

public class rasberryImages extends Fragment {

    private RasberryImagesViewModel mViewModel;

    GridView imagenes;

    public static rasberryImages newInstance() {
        return new rasberryImages();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.rasberry_images_fragment, container, false);

        //codigo
        imagenes = vista.findViewById(R.id.grid_imagenes_rasberry);
        imagenes.setAdapter(new GaleriaImagenesAdapter(getContext())); // asignar adapatador con el contexto



        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(RasberryImagesViewModel.class);
        // TODO: Use the ViewModel
    }

}