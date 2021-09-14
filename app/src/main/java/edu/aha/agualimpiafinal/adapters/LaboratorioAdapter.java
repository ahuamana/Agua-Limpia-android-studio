package edu.aha.agualimpiafinal.adapters;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.CardviewAnimalsBinding;
import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.models.MoldeSustantivo;
import edu.aha.agualimpiafinal.utils.RelativeTime;

public class LaboratorioAdapter extends FirestoreRecyclerAdapter<MoldeSustantivo, LaboratorioAdapter.ViewHolder> {

    Context context;

    public LaboratorioAdapter(@NonNull FirestoreRecyclerOptions<MoldeSustantivo> options, Context context) {
        super(options);

        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MoldeSustantivo model) {

        //asignar variables con firebase
        holder.binding.name.setText(model.getName());

        Glide.with(context)
                .load(model.getUrl())
                .into(holder.binding.roundedImageView);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardviewAnimalsBinding view =CardviewAnimalsBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);


        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardviewAnimalsBinding binding;

        public ViewHolder(@NonNull CardviewAnimalsBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }


    }
}
