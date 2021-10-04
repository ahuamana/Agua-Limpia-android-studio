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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.CardviewComentarioBinding;
import edu.aha.agualimpiafinal.databinding.CardviewInsectosBinding;
import edu.aha.agualimpiafinal.models.Comment;
import edu.aha.agualimpiafinal.models.MoldeComentarios;
import edu.aha.agualimpiafinal.models.User;
import edu.aha.agualimpiafinal.providers.UserProvider;
import edu.aha.agualimpiafinal.utils.RelativeTime;
import edu.aha.agualimpiafinal.utils.TextUtilsText;

public class CommentariosAdapter extends FirestoreRecyclerAdapter<Comment, CommentariosAdapter.ComentariosHolder> {

    Context context;
    UserProvider mUserProvider;

    public CommentariosAdapter(@NonNull FirestoreRecyclerOptions<Comment> options, Context context) {
        super(options);

            this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ComentariosHolder holder, int position, @NonNull Comment model) {

        getUserInfoToken(holder, model);

    }

    private void getUserInfoToken(ComentariosHolder holder, Comment model) {

        mUserProvider.searchUser(model.getToken()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.isSuccessful())
                {
                    String first = task.getResult().get("author_firstname").toString();
                    String last = task.getResult().get("author_lastname").toString();

                    String fin =TextUtilsText.instance.replaceFirstCharInSequenceToUppercase(first+" "+last);
                    holder.binding.textViewfullname.setText(fin);
                    holder.binding.message.setText(model.getMessage());

                }else
                {
                    Log.e("ERROR","Error al traer los datos de Firebase");
                }

            }
        });

    }

    @NonNull
    @Override
    public ComentariosHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Aqui se infla el contenedor del molde donde se cargaran todos los datos
        ///View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post,parent,false);
        CardviewComentarioBinding view = CardviewComentarioBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);


        return new ComentariosHolder(view);
    }


    public class ComentariosHolder extends RecyclerView.ViewHolder {

        private CardviewComentarioBinding binding;

        public ComentariosHolder(@NonNull CardviewComentarioBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }
}
