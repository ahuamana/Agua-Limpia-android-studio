package edu.aha.agualimpiafinal.lista;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


import edu.aha.agualimpiafinal.Entidades.MoldeMuestra;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapter.MuestrasAdapter;

public class lista extends Fragment  implements SearchView.OnQueryTextListener {

    RecyclerView recyclerUsuarios;
    MuestrasAdapter adapter;

    DatabaseReference ref;

    //Referencias para Cloudfirestore
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;
    private SearchView svSearch;


    private ListaViewModel mViewModel;

    public static lista newInstance() {
        return new lista();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.lista_fragment, container, false);

        //Instanciar variables
        fAuth=FirebaseAuth.getInstance();
        fStore=FirebaseFirestore.getInstance();

        //inicializar variables para buscar
        svSearch= (SearchView) vista.findViewById(R.id.Isearch);
        initListener();

        //codigo
        //iniciar arraylist y refrencia al contenedor

        recyclerUsuarios= (RecyclerView) vista.findViewById(R.id.idRecycler);
        recyclerUsuarios.setLayoutManager( new LinearLayoutManager(this.getContext()));
        recyclerUsuarios.setHasFixedSize(true);

        //crear referencia a Firebase
        CollectionReference datosEmpresa = fStore.collection("DatosMuestra");

        //
        Query query = datosEmpresa;
        //Craer un builder de firebase del children



        FirestoreRecyclerOptions<MoldeMuestra> options = new FirestoreRecyclerOptions.Builder<MoldeMuestra>()
                .setQuery(query,MoldeMuestra.class)
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

    private  void initListener(){
        svSearch.setOnQueryTextListener(this);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        processSearch(newText);

        return false;
    }

    private void processSearch(String newText) {
        adapter=null;
        Log.e("mensajebusqueda: ",newText.toLowerCase());
        FirestoreRecyclerOptions <MoldeMuestra> newoptions = new FirestoreRecyclerOptions.Builder<MoldeMuestra>()
                .setQuery(fStore.collection("DatosMuestra").orderBy("MuestraDepartamento").startAt(newText.toLowerCase()).limit(25).endAt(newText.toLowerCase()+'\uf8ff'),MoldeMuestra.class)
                .build();

        //enviar los datos al adapter
        adapter=new MuestrasAdapter(newoptions);
        adapter.startListening();

        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);

    }



}