package edu.aha.agualimpiafinal.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.LaboratorioAdapter;
import edu.aha.agualimpiafinal.databinding.LaboratorioDigitalFragmentBinding;
import edu.aha.agualimpiafinal.models.MoldeSustantivo;
import edu.aha.agualimpiafinal.providers.InsectosProvider;
import edu.aha.agualimpiafinal.viewModels.LaboratorioDigitalViewModel;

public class LaboratorioDigital extends Fragment {

    private LaboratorioDigitalViewModel mViewModel;
    private LaboratorioDigitalFragmentBinding binding;

    LaboratorioAdapter mAdapter;
    InsectosProvider mInsectosProvider;
    LinearLayoutManager mLinearLayoutManager;


    public static LaboratorioDigital newInstance() {
        return new LaboratorioDigital();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LaboratorioDigitalFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        setOnclickListeners();

        getDataLaboratorio();

        return view;

    }

    private void getDataLaboratorio() {

        mLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerAnimales.setLayoutManager( mLinearLayoutManager);


        mInsectosProvider = new InsectosProvider();

        FirestoreRecyclerOptions<MoldeSustantivo> options = new FirestoreRecyclerOptions.Builder<MoldeSustantivo>()
                .setQuery(mInsectosProvider.getMuestrasListOrderByTimeStamp(),MoldeSustantivo.class)
                .build();

        //enviar los datos al adapter
        //Log.e("TEST",""+ options.getSnapshots().get(0));

        mAdapter=new LaboratorioAdapter(options, getContext());
        //asignar datos al recyclerView
        binding.recyclerAnimales.setAdapter(mAdapter);

    }

    private void setOnclickListeners() {



        binding.textViewInsectos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new AnimalsFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nuevoFragmento);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LaboratorioDigitalViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onStart() {
        super.onStart();

        mAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        mAdapter.stopListening();
    }
}