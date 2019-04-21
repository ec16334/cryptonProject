package com.dheia.finalyear;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Dheia on 05/04/2019.
 */

public class TransactionFragment extends Fragment {
    RecyclerView.LayoutManager mlayoutManager;
    RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;


    String name;

    TransactionDatabaseHelper transactionDatabaseHelper;

    ArrayList<TransactionObject> tList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.transaction_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerViewTransactionsFragment);

        name = TabbedCoinData.returncoinName();

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mlayoutManager = new LinearLayoutManager(getContext());
        transactionDatabaseHelper = new TransactionDatabaseHelper(getActivity());

        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(adapter);


        mlayoutManager = new LinearLayoutManager(getContext());
        adapter = new TransactionAdapter(tList);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(adapter);

        if (tList.isEmpty()) {

            int i = 0;
            Cursor data = transactionDatabaseHelper.getData();
            try {
                while (data.moveToNext()) {


                    String cName = TabbedCoinData.returncoinName();
                    if (cName.toLowerCase().equals(data.getString(2).toLowerCase())) {
                        Double total = Double.parseDouble(data.getString(3)) * Double.parseDouble(data.getString(4));
                        String sTotal = Double.toString(total);
                        tList.add(new TransactionObject(data.getString(2), data.getString(4), data.getString(3), sTotal, data.getString(5), data.getString(6)));
                    }


                }

                adapter.notifyDataSetChanged();
            } catch (Exception e) {
            }

        }

    }

}
