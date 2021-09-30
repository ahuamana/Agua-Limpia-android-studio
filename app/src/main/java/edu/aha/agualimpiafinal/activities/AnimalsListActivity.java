package edu.aha.agualimpiafinal.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.adapters.InsectosAdapter;
import edu.aha.agualimpiafinal.databinding.ActivityAnimalsListBinding;

public class AnimalsListActivity extends AppCompatActivity {

    private ActivityAnimalsListBinding binding;

    GridView gridViewInsectos;

    int[] imageInsectos = {R.drawable.cat_icon,
            R.drawable.perro_icon,
            R.drawable.mariposa_icon,
            R.drawable.hen_icon,
            R.drawable.hare_icon,
            R.drawable.chicken_icon,
            R.drawable.fish_icon,
            R.drawable.tortuga_icon,
            R.drawable.cricket_bug_icon,
            R.drawable.worm_icon,
            R.drawable.beetle_icon,
            R.drawable.praying_mantis_icon};


    String[] nameInsectos = {"Gato"
            ,"Perro"
            ,"Mariposa"
            ,"Gallina"
            ,"Conejo"
            ,"Pollito"
            ,"Pescado"
            ,"Tortuga"
            ,"Grillo"
            ,"Gusano"
            ,"Escarabajo"
            ,"Mantis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAnimalsListBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //set all code here

        fillGridView();

        finishThisActivity();

    }

    private void finishThisActivity() {

        binding.imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void fillGridView() {

        InsectosAdapter mAdapter = new InsectosAdapter(AnimalsListActivity.this, imageInsectos, nameInsectos);
        binding.gridViewInsectos.setAdapter(mAdapter);

    }
}