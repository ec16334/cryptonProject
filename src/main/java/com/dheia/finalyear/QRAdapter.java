package com.dheia.finalyear;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Dheia on 09/01/2019.
 */

public class QRAdapter extends RecyclerView.Adapter<QRAdapter.ViewHolder> {
//recyclerview mainadapter


    ArrayList<String> names = new ArrayList<>();


    public QRAdapter(ArrayList<String> names1) {

        names = names1;
    }


    @Override
    public QRAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qrcustomrow, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QRAdapter.ViewHolder holder, final int position) {

        holder.singleName.setText(names.get(position));


        final QRAdapter.ViewHolder h = holder;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc = new Intent(h.itemView.getContext(), PublicPrivateKeyActivity.class);
                abc.putExtra("coinName", names.get(position));
                h.itemView.getContext().startActivity(abc);
            }
        });


    }

    @Override
    public int getItemCount() {
        return names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView singleName;




        public ViewHolder(View itemView) {
            super(itemView);

            singleName = itemView.findViewById(R.id.qrlisttextview);

        }
    }
}
