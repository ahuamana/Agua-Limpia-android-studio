package edu.aha.agualimpiafinal.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.GaleriaImagenesAdapter;
import edu.aha.agualimpiafinal.viewModels.RasberryImagesViewModel;

public class rasberryImages extends Fragment {

    private RasberryImagesViewModel mViewModel;

    GridView imagenes;
    List<MoldeMuestra> listatotalDataURLS= new ArrayList<>();
    List<String> listaURLs = new ArrayList<>();
    //Cloud Firestore
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    public static rasberryImages newInstance() {
        return new rasberryImages();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.rasberry_images_fragment, container, false);

        //Inicializar firestore
        fAuth= FirebaseAuth.getInstance();
        fStore= FirebaseFirestore.getInstance();

        fStore.collection("DatosMuestra").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
             //Codigo obtener

                if(queryDocumentSnapshots.isEmpty())
                {
                    Log.d("TAG", "onSuccess: LIST EMPTY");
                    return;
                }else {
                    //Asignar Datos de Firestore al molde
                    List<MoldeMuestra> types = queryDocumentSnapshots.toObjects(MoldeMuestra.class);
                    listatotalDataURLS.addAll(types);

                    //Obtener datos del molde y rellenar al array con strings
                    for(int i=0;i<listatotalDataURLS.size();i++)
                    {


                    }

                        }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //codigo cancelar
                Toast.makeText(getActivity(), "Error al traer los datos de firebase!", Toast.LENGTH_SHORT).show();

            }
        });

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