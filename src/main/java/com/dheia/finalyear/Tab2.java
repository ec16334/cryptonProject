package com.dheia.finalyear;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Tab2 extends Fragment {


    private ArrayList<NewsObject> newsArticles = new ArrayList<>();

    private RequestQueue mQueue;

    ProgressBar progressBar;

    private SwipeRefreshLayout mSwipeRefreshLayout;


    NewsDatabaseHelper mDatabaseHelper;

    RecyclerView.LayoutManager mlayoutManager;
    RecyclerView.Adapter adapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.tab2, container, false);
        
        recyclerView = rootView.findViewById(R.id.newsListView);

        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(getActivity());
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        //populateListView1();
        mDatabaseHelper = new NewsDatabaseHelper(getActivity());


        mSwipeRefreshLayout = getActivity().findViewById(R.id.swipeviewcontainer);
        progressBar = getActivity().findViewById(R.id.progressBar);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                topRefreshNews();
            }
        });


        mlayoutManager = new LinearLayoutManager(getContext());
        adapter = new Tab2Adapter(newsArticles);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(adapter);
        getNews();
        if (!isFirstRun) {
            Cursor data = mDatabaseHelper.getData();
            try {
                while (data.moveToNext()) {

                    newsArticles.add(new NewsObject(data.getString(2),data.getString(3),data.getString(4),Integer.toString(data.getInt(5)),data.getString(1),data.getString(6)));


                    }
            } catch (Exception e) {
                Log.d("Populate1", e.getStackTrace().toString());
            }
        }
        if (isFirstRun) {

            getNews();
            SharedPreferences.Editor editor = wmbPreference.edit();
            editor.putBoolean("FIRSTRUN", false);
            editor.commit();
        }
    }

    public void topRefreshNews() {
        getNews();
        mSwipeRefreshLayout.setRefreshing(false);
    }


        public void getNews() {
        progressBar.setVisibility(View.VISIBLE);

        progressBar.bringToFront();
            mQueue = Volley.newRequestQueue(getContext());
            String url = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

//                JSONObject dataObject = new JSONObject(response);
                    JSONArray dataArray = null;
                    int numberList = 50;

                    try {
                        dataArray = response.getJSONArray("Data");

                        Toast.makeText(getActivity(), Integer.toString(dataArray.length()), Toast.LENGTH_SHORT).show();
                        if (dataArray.length() <= numberList)
                            numberList = dataArray.length();


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    for (int i = 0; i < numberList; i++) {
                        JSONObject obj = null;
                        try {
                            obj = dataArray.getJSONObject(i);

                            String icon = dataArray.getJSONObject(i).getJSONObject("source_info").getString("img");

                            newsArticles.add(new NewsObject(Html.fromHtml(dataArray.getJSONObject(i).getString("title")).toString(),Html.fromHtml(dataArray.getJSONObject(i).getString("body")).toString(),dataArray.getJSONObject(i).getString("url"),dataArray.getJSONObject(i).getString("published_on"),dataArray.getJSONObject(i).getString("source").toUpperCase(),icon));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.i("Volley in newsfragment", "not working");

                }
            });
            mQueue.add(request);
            //progressBar.setVisibility(View.GONE);
            //populateRecycleView();



        }


    public void populateRecycleView(){

        for (int i = 0; i < newsArticles.size(); i++) {
//            AddData(site.get(i), titles.get(i), body.get(i), newsUrl.get(i), Integer.parseInt(time.get(i)));

            //this was removed as well
            //AddData(site.get(i), titles.get(i), body.get(i), newsUrl.get(i), Integer.parseInt(time.get(i)),imgurl.get(i));
            AddData(newsArticles.get(i).getSite(),newsArticles.get(i).getTitle(),newsArticles.get(i).getBody(),newsArticles.get(i).getNewsUrl(),Integer.parseInt(newsArticles.get(i).getTime()),newsArticles.get(i).getIconURL());


        }

    }


    public void AddData(String sitename, String title, String body, String url, int date, String iconUrl) {
        boolean insertData = mDatabaseHelper.addData(sitename, title, body, url, date,iconUrl);
        if (insertData) {
            //toast it works
            Toast.makeText(getActivity().getApplicationContext(), "adddata works", Toast.LENGTH_SHORT).show();
        }
    }
}


