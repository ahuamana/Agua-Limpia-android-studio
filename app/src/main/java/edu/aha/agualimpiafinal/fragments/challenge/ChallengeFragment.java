package edu.aha.agualimpiafinal.fragments.challenge;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.FragmentChallengeBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChallengeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChallengeFragment extends Fragment {

    FragmentChallengeBinding binding;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public ChallengeFragment() {
        // Required empty public constructor
    }


    public static ChallengeFragment newInstance(String param1, String param2) {
        ChallengeFragment fragment = new ChallengeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChallengeBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();




        return view;
    }
}