package edu.aha.agualimpiafinal.lista;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import edu.aha.agualimpiafinal.Entidades.Ingreso;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapter.MuestrasAdapter;

public class lista extends Fragment {

    RecyclerView recyclerUsuarios;
    MuestrasAdapter adapter;

    DatabaseReference ref;

    private ListaViewModel mViewModel;

    public static lista newInstance() {
        return new lista();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.lista_fragment, container, false);

        //codigo
        //iniciar arraylist y refrencia al contenedor

        recyclerUsuarios= (RecyclerView) vista.findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager( new LinearLayoutManager(this.getContext()));
        recyclerUsuarios.setHasFixedSize(true);

        //crear referencia a Firebase
        ref= FirebaseDatabase.getInstance().getReference().child("Muestrass");

        //Craer un builder de firebase del children

        FirebaseRecyclerOptions<Ingreso> options= new FirebaseRecyclerOptions.Builder<Ingreso>()
                .setQuery(ref,Ingreso.class)
                .build();
        //enviar los datos al adapter

        adapter=new MuestrasAdapter(options);

        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);




        return vista;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ListaViewModel.class);
        // TODO: Use the ViewModel
    }




}