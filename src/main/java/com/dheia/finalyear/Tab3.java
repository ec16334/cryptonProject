package com.dheia.finalyear;

import android.app.ActionBar;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dheia on 11/11/2018.
 */

public class Tab3 extends Fragment {


    Spinner lock, currencySelection;
    ImageView changellybutton, cryptosafe;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab3, container, false);


//        lock = rootView.findViewById(R.id.lockSpinner);
//        currencySelection = rootView.findViewById(R.id.currencySpinner);
        changellybutton = rootView.findViewById(R.id.changellybutton);
        cryptosafe = rootView.findViewById(R.id.cryptosafebutton);


        return rootView;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        changellybutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChangellyExchange.class));

            }
        });


        cryptosafe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PublicKeys.class);
                startActivity(intent);
            }
        });

//        lock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

//        currencySelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
////                SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                String chosen = currencySelection.getSelectedItem().toString();
////                chosen = chosen.toLowerCase();
////                String q = wmbPreference.getString("currencyPreference", chosen);
//
//                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
//                SharedPreferences.Editor editor = prefs.edit();
//                editor.putString("currencyPreference", chosen);
//                editor.commit();
//
//                Toast.makeText(getActivity(), chosen, Toast.LENGTH_SHORT).show();
////                Toast.makeText(getActivity(), q, Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });


    }

}
