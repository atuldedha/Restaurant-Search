package com.example.hungry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private RecyclerView recyclerView;
    private EditText searchEditText;

    String search = "";

    private List<Restaurant> restaurantList = new ArrayList<>();

    private Adapter adapter;

    private LoadData loadData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadData = new LoadData(this);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);

        searchEditText = findViewById(R.id.searchEditText);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                search = s.toString().toLowerCase();
                onStart();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(search!=null){
                loadData.loadDataWithKeyword(search, recyclerView);
        }else{
                loadData.loadDataWithLocation(recyclerView);

        }

    }
}