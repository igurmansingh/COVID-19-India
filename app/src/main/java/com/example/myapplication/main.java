package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.transform.Result;

import kotlinx.coroutines.GlobalScope;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;



public class main extends Fragment {

    private RequestQueue mQueue;
    public int[] ans=new int[6];
    public String date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false);

    }

    PieChart pi;

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextView t1=(TextView) view.findViewById(R.id.Totalcases);
        final TextView newcases=(TextView) view.findViewById(R.id.newcases);
        final TextView newcase=(TextView) view.findViewById(R.id.newcase);

        mQueue = Volley.newRequestQueue(getContext());
        jasonParse();


        //wait function starts

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                t1.setText(String.valueOf(ans[0]));
                newcases.setText(String.valueOf(ans[4]));
                newcase.setText("New Cases at \n"+date+" ");

                pi=(PieChart)view.findViewById(R.id.pichart);
                pi.setUsePercentValues(true);
                pi.getDescription().setEnabled(false);
                pi.setExtraOffsets(5, 10, 5, 5);

                pi.setDragDecelerationFrictionCoef(0.95f);

                pi.setDrawHoleEnabled(false);
                pi.setHoleColor(Color.WHITE);

                pi.setTransparentCircleRadius(51f);

                ArrayList<PieEntry> values= new ArrayList<>();

                float a,b,c,d;
                a=ans[0];
                b=ans[1];
                c=ans[2];
                d=ans[3];
                float na,nb,nc;
                na=(d/a)*100;
                nb=(b/a)*100;
                nc=(c/a)*100;


                values.add(new PieEntry(na,"Active:"+ans[3]));
                values.add(new PieEntry(nb,"Desceased:"+ans[1]));
                values.add(new PieEntry(nc,"Recovered:"+ans[2]));

                pi.animateY(1400, Easing.EaseInOutCubic);

                PieDataSet dataSet= new PieDataSet(values,"type");
                dataSet.setSliceSpace(4f);
                dataSet.setSelectionShift(5f);
                dataSet.setColors(Color.rgb(80, 143, 162),Color.rgb(225, 139, 79  ),Color.rgb(159, 189, 104));

                PieData data=new PieData(dataSet);
                data.setValueTextSize(20f);
                data.setValueTextColor(Color.rgb(1, 83, 50          ));

                pi.setData(data);



            }
        }, 300);

    }

    private void jasonParse(){
        String url="https://api.covid19india.org/data.json";

        JsonObjectRequest req= new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonarray = response.getJSONArray("statewise");

                            for(int i=0; i<1; i++) {
                                JSONObject state= jsonarray.getJSONObject(i);
                                int confirmed, deaths, recovered, active,deltaconfirmed;
                                String lastupdatedtime;
                                confirmed = state.getInt("confirmed");
                                deaths = state.getInt("deaths");
                                recovered = state.getInt("recovered");
                                active = state.getInt("active");
                                deltaconfirmed=state.getInt("deltaconfirmed");
                                lastupdatedtime=state.getString("lastupdatedtime");

                                ans[0]=confirmed;
                                ans[1]=deaths;
                                ans[2]=recovered;
                                ans[3]=active;
                                ans[4]=deltaconfirmed;
                                date=lastupdatedtime;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(req);
    }

}