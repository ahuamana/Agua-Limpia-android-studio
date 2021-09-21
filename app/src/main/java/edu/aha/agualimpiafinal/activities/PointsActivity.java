package edu.aha.agualimpiafinal.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.databinding.ActivityPointsBinding;
import edu.aha.agualimpiafinal.models.User;
import edu.aha.agualimpiafinal.providers.UserProvider;

public class PointsActivity extends AppCompatActivity {

    private ActivityPointsBinding binding;

    UserProvider mUserProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPointsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        SetOnClickListeners();

        cargarTokenLocalmente();

    }

    private void getUserInfo(String token) {

        mUserProvider = new UserProvider();

        mUserProvider.searchUser(token).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if(task.getResult().exists())
                {
                    String points = task.getResult().get("points").toString();
                    binding.textViewPoints.setText(points);

                }else
                {
                    Log.e("TAG", "USER NOT FOUND");
                }

            }
        });

    }

    private void SetOnClickListeners() {

        binding.backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



    }

    private void cargarTokenLocalmente() {
        SharedPreferences preferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        String token= preferences.getString("token","");

        getUserInfo(token);

    }
}