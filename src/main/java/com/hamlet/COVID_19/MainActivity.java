package com.hamlet.COVID_19;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.OnItemClickListner {

    public static final String EXTRA_NAME = "countryName";
    public static final String EXTRA_CON = "confirmed";
    public static final String EXTRA_REC = "recovered";
    public static final String EXTRA_DEA = "deaths";
    public static final String EXTRA_NEW_CON = "newConfirmed";
    public static final String EXTRA_NEW_REC = "newRecovered";
    public static final String EXTRA_NEW_DEA = "newDeathes";

    public String defaultCountryName = "Pakistan";
    public int defaultCountryTCon;
    public int defaultCountryTRec;
    public int defaultCountryTDea;
    public int defaultCountryNCon;
    public int defaultCountryNRec;
    public int defaultCountryNDea;

    private RecyclerView recyclerView;
    private ExampleAdapter exampleAdapter;
    private ArrayList<ExampleItem> exampleItems;
    private RequestQueue requestQueue;

    private FloatingActionButton floatingActionButton;

    private TextView PakText;
    private TextView PakConText;
    private TextView PakRecText;
    private TextView PakDeaText;

    private long totalconfirmed;
    private long totalRecovered;
    private long totalDeaths;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View parentLayout = findViewById(android.R.id.content);
        String message = "Stay Home,Stay Safe!";
//        Snackbar.make(parentLayout,"Stay Home,Stay Safe",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        showSnackbar(parentLayout,message);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent defalut = new Intent(MainActivity.this,defaultCountryActivity.class);

                defalut.putExtra(EXTRA_NAME,defaultCountryName);
                defalut.putExtra(EXTRA_CON,defaultCountryTCon);
                defalut.putExtra(EXTRA_NEW_CON,defaultCountryNCon);
                defalut.putExtra(EXTRA_REC,defaultCountryTRec);
                defalut.putExtra(EXTRA_NEW_REC,defaultCountryNRec);
                defalut.putExtra(EXTRA_DEA,defaultCountryTDea);
                defalut.putExtra(EXTRA_NEW_DEA,defaultCountryNDea);

                startActivity(defalut);
            }
        });

        PakText = findViewById(R.id.PakText);
        PakConText = findViewById(R.id.PakConText);
        PakRecText = findViewById(R.id.PakRecText);
        PakDeaText = findViewById(R.id.PakDeaText);

        exampleItems = new ArrayList<>();

        if(isAvailable()){
            requestQueue = Volley.newRequestQueue(this);
            parseJson();
        } else {
            Intent intent = new Intent(MainActivity.this,NoConnection.class);
            startActivity(intent);
        }
    }

    private void parseJson(){
        Toast.makeText(this, "Loading,Please Wait!", Toast.LENGTH_SHORT).show();
        String url = "https://api.covid19api.com/summary";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("Countries");

                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject country = jsonArray.getJSONObject(i);

                        String countryName = country.getString("Country");
                        int recovered = country.getInt("TotalRecovered");
                        int confirmed = country.getInt("TotalConfirmed");
                        int deaths = country.getInt("TotalDeaths");
                        int newConfirmed = country.getInt("NewConfirmed");
                        int newRecovered = country.getInt("NewRecovered");
                        int newDeaths = country.getInt("NewDeaths");

                        totalconfirmed += confirmed;
                        totalRecovered += recovered;
                        totalDeaths += deaths;

                        exampleItems.add(new ExampleItem(countryName,confirmed, recovered, deaths,newConfirmed,newRecovered,newDeaths));

                        if(countryName.equals(defaultCountryName)){
                            defaultCountryTCon = confirmed;
                            defaultCountryTDea = deaths;
                            defaultCountryTRec = recovered;
                            defaultCountryNCon = newConfirmed;
                            defaultCountryNRec = newRecovered;
                            defaultCountryNDea = newDeaths;
                        }

                    }

                    PakText.setText("Global");
                    PakConText.setText("Confirmed : " + totalconfirmed);
                    PakRecText.setText("Recovered : " + totalRecovered);
                    PakDeaText.setText("Deaths : " + totalDeaths);

                    exampleAdapter = new ExampleAdapter(MainActivity.this,exampleItems);
                    recyclerView.setAdapter(exampleAdapter);

                    exampleAdapter.setOnItemClickListner(MainActivity.this);

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

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this,DetailActivity.class);
        ExampleItem exampleItem = exampleItems.get(position);

        intent.putExtra(EXTRA_NAME,exampleItem.getCountryName());
        intent.putExtra(EXTRA_CON,exampleItem.getConfirmedCases());
        intent.putExtra(EXTRA_REC,exampleItem.getRecoveredCases());
        intent.putExtra(EXTRA_DEA,exampleItem.getDeathes());

        intent.putExtra(EXTRA_NEW_CON,exampleItem.getNewConfirmedCases());
        intent.putExtra(EXTRA_NEW_REC,exampleItem.getNewRecoveredCases());
        intent.putExtra(EXTRA_NEW_DEA,exampleItem.getNewDeathes());

        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_search,menu);

        MenuItem searchItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                exampleAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    public boolean isAvailable(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo acitive = connectivityManager.getActiveNetworkInfo();
        return acitive != null && acitive.isConnected();
    }

    public static void showSnackbar(View view,String message){
        Snackbar.make(view,message,Snackbar.LENGTH_LONG).setAction("Action",null).show();
    }

}
