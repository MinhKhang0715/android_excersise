package com.example.nationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_CODE = "nationCode";
    public static final String EXTRA_NAME = "nationName";
    public static final String EXTRA_POPULATION = "population";
    public static final String EXTRA_AREA = "area";

    ListView _listView;
    ArrayList<NationItem> _nationList;
    RequestQueue _requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _listView = findViewById(R.id.listView);
        _nationList = new ArrayList<>();
        _requestQueue = Volley.newRequestQueue(getApplicationContext());

        _listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent detailIntent = new Intent(MainActivity.this, DetailActivity.class);
            NationItem clickedItem = _nationList.get(position);

            detailIntent.putExtra(EXTRA_CODE, clickedItem.getNationCode());
            detailIntent.putExtra(EXTRA_NAME, clickedItem.getNationName());
            detailIntent.putExtra(EXTRA_POPULATION, clickedItem.getPopulation());
            detailIntent.putExtra(EXTRA_AREA, clickedItem.getAreaInSqKm());
            startActivity(detailIntent);
        });

        new ProcessInBackground().execute();
    }

    @SuppressLint("StaticFieldLeak")
    public class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
        ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        Exception exception = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog.setMessage("Busy loading rss feed...please wait...");
            progressDialog.show();
        }

        @Override
        protected Exception doInBackground(Integer... params) {

            String url = "http://api.geonames.org/countryInfoJSON?formatted=true&lang=it&username=vinhnguyen989&style=full";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            JSONArray jsonArray = response.getJSONArray("geonames");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject geoname = jsonArray.getJSONObject(i);
                                int population = geoname.getInt("population");
                                int areaInSqKm = geoname.getInt("areaInSqKm");
                                String countryCode = geoname.getString("countryCode");
                                String countryName = geoname.getString("countryName");

                                _nationList.add(new NationItem(countryCode, countryName, population, areaInSqKm));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }, Throwable::printStackTrace);
            _requestQueue.add(request);
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<NationItem> arrayAdapter = new ArrayAdapter<>(MainActivity.this, R.layout.row, _nationList);

            _listView.setAdapter(arrayAdapter);

            progressDialog.dismiss();
        }
    }

}
