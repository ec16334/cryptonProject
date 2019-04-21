package com.dheia.finalyear;

/**
 * Created by Dheia on 16/02/2019.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class ChangellySpinnerAdapter extends ArrayAdapter<CoinItemChangelly> {


    public ChangellySpinnerAdapter(Context context, ArrayList<CoinItemChangelly> coinList) {
        super(context, 0, coinList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.changellyspinnerlayout, parent, false
            );
        }

        ImageView imageViewFlag = convertView.findViewById(R.id.spinnercoinImageChangelly);
        TextView textViewName = convertView.findViewById(R.id.spinnerCoinSymbol);
        TextView textViewSymbol = convertView.findViewById(R.id.spinnercoinName);

        CoinItemChangelly currentItem = getItem(position);

        if (currentItem != null) {

            Picasso.get().load(currentItem.getCoinImage()).into(imageViewFlag);
            textViewName.setText(currentItem.getCoinSymbol());
            textViewSymbol.setText(currentItem.getCoinName());
        }

        return convertView;
    }
}


