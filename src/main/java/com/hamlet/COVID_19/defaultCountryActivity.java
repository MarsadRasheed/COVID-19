package com.hamlet.COVID_19;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.hamlet.COVID_19.MainActivity.EXTRA_CON;
import static com.hamlet.COVID_19.MainActivity.EXTRA_DEA;
import static com.hamlet.COVID_19.MainActivity.EXTRA_NAME;
import static com.hamlet.COVID_19.MainActivity.EXTRA_NEW_CON;
import static com.hamlet.COVID_19.MainActivity.EXTRA_NEW_DEA;
import static com.hamlet.COVID_19.MainActivity.EXTRA_NEW_REC;
import static com.hamlet.COVID_19.MainActivity.EXTRA_REC;
import static com.hamlet.COVID_19.MainActivity.showSnackbar;

public class defaultCountryActivity extends AppCompatActivity {

    private TextView defaultNameText;
    private TextView defaultTConText;
    private TextView defaultNConText;
    private TextView defaultTRecText;
    private TextView defaultNRecText;
    private TextView defaultTDeaText;
    private TextView defaultNDeaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_country);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View parentLayout = findViewById(android.R.id.content);
        String message = "Wash your hands with Sanitizer and Maintain a safe Distance!";
        showSnackbar(parentLayout,message);
//
//        Snackbar.make(parentLayout, "Stay Home,Stay Safe!", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show();

        defaultNameText = (TextView) findViewById(R.id.defaultName);
        defaultTConText = (TextView) findViewById(R.id.defaultCon);
        defaultNConText = (TextView) findViewById(R.id.defaultNewCon);
        defaultTRecText = (TextView) findViewById(R.id.defaultRec);
        defaultNRecText = (TextView) findViewById(R.id.defaultNewRec);
        defaultTDeaText = (TextView) findViewById(R.id.defaultDea);
        defaultNDeaText = (TextView) findViewById(R.id.defaultNewDea);

        defaultNameText.setText(getIntent().getStringExtra(EXTRA_NAME));
        defaultNConText.setText("New Confirmd : " + getIntent().getIntExtra(EXTRA_NEW_CON,0));
        defaultTConText.setText("Total Confirmd : " + getIntent().getIntExtra(EXTRA_CON,0));
        defaultNRecText.setText("New Recovered : " + getIntent().getIntExtra(EXTRA_NEW_REC,0));
        defaultTRecText.setText("Total Recovered : " + getIntent().getIntExtra(EXTRA_REC,0));
        defaultNDeaText.setText("New Deaths : " + getIntent().getIntExtra(EXTRA_NEW_DEA,0));
        defaultTDeaText.setText("Total Deaths : " + getIntent().getIntExtra(EXTRA_DEA,0));

    }
}
