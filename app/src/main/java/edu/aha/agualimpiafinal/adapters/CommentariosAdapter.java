package edu.aha.agualimpiafinal.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import edu.aha.agualimpiafinal.databinding.CardviewComentarioBinding;
import edu.aha.agualimpiafinal.models.Comment;
import edu.aha.agualimpiafinal.providers.UserProvider;
import edu.aha.agualimpiafinal.utils.TextUtilsText;

public class CommentariosAdapter extends FirestoreRecyclerAdapter<Comment, CommentariosAdapter.ViewHolder> {

    Context context;
    UserProvider mUserProvider;

    public CommentariosAdapter(@NonNull FirestoreRecyclerOptions<Comment> options, Context context) {
        super(options);

        this.context = context;
        Log.e("EMPEZAR","CONSTRUCTOR");
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Comment model) {

        Log.e("EMPEZAR","ONBINDING");
        getUserInfoToken(holder, model);

    }

    private void getUserInfoToken(ViewHolder holder, Comment model) {

        Log.e("EMPEZAR","BUSCAR USER");
        mUserProvider = new UserProvider();

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
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Aqui se infla el contenedor del molde donde se cargaran todos los datos
        ///View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_post,parent,false);
        CardviewComentarioBinding view = CardviewComentarioBinding.inflate(LayoutInflater.from(parent.getContext()), parent,false);


        return new ViewHolder(view);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardviewComentarioBinding binding;

        public ViewHolder(@NonNull CardviewComentarioBinding binding) {
            super(binding.getRoot());

            this.binding = binding;

        }
    }
}
