package com.hamlet.COVID_19;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class DetailActivity extends AppCompatActivity {

    private TextView nameText;
    private TextView conText;
    private TextView recText;
    private TextView deaText;
    private TextView newConText;
    private TextView newRecText;
    private TextView newDeaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        View parentLayout = findViewById(android.R.id.content);
        String message = "Don't touch your eyes,nose and mouth";
        showSnackbar(parentLayout,message);

        nameText = findViewById(R.id.detailName);
        conText = findViewById(R.id.detailCon);
        recText = findViewById(R.id.detailRec);
        deaText = findViewById(R.id.detailDea);
        newConText = findViewById(R.id.detailNewCon);
        newRecText = findViewById(R.id.detailNewRec);
        newDeaText = findViewById(R.id.detailNewDea);

        Intent intent = getIntent();

        String name = intent.getStringExtra(EXTRA_NAME);
        int confirmed = intent.getIntExtra(EXTRA_CON,0);
        int recovered = intent.getIntExtra(EXTRA_REC,0);
        int deaths = intent.getIntExtra(EXTRA_DEA,0);
        int newConfimed = intent.getIntExtra(EXTRA_NEW_CON,0);
        int newRecovered = intent.getIntExtra(EXTRA_NEW_REC,0);
        int newDeathes = intent.getIntExtra(EXTRA_NEW_DEA,0);

        nameText.setText(name);
        newConText.setText("New Confirmed : " + newConfimed);
        conText.setText("Total Confirmed : " + confirmed);
        newRecText.setText("New Recovered : " + newRecovered);
        recText.setText("Total Recovered : " + recovered);
        newDeaText.setText("New Deathes : " + newDeathes);
        deaText.setText("Total Deaths : " + deaths);

    }
}
