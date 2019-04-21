package com.dheia.finalyear;

/**
 * Created by Dheia on 11/11/2018.
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Tab1 extends Fragment {


    ArrayAdapter<String> arrayAdapter;


    ListView userHoldingsList;

    private static final String TAG = "TAB1";

    DatabaseHelper mDatabaseHelper;


    public String currency = "gbp";

    TextView portfolioTotal;

    private RequestQueue mQueue;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    ArrayList<String> userHoldingsName = new ArrayList<>();
    ArrayList<Double> userHoldingsQTY = new ArrayList<>();
    ArrayList<Double> userHoldingsprice = new ArrayList<>();
    ArrayList<String> userHoldingsID = new ArrayList<>();
    ArrayList<String> userHoldingsURL = new ArrayList<>();
    ArrayList<String> userHoldingsSYMBOL = new ArrayList<>();
    ArrayList<String> userHoldingsPERCENTAGE = new ArrayList<>();
    ArrayList<String> userHoldingsDBID = new ArrayList<>();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.tab1, container, false);


        //this makes the list and fills it with the holdings of user
        userHoldingsList = rootView.findViewById(R.id.holdingsList);

        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        portfolioTotal = getActivity().findViewById(R.id.portfolioTotal);

        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        wmbPreference.getString("currencyPreference", "gbp");

        mDatabaseHelper = new DatabaseHelper(getActivity());
        populateListView();
        updateTotalHoldings();
        ImageButton button;
        button = getActivity().findViewById(R.id.addCoins);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AllCoinSearch.class));
            }
        });

        mSwipeRefreshLayout = getActivity().findViewById(R.id.swipeviewcontainerTab1);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                for (int i = 0; i < userHoldingsSYMBOL.size(); i++) {
                    getCurrentMarketPrices(userHoldingsSYMBOL.get(i));
                }
                updateTotalHoldings();

                mSwipeRefreshLayout.setRefreshing(false);


            }
        });


    }


    public void populateListView() {

        userHoldingsName = new ArrayList<>();
        userHoldingsQTY = new ArrayList<>();
        userHoldingsprice = new ArrayList<>();
        userHoldingsID = new ArrayList<>();
        userHoldingsURL = new ArrayList<>();
        userHoldingsSYMBOL = new ArrayList<>();
        userHoldingsPERCENTAGE = new ArrayList<>();
        userHoldingsDBID = new ArrayList<>();


        String coinName = "";
        String coinQuantity = "";
        String coinTotalPrice = "";
        String coinUrl = "";
        String coinSymbol = "";
        String coinID = "";
        String dbID = "";


        //get the data and append to the list
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()) {

            //set up variable names with entries from the DB
            //the values stored here and the investment prices by the user
            dbID = data.getString(0);
            coinName = data.getString(1);
            coinQuantity = Double.toString(data.getDouble(2));
            coinTotalPrice = Double.toString(data.getDouble(3));
            coinID = data.getString(4);
            coinUrl = data.getString(5);
            coinSymbol = data.getString(6);

            double temp = Double.parseDouble(coinQuantity);
            if(temp>0) {

                userHoldingsName.add(coinName);
                userHoldingsQTY.add(Double.parseDouble(coinQuantity));
                userHoldingsSYMBOL.add(coinSymbol);
                userHoldingsID.add(coinID);
                userHoldingsprice.add(Double.parseDouble(coinTotalPrice));
                userHoldingsPERCENTAGE.add("0");
                userHoldingsURL.add(coinUrl);
                userHoldingsDBID.add(dbID);
            }



        }

        arrayAdapter = new Tab1Adapter(getActivity().getApplicationContext(), userHoldingsName, userHoldingsQTY, userHoldingsprice, userHoldingsURL, userHoldingsPERCENTAGE);
        userHoldingsList.setAdapter(arrayAdapter);

        userHoldingsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + name);
                Toast.makeText(getActivity(), name, Toast.LENGTH_SHORT).show();

                Cursor data = mDatabaseHelper.getItemID(name); //get the id associated with name
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);

                }
                if (itemID > -1) {

                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent abc = new Intent(getActivity(), TabbedCoinData.class);
                    abc.putExtra("id", itemID);
                    abc.putExtra("name", name);
                    abc.putExtra("coinID", userHoldingsID.get(userHoldingsName.indexOf(name)));
                    abc.putExtra("coinSymbol", userHoldingsSYMBOL.get(userHoldingsName.indexOf(name)));
                    abc.putExtra("coinTotal", Double.toString(userHoldingsprice.get(userHoldingsName.indexOf(name))));
                    abc.putExtra("coinQuantity", Double.toString(userHoldingsQTY.get(userHoldingsName.indexOf(name))));
                    startActivity(abc);
                } else {
                    Toast.makeText(getActivity(), "NO ID associated with that name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void getCurrentMarketPrices(String symbol) {
        ArrayList<String> price1 = new ArrayList<>();

        final int dbEntry = userHoldingsSYMBOL.indexOf(symbol);

        mQueue = Volley.newRequestQueue(getContext());

        String getpriceURL = "https://min-api.cryptocompare.com/data/price?fsym=" + symbol.toUpperCase().trim() + "&tsyms=GBP";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, getpriceURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String price = response.getString("GBP");
                    price1.add(price);


                    double totalprice = userHoldingsprice.get(dbEntry);
                    double qty1 = userHoldingsQTY.get(dbEntry);
                    double indivpricing = totalprice / qty1;
                    double dif = Double.parseDouble(price) - indivpricing;
                    double per = dif / indivpricing;
                    double newTotal = (Double.parseDouble(price) * qty1);
                    mDatabaseHelper.updatePrice(Double.toString(newTotal), dbEntry, Double.toString(totalprice));
                    String numberAsString = String.format("%.3f", per);
                    userHoldingsPERCENTAGE.set(dbEntry, numberAsString);
                    userHoldingsprice.set(dbEntry, newTotal);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        mQueue.add(request);

        arrayAdapter.notifyDataSetChanged();

    }

    private void updateTotalHoldings() {


        double currentpotfoliovalue = 0;
        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()) {
            //get the pricing of the coin
            currentpotfoliovalue += Double.parseDouble(data.getString(3));
        }
        String numberAsString = String.format("%.2f", currentpotfoliovalue);
        String symbol = "£";
        portfolioTotal.setText(symbol + " " + numberAsString);


    }


}
