package edu.aha.agualimpiafinal.providers;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class UsersProvider {

    private CollectionReference mCollection;

    public UsersProvider() {

    mCollection = FirebaseFirestore.getInstance().collection("DatosMuestra");

    }

    public CollectionReference getCollectionDatosMuestra() {
        return mCollection;
    }
}
