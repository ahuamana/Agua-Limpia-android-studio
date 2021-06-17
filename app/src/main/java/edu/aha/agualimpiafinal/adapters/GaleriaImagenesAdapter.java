package edu.aha.agualimpiafinal.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import edu.aha.agualimpiafinal.R;

public class GaleriaImagenesAdapter extends BaseAdapter {
    //variables
    private Context mContext;
    public List<String> imagenesArray = new ArrayList<String>();


    //constructor
    public GaleriaImagenesAdapter(Context mContext) {

        this.mContext = mContext;
        imagenesArray.add("https://www.peru-retail.com/wp-content/uploads/Real-Plaza-nueva-imagen-1024x411.jpg");
        imagenesArray.add("https://firebasestorage.googleapis.com/v0/b/agualimpia-image.appspot.com/o/1606202122%3A51%3A28.jpg?alt=media&token=1606202122:51:28.jpg");
        imagenesArray.add("https://firebasestorage.googleapis.com/v0/b/agualimpia-image.appspot.com/o/1606202122%3A51%3A23.jpg?alt=media&token=1606202122:51:23.jpg");
        imagenesArray.add("https://firebasestorage.googleapis.com/v0/b/agualimpia-image.appspot.com/o/1606202122%3A51%3A12.jpg?alt=media&token=1606202122:51:12.jpg");

    }

    //
    @Override
    public int getCount() {
        return imagenesArray.size();
    }

    @Override
    public Object getItem(int position) {
        return imagenesArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Codigo para mostrar imagenes
        ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//        imageView.setImageResource(imagenesArray[position]);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        imageView.setLayoutParams( new ViewGroup.LayoutParams(
//                //ancho y altura de imagenes
//                340,
//                350));
//
//        ///
        Glide.with(mContext)
                .load(imagenesArray.get(position))
                .apply(new RequestOptions().override(600,600))  //set new size from each image
                .into(imageView);



        return imageView;
    }
}
