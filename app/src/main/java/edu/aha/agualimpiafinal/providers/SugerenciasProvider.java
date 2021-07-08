package edu.aha.agualimpiafinal.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class SugerenciasProvider {

    private CollectionReference mCollection;

    public SugerenciasProvider() {
        mCollection = FirebaseFirestore.getInstance().collection("DataComentarios");
    }


    public Query getCommentsListOrderByTimeStamp()
    {
        return mCollection.orderBy("SugerenciaFechaUnixtime", Query.Direction.ASCENDING);
    }



}
