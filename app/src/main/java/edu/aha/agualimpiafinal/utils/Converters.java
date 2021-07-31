package edu.aha.agualimpiafinal.utils;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters extends AppCompatActivity {

    public static Converters instance = new Converters();


    public String epochTimeToDate(Long DateToConvert)
    {

        Date dateFormat = new java.util.Date(DateToConvert);
        String dateConverted = new SimpleDateFormat("MM dd, yyyy hh:mma").format(dateFormat);

        return dateConverted;
    }
}
