package edu.aha.agualimpiafinal.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import edu.aha.agualimpiafinal.models.Like;

public class LikeProvider {

    private CollectionReference mCollection;

    public LikeProvider()
    {
        mCollection = FirebaseFirestore.getInstance().collection("Likes");
    }

    public Task<Void> create (Like objeto)
    {
        return mCollection.document(objeto.getId_token()).set(objeto);
    }
}