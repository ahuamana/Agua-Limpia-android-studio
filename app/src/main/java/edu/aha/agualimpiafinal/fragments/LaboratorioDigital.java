package edu.aha.agualimpiafinal.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.LaboratorioDigitalFragmentBinding;

public class LaboratorioDigital extends Fragment {

    private LaboratorioDigitalViewModel mViewModel;
    private LaboratorioDigitalFragmentBinding binding;

    public static LaboratorioDigital newInstance() {
        return new LaboratorioDigital();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = LaboratorioDigitalFragmentBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();

        binding.textViewAnimals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new RegistrarIngresoFragment();
                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.nav_host_fragment, nuevoFragmento);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();

            }
        });



        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LaboratorioDigitalViewModel.class);
        // TODO: Use the ViewModel
    }

}