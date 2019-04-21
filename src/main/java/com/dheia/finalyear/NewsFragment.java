package com.dheia.finalyear;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

/**
 * Created by Dheia on 18/01/2019.
 */
public class NewsFragment extends Fragment {
    RecyclerView.LayoutManager mlayoutManager;
    RecyclerView.Adapter adapter;
    private RecyclerView recyclerView;

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> body = new ArrayList<>();
    private ArrayList<String> newsUrl = new ArrayList<>();
    private ArrayList<String> time = new ArrayList<>();
    private ArrayList<String> site = new ArrayList<>();
    private ArrayList<String> iconURL = new ArrayList<>();

    private ArrayList<NewsObject> newsObjects = new ArrayList<>();
    private ArrayList<NewsObject> filteredNewsObjects = new ArrayList<>();


    private ProgressBar progressBar;
    private RequestQueue mQueue;

    private String name = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.news_fragment, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerViewNewsFragment);

        name = TabbedCoinData.returncoinName();
        getNews();
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mlayoutManager = new LinearLayoutManager(getContext());
//        adapter = new Tab2Adapter(titles, body, newsUrl, time, site, iconURL);
        adapter = new Tab2Adapter(filteredNewsObjects);
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(adapter);

        getNews();

    }


    public void getNews() {
        mQueue = Volley.newRequestQueue(getContext());
        String url = "https://min-api.cryptocompare.com/data/v2/news/?lang=EN";

        TabbedCoinData activity = (TabbedCoinData) getActivity();
        String myDataFromActivity = activity.getMyCoinNameNews();
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

//                JSONObject dataObject = new JSONObject(response);
                JSONArray dataArray = null;
                int numberList = 100;

                try {
                    dataArray = response.getJSONArray("Data");

                    if (dataArray.length() < numberList)
                        numberList = dataArray.length();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < numberList; i++) {
                    JSONObject obj = null;
                    try {
                        obj = dataArray.getJSONObject(i);
                        String titleCheck = Html.fromHtml(dataArray.getJSONObject(i).getString("title")).toString();


                        String icon = dataArray.getJSONObject(i).getJSONObject("source_info").getString("img");
                        newsObjects.add(new NewsObject(Html.fromHtml(dataArray.getJSONObject(i).getString("title")).toString(), Html.fromHtml(dataArray.getJSONObject(i).getString("body")).toString(), dataArray.getJSONObject(i).getString("url"), dataArray.getJSONObject(i).getString("published_on"), dataArray.getJSONObject(i).getString("source").toUpperCase(), icon));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                int numb = 50;

                for (int j = 0; j < newsObjects.size(); j++) {
                    if(numb==0){
                        break;
                    }
                    if (newsObjects.get(j).getBody().toLowerCase().contains(name.toLowerCase())||newsObjects.get(j).getTitle().toLowerCase().contains(name.toLowerCase())) {
                        filteredNewsObjects.add(newsObjects.get(j));
                        Log.i("filterednewsobjects",Integer.toString(numb));
                        numb--;
                    }
                }

                if(filteredNewsObjects.size()<1){
                    Toast.makeText(activity, "There is no recent "+name.toLowerCase()+" news", Toast.LENGTH_LONG).show();
                }

                Log.i("newsfragment", "part2");


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Volley in newsfragment", "not working");

            }
        });
        mQueue.add(request);



    }


    public boolean titleChecker(String body, String coin) {

        if (body.contains(coin)) {
            return true;
        }
        return false;

    }


}
