package com.dheia.finalyear;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class PublicKeys extends AppCompatActivity {

    ImageButton add;
    Button delete;

    RecyclerView.LayoutManager mlayoutManager;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;

    QRDatabaseHelper mDatabaseHelper;

    private RecyclerView gridView;
    private GridViewAdapter gridViewAdapter;
    private ArrayList<WalletCoin> walletStoredCoins;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_keys);

        setTitle("Wallet");

        add = findViewById(R.id.addPublicKey);
        gridView = findViewById(R.id.grid);
        mDatabaseHelper = new QRDatabaseHelper(this);
        setData();
        gridView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        gridView.setLayoutManager(layoutManager);
        gridViewAdapter = new GridViewAdapter(this, walletStoredCoins);
        gridView.setAdapter(gridViewAdapter);


        //when add button is clicked
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddKeys.class);
                startActivity(intent);
                finish();
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        gridViewAdapter.notifyDataSetChanged();
    }

    private void setData() {

        walletStoredCoins = new ArrayList<>();


        Cursor data = mDatabaseHelper.getQRData();
        try {
            while (data.moveToNext()) {
                //get value from the database in col1 and 2
                walletStoredCoins.add(new WalletCoin(data.getString(1), data.getString(4), data.getString(3), data.getString(2)));

            }
            gridViewAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("Populate1", e.getStackTrace().toString());
        }
//
    }
}
