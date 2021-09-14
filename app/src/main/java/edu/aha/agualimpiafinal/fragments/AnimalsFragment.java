package edu.aha.agualimpiafinal.fragments;

import static android.app.Activity.RESULT_CANCELED;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.fxn.utility.PermUtil;

import java.io.File;
import java.util.ArrayList;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.FragmentAnimalsBinding;


public class AnimalsFragment extends Fragment {

    private FragmentAnimalsBinding binding;

    Options mOptions;
    ArrayList<String> mReturnValues = new ArrayList<>();
    File mImageFile;

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


        setOnClickListeners();



        return view;
    }

    private void setOnClickListeners() {

        binding.fabSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openCamera();
            }
        });

    }

    private void openCamera() {

        //ImagePicker
        mOptions = Options.init()
                .setRequestCode(100)                                           //Request code for activity results
                .setCount(1)                                                   //Number of images to restict selection count
                .setFrontfacing(false)                                         //Front Facing camera on start
                .setPreSelectedUrls(mReturnValues)                               //Pre selected Image Urls
                .setSpanCount(4)                                               //Span count for gallery min 1 & max 5
                .setMode(Options.Mode.Picture)                                     //Option to select only pictures or videos or both
                .setVideoDurationLimitinSeconds(30)                            //Duration for video recording
                .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)     //Orientaion
                .setPath("/pix/images");


        Pix.start(AnimalsFragment.this, mOptions);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //check condition

        switch (requestCode) {
            case PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(getActivity(), mOptions);
                } else {
                    Toast.makeText(getActivity(), "Approve permissions to open Pix ImagePicker", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_CANCELED)
        {
            if (data != null)
            {
                if (resultCode == Activity.RESULT_OK && requestCode == 100)
                {
                    Log.e("DATA INGRESASTE: ", "RequestCode: " + requestCode + " & resultacode: "+resultCode);

                    mReturnValues = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
                    mImageFile = new File(mReturnValues.get(0)); // Guardar en File la imagen recibida si el usuario selecciono una imagen
                    binding.circleImageViewPhoto.setBorderColor(0);//eliminar border color del XML para que se vea mas agradable
                    binding.circleImageViewPhoto.setBorderWidth(0);//eliminar ancho de border del XML para que se vea mas agradable
                    binding.circleImageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(mImageFile.getAbsolutePath())); //Asignar la imagen al id del xml

                    Log.e("IMAGE PATH",""+ mReturnValues.get(0));
                    Log.e("IMAGE ABS PATH",""+ mImageFile.getAbsolutePath());

                } else {
                    Toast.makeText(getContext(), "error al seleccionar la foto", Toast.LENGTH_SHORT).show();
                }
            }

        }else
            {
                Toast.makeText(getContext(), "operacion Cancelado!", Toast.LENGTH_SHORT).show();
            }
    }
}