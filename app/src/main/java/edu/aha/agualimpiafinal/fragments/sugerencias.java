package edu.aha.agualimpiafinal.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.jaeger.library.StatusBarUtil;

import java.util.HashMap;
import java.util.Map;

import edu.aha.agualimpiafinal.providers.MuestrasProvider;
import edu.aha.agualimpiafinal.providers.SugerenciasProvider;
import edu.aha.agualimpiafinal.viewModels.SugerenciasViewModel;
import edu.aha.agualimpiafinal.models.MoldeComentarios;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.ComentariosAdapter;

public class sugerencias extends Fragment {

    private SugerenciasViewModel mViewModel;


    RecyclerView recyclerComentarios;
    ComentariosAdapter adapter;
    LinearLayoutManager mLinearLayoutManager;
    SugerenciasProvider mSugerenciasProvider;

    MoldeComentarios mMoldeComentarios;


    //datos de Shared preferences
    String firstname,middlename,lastname, email;

    //boton flotante
    ImageView imageViewSend;


    View mView;


    public static sugerencias newInstance() {
        return new sugerencias();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sugerencias_fragment, container, false);

        //Obtener datos guardados del telefono de SharedPreferences
        cargarPreferencias();
        setStatusBarFullTransparent();

        mSugerenciasProvider = new SugerenciasProvider();
        mMoldeComentarios = new MoldeComentarios();
        mLinearLayoutManager = new LinearLayoutManager(getContext());

        imageViewSend = mView.findViewById(R.id.imageViewSend);
        recyclerComentarios = mView.findViewById(R.id.SUreclyclerComentarios);

        implementedClickListener();


        getInfoComentarios();


        return mView;
    }

    private void getInfoComentarios() {

        //Inicializar Arraylist y asignar contenedor
        recyclerComentarios.setLayoutManager(mLinearLayoutManager);
        mLinearLayoutManager.setStackFromEnd(true);//messages on recycler put over keyboard
        recyclerComentarios.setHasFixedSize(true);

        //traer los datos de la coleccion de firebase

        FirestoreRecyclerOptions<MoldeComentarios> options = new FirestoreRecyclerOptions.Builder<MoldeComentarios>()
                .setQuery(mSugerenciasProvider.getCommentsListOrderByTimeStamp(), MoldeComentarios.class)
                .build();

        //asignar todos lo datos obtenidos al adaptador
        adapter = new ComentariosAdapter(options, getContext());


        //asignar datos al recyclerView
        recyclerComentarios.setAdapter(adapter);

        //Levar a la ultima posicion del adapter
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);

                mLinearLayoutManager.setStackFromEnd(true);
                Log.e("TAG-positionStart",String.valueOf(positionStart));
                Log.e("TAG-itemCount",String.valueOf(itemCount));

                //updateStatusMessage();
                //
                int numberItems = adapter.getItemCount();
                Log.e("TAG-NUMBER-OF-ITEMS",String.valueOf(numberItems));
                int lastItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                Log.e("TAG-LASTITEM",String.valueOf(lastItemPosition));

                if(lastItemPosition == -1 || (positionStart >= (numberItems - 1) && lastItemPosition == (positionStart - 1)))
                {
                    Log.e("TAG","ENTRASTE");
                    recyclerComentarios.scrollToPosition(positionStart-1);
                    mLinearLayoutManager.setStackFromEnd(false);
                }

            }
        });
    }

    private void implementedClickListener() {

        imageViewSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AbrirDialogoComentar();
                //mensaje al hacer click
                //Snackbar.make(v, "Here's a snackbar",Snackbar.LENGTH_LONG)
                //       .setAction("ACtion",null).show();

            }
        });


    }

    private void AbrirDialogoComentar() {

        //Llamar al dialogo
        final Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);//dialog se carga con tema creado en styles y carga los valores de 90%
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_dialogo_s_u_fragment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        final EditText tvComentario = dialog.findViewById(R.id.SUDItvDejarComentario);
        Button btnComentar=dialog.findViewById(R.id.SUDIbtncomentar);
        dialog.show();

        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Hola mundo", Toast.LENGTH_SHORT).show();


                if(!TextUtils.isEmpty(tvComentario.getText().toString())) {

                    mMoldeComentarios.setAuthorAlias(middlename.toLowerCase());
                    mMoldeComentarios.setAuthorEmail(email.toLowerCase());
                    mMoldeComentarios.setAuthorFirstname(firstname.toLowerCase());
                    mMoldeComentarios.setAuthorLastname(lastname.toLowerCase());
                    mMoldeComentarios.setSugerenciaMensaje(tvComentario.getText().toString().toLowerCase());
                    mMoldeComentarios.setSugerenciaFechaUnixtime(System.currentTimeMillis()/1000);

                    mSugerenciasProvider.createSuggestion(mMoldeComentarios).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(getActivity(), "Comentario registrado, correctamente!", Toast.LENGTH_SHORT).show();

                                //Limpiamos los campos
                                tvComentario.setText("");

                                //Cerrar dialogo
                                dialog.dismiss();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            dialog.dismiss();
                            Toast.makeText(getContext(), "Error al registrar un comentario.", Toast.LENGTH_LONG).show();

                        }
                    });

                }else {
                    Toast.makeText(getContext(), "Escribe un comentario!", Toast.LENGTH_SHORT).show();
                }


            }
        });







    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SugerenciasViewModel.class);

    }

    private void cargarPreferencias() {

        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        firstname= preferences.getString("spfirstname","");
        middlename= preferences.getString("spmiddlename","");
        lastname= preferences.getString("splastname","");
        email= preferences.getString("spEmail","");

    }

    private void setStatusBarFullTransparent()
    {
       getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
       getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
    }




    //on Stop y on Start son metodos para escuchar al adapter y poder mostrar los datos
    @Override
    public void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }
}