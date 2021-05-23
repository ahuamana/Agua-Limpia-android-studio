package edu.aha.agualimpiafinal.menu.sugerencias;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import edu.aha.agualimpiafinal.R;

public class sugerencias extends Fragment {

    private SugerenciasViewModel mViewModel;

    TextView SUdescripcion;
    Button SUbtnComentar;

    //Cloud Firestore
    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    //datos de Shared preferences
    String firstname,middlename,lastname, email;

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


        SUdescripcion = vista.findViewById(R.id.SUtvDejarComentario);
        SUbtnComentar = vista.findViewById(R.id.SUbtncomentar);

        SUbtnComentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Enviar datos a Firestore

                ////Inicializar Collecion nueva
                DocumentReference reference = fStore.collection("DataComentarios").document(); // con .documents Genera automaticamente la KEY

                //se añade un evento si termina la accion
                reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                        //si el evento se crea normal este se enviara a firestore
                        if(task.isSuccessful())
                        {
                            //Creamos un map con objetos strings y que no haya valores duplicados
                            Map<String, Object> sugerenciaData = new HashMap<>();
                            sugerenciaData.put("SugerenciaFechaUnixtime",System.currentTimeMillis()/1000);
                            sugerenciaData.put("SugerenciaMensaje",SUdescripcion.getText().toString().toLowerCase());
                            sugerenciaData.put("AuthorFirstname",firstname.toLowerCase());
                            sugerenciaData.put("AuthorLastname",lastname.toLowerCase());
                            sugerenciaData.put("AuthorAlias",middlename.toLowerCase());
                            sugerenciaData.put("AuthorEmail",email.toLowerCase());

                        }

                    }
                });
                //termina el evento de enviar datos a firebase

            }
        });





        return vista;
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

}