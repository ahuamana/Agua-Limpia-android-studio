package edu.aha.agualimpiafinal.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.viewModels.NavHeaderMainViewModel;

public class NavHeaderMain extends Fragment {

    private NavHeaderMainViewModel mViewModel;
    TextView navEmail;

    public static NavHeaderMain newInstance() {
        return new NavHeaderMain();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.nav_header_main_fragment, container, false);

        navEmail = vista.findViewById(R.id.NAVemail);
        navEmail.setText("Drogo");



        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(NavHeaderMainViewModel.class);
        // TODO: Use the ViewModel


    }

}