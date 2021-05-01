package edu.aha.agualimpiafinal.arrays;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.lang.reflect.Array;

import edu.aha.agualimpiafinal.R;

public class arrays {

    public ArrayAdapter<String> ObtenerArray(Context contexts, int nuevo){

        Context context=  contexts;
        String [] adapter = context.getResources().getStringArray(nuevo);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                context, android.R.layout.simple_spinner_dropdown_item, adapter );

        return spinnerArrayAdapter;

    }
}
