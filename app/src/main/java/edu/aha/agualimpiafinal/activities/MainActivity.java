package edu.aha.agualimpiafinal.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.DocumentSnapshot;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityMainBinding;
import edu.aha.agualimpiafinal.models.User;
import edu.aha.agualimpiafinal.providers.UserProvider;

public class

MainActivity extends AppCompatActivity {

    UserProvider mUserProvider;
    User mUser;

    String firstname, middlename, lastname, email;

    private AppBarConfiguration mAppBarConfiguration;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        cargarPreferencias();
        getUserInfo();


        binding.appBarMain.fabPoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, PointsActivity.class);
                startActivity(i);

            }
        });





        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio,
                //R.id.nav_lista,
                //R.id.nav_comomedir,
                //R.id.nav_nosotros,
                //R.id.nav_registrar,
                //R.id.nav_sugerencias,
                //R.id.nav_compartir,
                //R.id.nav_dashboard,
                R.id.nav_laboratorio)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void getUserInfo() {

        mUserProvider = new UserProvider();
        mUser = new User();

        mUserProvider.createToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {

                if(task.isSuccessful())
                {
                    mUser.setToken(task.getResult());

                    guardarTokenLocalmente(mUser.getToken());

                    Log.d("TAG", "TOKENCREADO: "+task.getResult());

                    mUserProvider.searchUser(mUser.getToken()).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if(task.getResult().exists())
                            {
                                Log.e("TAG",""+task.getResult());
                            }else
                            {

                                Log.e("TAG",""+task.getResult());
                                goToCreateData(mUser);
                            }

                        }
                    });




                }else
                {
                    Log.d("TAG", "No se pudo crear el token");

                }

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Log.e("ERROR","ERROR: "+e.getMessage());

            }
        });

    }

    private void guardarTokenLocalmente(String tokenReciever) {

            SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
            //editor permite editar y almacenar las variables
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("token",tokenReciever);
            editor.commit();
    }

    private void goToCreateData(User mUser) {

        mUser.setAuthor_firstname(firstname);
        mUser.setAuthor_email(email);
        mUser.setAuthor_alias(middlename);
        mUser.setAuthor_lastname(lastname);
        mUser.setPoints(0);
        mUserProvider.create(mUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Log.e("USERPROFILE","User Points creado");

                }else
                {
                   Log.e("USERPROFILE","Error al crear la Base de datos");
                }

            }
        });

    }


    private void cargarPreferencias() {

        SharedPreferences preferences = getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        firstname= preferences.getString("spfirstname","");
        middlename= preferences.getString("spmiddlename","");
        lastname= preferences.getString("splastname","");
        email= preferences.getString("spEmail","");
        //asignar datos guardados a los respectivos campos


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        getFragmentManager().popBackStack();
    }
}