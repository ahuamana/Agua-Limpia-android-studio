package edu.aha.agualimpiafinal.menu.sugerencias.dialogoSU;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import edu.aha.agualimpiafinal.R;


public class dialogoSU_fragment extends DialogFragment {


    Button btnComentarSU;
    TextView tvComentarSU;
    LinearLayout layout;

    public dialogoSU_fragment() {

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_dialogo_s_u_fragment, container, false);



        return vista;
    }
}