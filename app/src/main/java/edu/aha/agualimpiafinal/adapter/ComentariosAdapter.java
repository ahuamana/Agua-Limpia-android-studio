package edu.aha.agualimpiafinal.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import edu.aha.agualimpiafinal.Entidades.MoldeComentarios;
import edu.aha.agualimpiafinal.R;

public class ComentariosAdapter  extends FirestoreRecyclerAdapter<MoldeComentarios,ComentariosAdapter.ComentariosHolder> {



    public ComentariosAdapter(@NonNull FirestoreRecyclerOptions<MoldeComentarios> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ComentariosAdapter.ComentariosHolder holder, int position, @NonNull MoldeComentarios model) {

        Log.e("DATA: ",""+ model.getSugerenciaMensaje());

    }

    @NonNull
    @Override
    public ComentariosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Aqui se infla el contenedor del molde donde se cargaran todos los datos
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.comentarios_molde,parent,false);

        return new ComentariosHolder(vista);
    }

    public class ComentariosHolder extends RecyclerView.ViewHolder {

        //crear variables del molde
        TextView comentariosAuthor, comentarioFecha, comentariosDescripcion;

        public ComentariosHolder(@NonNull View vista) {
            super(vista);

            //referencias variables y vincularlos con las variables locales
            comentariosAuthor = vista.findViewById(R.id.ComentariostvAuthor);
            comentarioFecha = vista.findViewById(R.id.ComentariostvHora);
            comentariosDescripcion = vista.findViewById(R.id.ComentariostvDescripcion);

        }
    }
}
