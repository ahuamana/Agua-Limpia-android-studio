package edu.aha.agualimpiafinal.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import edu.aha.agualimpiafinal.R;

public class InsectosAdapter extends BaseAdapter {

    Context context;
    int[] imageInsectos;
    String[] nameInsectos;
    LayoutInflater inflater;

    public InsectosAdapter(Context context, int[] imageInsectos, String[] nameInsectos)
    {
        this.context = context;
        this.imageInsectos = imageInsectos;
        this.nameInsectos = nameInsectos;
    }

    @Override
    public int getCount() {
        return imageInsectos.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater == null)
        {
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if(convertView == null)
        {
            convertView = inflater.inflate(R.layout.row_item_insectos, null);
        }

        //Declarar variables del layout
        ImageView imageView = convertView.findViewById(R.id.imageView_row_insectos);
        CardView cardView = convertView.findViewById(R.id.cardView_insectos);
        TextView textViewNameInsectos = convertView.findViewById(R.id.textView_name_insectos);


        ////All code here
        //cardView.setCardBackgroundColor(Color.GREEN);  //Background color for cardview
        imageView.setImageResource(imageInsectos[position]);
        textViewNameInsectos.setText(nameInsectos[position]);

        return convertView;
    }
}
