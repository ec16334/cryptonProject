package com.dheia.finalyear;

import android.app.ActionBar;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dheia.finalyear.util.MyJsonStringRequest;
import com.dheia.finalyear.util.Util;

import java.util.ArrayList;
import java.util.Arrays;

public class ChangellyExchange extends AppCompatActivity {

    private ChangellySpinnerAdapter mAdapter;

    private Button exchangeButton;

    private String depCurrency = "";
    private String recCurrency = "";
    private String minimum = "";
    private String depositAmount = "";
    EditText deposit;
    EditText recieve;

    Spinner spinner1, spinner2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changelly_exchange);
        setTitle("Crypton");
        //setTitle("CryptoCurrency Exchange");

//


        JSONObject reqData = null;
        ArrayList<CoinItemChangelly> mCoinItemListChangelly = new ArrayList<>();
        spinner1 = findViewById(R.id.changellySpinner1);
        spinner2 = findViewById(R.id.changellySpinner2);
        deposit = findViewById(R.id.depositQuantityExchange);
        recieve = findViewById(R.id.recieveQuantityExchange);

        exchangeButton = findViewById(R.id.changellyChangeButton);

        String[] coinsworking = new String[]{"btc", "eth", "etc", "xem", "lsk", "zec", "strat", "ardr", "rep", "maid", "ltc", "xrp", "doge", "nxt", "dash", "nav", "gnt", "waves", "swt", "dgd", "trst", "edg", "wings", "rlc", "gno", "dcr", "gup", "lun", "xlm", "bat", "ant", "bnt", "eos", "pay", "neo", "omg", "mco", "adx", "zrx", "qtum", "ptoy", "fun", "hmq", "salt", "xvg", "btg", "dgb", "dnt", "vib", "rcn", "trx", "ppt", "stx", "kmd", "wax", "brd", "dcn", "ngc", "noah", "bnb", "zen", "ae", "wtc", "arn", "zap", "abyss", "lrc", "xzc", "smart", "bqx", "nexo", "ont", "betr", "enj", "bcd", "tusd", "mith", "tel", "dai", "mkr", "r", "ada", "proc", "gusd", "usdc", "ht", "grs", "eurs", "bchabc", "bchsv", "dgtx", "pax", "storm", "mana", "nim", "pma", "xtz", "btt", "gas", "fet"};
        ArrayList<String> coinsWhichAreWorking = new ArrayList<>(Arrays.asList(coinsworking));


        String tag = "getCurrenciesFull";
        try {
            reqData = new JSONObject("{\"jsonrpc\": \"2.0\", \"id\": 1, \"method\": \"getCurrenciesFull\", \"params\": []}");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyJsonStringRequest request = new MyJsonStringRequest(Request.Method.POST, Util.URL, reqData,
                (String response) -> {
                    Log.i(tag, response);
                    JSONObject eachElement = null;
                    JSONObject dataObject = null;
                    JSONArray dataArray = null;
                    try {
                        dataObject = new JSONObject(response);
                        dataArray = dataObject.getJSONArray("result");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    for (int i = 0; i < dataArray.length(); i++) {
                        try {
                            eachElement = dataArray.getJSONObject(i);
                            //
                            //  if(coinsWhichAreWorking.contains(eachElement.getString("name").toLowerCase())) {
                            mCoinItemListChangelly.add(new CoinItemChangelly(eachElement.getString("name").toUpperCase(), eachElement.getString("image"), eachElement.getString("fullName"), null));
                            // }
                            Log.d("Names", mCoinItemListChangelly.get(i).getCoinName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    ArrayList<CoinItemChangelly> filteredList = new ArrayList<>();
                    for (int i = 0; i < mCoinItemListChangelly.size(); i++) {
                        if (coinsWhichAreWorking.contains(mCoinItemListChangelly.get(i).getCoinSymbol().toLowerCase())) {
                            filteredList.add(mCoinItemListChangelly.get(i));
                        }
                    }
                    mAdapter = new ChangellySpinnerAdapter(this, filteredList);
                    spinner1.setAdapter(mAdapter);
                    spinner2.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();

                    spinner2.setSelection(11);


                },
                error -> {
                    Log.i(tag, "" + error);
                });


        App.getInstance().addToRequestQueue(request);



        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                //Toast.makeText(ChangellyExchange.this, adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                depCurrency = adapterView.getSelectedItem().toString();
                deposit.setText("");
                recieve.setText("");
                getMinimum();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ChangellyExchange.this, parent.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                recCurrency = parent.getSelectedItem().toString();
                deposit.setText("");
                recieve.setText("");
                getMinimum();


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        exchangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (depCurrency.equals(recCurrency)) {
                    Toast.makeText(ChangellyExchange.this, "Please Select Different Currencies to Exchange", Toast.LENGTH_LONG).show();
                }
                if (!(deposit.getText().toString().isEmpty() || recieve.getText().toString().isEmpty())) {

                    if (Double.parseDouble(deposit.getText().toString()) < Double.parseDouble(minimum)) {

                        Toast.makeText(ChangellyExchange.this, "Below Min", Toast.LENGTH_SHORT).show();
                        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(ChangellyExchange.this);
                        alertDialog.setTitle("Amount Too Small").setMessage("Minimum amount of " + depCurrency.toUpperCase() + " you can exchange is " + minimum.toString());
                        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.setPositiveButton("Set Min", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deposit.setText(minimum);
                            }
                        });
                        alertDialog.create();
                        alertDialog.show();

                    } else {
                        Intent intent = new Intent(getApplicationContext(), RecievingAddress.class);

                        intent.putExtra("coinSymbolRecieving", recCurrency);
                        intent.putExtra("coinSymbolSending", depCurrency);
                        intent.putExtra("sendingAmount", deposit.getText().toString());
                        intent.putExtra("recieveAmount", recieve.getText().toString());

                        startActivity(intent);
                    }
                } else {
                    deposit.setError("Required");
                    Toast.makeText(getApplicationContext(), "Please Select Different Coins", Toast.LENGTH_SHORT).show();
                    // Get instance of Vibrator from current Context
                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                    // Vibrate for 400 milliseconds
                    v.vibrate(1000);
                }

            }
        });


        recieve.setEnabled(false);

        deposit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getExchangeAmount(editable.toString());

            }
        });

    }

    public void getMinimum() {

        if (spinner1.getSelectedItem().equals(spinner2.getSelectedItem())) {
            Toast.makeText(this, "Please Select Different Coins", Toast.LENGTH_SHORT).show();
            // Get instance of Vibrator from current Context
            Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

            // Vibrate for 400 milliseconds
            v.vibrate(1000);
            return;
        }
        if (!(recCurrency.equals("") || depCurrency.equals(""))) {
            JSONObject reqData = null;


            if (depCurrency.isEmpty() || recCurrency.isEmpty()) {
                //Toast.makeText(this, "one or more fields are empty", Toast.LENGTH_SHORT).show();
                //Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // Vibrate for 400 milliseconds
                //v.vibrate(1000);
                return;
            }

            try {
                reqData = new JSONObject("{\n" +
                        "  \"id\": \"test\",\n" +
                        "  \"jsonrpc\": \"2.0\",\n" +
                        "  \"method\": \"getMinAmount\",\n" +
                        "  \"params\": {\n" +
//                    "  \t\"from\": \"btc\", \n" +
//                    "  \t\"to\": \"eth\"\n" +
                        "  }\n" +
                        "}\n");
                JSONObject params = new JSONObject();
                params.put("from", depCurrency);
                params.put("to", recCurrency);
                reqData.put("params", params);
                String tag = reqData.getString("method");

                MyJsonStringRequest request = new MyJsonStringRequest(Request.Method.POST, Util.URL, reqData,
                        response -> {
                            Log.i(tag, response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.has("result")) {
                                    minimum = jsonObject.getString("result");
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        },
                        error -> {
                            Log.i(tag, "" + error);
                        });
                App.getInstance().addToRequestQueue(request, tag);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }


    public void getExchangeAmount(String a) {

        JSONObject reqData = null;


        if (recCurrency.isEmpty() || depCurrency.isEmpty() || a.isEmpty()) {
            Toast.makeText(this, "one or more fields are empty", Toast.LENGTH_SHORT).show();
            recieve.setText("");
            return;
        }

        try {
            reqData = new JSONObject("{\n" +
                    "  \"id\": \"test\",\n" +
                    "  \"jsonrpc\": \"2.0\",\n" +
                    "  \"method\": \"getExchangeAmount\",\n" +
                    "  \"params\": {}\n" +
                    "}\n");
            JSONObject params = new JSONObject();
            params.put("from", depCurrency);
            params.put("to", recCurrency);
            params.put("amount", a);
            reqData.put("params", params);
            String tag = reqData.getString("method");

            MyJsonStringRequest request = new MyJsonStringRequest(Request.Method.POST, Util.URL, reqData,
                    response -> {
                        Log.i(tag, response);
                        //Toast.makeText(this, response, Toast.LENGTH_LONG).show();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("result")) {
                                String rate = jsonObject.getString("result");
                                recieve.setText(rate);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    },
                    error -> {
                        Log.i(tag, "" + error);
                    });
            App.getInstance().addToRequestQueue(request, tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
