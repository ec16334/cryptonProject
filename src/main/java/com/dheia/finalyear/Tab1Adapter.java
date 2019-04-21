package com.dheia.finalyear;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Dheia on 17/11/2018.
 */

class Tab1Adapter extends ArrayAdapter<String> {

    private ArrayList<Double> qty;
    private ArrayList<Double> prices1;
    private ArrayList<String> url1;
    private ArrayList<String> percent;



    public Tab1Adapter(@NonNull Context context, ArrayList<String> coins, ArrayList<Double> quantity, ArrayList<Double> prices, ArrayList<String> urls, ArrayList<String> percent1) {
        super(context, R.layout.tab1customrow, coins);
        qty = quantity;
        prices1 = prices;
        url1 = urls;
        percent = percent1;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.tab1customrow, parent, false);


        String singleName = getItem(position);
        String singleQty = qty.get(position).toString();
        String singlePrice = prices1.get(position).toString();
        String singleURL = url1.get(position).toLowerCase().toString();
        String singlePercent = percent.get(position);
        TextView coinName = customView.findViewById(R.id.portfolioCoinName);
        TextView coinQuantity = customView.findViewById(R.id.portfolioCoinQuantity);
        ImageView coinIcon = customView.findViewById(R.id.portfolioIcon);
        TextView coinPrice = customView.findViewById(R.id.coinPricing);
        TextView EachCoinPrice = customView.findViewById(R.id.eachCoinPriceTab1);
        TextView EachCoinPercentage = customView.findViewById(R.id.percentageDiffTab1);


        Context context = coinIcon.getContext(); //<----- Add this line


        coinName.setText(singleName);
        String coinNameNoSpaces = singleName.replaceAll("\\s", "");
        int resID = getResId(coinNameNoSpaces.toLowerCase(), R.drawable.class);
        //myImageList[position]

        Double indivprice = Double.parseDouble(singlePrice) / Double.parseDouble(singleQty);
        //coinIcon.setImageResource(resID);h
        Picasso.get().load(singleURL).into(coinIcon);
        coinQuantity.setText(singleQty);
        coinPrice.setText(singlePrice);
        EachCoinPrice.setText(Double.toString(indivprice));
        if(Double.parseDouble(singlePercent)>0){
            EachCoinPercentage.setTextColor(Color.parseColor("#008000"));
            EachCoinPercentage.setText("+"+singlePercent + "%");

        }
        else if(Double.parseDouble(singlePercent)<0){
            EachCoinPercentage.setTextColor(Color.parseColor("#FF0000"));
        }
        else {
            EachCoinPercentage.setText(singlePercent + "%");
        }





        return customView;


    }

    public void testerpricingmethod(String s){

    }

    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
