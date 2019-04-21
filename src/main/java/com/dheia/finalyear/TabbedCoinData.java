package com.dheia.finalyear;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class TabbedCoinData extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TextView titleName;
    private TextView price, marketcap, circulatingsupply, totalsupply, pricechange1h, pricechange24h, pricechange7d, volume24h;
    private static String coin, coinID, coinSymbol, coinTotal, coinQuantity;
    private RequestQueue mQueue;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public static String returncoinName() {
        return coin;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_coin_data);


        Toolbar toolbar = findViewById(R.id.toolbar);
        titleName = findViewById(R.id.toolbar_title);
        coin = getIntent().getStringExtra("name");
        coinID = getIntent().getStringExtra("coinID");
        coinSymbol = getIntent().getStringExtra("coinSymbol");
        coinTotal = getIntent().getStringExtra("coinTotal");
        coinQuantity = getIntent().getStringExtra("coinQuantity");

        titleName.setText(coin);
        setSupportActionBar(toolbar);

        if (coinID != null) {
            Bundle bundle = new Bundle();
            bundle.putString("coinID", coinID);
            bundle.putString("coinname", coin);
            GraphFragment fragobj = new GraphFragment();
            fragobj.setArguments(bundle);
        }

        if (coin != null) {
            NewsFragment fragInfo = new NewsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("coinFullName", coin);
            fragInfo.setArguments(bundle);
            getFragmentManager().beginTransaction();


        }
        price = findViewById(R.id.coin_priceCoinData);
        marketcap = findViewById(R.id.market_cap_coindata);
        circulatingsupply = findViewById(R.id.circsupply_coindata);
        //this is the holding
        pricechange1h = findViewById(R.id.chg_1h_coindata);
        pricechange24h = findViewById(R.id.chg_24h_coindata);
        //this is the total value of holding
        pricechange7d = findViewById(R.id.chg_7d_coindata);
        volume24h = findViewById(R.id.volume_coindata);

        loadCoinDataNEW();

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabs);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));


    }

    public String getMyCoinNameNews() {
        return coin;
    }

    public static String getMyCoinID(){return coinID;}

    public static String getMyCoinSymbol(){ return coinSymbol;}


    //NEW METHOD
    public void loadCoinDataNEW() {

        mQueue = Volley.newRequestQueue(this);
        String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=" + coinSymbol.toUpperCase().trim() + "&tsyms=GBP";
        Toast.makeText(this, coinSymbol, Toast.LENGTH_SHORT).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                JSONObject obj = null;
                JSONObject inner2 = null;
                try {
                    Log.i("JJJJJJJ","STARTS");

                    obj = response.getJSONObject("RAW");
                    JSONObject inner1 = obj.getJSONObject(coinSymbol.trim().toUpperCase());
                    inner2 = inner1.getJSONObject("GBP");
                    Log.i("JJJJJJJ",inner1.toString());
                    Log.i("JJJJJJJ",inner2.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                String name = null;
                String volume = null;
                String market_cap = null;
                String current_price = null;
                String circulating_supply = null;
                String total_supply = null;
                String price_change_percentage_1h_in_currency = null;
                String price_change_percentage_24h_in_currency = null;
                String price_change_percentage_7d_in_currency = null;

                try {
                    Log.i("JJJJJJJ","STARTS1");

                    //name = inner2.getString("id");
                    market_cap = inner2.getString("MKTCAP");
                    current_price = inner2.getString("PRICE");
                    circulating_supply = inner2.getString("SUPPLY");
                    //total_supply = inner2.getString("total_supply");
                    //price_change_percentage_1h_in_currency = inner2.getString("price_change_percentage_1h_in_currency");
                    price_change_percentage_24h_in_currency = inner2.getString("CHANGEPCT24HOUR");
                    //price_change_percentage_7d_in_currency = inner2.getString("price_change_percentage_7d_in_currency");
                    volume = inner2.getString("TOTALVOLUME24HTO");

                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(15);
                    DecimalFormat dm = new DecimalFormat("#");
                    dm.setMaximumFractionDigits(0);
                    DecimalFormat d24chnage = new DecimalFormat("#");
                    d24chnage.setMaximumFractionDigits(3);
//                        System.out.println(df.format(num));

                    if(Double.parseDouble(price_change_percentage_24h_in_currency)>0){
                        pricechange24h.setTextColor(Color.parseColor("#008000"));
                        String temprry = "+"+d24chnage.format(Double.parseDouble(price_change_percentage_24h_in_currency));
                        pricechange24h.setText(temprry);

                    }
                    else if(Double.parseDouble(price_change_percentage_24h_in_currency)<0){
                        pricechange24h.setTextColor(Color.parseColor("#FF0000"));
                        pricechange24h.setText(d24chnage.format(Double.parseDouble(price_change_percentage_24h_in_currency)));


                    }
                    else{
                        pricechange24h.setText(d24chnage.format(Double.parseDouble(price_change_percentage_24h_in_currency)));

                    }

                    marketcap.setText(dm.format(Double.parseDouble(market_cap)));
                    price.setText(df.format(Double.parseDouble(current_price)));
                    circulatingsupply.setText(dm.format(Double.parseDouble(circulating_supply)));
                   //pricechange1h.setText(df.format(Double.parseDouble(price_change_percentage_1h_in_currency)));
                   pricechange1h.setText(coinQuantity);
                   pricechange7d.setText(coinTotal);

                    //pricechange7d.setText(df.format(Double.parseDouble(price_change_percentage_7d_in_currency)));
                    volume24h.setText(dm.format(Double.parseDouble(volume)));



                } catch (JSONException e) {
                    e.printStackTrace();
                }


        }
    },new Response.ErrorListener()

    {
        @Override
        public void onErrorResponse (VolleyError error){
        Log.i("Volley in Tab4", "not working");

    }
    });
        mQueue.add(request);


}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tabbed_coin_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/**
 * A placeholder fragment containing a simple view.
 */
public static class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tabbed_coin_data, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        return rootView;
    }
}

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        //returning the current tabs
        switch (position) {
            case 0:
                GraphFragment t1 = new GraphFragment();
                return t1;
            case 1:
                NewsFragment t2 = new NewsFragment();
                return t2;
            case 2:
                TransactionFragment t3 = new TransactionFragment();
                return t3;

            default:
                throw new IllegalArgumentException("unexpected position: " + position);
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 3;
    }
}
}
