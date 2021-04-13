package edu.aha.agualimpiafinal.adapter;

import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.aha.agualimpiafinal.Entidades.Ingreso;
import edu.aha.agualimpiafinal.R;

public class MuestrasAdapter extends FirebaseRecyclerAdapter<Ingreso,MuestrasAdapter.MuestrasHolder> {

    List<Ingreso> listaMuestras;


    public MuestrasAdapter(@NonNull FirebaseRecyclerOptions<Ingreso> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MuestrasHolder holder, int position, @NonNull Ingreso model) {

        //asignar variables con firebase
        holder.txtnombrecompleto.setText(model.getNombrecompleto());
        holder.txtcantidadmuestra.setText(String.valueOf(model.getCantidadmuestraDB()));
        holder.txttiempo.setText(String.valueOf(model.getTiempoDB()));
        holder.txtBVQ.setText(String.valueOf(model.getBqvDB()));



        Glide.with(holder.foto.getContext())
                .load(model.getFotopathDB())
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
        TextView txtnombrecompleto, txtcantidadmuestra, txttiempo, txtBVQ;
        String   txtidmuestra;
        ImageView foto;
        Button btnmasinformacion;

        public MuestrasHolder(@NonNull View vista) {
            super(vista);

            txtnombrecompleto= vista.findViewById(R.id.MMtxtauthor);
            txtcantidadmuestra=vista.findViewById(R.id.MMtxtMuestra);
            txttiempo=vista.findViewById(R.id.MMtxtTiempo);
            txtBVQ=vista.findViewById(R.id.MMtxtBQV);
            foto=vista.findViewById(R.id.MMivImagen);


        }


    }
}
