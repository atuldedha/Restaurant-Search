package com.example.hungry;

import android.content.Context;
import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.hungry.MainActivity.gpsTracker;

public class LoadData {

    public static final String API_KEY = "e33f7a2136d0da0aecfeeb6572adc3eb";
    public static String url = "https://developers.zomato.com/api/v2.1/search?";

    public static List<Restaurant> restaurantList = new ArrayList<>();
    public static List<Restaurant> searchedList = new ArrayList<>();

    public static Context mContext;

    public LoadData(Context mContext) {
        this.mContext = mContext;
    }

    public static void loadDataWithLocation(final RecyclerView recyclerView){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url + "lat=" + gpsTracker.getLatitude() + "&lon=" + gpsTracker.getLongitude(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            if(!response.isEmpty()) {

                                if(restaurantList.size() != 0){
                                    restaurantList.clear();
                                }

                                Log.d("Success", "" + response);

                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject r = jsonArray.getJSONObject(i);
                                    JSONObject restaurant = r.getJSONObject("restaurant");
                                    String name = restaurant.getString("name");
                                    String image = restaurant.getString("thumb");
                                    int cost = restaurant.getInt("average_cost_for_two");
                                    String costForTwo = String.valueOf(cost);

                                    Log.d("Namr", "" + name);

                                    Restaurant restaurantObject = new Restaurant(name, image, costForTwo);
                                    restaurantList.add(restaurantObject);
                                }
                                if (restaurantList != null) {
                                    Adapter adapter = new Adapter(restaurantList, mContext);

                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (Exception e) {
                            Log.d("Exception", "" + e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "" + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_key", API_KEY);
                params.put("Accept", "application/json");

                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);

        //return restaurantList;
    }

    public static void loadDataWithKeyword(String keyword, final RecyclerView recyclerView){
        RequestQueue requestQueue = Volley.newRequestQueue(mContext);

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, MainActivity.url + "q=" + keyword + "&lat=" + MainActivity.gpsTracker.getLatitude() + "&lon=" + MainActivity.gpsTracker.getLongitude(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {


                            if(!response.isEmpty()) {

                                if (searchedList.size() != 0) {
                                    searchedList.clear();
                                }
                                Log.d("Success", "" + response);

                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = jsonObject.getJSONArray("restaurants");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject r = jsonArray.getJSONObject(i);
                                    JSONObject restaurant = r.getJSONObject("restaurant");
                                    String name = restaurant.getString("name");
                                    String image = restaurant.getString("thumb");
                                    int cost = restaurant.getInt("average_cost_for_two");
                                    String costForTwo = String.valueOf(cost);

                                    Log.d("Namr", "" + name);

                                    Restaurant restaurantObject = new Restaurant(name, image, costForTwo);
                                    if(!(searchedList.contains(restaurantObject))) {
                                        searchedList.add(restaurantObject);
                                    }
                                }

                                if (searchedList != null) {
                                    Adapter adapter = new Adapter(searchedList, mContext);

                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                }
                            }

                        }catch (Exception e) {
                            Log.d("Exception", "" + e.toString());
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", "" + error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("user_key", MainActivity.API_KEY);
                params.put("Accept", "application/json");

                return params;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }

}
