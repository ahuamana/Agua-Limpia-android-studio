package edu.aha.agualimpiafinal.fragments;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.viewModels.RegistrarViewModel;

public class registrar extends Fragment implements View.OnClickListener {

    Button REbtnfisio, REbtnmicro;

    private RegistrarViewModel mViewModel;

    public static registrar newInstance() {
        return new registrar();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista= inflater.inflate(R.layout.registrar_fragment, container, false);

        REbtnmicro = vista.findViewById(R.id.REbtnmicrobiologico);
        REbtnfisio = vista.findViewById(R.id.REbtnfisioquimico);
        REbtnfisio.setVisibility(View.GONE);



        REbtnmicro.setOnClickListener(this);



        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RegistrarViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.REbtnmicrobiologico:
            {

                // Crea el nuevo fragmento y la transacción.
                Fragment nuevoFragmento = new registraringreso();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.REcontenedorRegistrar, nuevoFragmento);
                transaction.addToBackStack(null);

                // Commit a la transacción
                transaction.commit();


                break;
            }



        }




    }
}