package edu.aha.agualimpiafinal.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import edu.aha.agualimpiafinal.models.MoldeMuestra;

public class UsersProvider {

    private CollectionReference mCollection;

    public UsersProvider() {

    mCollection = FirebaseFirestore.getInstance().collection("DatosMuestra");

    }

    public CollectionReference getCollectionDatosMuestra() {
        return mCollection;
    }

    public Task<Void> create (MoldeMuestra muestra)
    {
        return mCollection.document().set(muestra);
    }
}
