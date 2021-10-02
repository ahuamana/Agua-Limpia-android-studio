package edu.aha.agualimpiafinal.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;
import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.CardviewInsectosBinding;
import edu.aha.agualimpiafinal.models.Like;
import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.models.MoldeSustantivo;
import edu.aha.agualimpiafinal.providers.LikeProvider;
import edu.aha.agualimpiafinal.utils.RelativeTime;
import edu.aha.agualimpiafinal.utils.TextUtilsText;

public class LaboratorioAdapter extends FirestoreRecyclerAdapter<MoldeSustantivo, LaboratorioAdapter.ViewHolder> {

    Context context;
    Like mLike;
    String token;

    public LaboratorioAdapter(@NonNull FirestoreRecyclerOptions<MoldeSustantivo> options, Context context) {
        super(options);

        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull MoldeSustantivo model) {

        //asignar variables con firebase


        cargarTokenLocalmente();

        setColorLikes(model, holder);

        setUserDetails(model, holder);


    }

    private void cargarTokenLocalmente() {

        SharedPreferences preferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        token= preferences.getString("token","");


    }

    private void setColorLikes(MoldeSustantivo model, ViewHolder holder) {

        holder.binding.linearLayoutLike.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.e("COLOR","Cambio de Color Aplicado");

                holder.binding.imageViewLike.setImageResource(R.drawable.facebook_good_like_icon512);
                holder.binding.textViewLike.setTextColor(context.getResources().getColor(R.color.facebook_color_like));

                createLike(model, holder);
            }
        });

    }

    private void createLike(MoldeSustantivo model, ViewHolder holder) {

        Log.e("TOKEN", ""+ token);
        String idLike = model.getId()+token;

        mLike.setId_token(idLike);
        mLike.setToken(token);
        mLike.setStatus(true);

        if(token != null)
        {
            if(!token.equals(""))
            {
                LikeProvider mLikeProvider = new LikeProvider();

                mLikeProvider.create(mLike).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if(task.isSuccessful())
                        {
                            Log.e("LIKE","CREADO LIKE CORRECTAMENTE");
                        }

                    }
                });
            }
        }

    }


    private void setUserDetails(MoldeSustantivo model, ViewHolder holder) {

        String ape=TextUtilsText.instance.replaceFirstCharInSequenceToUppercase(model.getAuthor_lastname());
        String nam=TextUtilsText.instance.replaceFirstCharInSequenceToUppercase(model.getAuthor_name());

        holder.binding.authorName.setText(nam+" "+ ape);

        holder.binding.name.setText(model.getName());

        Glide.with(context)
                .load(model.getUrl())
                .placeholder(R.drawable.loading_icon)
                .into(holder.binding.roundedImageView);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CardviewInsectosBinding view =CardviewInsectosBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);


        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardviewInsectosBinding binding;

        public ViewHolder(@NonNull CardviewInsectosBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }


    }
}
