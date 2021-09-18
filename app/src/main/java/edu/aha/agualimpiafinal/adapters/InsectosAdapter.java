package edu.aha.agualimpiafinal.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import edu.aha.agualimpiafinal.R;

public class InsectosAdapter extends BaseAdapter {

    Context context;
    int[] imageInsectos;
    LayoutInflater inflater;

    public InsectosAdapter(Context context, int[] imageInsectos)
    {
        this.context = context;
        this.imageInsectos = imageInsectos;
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

        imageView.setImageResource(imageInsectos[position]);

        return convertView;
    }
}
