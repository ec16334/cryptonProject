package com.dheia.finalyear;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.dheia.finalyear.util.MyJsonStringRequest;
import com.dheia.finalyear.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

public class AllCoinSearch extends AppCompatActivity {
//testing
    private RequestQueue mQueue;

    RecyclerView.LayoutManager mlayoutManager;
    SearchAdapter adapter;

    private RecyclerView recyclerView;

    private ArrayList<CoinItemChangelly> coinitemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_coin_search);
        setTitle("Select Coin");

        coinitemList = new ArrayList<>();
        recyclerView = findViewById(R.id.allSearchCoinsRecyclerView);
        mlayoutManager = new LinearLayoutManager(this);
        adapter = new SearchAdapter(coinitemList);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(adapter);

        getCoins();

    }

    public void getCoins() {
        mQueue = Volley.newRequestQueue(this);
        String url = "https://min-api.cryptocompare.com/data/all/coinlist";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONObject arr = response.getJSONObject("Data");
                    Iterator<String> keys = arr.keys();
                    int i = 0;
                    while (keys.hasNext()) {
                        String keyValue = keys.next();
                        if (arr.getJSONObject(keyValue).has("ImageUrl")) {
                            String coinName = arr.getJSONObject(keyValue).getString("CoinName");
                            String coinSymbol = arr.getJSONObject(keyValue).getString("Symbol");
                            String coinImage = arr.getJSONObject(keyValue).getString("ImageUrl");
                            String coinID = arr.getJSONObject(keyValue).getString("Id");
                            coinitemList.add(new CoinItemChangelly(coinSymbol, "https://www.cryptocompare.com/" + coinImage, coinName, coinID));
                            i++;
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR: ", "not working");
            }
        });
        mQueue.add(request);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.searchbarmenu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String userinput = newText.toLowerCase();
                ArrayList<CoinItemChangelly> newList = new ArrayList<>();

                for (CoinItemChangelly name : coinitemList) {
                    if (name.getCoinName().toLowerCase().contains(userinput) || name.getCoinSymbol().toLowerCase().contains(userinput)) {
                        newList.add(name);
                    }
                }
                adapter.updateList(newList);
                return false;
            }
        });
        return true;
    }
}
