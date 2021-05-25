package edu.aha.agualimpiafinal.menu.sugerencias.dialogoSU;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import edu.aha.agualimpiafinal.R;


public class dialogoSU_fragment extends DialogFragment {

    Activity actividad;
    Button btnComentarSU;
    TextView tvComentarSU;
    LinearLayout layout;

    public dialogoSU_fragment() {

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return crearDialogoSugerencias();
    }

    private Dialog crearDialogoSugerencias() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.fragment_dialogo_s_u_fragment,null);
        builder.setView(v);

        btnComentarSU = v.findViewById(R.id.SUDIbtncomentar);
        tvComentarSU = v.findViewById(R.id.SUDItvDejarComentario);
        layout = v.findViewById(R.id.SUDIlinearlayout);
        layout.setAlpha(0.5f);


        //Eventos de botones
        btnComentarSU.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                Toast.makeText(getActivity(), "Mi primer ejemplo", Toast.LENGTH_SHORT).show();
            }
        });

        return builder.create();

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if(context instanceof Activity)
        {
            this.actividad= (Activity) context;


        }else { throw new RuntimeException(context.toString() + "must implement OngrafmentInteraccionListener");}

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dialogo_s_u_fragment, container, false);
    }
}