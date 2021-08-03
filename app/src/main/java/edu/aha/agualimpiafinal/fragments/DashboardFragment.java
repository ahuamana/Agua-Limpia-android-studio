package edu.aha.agualimpiafinal.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import edu.aha.agualimpiafinal.R;
import edu.aha.agualimpiafinal.models.MoldeMuestra;
import edu.aha.agualimpiafinal.providers.MuestrasProvider;
import edu.aha.agualimpiafinal.utils.Converters;


public class DashboardFragment extends Fragment {

    MuestrasProvider mMuestrasProvider;
    View mView;

    int amountNegative;
    int amountPositive;

    DonutProgress donutProgressNegative;
    DonutProgress donutProgressPositive;

    BarChart mBarChart;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        donutProgressNegative = mView.findViewById(R.id.donut_progressNegative);
        donutProgressPositive = mView.findViewById(R.id.donut_progressPositive);
        mBarChart = mView.findViewById(R.id.barchart);

        mMuestrasProvider = new MuestrasProvider();

        getPreferences();


        getDataForGroupedBarChart();




        return mView;
    }

    private void getDataForGroupedBarChart() {


        final String currentYear= String.valueOf(Converters.instance.epochTimeToDate(Converters.instance.currentUnixTime())).substring(6,10); // get Current Time


        mMuestrasProvider.getCollectionDatosMuestra().get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot querySnapshot) {

                int Muestras_del_dos_mil_veintiuno_positivo = 0;
                int Muestras_del_dos_mil_veintiuno_negativo = 0;

                List<MoldeMuestra> moldeMuestra   = querySnapshot.toObjects(MoldeMuestra.class);

                for( MoldeMuestra molde : moldeMuestra)
                {
                    String yearMuestra;
                    yearMuestra = Converters.instance.epochTimeToDate(molde.getMuestraTimeStamp()).substring(6,10);

                    android.util.Log.e("MUESTRA","MUESTRA: "+ yearMuestra);

                    if(yearMuestra.equals(currentYear))
                    {
                        if(molde.getMuestraResultado().equals("Positivo"))
                        {
                            Muestras_del_dos_mil_veintiuno_positivo++;
                        }

                        if(molde.getMuestraResultado().equals("Negativo"))
                        {
                            Muestras_del_dos_mil_veintiuno_negativo++;
                        }

                    }

                }

                android.util.Log.e("MUESTRA","MUESTRA: "+ Muestras_del_dos_mil_veintiuno_positivo);
                android.util.Log.e("MUESTRA","MUESTRA: "+ Muestras_del_dos_mil_veintiuno_negativo);




            }
        });


        createGroupedBarChat();

    }

    private void createGroupedBarChat() {

        BarDataSet barDataSet1 = new BarDataSet(barEntries1(),"Positivo");
        barDataSet1.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet1.setColor(getResources().getColor(R.color.red));

        BarDataSet barDataSet2 = new BarDataSet(barEntries2(),"Negativo");
        barDataSet2.setAxisDependency(YAxis.AxisDependency.LEFT);
        barDataSet2.setColor(getResources().getColor(R.color.lightblue));

        BarData data = new BarData(barDataSet1,barDataSet2);

        mBarChart.setData(data);

        //String[] days = new String[]{"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

        //XAxis xAxis = mBarChart.getXAxis();
        //xAxis.setValueFormatter(new IndexAxisValueFormatter(days));
        //xAxis.setCenterAxisLabels(true);
        //xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setGranularity(1);
        //xAxis.setGranularityEnabled(true);

        //mBarChart.setDragEnabled(true);
        //mBarChart.setVisibleXRangeMaximum(7);

        float groupSpace = 0.09f;
        float barSpace = 0.02f; // x2 dataset
        float barWidth = 0.45f; // x2 dataset
        data.setBarWidth(barWidth);

        //mBarChart.getXAxis().setAxisMinimum(0);
        //mBarChart.getXAxis().setAxisMaximum(0+mBarChart.getBarData().getGroupWidth(groupSpace,barSpace));
        //mBarChart.getAxisLeft().setAxisMinimum(0);

        mBarChart.groupBars(0,groupSpace,barSpace);// start at x = 0



    }

    private ArrayList<BarEntry> barEntries1()
    {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,2000));
        barEntries.add(new BarEntry(2,791));
        barEntries.add(new BarEntry(3,630));
        barEntries.add(new BarEntry(4,458));
        barEntries.add(new BarEntry(5,2724));
        barEntries.add(new BarEntry(6,500));
        barEntries.add(new BarEntry(7,2173));

        return barEntries;
    }

    private ArrayList<BarEntry> barEntries2()
    {
        ArrayList<BarEntry> barEntries = new ArrayList<>();
        barEntries.add(new BarEntry(1,900));
        barEntries.add(new BarEntry(2,631));
        barEntries.add(new BarEntry(3,800));
        barEntries.add(new BarEntry(4,384));
        barEntries.add(new BarEntry(5,1614));
        barEntries.add(new BarEntry(6,5000));
        barEntries.add(new BarEntry(7,1173));

        return barEntries;
    }


    private void getPreferences( )
    {

        SharedPreferences preferences = getContext().getSharedPreferences("credenciales", Context.MODE_PRIVATE);
        String email =  preferences.getString("spEmail","");

        getDataFirebase(email);

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


}