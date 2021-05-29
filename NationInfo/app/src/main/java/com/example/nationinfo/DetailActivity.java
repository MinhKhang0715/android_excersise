package com.example.nationinfo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.nationinfo.MainActivity.EXTRA_AREA;
import static com.example.nationinfo.MainActivity.EXTRA_CODE;
import static com.example.nationinfo.MainActivity.EXTRA_NAME;
import static com.example.nationinfo.MainActivity.EXTRA_POPULATION;

public class DetailActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String nationCode = intent.getStringExtra(EXTRA_CODE);
        String nationName = intent.getStringExtra(EXTRA_NAME);
        int population = intent.getIntExtra(EXTRA_POPULATION, 0);
        int area = intent.getIntExtra(EXTRA_AREA, 0);

        TextView txtNationName = findViewById(R.id.nationName_detail);
        ImageView nationFlag = findViewById(R.id.nationFlag_detail);
        TextView txtPopulation = findViewById(R.id.population_detail);
        TextView txtArea = findViewById(R.id.area_detail);

        assert nationCode != null;
        String imageUrl ="https://img.geonames.org/flags/l/"+nationCode.toLowerCase()+".gif";

        txtNationName.setText(nationName);
        Picasso.get().load(imageUrl).fit().centerInside().into(nationFlag);
        txtPopulation.setText("Population: " + population);
        txtArea.setText("Area: " + area +" sqKm");
    }
}
