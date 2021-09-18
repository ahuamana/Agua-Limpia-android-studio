package edu.aha.agualimpiafinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.InsectosAdapter;


public class InsectosListFragment extends Fragment {

    GridView gridViewInsectos;
    int[] imageInsectos = {R.drawable.abdomen_mariposa, R.drawable.cabeza_mariposa};


    public InsectosListFragment() {

    }

    public static InsectosListFragment newInstance(String param1, String param2) {
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
        InsectosAdapter mAdapter = new InsectosAdapter(getContext(), imageInsectos);



        return vista;
    }
}