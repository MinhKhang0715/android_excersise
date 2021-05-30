package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.math.RoundingMode;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText _input;
    TextView _output;
    Spinner _spinner1;
    Spinner _spinner2;
    Button _bConvert;
    ImageView _switch;
    ArrayList<String> _currencies;
    ArrayList<Double> _exchangeRates;
    Double _exchangeRate1;
    Double _exchangeRate2;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _input = findViewById(R.id.input);
        _output = findViewById(R.id.output);
        _spinner1 = findViewById(R.id.currency1);
        _spinner2 = findViewById(R.id.currency2);
        _bConvert = findViewById(R.id.convert);
        _switch = findViewById(R.id.imageView2);
        _currencies = new ArrayList<>();
        _exchangeRates = new ArrayList<>();

        _spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _exchangeRate1 = _exchangeRates.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        _spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                _exchangeRate2 = _exchangeRates.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        _bConvert.setOnClickListener(v -> {
            String s = _input.getText().toString();
            if (!s.equals("")) {
                DecimalFormat decimalFormat = new DecimalFormat("#.###");
                decimalFormat.setRoundingMode(RoundingMode.CEILING);
                double result = _exchangeRate2 / _exchangeRate1 * (Double.parseDouble(s));
                String resultAfterRounding = decimalFormat.format(result);
                _output.setText(resultAfterRounding + "");
            } else {
                Toast.makeText(getApplicationContext(), "Enter Value", Toast.LENGTH_SHORT).show();
            }
        });

        _switch.setOnClickListener(v -> {
            int pos1 = _spinner1.getSelectedItemPosition();
            int pos2 = _spinner2.getSelectedItemPosition();
            _spinner1.setSelection(pos2);
            _spinner2.setSelection(pos1);
            _output.setText("");
        });

        new ProcessInBackground().execute();
    }

    public InputStream getInputStream(URL url) {
        try {
            return url.openConnection().getInputStream();
        } catch (IOException e) {
            return null;
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class ProcessInBackground extends AsyncTask<Integer, Void, Exception> {
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
            try {
                URL url = new URL("https://usd.fxexchangerate.com/rss.xml");
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(getInputStream(url), "UTF_8");
                boolean insideItem = false;
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT) {
                    if (eventType == XmlPullParser.START_TAG) {
                        if (xpp.getName().equalsIgnoreCase("item")) {
                            insideItem = true;
                        } else if (xpp.getName().equalsIgnoreCase("title")) {
                            if (insideItem) {
                                String s = xpp.nextText();
                                _currencies.add(s.substring(s.indexOf('/') + 1));
                            }
                        } else if (xpp.getName().equalsIgnoreCase("description")) {
                            if (insideItem) {
                                String s = xpp.nextText();
                                s = s.replaceAll("[^0-9,-=]", "");
                                String[] item = s.split("=");
                                _exchangeRates.add(Double.parseDouble(item[1]));
                            }
                        }
                    } else if (eventType == XmlPullParser.END_TAG && xpp.getName().equalsIgnoreCase("item")) {
                        insideItem = false;
                    }
                    eventType = xpp.next();
                }
            } catch (XmlPullParserException | IOException e) {
                exception = e;
            }
            return exception;
        }

        @Override
        protected void onPostExecute(Exception s) {
            super.onPostExecute(s);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, _currencies);

            _spinner1.setAdapter(adapter);
            _spinner2.setAdapter(adapter);
            progressDialog.dismiss();
        }
    }
}
