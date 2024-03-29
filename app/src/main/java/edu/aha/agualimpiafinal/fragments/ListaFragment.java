package edu.aha.agualimpiafinal.fragments;

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
import com.google.firebase.firestore.FirebaseFirestore;


import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.MuestrasAdapter;
import edu.aha.agualimpiafinal.providers.MuestrasProvider;
import edu.aha.agualimpiafinal.viewModels.ListaViewModel;

public class ListaFragment extends Fragment {

    RecyclerView recyclerUsuarios;
    MuestrasAdapter adapter;

    //Referencias para Cloudfirestore
    FirebaseFirestore fStore;

    MuestrasProvider mMuestrasProvider;

    private SearchView svSearchDepartamento, svSearchProvincia, svSearchAuthorAlias;

    LinearLayoutManager mLinearLayoutManager;

    private ListaViewModel mViewModel;

    public static ListaFragment newInstance() {
        return new ListaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.lista_fragment, container, false);

        //Instanciar variables
        fStore=FirebaseFirestore.getInstance();
        mMuestrasProvider = new MuestrasProvider();

        //inicializar variables para buscar
        svSearchDepartamento= (SearchView) vista.findViewById(R.id.Isearch);
        //Implementado segunda forma de buscar (provincia)
        svSearchProvincia = vista.findViewById(R.id.IsearchProvincia);
        //Implementado 3ra forma de buscar (authoralias)
        svSearchAuthorAlias = vista.findViewById(R.id.IsearchAuthorAlias);


        svSearchDepartamento.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                buscarDepartamentoOnFirestorage(newText);

                return false;
            }
        });


        svSearchProvincia.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                buscarProvinciaOnFirestorage(newText);

                return false;
            }
        });


        svSearchAuthorAlias.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                buscarAuthorAliasOnFirestorage(newText);

                return false;
            }
        });
        //fin de implementar buscar


        //codigo
        //iniciar arraylist y refrencia al contenedor

        recyclerUsuarios= vista.findViewById(R.id.idRecycler);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setStackFromEnd(true);//messages on recycler put over keyboard
        recyclerUsuarios.setLayoutManager( mLinearLayoutManager);

        recyclerUsuarios.setHasFixedSize(true);


        //crear referencia a Firebase
        //Craer un builder de firebase del children

        FirestoreRecyclerOptions<MoldeMuestra> options = new FirestoreRecyclerOptions.Builder<MoldeMuestra>()
                .setQuery(mMuestrasProvider.getMuestrasListOrderByTimeStamp(),MoldeMuestra.class)
                .build();

        //enviar los datos al adapter
        adapter=new MuestrasAdapter(options, getContext());
        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);



        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                mLinearLayoutManager.setStackFromEnd(true);
                Log.e("TAG-positionStart",String.valueOf(positionStart));
                Log.e("TAG-itemCount",String.valueOf(itemCount));

                //updateStatusMessage();
                //
                int numberItems = adapter.getItemCount();
                Log.e("TAG-NUMBER-OF-ITEMS",String.valueOf(numberItems));
                int lastItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.e("TAG-LASTITEM",String.valueOf(lastItemPosition));

                if(lastItemPosition == -1 || (positionStart >= (numberItems - 1) && lastItemPosition == (positionStart - 1)))
                {
                    Log.e("TAG","ENTRASTE");
                    recyclerUsuarios.scrollToPosition(positionStart-1);
                    mLinearLayoutManager.setStackFromEnd(false);
                }

            }
        });

        return vista;
    }

    private void buscarDepartamentoOnFirestorage(String newText) {

        adapter=null;
        Log.e("mensajebusqueda: ",newText.toLowerCase());
        FirestoreRecyclerOptions <MoldeMuestra> newoptions = new FirestoreRecyclerOptions.Builder<MoldeMuestra>()
                .setQuery(mMuestrasProvider.getMuestrasListOrderByDepartment(newText), MoldeMuestra.class)
                .build();

        //enviar los datos al adapter
        adapter=new MuestrasAdapter(newoptions, getContext());


        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);

        adapter.startListening();//que escuche en timepo real los cambios

        adapter.notifyDataSetChanged();




    }

    private void buscarAuthorAliasOnFirestorage(String newText) {

        //codigo
        adapter=null;
        Log.e("mensajebusqueda: ",newText.toLowerCase());

                FirestoreRecyclerOptions <MoldeMuestra> newoptions = new FirestoreRecyclerOptions.Builder<MoldeMuestra>()
                .setQuery(mMuestrasProvider.getMuestrasListOrderByAuthorAlias(newText),MoldeMuestra.class)
                .build();

        //enviar los datos al adapter
        adapter=new MuestrasAdapter(newoptions, getContext());
        adapter.startListening();

        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);
        ////
    }

    private void buscarProvinciaOnFirestorage(String newText) {
        //codigo implementar
        //Log.e("data cambiado", "cambiando a provincia");

        adapter=null;
        Log.e("mensajebusqueda: ",newText.toLowerCase());

        FirestoreRecyclerOptions <MoldeMuestra> newoptions = new FirestoreRecyclerOptions.Builder<MoldeMuestra>()
                .setQuery(mMuestrasProvider.getMuestrasListOrderByProvince(newText),MoldeMuestra.class)
                .build();

        //enviar los datos al adapter
        adapter=new MuestrasAdapter(newoptions, getContext());
        adapter.startListening();

        //asignar datos al recyclerView
        recyclerUsuarios.setAdapter(adapter);
        ////
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