package edu.aha.agualimpiafinal.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.fragments.AnimalsFragment;

public class InsectosAdapter extends BaseAdapter {

    Context context;
    int[] imageInsectos;
    String[] nameInsectos;
    LayoutInflater inflater;
    FragmentManager fragmentManager;

    public InsectosAdapter(Context context, int[] imageInsectos, String[] nameInsectos, FragmentManager fragmentManager)
    {
        this.context = context;
        this.imageInsectos = imageInsectos;
        this.nameInsectos = nameInsectos;
        this.fragmentManager= fragmentManager;

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

        goToNextActivity(context, position, cardView);

        return convertView;
    }

    private void goToNextActivity(final Context context, int position, CardView cardView) {

        switch (position)
        {
            case 1:
                break;


            case 2:

                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "Position 3", Toast.LENGTH_SHORT).show();
                        Log.e("POSITION","POSITION 3 ");

                        //Intent i = new Intent(context, AnimalsActivity.class);
                        //context.startActivity(i);

                        replaceFragmentWithOther(new AnimalsFragment());

                    }
                });

                break;
        }
    }

    private void replaceFragmentWithOther(Fragment nuevoFragmento)
    {
        // Crea el nuevo fragmento y la transacción.
        //Fragment nuevoFragmento = new AnimalsFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.nav_host_fragment, nuevoFragmento);
        transaction.addToBackStack("insectos");
        // Commit a la transacción
        transaction.commit();
    }


}
