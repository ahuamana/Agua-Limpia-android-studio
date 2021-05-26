package edu.aha.agualimpiafinal.menu.sugerencias;

import androidx.lifecycle.ViewModelProvider;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.Adapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

import edu.aha.agualimpiafinal.Entidades.MoldeComentarios;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapter.ComentariosAdapter;
import edu.aha.agualimpiafinal.menu.sugerencias.dialogoSU.dialogoSU_fragment;

public class sugerencias extends Fragment {

    private SugerenciasViewModel mViewModel;

    EditText SUdescripcion;
    Button SUbtnComentar;
    RecyclerView recyclerComentarios;
    ComentariosAdapter adapter;

    //Cloud Firestore
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    //datos de Shared preferences
    String firstname,middlename,lastname, email;

    //boton flotante
    FloatingActionButton btnComentar;

    public static sugerencias newInstance() {
        return new sugerencias();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.sugerencias_fragment, container, false);

        //Inicializar instancias a firestore
        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        //Obtener datos guardados del telefono de SharedPreferences
        cargarPreferencias();

        SUdescripcion = vista.findViewById(R.id.SUtvDejarComentario);
        SUbtnComentar = vista.findViewById(R.id.SUbtncomentar);
        btnComentar = vista.findViewById(R.id.SUbtnFlotanteComentar);

        //Inflar Boton
        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AbrirDialogoComentar();

                //mensaje al hacer click
                //Snackbar.make(v, "Here's a snackbar",Snackbar.LENGTH_LONG)
                 //       .setAction("ACtion",null).show();

            }
        });


        SUbtnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar datos a Firestore

                ////Inicializar Collecion nueva
                final DocumentReference reference = fStore.collection("DataComentarios").document(); // con .documents Genera automaticamente la KEY

                //se añade un evento si termina la accion
                reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        //Validar campo vacio de la descripción
                        if(!TextUtils.isEmpty(SUdescripcion.getText().toString()))
                        {

                            //si el evento se crea normal este se enviara a firestore
                            if(task.isSuccessful())
                            {
                                //Creamos un map con objetos strings y que no haya valores duplicados y lo guardamos todos los datos en el map
                                Map<String, Object> sugerenciaData = new HashMap<>();
                                sugerenciaData.put("SugerenciaFechaUnixtime",System.currentTimeMillis()/1000);
                                sugerenciaData.put("SugerenciaMensaje",SUdescripcion.getText().toString().toLowerCase());
                                sugerenciaData.put("AuthorFirstname",firstname.toLowerCase());
                                sugerenciaData.put("AuthorLastname",lastname.toLowerCase());
                                sugerenciaData.put("AuthorAlias",middlename.toLowerCase());
                                sugerenciaData.put("AuthorEmail",email.toLowerCase());

                                //asiganmos a la coleccion los datos almacenado en el map
                                reference.set(sugerenciaData);
                                //Mostramos mensaje al usuario
                                Toast.makeText(getActivity(), "Comentario registrado, correctamente!", Toast.LENGTH_SHORT).show();

                                //Limpiamos los campos
                                SUdescripcion.setText("");

                            }


                        }else {
                            Toast.makeText(getContext(), "Escribe un comentario!", Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                //termina el evento de enviar datos a firebase

            }
        });


        //Inicializar Arraylist y asignar contenedor
        recyclerComentarios = vista.findViewById(R.id.SUreclyclerComentarios);
        recyclerComentarios.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerComentarios.setHasFixedSize(true);

        //traer los datos de la coleccion de firebase
        CollectionReference dataSugerencia = fStore.collection("DataComentarios");

        Query query = dataSugerencia;

        FirestoreRecyclerOptions<MoldeComentarios> options = new FirestoreRecyclerOptions.Builder<MoldeComentarios>()
                .setQuery(query, MoldeComentarios.class)
                .build();

        //asignar todos lo datos obtenidos al adaptador
        adapter = new ComentariosAdapter(options);


        //asignar datos al recyclerView
        recyclerComentarios.setAdapter(adapter);

        return vista;
    }

    private void AbrirDialogoComentar() {

        //Llamar al dialogo
        Dialog dialog = new Dialog(getActivity(), R.style.Theme_Dialog);//dialog se carga con tema creado en styles y carga los valores de 90%
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.fragment_dialogo_s_u_fragment);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button btnComentar=dialog.findViewById(R.id.SUDIbtncomentar);
        btnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Hola mundo", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();




    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SugerenciasViewModel.class);
        // TODO: Use the ViewModel
    }

    private void cargarPreferencias() {

        SharedPreferences preferences = getActivity().getSharedPreferences("credenciales", Context.MODE_PRIVATE);

        firstname= preferences.getString("spfirstname","");
        middlename= preferences.getString("spmiddlename","");
        lastname= preferences.getString("splastname","");
        email= preferences.getString("spEmail","");

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