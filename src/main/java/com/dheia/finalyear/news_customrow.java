package com.dheia.finalyear;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

class news_customrow extends ArrayAdapter<String> {

    ArrayList<String> body = new ArrayList<>();
    ArrayList<String> source = new ArrayList<>();
    ArrayList<String> published = new ArrayList<>();
    ArrayList<String> img = new ArrayList<>();



    public news_customrow(@NonNull Context context, ArrayList<String> titles, ArrayList<String> body, ArrayList<String> source, ArrayList<String> published, ArrayList<String> img1) {
        super(context, R.layout.activity_news_customrow,titles);
        this.body=body;
        this.source = source;
        this.published=published;
        img=img1;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.activity_news_customrow, parent, false);

        String singleTitle = getItem(position);
        String singleBody = body.get(position);
        String singleSource = source.get(position);
        String singlePublished= published.get(position);


        TextView titleTextView = customView.findViewById(R.id.newsTitle);
        TextView sourceTextView = customView.findViewById(R.id.newSource);
        TextView publishedTextView = customView.findViewById(R.id.newsPublishedBy);

        titleTextView.setText(singleTitle);
        sourceTextView.setText(singleSource);
        publishedTextView.setText(singlePublished);


        return customView;

    }
}
