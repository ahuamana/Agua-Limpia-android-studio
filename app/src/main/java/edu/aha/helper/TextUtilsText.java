package edu.aha.helper;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
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

    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



}
