package edu.aha.agualimpiafinal.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import edu.aha.agualimpiafinal.activities.AnimalsListActivity;
import edu.aha.agualimpiafinal.activities.PlantsChallengerActivity2;
import edu.aha.agualimpiafinal.activities.WaterChallengerActivity;
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

        openAnimalsChallenge();

        openFrutasChallenge();

        openPlantasChallenge();

        openSustanciasChallenge();

        openObjetosChallenge();

        openOtrosChallenge();

        getDataLaboratorio();


        return view;

    }

    private void openOtrosChallenge() {

        binding.linearLayoutOtros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), WaterChallengerActivity.class);
                startActivity(i);

            }
        });

    }

    private void openObjetosChallenge() {

        binding.linearLayoutObjetos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Pronto Implementaremos esta seccion :)",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openSustanciasChallenge() {

        binding.linearLayoutSustancias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getContext(),"Pronto Implementaremos esta seccion :)",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void openPlantasChallenge() {

        binding.linearLayoutPlantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), PlantsChallengerActivity2.class);
                startActivity(i);

            }
        });

    }

    private void openFrutasChallenge() {

        binding.linearLayoutFrutas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Pronto Implementaremos esta seccion :)",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getDataLaboratorio() {

        mLinearLayoutManager = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        binding.recyclerAnimales.setLayoutManager( mLinearLayoutManager);

        mInsectosProvider = new InsectosProvider();

        FirestoreRecyclerOptions<MoldeSustantivo> options = new FirestoreRecyclerOptions.Builder<MoldeSustantivo>()
                .setQuery(mInsectosProvider.getMuestrasListOrderByTimeStamp(),MoldeSustantivo.class)
                .build();

        //enviar los datos al adapter
        //Log.e("TEST",""+ options.getSnapshots().get(0).toString());

        mAdapter=new LaboratorioAdapter(options, getContext());
        //asignar datos al recyclerView
        binding.recyclerAnimales.setAdapter(mAdapter);

    }

    private void openAnimalsChallenge() {


        binding.linearLayoutAnimales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getContext(), AnimalsListActivity.class);
                startActivity(i);

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