package edu.aha.agualimpiafinal.utils;


import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters extends AppCompatActivity {

    public static Converters instance = new Converters();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        instance = new Converters();
    }

    public String epochTimeToDate(Long DateToConvert)
    {
        long dateEpoch = DateToConvert*1000;// its need to be in milisecond
        Date dateFormat = new java.util.Date(dateEpoch);
        String dateFinal = new SimpleDateFormat("MM dd, yyyy hh:mma").format(dateFormat);

        return dateFinal;
    }
}
