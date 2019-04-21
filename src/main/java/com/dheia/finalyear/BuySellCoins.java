package com.dheia.finalyear;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Vibrator;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Calendar;

public class BuySellCoins extends AppCompatActivity {

    private Button buysell;
    private Switch buyOrSell;
    private TextView dateTextView;
    private EditText priceEditText, quantityEditText, transactionTotal;
    private RequestQueue mQueue;
    private DatePickerDialog.OnDateSetListener mdatesetlistener;


    DatabaseHelper mDatabaseHelper;
    TransactionDatabaseHelper transactionDatabaseHelper;

    String coinSymbol = "";
    String coinName = "";
    String coinID = "";
    String coinURL = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_sell_coins);
        setTitle("Add Transaction");

        priceEditText = findViewById(R.id.buysellPriceCoin);
        quantityEditText = findViewById(R.id.buysellQuantityCoin);
        transactionTotal = findViewById(R.id.buysellTransactionValue);
        buysell = findViewById(R.id.buysellbutton);
        buyOrSell = findViewById(R.id.buysellswitch);
        buysell.setBackgroundColor(Color.parseColor("#008000"));
        dateTextView = findViewById(R.id.dateTextView);

        transactionTotal.setEnabled(false);
        coinSymbol = getIntent().getStringExtra("coinSYMBOL");
        coinName = getIntent().getStringExtra("coinName");
        coinID = getIntent().getStringExtra("coinID");
        coinURL = getIntent().getStringExtra("coinURL");

        getCoinPrice(coinSymbol);

        mDatabaseHelper = new DatabaseHelper(this);
        transactionDatabaseHelper = new TransactionDatabaseHelper(this);

        double quantityHeld = 0;
        double priceStored = 0;
        boolean held = false;

        Cursor dataSQL = mDatabaseHelper.getData();
        while (dataSQL.moveToNext()) {
            //check if coin is held already and avoid duplicates on portfolio listview
            if (dataSQL.getString(1).equals(coinName)) {
                quantityHeld = dataSQL.getDouble(2);
                priceStored = dataSQL.getDouble(3);
                Toast.makeText(this, Double.toString(quantityHeld) + " is QTYHELD", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, Double.toString(priceStored) + " is QTYHELD", Toast.LENGTH_SHORT).show();
                held = true;
            }
        }
        Toast.makeText(this, "Coin Name: " + coinName + " and quantity held is " + Double.toString(quantityHeld), Toast.LENGTH_SHORT).show();

        final double qtyHeld = quantityHeld;
        final boolean isHeld = held;
        final double priceCoin = priceStored;
        final double oldPrice = priceStored;

        dateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(BuySellCoins.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mdatesetlistener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        mdatesetlistener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month++;
                String date = day + "/" + month + "/" + year;
                dateTextView.setText(date);
            }
        };

        buyOrSell.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    buysell.setText("SELL");
                    buysell.setBackgroundColor(Color.parseColor("#ff0000"));
                } else {
                    buysell.setText("BUY");
                    buysell.setBackgroundColor(Color.parseColor("#008000"));
                }
            }
        });


        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!(editable.toString().isEmpty())) {
                    transactionTotal.setText(Double.toString((Double.parseDouble("0" + editable.toString())) * Double.parseDouble("0" + quantityEditText.getText().toString())));
                }
            }
        });

        quantityEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!(editable.toString().isEmpty())) {
                    transactionTotal.setText(Double.toString((Double.parseDouble("0" + priceEditText.getText().toString())) * Double.parseDouble("0" + editable.toString())));
                }

            }
        });

        try {
            buysell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Double coinPriceLive = Double.parseDouble(priceEditText.getText().toString());
                    //if button is selling
                    if (dateTextView.getText().toString().equals("") || priceEditText.getText().toString().equals("") || quantityEditText.getText().toString().equals("") || transactionTotal.getText().toString().equals("")) {
                        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                        Toast.makeText(BuySellCoins.this, "Please Enter Required Fields", Toast.LENGTH_SHORT).show();
                        v.vibrate(400);
                        if (dateTextView.getText().toString().equals("")) {
                            dateTextView.setError("");
                        }
                        if (priceEditText.getText().toString().equals("")) {
                            priceEditText.setError("");
                        }
                        if (quantityEditText.getText().toString().equals("")) {
                            quantityEditText.setError("");
                        }

                    } else {
                        if (buyOrSell.isChecked()) {
                            String amountString = quantityEditText.getText().toString();
                            if (!amountString.equals("")) {
                                double newamount = Double.parseDouble(amountString);
                                if (!isHeld) {
                                    Toast.makeText(BuySellCoins.this, "You do not own any " + coinName, Toast.LENGTH_SHORT).show();
                                    Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                                    v.vibrate(400);

                                } else if (isHeld) {
                                    if (newamount <= qtyHeld) {
                                        newamount = qtyHeld - newamount;
                                        Cursor data = mDatabaseHelper.getItemID(coinName);
                                        int itemId = -1;
                                        double newpriceheld = 999;
                                        while (data.moveToNext()) {
                                            itemId = data.getInt(0);
                                            newpriceheld = newamount * coinPriceLive;
                                        }
                                        if (itemId > -1) {
                                            Toast.makeText(BuySellCoins.this, "No Id with this coinname", Toast.LENGTH_SHORT).show();
                                        }
                                        if(newamount<qtyHeld) {
                                            mDatabaseHelper.updateQuantity(Double.toString(newamount), itemId, Double.toString(qtyHeld));
                                            mDatabaseHelper.updatePrice(Double.toString(newpriceheld), itemId, Double.toString(oldPrice));
                                            AddTransactionInfo(coinSymbol.toUpperCase(), coinName.toUpperCase(), amountString, priceEditText.getText().toString(), dateTextView.getText().toString(), "selling");
                                        }
                                        else if(newamount==qtyHeld){
                                            DeleteData(itemId,coinName);
                                        }


                                        Intent intent = new Intent(BuySellCoins.this, MainActivity.class);
                                        intent.putExtra("loggedin", true);
                                        startActivity(intent);

                                    } else {
                                        Toast.makeText(BuySellCoins.this, "You have " + Double
                                                .toString(qtyHeld) + " " + coinName + "." + "\nPlease Enter a Smaller Amount to Sell.", Toast.LENGTH_SHORT).show();

                                    }
                                }
                            } else {
                                Toast.makeText(BuySellCoins.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();

                            }
                        }
                        //if button is buying
                        else if (buyOrSell.isChecked() != true) {
                            String amountString = quantityEditText.getText().toString();
                            Log.d("updateName11", " " + amountString);

                            if (!amountString.equals("")) {
                                double newamount = Double.parseDouble(amountString);

                                if (!isHeld) {
                                    Log.d("currentPrice", " " + oldPrice);
                                    double totalNumberAdd = newamount * coinPriceLive;

                                    AddData(coinName, Double.toString(newamount), Double.toString(totalNumberAdd), coinID, coinURL, coinSymbol.trim());
                                    AddTransactionInfo(coinSymbol.toUpperCase(), coinName.toUpperCase(), amountString, priceEditText.getText().toString(), dateTextView.getText().toString(), "buying");

                                    //completed add successfully

                                    //adding if the coin is already in DB
                                } else if (isHeld) {
                                    newamount += qtyHeld;
                                    Cursor data = mDatabaseHelper.getItemID(coinName);
                                    int itemId = -1;
                                    while (data.moveToNext()) {
                                        itemId = data.getInt(0);
                                    }
                                    if (itemId > -1) {
                                        //no id with coinname
                                    }
                                    double c = coinPriceLive;
                                    double totalNumberUpdate = newamount * c;
                                    mDatabaseHelper.updateQuantity(Double.toString(newamount), itemId, Double.toString(qtyHeld));
                                    mDatabaseHelper.updatePrice(Double.toString(totalNumberUpdate), itemId, Double.toString(oldPrice));

                                    AddTransactionInfo(coinSymbol.toUpperCase(), coinName.toUpperCase(), amountString, priceEditText.getText().toString(), dateTextView.getText().toString(), "buying");

                                }

                                Intent intent = new Intent(BuySellCoins.this, MainActivity.class);
                                intent.putExtra("loggedin", true);
                                startActivity(intent);
                            } else {
                                Toast.makeText(BuySellCoins.this, "Please Enter Quantity", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }
            });
        } catch (Exception E) {

        }
    }

    private void getCoinPrice(String coinSymbol) {
        mQueue = Volley.newRequestQueue(getApplicationContext());
        String url = "https://min-api.cryptocompare.com/data/price?fsym=" + coinSymbol.trim().toUpperCase() + "&tsyms=GBP";
        Toast.makeText(BuySellCoins.this, "here1", Toast.LENGTH_SHORT).show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String price = response.getString("GBP");
                    DecimalFormat df = new DecimalFormat("#");
                    df.setMaximumFractionDigits(15);
                    Double priceD = Double.parseDouble(price);
                    String priceF = df.format(priceD);
                    priceEditText.setText(priceF);


                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, error -> Log.i("GetPrice", "not working"));
        mQueue.add(request);
    }


    private void AddData(String newEntry, String amount, String price, String id, String imageURL, String symbol) {
        boolean insertData = mDatabaseHelper.addData(newEntry, amount, price, id, imageURL, symbol);
        if (insertData) {
            //data inserted sucessfully
        } else {
            Toast.makeText(this, "Error Adding Data", Toast.LENGTH_SHORT).show();

        }
    }

    public void DeleteData(int id, String name){
        mDatabaseHelper.deleteName(id,name);

    }

    public void AddTransactionInfo(String symbol, String name, String quantity, String price, String date, String buyS) {
        boolean insertData = transactionDatabaseHelper.addData(symbol, name, quantity, price, date, buyS);

        if (insertData) {
            Toast.makeText(this, "Transaction Inserted Successfully!", Toast.LENGTH_SHORT).show();
            //transaction entered successfully
        } else {
            Toast.makeText(this, "Error Adding Transaction", Toast.LENGTH_SHORT).show();

        }
    }
}



