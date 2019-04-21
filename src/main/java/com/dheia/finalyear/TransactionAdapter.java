package com.dheia.finalyear;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.ViewHolder> {


    private ArrayList<TransactionObject> transctionList = new ArrayList<>();
    private ArrayList<Integer> locations = new ArrayList<>();

    @Override
    public TransactionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_customrow, parent, false);

        return new ViewHolder(view);
    }

    public TransactionAdapter(ArrayList<TransactionObject> arrayList){
        transctionList = arrayList;
    }


    @Override
    public void onBindViewHolder(TransactionAdapter.ViewHolder holder, final int position) {

        //check if its selling or buying
        if(transctionList.get(position).getBuyorsell().equals("buying")){
            holder.singleTransactionHeader.setText("Buy Price");
        }
        else if(transctionList.get(position).getBuyorsell().equals("selling")){
            holder.singleTransactionHeader.setText("Sell Price");
        }

//        String coinname = TabbedCoinData.returncoinName();
//        if(coinname.toLowerCase().equals(transctionList.get(position).getName().toLowerCase())) {
            //only display transactions relating to the specific coin
            holder.singleTransactionPrice.setText(transctionList.get(position).getPrice());
            holder.singleTransactionQTY.setText(transctionList.get(position).getQuantity());
            holder.singleTransactionTotal.setText(transctionList.get(position).getTotal());
            holder.singleTransactionDate.setText(transctionList.get(position).getDate());
//        }


        final TransactionAdapter.ViewHolder h = holder;

    }

    @Override
    public int getItemCount() {
        return transctionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView singleTransactionPrice;
        public TextView singleTransactionQTY;
        public TextView singleTransactionTotal;
        public TextView singleTransactionDate;
        public TextView singleTransactionHeader;


        public ViewHolder(View itemView) {
            super(itemView);

            singleTransactionHeader = itemView.findViewById(R.id.transactionHeader);
            singleTransactionPrice = itemView.findViewById(R.id.transactionPrice);
            singleTransactionQTY = itemView.findViewById(R.id.transactionQTY);
            singleTransactionTotal = itemView.findViewById(R.id.transactionTotal);
            singleTransactionDate = itemView.findViewById(R.id.transactionDate);
        }
    }
}
