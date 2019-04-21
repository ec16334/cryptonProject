package com.dheia.finalyear;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
public class AddKeys extends AppCompatActivity {

    EditText pubKey;
    EditText privKey;
    ImageButton qr1;
    ImageButton qr2;
    Button save;
    Spinner namesSpinner;

    String publickey;
    String privateKey;
    String selectedItem;


    private RequestQueue mQueue;

    ArrayList<RecyclerViewItem> coinSelection;

    QRDatabaseHelper mDatabaseHelper;

    ArrayAdapter<RecyclerViewItem> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Always call the superclass first
        setTitle("Wallet Entry");
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            publickey = savedInstanceState.getString("puk");
            privateKey = savedInstanceState.getString("prk");
        } else {
            // This is the case when you are openning this Activity for the for the first time
        }

        if(getIntent().getStringExtra("coinSelectedSpinner")!=null){
            Toast.makeText(this, getIntent().getStringExtra("coinSelectedSpinner"), Toast.LENGTH_SHORT).show();
        }
        setContentView(R.layout.activity_add_keys);

        selectedItem = getIntent().getStringExtra("coinSelectedSpinner");
        pubKey = findViewById(R.id.edittextPublicKey);
        //privKey = findViewById(R.id.EditTextPrivateKEy);
        qr1 = findViewById(R.id.qr1);
        //qr2 = findViewById(R.id.qr2);
        namesSpinner = findViewById(R.id.coinEntryNamesSpinner);
        save = findViewById(R.id.saveKeysButton);

        mDatabaseHelper = new QRDatabaseHelper(this);

        coinSelection = new ArrayList<>();

        adapter = new ArrayAdapter<RecyclerViewItem>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, coinSelection);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        namesSpinner.setAdapter(adapter);


        getAddKeyCoins();

        namesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });




        qr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Qrscanner.class);
                intent.putExtra("coinSelectedSpinner", namesSpinner.getSelectedItem().toString());
                intent.putExtra("box1", pubKey.getText().toString());
                intent.putExtra("key", false);
                startActivity(intent);
                finish();
            }
        });

        String a = "";

        a = getIntent().getStringExtra("publicKey");

        if (!(a == null)) {
            pubKey.setText(a);
        }


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String publickey1 = pubKey.getText().toString().trim();
                String coiname = namesSpinner.getSelectedItem().toString();
                String symbol = coinSelection.get(namesSpinner.getSelectedItemPosition()).getQRSymbol();
                String url = coinSelection.get(namesSpinner.getSelectedItemPosition()).getQRUrl();


                if (!(publickey1.equals(""))) {
                    boolean held = check(coiname);
                    if (!held) {
                        AddData(coiname, publickey1, symbol, url);
                        startActivity(new Intent(AddKeys.this,PublicKeys.class));
                        finish();

                    } else if (held) {
                        Toast.makeText(AddKeys.this, coiname+" is already in Wallet", Toast.LENGTH_SHORT).show();
                    }
                } else {

                    if (publickey1.equals("")) {
                        pubKey.setError("Required");
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                        v.vibrate(1500);

                    }
                }
            }
        });

    }

    private boolean check(String name) {
        Cursor dataSQL = mDatabaseHelper.getQRData();
        boolean trueFalse = false;
        while (dataSQL.moveToNext()) {
            //check if coin is held already and avoid duplicates on portfolio listview
            if (dataSQL.getString(1).equals(name)) {
                trueFalse = true;

            }
        }
        return trueFalse;
    }

    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }

        return 0;
    }

    private void AddData(String name, String publicK, String symbol, String url) {

        boolean insertData = mDatabaseHelper.addQRData(name, publicK, symbol.toUpperCase(), url);

        if (insertData) {
            Toast.makeText(this, "Coin Added!", Toast.LENGTH_SHORT).show();
        }
    }

    public void getAddKeyCoins() {
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
                            coinSelection.add(new RecyclerViewItem(coinName, "https://www.cryptocompare.com/" + coinImage, coinSymbol));
                            i++;

                        }

                    }
                    namesSpinner.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    namesSpinner.setSelection(getIndex(namesSpinner,selectedItem));

                } catch (JSONException e) {
                    Log.i("PRINE", e.toString());

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("ERROR: ", "not working");

            }
        });
        mQueue.add(request);
        adapter.notifyDataSetChanged();


    }

    public RecyclerViewItem findIndex(String coinName) {
        for (RecyclerViewItem item : coinSelection) {
            if (getIntent().getStringExtra("coinSelectedSpinner").equals(item.getQRname())) {
                return item;
            }

        }
        return null;
    }
}



