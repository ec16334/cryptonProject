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

public class Tab2Adapter extends RecyclerView.Adapter<Tab2Adapter.ViewHolder> {
//recyclerview mainadapter


//    ArrayList<String> titles = new ArrayList<>();
//    ArrayList<String> body = new ArrayList<>();
//    ArrayList<String> newsUrl = new ArrayList<>();
//    ArrayList<String> time = new ArrayList<>();
//    ArrayList<String> site = new ArrayList<>();
//    ArrayList<String> img = new ArrayList<>();

    private ArrayList<NewsObject> newsStory = new ArrayList<>();

//    public Tab2Adapter(ArrayList<String> titles1, ArrayList<String> body1, ArrayList<String> news1, ArrayList<String> time1, ArrayList<String> site1, ArrayList<String> imgurl) {
//
//        titles = titles1;
//        body = body1;
//        newsUrl = news1;
//        time = time1;
//        site = site1;
//        img = imgurl;
//
//
//    }

    public Tab2Adapter(ArrayList<NewsObject> arrayList){
        newsStory = arrayList;
    }


    @Override
    public Tab2Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_news_customrow, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(Tab2Adapter.ViewHolder holder, final int position) {


//        holder.singleBody.setText(body.get(position));
        //this was removed
//        holder.singleTitle.setText(titles.get(position));
//        holder.singleSource.setText(site.get(position));
//        holder.singleTime.setText(time.get(position));
//        Picasso.get().load(img.get(position)).into(holder.singleImage);
        holder.singleTitle.setText(newsStory.get(position).getTitle());
        holder.singleSource.setText(newsStory.get(position).getSite());
        holder.singleTime.setText(newsStory.get(position).getTime());
        Picasso.get().load(newsStory.get(position).getIconURL()).into(holder.singleImage);


        final Tab2Adapter.ViewHolder h = holder;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent abc = new Intent(h.itemView.getContext(), NewsWebview.class);
//                abc.putExtra("url", newsUrl.get(position));
                abc.putExtra("url", newsStory.get(position).getNewsUrl());
                h.itemView.getContext().startActivity(abc);
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(h.itemView.getContext());
                alertDialog.setTitle(newsStory.get(position).getTitle()).setMessage(newsStory.get(position).getBody());
                alertDialog.create();
                alertDialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsStory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView singleTitle;
        public TextView singleSource;
        public TextView singleTime;
        public ImageView singleImage;


        public ViewHolder(View itemView) {
            super(itemView);

            singleTitle = itemView.findViewById(R.id.newsTitle);
            singleSource = itemView.findViewById(R.id.newSource);
            singleTime = itemView.findViewById(R.id.newsPublishedBy);
            singleImage = itemView.findViewById(R.id.newsimage);
        }
    }
}
