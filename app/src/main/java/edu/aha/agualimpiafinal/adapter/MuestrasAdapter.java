package edu.aha.agualimpiafinal.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.aha.agualimpiafinal.Entidades.MoldeMuestra;
import edu.aha.agualimpiafinal.R;

public class MuestrasAdapter extends FirestoreRecyclerAdapter<MoldeMuestra,MuestrasAdapter.MuestrasHolder> {



    public MuestrasAdapter(@NonNull FirestoreRecyclerOptions<MoldeMuestra> options) { super(options); }


    @SuppressLint("Range")
    @Override
    protected void onBindViewHolder(@NonNull MuestrasHolder holder, int position, @NonNull MoldeMuestra model) {

        Log.e("DATA: ",""+ model.getAuthorFirstname());
        //asignar variables con firebase
        String inputnombrecompleto= model.getAuthorAlias();
        String outputnombrecompleto = inputnombrecompleto.substring(0, 1).toUpperCase() + inputnombrecompleto.substring(1);
        holder.txtnombrecompleto.setText(outputnombrecompleto);

        //cambiar Hora actual
        Date df = new java.util.Date(model.getMuestraTimeStamp()*1000);
        String horaNueva = new SimpleDateFormat("dd MMMM yyyy", new Locale("es","ES")).format(df);

        holder.txttiempo.setText(horaNueva); // Asignar la nueva hora en formato humano xd
        holder.txtResultado.setText(String.valueOf(model.getMuestraResultado()));

        //Asignar color de letra dependiendo el resultado

        if(model.getMuestraResultado().equals("Positivo"))
        {
            holder.txtResultado.setTextColor(Color.RED);
        }else {
                if (model.getMuestraResultado().equals("Negativo")) {
                holder.txtResultado.setTextColor(Color.parseColor("#00bcd4"));
                }
            }

        //Fin de Asignar color de letra dependiendo el resultado


        //set first letter to Uppercase
        String input= model.getMuestraDepartamento();
        String output = input.substring(0, 1).toUpperCase() + input.substring(1);
        holder.txtDepartamento.setText(output);

        String input1= model.getMuestraProvincia();
        String output1 = input1.substring(0, 1).toUpperCase() + input1.substring(1);
        holder.txtProvincia.setText(output1);



        Glide.with(holder.foto.getContext())
                .load(model.getMuestraFotoPATH())
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.foto);

    }

    @NonNull
    @Override
    public MuestrasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.muestras_molde,parent,false);

        return new MuestrasHolder(vista);
    }


    public class MuestrasHolder extends RecyclerView.ViewHolder {

        //crear variables para hacer la refenrencia al molde xml
        TextView txtnombrecompleto, txtcantidadmuestra, txttiempo, txtResultado, txtDepartamento, txtProvincia;
        String   txtidmuestra;
        ImageView foto;
        Button btnmasinformacion;

        public MuestrasHolder(@NonNull View vista) {
            super(vista);

            txtnombrecompleto= vista.findViewById(R.id.MMtxtauthor);
            //txtcantidadmuestra=vista.findViewById(R.id.MMtxtMuestra);
            txttiempo=vista.findViewById(R.id.MMtxtTiempo);
            txtResultado=vista.findViewById(R.id.MMtxtResultado);
            txtDepartamento= vista.findViewById(R.id.MMtxtDepartamento);
            txtProvincia= vista.findViewById(R.id.MMtxtProvincia);
            foto=vista.findViewById(R.id.MMivImagen);


        }


    }
}
