package com.babis.iatplhcovid19;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView tvCases, tvRecovered, tvCritical, tvActive, tvTodayCases, tvTotalDeaths, tvTodayDeaths, tvAffectedCountries;
    ScrollView scrollView;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);

        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);

        hint();
        fetchdata();
        
    }

    private void fetchdata() {

        // Create a String request using Volley Library
        String url = "https://corona.lmao.ninja/v2/all";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response.toString());

                    tvCases.setText(jsonObject.getString("cases"));
                    tvRecovered.setText(jsonObject.getString("recovered"));
                    tvCritical.setText(jsonObject.getString("critical"));
                    tvActive.setText(jsonObject.getString("active"));
                    tvTodayCases.setText(jsonObject.getString("todayCases"));
                    tvTotalDeaths.setText(jsonObject.getString("deaths"));
                    tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                    tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));

                    pieChart.addPieSlice(new PieModel("Total Cases",
                            Integer.parseInt(tvCases.getText().toString()), Color.parseColor("#FFA726")));
                    pieChart.addPieSlice(new PieModel("Recovered",
                            Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                    pieChart.addPieSlice(new PieModel("Deaths",
                            Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                    pieChart.addPieSlice(new PieModel("Active",
                            Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                    pieChart.startAnimation();

                    scrollView.setVisibility(View.VISIBLE);

                }
                catch (JSONException ex){
                    ex.printStackTrace();
                    scrollView.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                scrollView.setVisibility(View.VISIBLE);
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    public void trackCountries(View view) {

        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
        startActivity(intent);
    }

    public void showMessage(String s){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle("Public safety advice");
        builder.setMessage(s);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    public void hint(){
        StringBuilder b = new StringBuilder();
        b.append("Based on the available scientific data relating to the new SARS-CoV-2 coronavirus infection, systematic implementation of all measures for prevention of the new coronavirus transmission and spread is considered of outmost importance. Please consider the following:\n" +
                "\n" +
                "WHAT TO DO\n" +
                "\n" +
                "Wash your hands regularly and thoroughly with soap and water or a disinfectant, and avoid touching your face (eyes, nose, mouth) with your hands.\n" +
                "Cover you cough or sneezes with a tissue which you must dispose of straightaway using a rubbish bin. If you don’t have a tissue, use your elbow.\n" +
                "If you experience mild symptoms of respiratory infection (cough, runny nose, fever, sore throat) stay home in isolation.\n" +
                "If symptoms become worse or if you are considered high-risk (elderly or people of any age with underlying health conditions e.g. diabetes mellitus, cardiovascular and chronic respiratory diseases, arterial hypertension), you should immediately contact your physician for evaluation.\n" +
                "If you are under medication, you must comply with your doctors’ advice.\n" +
                " \n" +
                "\n" +
                "WHAT NOT TO DO\n" +
                "\n" +
                "You should not come in contact with persons who show symptoms of respiratory infection (cough, runny nose, fever, sore throat).\n" +
                "Avoid gatherings, social events, visits to other houses and crowded open spaces.\n" +
                "Avoid any unnecessary travels abroad or within the country.\n" +
                "Do not visit your doctor or a health unit if you develop mild symptoms of respiratory infection (cough, runny nose, fever, sore throat).\n" +
                "Strictly avoid visits to patients in hospitals.\n" +
                "Strictly avoid contact with people in high-risk groups. If this is not possible, take all personal hygiene measures (wash your hands thoroughly, use a mask or keep a 2-meter distance from one another).");
        showMessage(b.toString());
    }
}