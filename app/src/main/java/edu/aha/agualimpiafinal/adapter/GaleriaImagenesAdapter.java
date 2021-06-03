package edu.aha.agualimpiafinal.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.ImageView;

import edu.aha.agualimpiafinal.R;

public class GaleriaImagenesAdapter extends BaseAdapter {
    //variables
    private Context mContext;
    public int [] imagenesArray = {
            //Rellenar imagenes aqui
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
            R.drawable.background_image_share,
    };

    //constructor


    public GaleriaImagenesAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //
    @Override
    public int getCount() {
        return imagenesArray.length;
    }

    @Override
    public Object getItem(int position) {
        return imagenesArray[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Codigo para mostrar imagenes
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(imagenesArray[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams( new ViewGroup.LayoutParams(
                //ancho y altura de imagenes
                340,
                350));

        ///
        return imageView;
    }
}
