package com.example.hungry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "e33f7a2136d0da0aecfeeb6572adc3eb";
    public static String url = "https://developers.zomato.com/api/v2.1/search?";

    Adapter adapter;

    List<Restaurant> restaurantList = new ArrayList<>();

    private RecyclerView recyclerView;
    private ImageView searchIcon;

    private LoadData loadData;

    public static GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData = new LoadData(this);

        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setHasFixedSize(false);

        checkPermission();

        searchIcon = findViewById(R.id.searchIcon);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(searchIntent);

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        gpsTracker = new GPSTracker(this);

        if (gpsTracker.canGetLocation) {
            gpsTracker.getLocation();
            Log.d("Location", "" + gpsTracker.getLatitude() + "," + gpsTracker.getLongitude());
        }

        if(loadData.restaurantList.size() == 0) {

            loadData.loadDataWithLocation(recyclerView);

        }else{

            adapter = new Adapter(loadData.restaurantList, this);
            recyclerView.setAdapter(adapter);

        }
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                        gpsTracker = new GPSTracker(this);

                        if (gpsTracker.canGetLocation) {
                            gpsTracker.getLocation();
                            Log.d("Location", "" + gpsTracker.getLatitude() + "," + gpsTracker.getLongitude());
                        }

                        loadData.loadDataWithLocation(recyclerView);

                    }
                } else {
                    checkPermission();
                }
                return;
            }
        }
    }

}