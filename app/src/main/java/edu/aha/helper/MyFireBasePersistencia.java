package edu.aha.helper;

import com.google.firebase.database.FirebaseDatabase;

public class MyFireBasePersistencia extends android.app.Application{

    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}