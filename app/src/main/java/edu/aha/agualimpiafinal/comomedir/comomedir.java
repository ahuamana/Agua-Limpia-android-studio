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

    ImageView img01,img02,img03;

    public static comomedir newInstance() {
        return new comomedir();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View vista=inflater.inflate(R.layout.comomedir_fragment, container, false);

        img01 = vista.findViewById(R.id.imgconce);
        img02=vista.findViewById(R.id.imgreac);
        img03=vista.findViewById(R.id.imgmedi);


        Glide.with(this)
                .load("https://www.mycometer.com/typo3temp/GB/2291658ab3.jpg")

                .placeholder(R.mipmap.ic_launcher)
                .into(img01);

        Glide.with(this)
                .load("https://www.mycometer.com/typo3temp/GB/e49cc1d26e.jpg")
                .placeholder(R.mipmap.ic_launcher)
                .into(img02);

        Glide.with(getActivity())
                .load("https://www.mycometer.com/typo3temp/GB/e7ea7a39d8.jpg")

                .placeholder(R.mipmap.ic_launcher)
                .into(img03);





        return vista;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ComomedirViewModel.class);
        // TODO: Use the ViewModel
    }

}