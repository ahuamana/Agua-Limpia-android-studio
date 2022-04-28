package edu.aha.agualimpiafinal.activities;

import static java.sql.DriverManager.println;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Objects;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityPointsBinding;
import edu.aha.agualimpiafinal.providers.UserProvider;

public class PointsActivity extends AppCompatActivity {

    private ActivityPointsBinding binding;

    UserProvider mUserProvider;
    Toolbar myToolbar;

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPointsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        //Vincular vistas
        myToolbar = binding.toolbarActivity;

        mDialog = new ProgressDialog(PointsActivity.this);
        mDialog.setTitle("Espere un momento");
        mDialog.setMessage("Cargando Información!");
        mDialog.setCancelable(false);
        mDialog.show();


        setToolbar();
        cargarTokenLocalmente();

    }


    private void getUserInfo(String token) {

        mUserProvider = new UserProvider();

        mUserProvider.searchUser(token).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.getResult().exists())
                {
                    int points = Integer.parseInt(task.getResult().get("points").toString());

                    binding.textViewPoints.setText( String.valueOf(points) );

                    if(points < 10)
                    {
                        binding.imageViewMedalPoints.setImageResource(R.drawable.bronze_medal);
                    }else
                    {
                        if(points>10 && points< 50)
                        {
                            binding.imageViewMedalPoints.setImageResource(R.drawable.silver_medal);
                        }else
                        {
                            if(points>50)
                            {
                                binding.imageViewMedalPoints.setImageResource(R.drawable.gold_medal);

                            }
                        }
                    }

                }else
                {
                    Log.e("TAG", "USER NOT FOUND");
                }

                mDialog.dismiss();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                android.util.Log.e("ERROR","No se pudo cargar la información: " + e.getMessage());
                mDialog.dismiss();
            }
        });

    }


    private void setToolbar() {

        setSupportActionBar(myToolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        binding.toolbarActivity.setNavigationIcon(getDrawable(R.drawable.ic_arrow_back));
        binding.toolbarActivity.setTitleTextColor(getColor(R.color.white));
        binding.toolbarActivity.setTitle("Puntos");
        myToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG","CLICKED ON BACK");
                onBackPressed();
            }
        });
    }


    private void cargarTokenLocalmente() {

        SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        String token= preferences.getString("token","");

        getUserInfo(token);

    }
}