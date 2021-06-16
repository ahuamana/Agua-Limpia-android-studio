package edu.aha.agualimpiafinal.fragments;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.viewModels.NosotrosViewModel;

public class nosotros extends Fragment {

    private NosotrosViewModel mViewModel;



    public static nosotros newInstance() {
        return new nosotros();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.nosotros_fragment, container, false);



        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(NosotrosViewModel.class);
        // TODO: Use the ViewModel
    }

}