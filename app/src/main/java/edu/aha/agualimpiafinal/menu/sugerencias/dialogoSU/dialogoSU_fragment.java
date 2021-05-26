package edu.aha.agualimpiafinal.menu.sugerencias.dialogoSU;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.aha.agualimpiafinal.R;


public class dialogoSU_fragment extends DialogFragment {


    Button btnComentarSU;
    TextView tvComentarSU;
    LinearLayout layout;

    public void dialogoSU_fragment() {


    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Dialog dialog = builder.create();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View vista = inflater.inflate(R.layout.fragment_dialogo_s_u_fragment,null);
        builder.setView(vista);

        //Declarar variables y asignar
        btnComentarSU = vista.findViewById(R.id.SUDIbtncomentar);

        btnComentarSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Hola mundo bb", Toast.LENGTH_SHORT).show();
            }
        });


        return dialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_dialogo_s_u_fragment, container, false);





        return vista;
    }
}