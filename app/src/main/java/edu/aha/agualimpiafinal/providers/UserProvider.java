package edu.aha.agualimpiafinal.providers;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;

import edu.aha.agualimpiafinal.models.User;

public class UserProvider {

    private CollectionReference mCollection;

    public UserProvider()
    {
        mCollection = FirebaseFirestore.getInstance().collection("Users");
    }

    public Task<String> createToken ()
    {
        return FirebaseMessaging.getInstance().getToken();
    }

    public Task<Void> create(User user)
    {
        return mCollection.document(user.getToken()).set(user);
    }

}
