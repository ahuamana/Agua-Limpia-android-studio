package edu.aha.agualimpiafinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.providers.MuestrasProvider;


public class DashboardFragment extends Fragment {

    MuestrasProvider mMuestrasProvider;
    View mView;

    int amountNegative;
    int amountPositive;

    DonutProgress donutProgressNegative;
    DonutProgress donutProgressPositive;

    public DashboardFragment() {

    }


    public static DashboardFragment newInstance(String param1, String param2) {
        DashboardFragment fragment = new DashboardFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void getDataFirebase(final String email) {


       mMuestrasProvider.getCollectionDatosMuestra().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
           @Override
           public void onSuccess(QuerySnapshot querySnapshot) {

                List<MoldeMuestra> moldeMuestra   = querySnapshot.toObjects(MoldeMuestra.class);

                for( MoldeMuestra molde : moldeMuestra)
               {
                   //android.util.Log.e("DATA", "EMAIL: "+ email);
                   //android.util.Log.e("DATA", "EMAILFIRESTORE: "+ molde.getAuthorEmail());

                   if(molde.getAuthorEmail().equals(email))
                   {
                       if(molde.getMuestraResultado().equals("Negativo"))
                       {
                           amountNegative++;
                       }

                       if(molde.getMuestraResultado().equals("Positivo"))
                       {
                           amountPositive++;
                       }
                   }
                    //android.util.Log.e("DATA", "CANTIDAD: "+ cantidad);

               }

                //asignar la cantidad de tus muestras

               setDonutProgressData(amountNegative, donutProgressNegative);
               setDonutProgressData(amountPositive, donutProgressPositive);




           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {


           }
       });



    }

    private void setDonutProgressData(int amount, DonutProgress donutProgress) {

        //donutProgress.setTextSize(100);

        switch (amount)
        {
            case 0: {
                donutProgress.setProgress(0f);
                donutProgress.setText("0");
                break;
            }

            case 1: {
                donutProgress.setProgress(10f);
                donutProgress.setText("1");
                break;
            }
            case 2: {
                donutProgress.setProgress(20f);
                donutProgress.setText("2");
                break;
            }
            case 3:{
                donutProgress.setProgress(30f);
                donutProgress.setText("3");
                break;
            }
            case 4:{
                donutProgress.setProgress(40);
                donutProgress.setText("4");
                break;
            }
            case 5:{
                donutProgress.setProgress(50);
                donutProgress.setText("5");
                break;
            }
            case 6:{
                donutProgress.setProgress(60);
                donutProgress.setText("6");
                break;
            }
            case 7:{
                donutProgress.setProgress(70);
                donutProgress.setText("7");
                break;
            }
            case 8:{
                donutProgress.setProgress(80);
                donutProgress.setText("8");
                break;
            }
            case 9:{
                donutProgress.setProgress(90);
                donutProgress.setText("9");
                break;
            }
            case 10:{
                donutProgress.setProgress(100);
                donutProgress.setText("10");
                break;
            }

            default:
                donutProgress.setProgress(0);
                donutProgress.setText("0");
                break;
        }

    }

    private void getPreferences( )
    {

        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String email =  preferences.getString("spEmail","");

        getDataFirebase(email);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        donutProgressNegative = mView.findViewById(R.id.donut_progressNegative);
        donutProgressPositive = mView.findViewById(R.id.donut_progressPositive);


        mMuestrasProvider = new MuestrasProvider();

        getPreferences();




        return mView;
    }


}