package edu.aha.agualimpiafinal.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Converters {

    public static Converters instance;

    public String epochTimeToDate(Long DateToConvert)
    {
        String dateFinal;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        dateFinal= sdf.format(new Date(DateToConvert));

        return dateFinal;
    }
}
