package edu.aha.agualimpiafinal.utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TextUtilsText {

    public static TextUtilsText instance = new TextUtilsText();

    public String replaceFirstCharInSequenceToUppercase(String text)
    {

        String[] words = text.split(" ");
        List<String> wordsUppercase = new ArrayList<>();


        for(String data: words)
        {
            String upperString = data.substring(0, 1).toUpperCase() + data.substring(1).toLowerCase();
            //Log.e("REPLACE","DATA: "+ upperString);
            wordsUppercase.add(upperString);
        }

        String datafinal= android.text.TextUtils.join(" ",wordsUppercase);

        //Log.e("REPLACE","DATA: "+ datafinal);

        return datafinal;
    }



}
