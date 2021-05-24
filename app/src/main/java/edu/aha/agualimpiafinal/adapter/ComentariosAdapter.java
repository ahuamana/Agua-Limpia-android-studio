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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import edu.aha.agualimpiafinal.Entidades.MoldeComentarios;
import edu.aha.agualimpiafinal.R;

public class ComentariosAdapter  extends FirestoreRecyclerAdapter<MoldeComentarios,ComentariosAdapter.ComentariosHolder> {



    public ComentariosAdapter(@NonNull FirestoreRecyclerOptions<MoldeComentarios> options) { super(options);    }

    @Override
    protected void onBindViewHolder(@NonNull ComentariosAdapter.ComentariosHolder holder, int position, @NonNull MoldeComentarios model) {

        Log.e("DATA: ", "emepzamos aqui");
        Log.e("DATA: ",""+ model.getSugerenciaMensaje());


        ////Author
            String inputlast = model.getAuthorLastname();
            String outputlast = inputlast.substring(0,1).toUpperCase()+ inputlast.substring(1, inputlast.length()-1); // inputlast.length()-1 --> remove el ultimo caracter que es un espacio vacio

            String inputname = model.getAuthorFirstname();
            String outputname = inputname.substring(0,1).toUpperCase()+ inputname.substring(1, inputname.length()-1); // inputname.length()-1 --> remove el ultimo caracter que es un espacio vacio
        ////Fin Author

        ////Horal Obtenida
            long time = model.getSugerenciaFechaUnixtime()*1000;  //
            Date df = new java.util.Date(time);
            String vv = new SimpleDateFormat("MM dd, yyyy hh:mma").format(df);

        ////Fin Horal Obtenida

        holder.comentariosFecha.setText(vv);

        //Mostrar Author
        holder.comentariosAuthor.setText(outputlast+", "+outputname);
        //Mostrar Mensaje de sugerencia de usuario
        holder.comentariosDescripcion.setText(model.getSugerenciaMensaje());

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
        TextView comentariosAuthor, comentariosFecha, comentariosDescripcion;

        public ComentariosHolder(@NonNull View vista) {
            super(vista);

            //referencias variables y vincularlos con las variables locales
            comentariosAuthor = vista.findViewById(R.id.ComentariostvAuthor);
            comentariosFecha = vista.findViewById(R.id.ComentariostvHora);
            comentariosDescripcion = vista.findViewById(R.id.ComentariostvDescripcion);

        }
    }
}
