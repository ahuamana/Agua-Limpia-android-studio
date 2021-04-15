package edu.aha.agualimpiafinal.comomedir;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.aha.agualimpiafinal.R;

public class comomedir extends Fragment {

    private ComomedirViewModel mViewModel;

    ImageView imgHealthMetric,imgWaterSafe;

    public static comomedir newInstance() {
        return new comomedir();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista=inflater.inflate(R.layout.comomedir_fragment, container, false);

        imgHealthMetric = vista.findViewById(R.id.CMivHealtMetric);
        imgWaterSafe = vista.findViewById(R.id.CMivWaterSafe);



        Glide.with(this)
                .load("https://images-na.ssl-images-amazon.com/images/I/81B8ZtKR%2BXL._AC_SY355_.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .into(imgWaterSafe);

        Glide.with(this)
                .load("https://images-na.ssl-images-amazon.com/images/I/71dvCVFZ71L._AC_SY355_.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .into(imgHealthMetric);

        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ComomedirViewModel.class);
        // TODO: Use the ViewModel
    }

}