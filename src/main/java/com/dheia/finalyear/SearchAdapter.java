package com.dheia.finalyear;

/**
 * Created by Dheia on 25/02/2019.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> implements SearchView.OnQueryTextListener {


    private ArrayList<CoinItemChangelly> searchCoins = new ArrayList<>();


    public SearchAdapter(ArrayList<CoinItemChangelly> coins) {

        searchCoins = coins;
    }


    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchcustomrow, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, final int position) {

        holder.name.setText(searchCoins.get(position).getCoinName());
        holder.symbol.setText(searchCoins.get(position).getCoinSymbol());
        Picasso.get().load(searchCoins.get(position).getCoinImage()).into(holder.icon);
        final SearchAdapter.ViewHolder h = holder;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent abc = new Intent(h.itemView.getContext(), BuySellCoins.class);
                abc.putExtra("coinName", searchCoins.get(position).getCoinName());
                abc.putExtra("coinSYMBOL", searchCoins.get(position).getCoinSymbol());
                abc.putExtra("coinID", searchCoins.get(position).getCoinId());
                abc.putExtra("coinURL",searchCoins.get(position).getCoinImage());
                h.itemView.getContext().startActivity(abc);
            }
        });


    }

    @Override
    public int getItemCount() {
        return searchCoins.size();
    }

    //implementing searchbar for filtering recyclerview
    public void updateList(ArrayList<CoinItemChangelly> newlist) {

        searchCoins = new ArrayList<>();
        searchCoins.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView symbol;
        private ImageView icon;


        public ViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.searchCoinName);
            icon = itemView.findViewById(R.id.searchCoinImage);
            symbol = itemView.findViewById(R.id.searchCoinSymbol);
        }
    }
}
