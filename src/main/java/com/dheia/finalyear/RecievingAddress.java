package com.dheia.finalyear;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.dheia.finalyear.util.MyJsonStringRequest;
import com.dheia.finalyear.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

public class RecievingAddress extends AppCompatActivity {

    EditText recieivingaddress;
    TextView amountsentTextView, amountRecievedTextView, exchangeRateTextView;
    Button confirmation;
    String address = "";
    String currencySymbolRecieving = "";
    String currencySymbolSending = "";
    String sendingAmount = "";
    String reciveingAmount = "";
    String scannedAddress = "";

    CheckBox checkbox;

    QRDatabaseHelper mDatabaseHelper;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recieving, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.scannerrr:
                Intent intent = new Intent(this, BarcodeScanner.class);
                intent.putExtra("coinSymbolRecieving", currencySymbolRecieving);
                intent.putExtra("coinSymbolSending", currencySymbolSending);
                intent.putExtra("sendingAmount", sendingAmount);
                intent.putExtra("recieveAmount", reciveingAmount);

               startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieving_address);
        setTitle("Recieve Address");

        recieivingaddress = findViewById(R.id.recievingaddressTextView);
        confirmation = findViewById(R.id.recievingaddressbuttonconfirmation);
        checkbox = findViewById(R.id.checkBoxRecievingCoin);
        checkbox.setVisibility(View.INVISIBLE);

        mDatabaseHelper = mDatabaseHelper = new QRDatabaseHelper(this);


        currencySymbolRecieving = getIntent().getStringExtra("coinSymbolRecieving");
        currencySymbolSending = getIntent().getStringExtra("coinSymbolSending");
        sendingAmount = getIntent().getStringExtra("sendingAmount");
        reciveingAmount = getIntent().getStringExtra("recieveAmount");

        Cursor stored = mDatabaseHelper.getQRData();
        String publicKeyStored="";

        while (stored.moveToNext()) {
            Log.i("CHECKIIIIIIIIIIIIIIING",stored.getString(3));
            Log.i("CHECKIIIIIIIIIIIIIIING1",currencySymbolRecieving);

            //get value from the database in col1 and 2
            if(stored.getString(3).equalsIgnoreCase(currencySymbolRecieving)){
                publicKeyStored = stored.getString(2);
                checkbox.setVisibility(View.VISIBLE);
                checkbox.setText("Would you like to use your "+currencySymbolRecieving+" address saved in your wallet to recieve your coins?");
                break;
            }

        }

        final String finalpublickeyStored = publicKeyStored;

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkbox.isChecked()) {
                    if (finalpublickeyStored.length() > 0) {
                        recieivingaddress.setText(finalpublickeyStored);

                    }
                }
                    else{
                        recieivingaddress.setText("");
                    }
                }

        });


        //String h = storedPublic.getString(0);

//        if(h.length()>0){
//            checkbox.setVisibility(View.VISIBLE);
//        }

        scannedAddress = getIntent().getStringExtra("scannedRecievingAddress");

        if (scannedAddress != null) {
            if (scannedAddress.length() > 0) {
                recieivingaddress.setText(scannedAddress);
            }
        }
        recieivingaddress.setHint(currencySymbolRecieving.toUpperCase() + " Wallet Address");

        confirmation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!(recieivingaddress.getText().equals(""))) {
                    address = recieivingaddress.getText().toString();
                    checkAddressValidity();
                }

            }
        });

    }

    public void checkAddressValidity() {

        JSONObject reqData = null;

        if (address.isEmpty()) {
            //Toast.makeText(this, "one or more fields are empty", Toast.LENGTH_SHORT).show();
            recieivingaddress.setError("Required");
            return;
        }

        try {
            reqData = new JSONObject("{\n" +
                    "  \"id\": \"test\",\n" +
                    "  \"jsonrpc\": \"2.0\",\n" +
                    "  \"method\": \"validateAddress\",\n" +
                    "  \"params\": {}\n" +
                    "}\n");
            JSONObject params = new JSONObject();
            params.put("currency", currencySymbolRecieving);
            params.put("address", address);
            reqData.put("params", params);
            String tag = reqData.getString("method");

            MyJsonStringRequest request = new MyJsonStringRequest(Request.Method.POST, Util.URL, reqData,
                    response -> {
                        Log.i(tag, response);
                        //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject innerobject = jsonObject.getJSONObject("result");
                            String result = innerobject.getString("result");
                            if (result.equals("true")) {
                                //create the transaction after confirmation of details. payment currencies and amount and transaction fee
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(RecievingAddress.this);
                                View mview = getLayoutInflater().inflate(R.layout.confirmationdialogue
                                        , null);
                                alertDialog.setTitle("Confirm Details");
                                amountsentTextView = mview.findViewById(R.id.dialogueAmountSending);
                                amountRecievedTextView = mview.findViewById(R.id.dialogueAmountRecieving);
                                exchangeRateTextView = mview.findViewById(R.id.dialogueRate);
                                amountsentTextView.setText(sendingAmount + " " + currencySymbolSending);
                                amountRecievedTextView.setText(reciveingAmount + " " + currencySymbolRecieving);
                                double exRate = Double.parseDouble(reciveingAmount) / Double.parseDouble(sendingAmount);
                                exchangeRateTextView.setText("1 " + currencySymbolSending.toUpperCase() + " = " + Double.toString(exRate) + " " + currencySymbolRecieving.toUpperCase());
                                alertDialog.setPositiveButton("MAKE PAYMENT", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        //create the transaction here
                                        createTransaction();


                                    }
                                });
                                alertDialog.setView(mview);
                                alertDialog.create();
                                alertDialog.show();


                            } else if (result.equals("false")) {
                                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(RecievingAddress.this);
                                alertDialog.setTitle("Invalid Address").setMessage("Please Enter a Valid " + currencySymbolRecieving.toUpperCase() + " Address To Proceed.");
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                                alertDialog.create();
                                alertDialog.show();
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

    public void createTransaction() {
        JSONObject reqData = null;

        if (currencySymbolRecieving.isEmpty() || currencySymbolSending.isEmpty() || address.isEmpty()) {
            //Toast.makeText(this, "one or more fields are empty", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            reqData = new JSONObject("{\n" +
                    "  \"id\": \"test\",\n" +
                    "  \"jsonrpc\": \"2.0\",\n" +
                    "  \"method\": \"createTransaction\",\n" +
                    "  \"params\": {}\n" +
                    "}\n");
            JSONObject params = new JSONObject();
            params.put("from", currencySymbolSending);
            params.put("to", currencySymbolRecieving);
            params.put("address", address.trim());
            params.put("amount", sendingAmount);
            reqData.put("params", params);
            String tag = reqData.getString("method");

            MyJsonStringRequest request = new MyJsonStringRequest(Request.Method.POST, Util.URL, reqData,
                    response -> {
                        Log.i(tag, response);
                        //Toast.makeText(this, response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject innerobject = jsonObject.getJSONObject("result");
                            String payinaddress = innerobject.getString("payinAddress");
                            String transactionID = innerobject.getString("id");


                            Intent intent = new Intent(this, SendingAddress.class);

                            intent.putExtra("payinaddress", payinaddress);
                            intent.putExtra("sendingCurrency", currencySymbolSending);
                            intent.putExtra("recievingCurrency", currencySymbolRecieving);
                            intent.putExtra("amountSending", sendingAmount);
                            intent.putExtra("amountRecieving", reciveingAmount);
                            intent.putExtra("transactionReference",transactionID);

                            startActivity(intent);
                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.i(tag, "" + error);
                        Toast.makeText(this, "The Exchange is having issues. Please try again later.", Toast.LENGTH_SHORT).show();
                    });
            App.getInstance().addToRequestQueue(request, tag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

