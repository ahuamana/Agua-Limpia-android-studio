package edu.aha.agualimpiafinal.adapters;

import android.content.Context;
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

import edu.aha.agualimpiafinal.models.MoldeComentarios;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.utils.RelativeTime;

public class ComentariosAdapter  extends FirestoreRecyclerAdapter<MoldeComentarios,ComentariosAdapter.ComentariosHolder> {

    Context context;

    public ComentariosAdapter(@NonNull FirestoreRecyclerOptions<MoldeComentarios> options, Context context) {
        super(options);

            this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ComentariosAdapter.ComentariosHolder holder, int position, @NonNull MoldeComentarios model) {

        Log.e("DATA: ", "empezamos aqui");
        Log.e("DATA: ",""+ model.getSugerenciaMensaje());

        String inputlast = "";
        String outputlast= "";
        if(model.getAuthorLastname() != null)
        {
            if(!model.getAuthorLastname().equals(""))
            {
                ////Author
                inputlast = model.getAuthorLastname();
                Log.e("TAG",""+inputlast);
                outputlast = inputlast.substring(0,1).toUpperCase()+ inputlast.substring(1, inputlast.length()-1); // inputlast.length()-1 --> remove el ultimo caracter que es un espacio vacio

            }
        }

        String inputname="";
        String outputname="";
        if(model.getAuthorFirstname() != null)
        {
            if(!model.getAuthorFirstname().equals(""))
            {
                inputname = model.getAuthorFirstname();
                outputname = inputname.substring(0,1).toUpperCase()+ inputname.substring(1, inputname.length()-1); // inputname.length()-1 --> remove el ultimo caracter que es un espacio vacio
                ////Fin Author
            }
        }


        holder.comentariosFecha.setText(RelativeTime.getTimeAgo(model.getSugerenciaFechaUnixtime(), context));

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