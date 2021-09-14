package edu.aha.agualimpiafinal.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import edu.aha.agualimpiafinal.models.MoldeSustantivo;

public class InsectosProvider {

    private CollectionReference mCollection;

    public InsectosProvider()
    {
        mCollection = FirebaseFirestore.getInstance().collection("LaboratorioDigital");
    }

    public Task<Void> create (MoldeSustantivo sustantivo)
    {
        return mCollection.document().set(sustantivo);
    }

    public Query getMuestrasListOrderByTimeStamp()
    {
        return mCollection.orderBy("timestamp", Query.Direction.ASCENDING);

    }
}
