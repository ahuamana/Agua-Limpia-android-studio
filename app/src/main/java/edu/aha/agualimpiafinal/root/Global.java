package edu.aha.agualimpiafinal.root;


import android.content.Context;



public class Global {

    private Context context;

    public static Global instance = new Global();


    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }
}
