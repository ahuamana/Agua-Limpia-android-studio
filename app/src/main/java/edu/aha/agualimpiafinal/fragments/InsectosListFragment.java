package edu.aha.agualimpiafinal.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.InsectosAdapter;


public class InsectosListFragment extends Fragment {

    GridView gridViewInsectos;
    int[] imageInsectos = {R.drawable.cat_icon,
            R.drawable.perro_icon,
            R.drawable.mariposa_icon,
            R.drawable.hen_icon,
            R.drawable.hare_icon,
            R.drawable.chicken_icon,
            R.drawable.fish_icon,
            R.drawable.tortuga_icon,
            R.drawable.cricket_bug_icon,
            R.drawable.worm_icon,
            R.drawable.beetle_icon,
            R.drawable.praying_mantis_icon};


    String[] nameInsectos = {"Gato"
            ,"Perro"
            ,"Mariposa"
            ,"Gallina"
            ,"Conejo"
            ,"Pollito"
            ,"Pescado"
            ,"Tortuga"
            ,"Grillo"
            ,"Gusano"
            ,"Escarabajo"
            ,"Mantis"};


    public InsectosListFragment() {

    }

    public static InsectosListFragment newInstance() {
        InsectosListFragment fragment = new InsectosListFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_insectos_list, container, false);

        gridViewInsectos = vista.findViewById(R.id.grid_view_insectos);
        InsectosAdapter mAdapter = new InsectosAdapter(getContext(), imageInsectos, nameInsectos);

        gridViewInsectos.setAdapter(mAdapter);

        return vista;
    }



}