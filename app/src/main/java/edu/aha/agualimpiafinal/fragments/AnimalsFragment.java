package edu.aha.agualimpiafinal.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.FragmentAnimalsBinding;


public class AnimalsFragment extends Fragment {

    private FragmentAnimalsBinding binding;

    public AnimalsFragment() {

    }


    public static AnimalsFragment newInstance(String param1, String param2) {
        AnimalsFragment fragment = new AnimalsFragment();
        Bundle args = new Bundle();

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

        binding = FragmentAnimalsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();






        return view;
    }
}