package com.dheia.finalyear;


import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

public class GraphFragment extends Fragment {
//mpandroidchart DEMO LINECHART

    private static final String TAG = "MainActivity";

    private LineChart mChart;

    private RequestQueue mQueue;

    ArrayList<Entry> yValues;
    ArrayList<String> xDATES;
    Button oneyear, sixmonths, onemonth, oneweek, oneday, onehour;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_graph_fragment, container, false);

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        mChart = (LineChart) getView().findViewById(R.id.linechart);
        oneday = getView().findViewById(R.id.onedaygraphbutton);
        onehour = getView().findViewById(R.id.onehourgraphbutton);
        oneweek = getView().findViewById(R.id.oneWeekGraphButton);
        onemonth = getView().findViewById(R.id.oneMGraphButton);
        sixmonths = getView().findViewById(R.id.sixMGraphButton);
        oneyear = getView().findViewById(R.id.oneYRGraphButton);

        mChart.setDragEnabled(true);
        mChart.setScaleEnabled(true);
        mChart.getLegend().setEnabled(false);


        //when graph first opened
        yValues = new ArrayList<>();
        xDATES = new ArrayList<>();

        yValues.clear();
        xDATES.clear();
        mChart.invalidate();
        mChart.clear();

        getDayvalues("25");


        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        // your code here
                        Log.i("here", "6");
                        LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                        set1.setFillAlpha(110);

                        set1.setColor(Color.RED);
                        set1.setLineWidth(3f);

                        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                        dataSets.add(set1);

                        LineData data = new LineData(dataSets);

                        mChart.setData(data);

                        String[] values = xDATES.toArray(new String[xDATES.size()]);

                        XAxis xAxis = mChart.getXAxis();
                        xAxis.setValueFormatter((new MyXAxisValueFormatter(values)));
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                        mChart.getDescription().setEnabled(false);

                        Log.i("here", "7");
                        mChart.notifyDataSetChanged();
                        mChart.invalidate();
                    }
                },
                500
        );


        oneday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yValues = new ArrayList<>();
                xDATES = new ArrayList<>();

                yValues.clear();
                xDATES.clear();
                mChart.invalidate();
                mChart.clear();

                getDayvalues("25");


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                Log.i("here", "6");
                                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                                set1.setFillAlpha(110);

                                set1.setColor(Color.RED);
                                set1.setLineWidth(3f);

                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(set1);

                                LineData data = new LineData(dataSets);

                                mChart.setData(data);

                                String[] values = xDATES.toArray(new String[xDATES.size()]);

                                XAxis xAxis = mChart.getXAxis();
                                xAxis.setValueFormatter((new MyXAxisValueFormatter(values)));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                mChart.getDescription().setEnabled(false);

                                Log.i("here", "7");
                                mChart.notifyDataSetChanged();
                                mChart.invalidate();
                            }
                        },
                        1000
                );


            }
        });
        onehour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yValues = new ArrayList<>();
                xDATES = new ArrayList<>();

                yValues.clear();
                xDATES.clear();
                mChart.invalidate();
                mChart.clear();

                getDayvalues("3");


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                Log.i("here", "6");
                                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                                set1.setFillAlpha(110);

                                set1.setColor(Color.RED);
                                set1.setLineWidth(3f);

                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(set1);

                                LineData data = new LineData(dataSets);

                                mChart.setData(data);

                                String[] values = xDATES.toArray(new String[xDATES.size()]);

                                XAxis xAxis = mChart.getXAxis();
                                xAxis.setValueFormatter((new MyXAxisValueFormatter(values)));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                mChart.getDescription().setEnabled(false);

                                Log.i("here", "7");
                                mChart.notifyDataSetChanged();
                                mChart.invalidate();
                            }
                        },
                        1000
                );


            }

        });
        oneweek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yValues = new ArrayList<>();
                xDATES = new ArrayList<>();

                yValues.clear();
                xDATES.clear();
                mChart.invalidate();
                mChart.clear();

                getvalues("7");


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                Log.i("here", "6");
                                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                                set1.setFillAlpha(110);

                                set1.setColor(Color.RED);
                                set1.setLineWidth(3f);

                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(set1);

                                LineData data = new LineData(dataSets);

                                mChart.setData(data);

                                String[] values = xDATES.toArray(new String[xDATES.size()]);

                                XAxis xAxis = mChart.getXAxis();
                                xAxis.setValueFormatter((new MyXAxisValueFormatter(values)));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                mChart.getDescription().setEnabled(false);

                                Log.i("here", "7");

                                mChart.notifyDataSetChanged();
                                mChart.invalidate();
                            }
                        },
                        1000
                );


            }
        });
        onemonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                yValues = new ArrayList<>();
                xDATES = new ArrayList<>();

                yValues.clear();
                xDATES.clear();
                mChart.invalidate();
                mChart.clear();

                getvalues("30");


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                Log.i("here", "6");
                                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                                set1.setFillAlpha(110);

                                set1.setColor(Color.RED);
                                set1.setLineWidth(3f);

                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(set1);

                                LineData data = new LineData(dataSets);

                                mChart.setData(data);

                                String[] values = xDATES.toArray(new String[xDATES.size()]);

                                XAxis xAxis = mChart.getXAxis();
                                xAxis.setValueFormatter((new MyXAxisValueFormatter(values)));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                mChart.getDescription().setEnabled(false);

                                Log.i("here", "7");
                                mChart.notifyDataSetChanged();
                                mChart.invalidate();
                            }
                        },
                        1000
                );


            }
        });
        sixmonths.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yValues = new ArrayList<>();
                xDATES = new ArrayList<>();

                yValues.clear();
                xDATES.clear();
                mChart.invalidate();
                mChart.clear();

                getvalues("180");


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                Log.i("here", "6");
                                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                                set1.setFillAlpha(110);

                                set1.setColor(Color.RED);
                                set1.setLineWidth(3f);

                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(set1);

                                LineData data = new LineData(dataSets);

                                mChart.setData(data);

                                String[] values = xDATES.toArray(new String[xDATES.size()]);

                                XAxis xAxis = mChart.getXAxis();
                                xAxis.setValueFormatter((new MyXAxisValueFormatter(values)));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                mChart.getDescription().setEnabled(false);

                                Log.i("here", "7");
                                mChart.notifyDataSetChanged();
                                mChart.invalidate();
                            }
                        },
                        1000
                );

            }
        });
        oneyear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                yValues = new ArrayList<>();
                xDATES = new ArrayList<>();

                yValues.clear();
                xDATES.clear();
                mChart.invalidate();
                mChart.clear();

                getvalues("365");


                new java.util.Timer().schedule(
                        new java.util.TimerTask() {
                            @Override
                            public void run() {
                                // your code here
                                Log.i("here", "6");
                                LineDataSet set1 = new LineDataSet(yValues, "Data Set 1");

                                set1.setFillAlpha(110);

                                set1.setColor(Color.RED);
                                set1.setLineWidth(3f);

                                ArrayList<ILineDataSet> dataSets = new ArrayList<>();
                                dataSets.add(set1);

                                LineData data = new LineData(dataSets);

                                mChart.setData(data);

                                String[] values = xDATES.toArray(new String[xDATES.size()]);

                                XAxis xAxis = mChart.getXAxis();
                                xAxis.setValueFormatter((new MyXAxisValueFormatter(values)));
                                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                                mChart.getDescription().setEnabled(false);

                                Log.i("here", "7");
                                mChart.notifyDataSetChanged();
                                mChart.invalidate();
                            }
                        },
                        1000
                );


            }
        });


        Log.i("here", "1");


    }


    //xAxis.setGranularity(1);


    ///change epoch to the date and time --used stack overflow for this
    public static String GetHumanReadableDate(String epoche) {
        long milliseconds = Long.parseLong(epoche) * 1000;
        Date date = new Date(milliseconds);
        SimpleDateFormat sdf = new SimpleDateFormat("d MMM", Locale.ENGLISH);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = sdf.format(date);
        return formattedDate;
    }

    public void getDayvalues(final String duration) {


        String coinsymbol = TabbedCoinData.getMyCoinSymbol().toUpperCase().trim();

        mQueue = Volley.newRequestQueue(getContext());
        String url = "https://min-api.cryptocompare.com/data/histohour?fsym=" + coinsymbol + "&tsym=GBP&limit=" + duration + "&aggregate=1&e=CCCAGG";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arr = response.getJSONArray("Data");
                    Log.i("here", "3");


                    for (int i = 0; i < Integer.parseInt(duration); i++) {
                        JSONObject arr1 = arr.getJSONObject(i);
                        String time = Integer.toString(arr1.getInt("time"));
                        String coinPrice = Integer.toString(arr1.getInt("close"));
                        yValues.add(new Entry(i, Float.parseFloat(coinPrice)));
                        //convert epoche time
                        long milliseconds = Long.parseLong(time) * 1000;
                        Date date = new Date(milliseconds);
                        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
                        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                        String formattedDate = sdf.format(date);
                        xDATES.add(formattedDate);
                    }


                } catch (
                        JSONException e)

                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(request);


    }

    public void getvalues(final String duration) {
        Log.i("here", "2");

        String coinsymbol = TabbedCoinData.getMyCoinSymbol().toUpperCase().trim();

        mQueue = Volley.newRequestQueue(getContext());
        String url = "https://min-api.cryptocompare.com/data/histoday?fsym=" + coinsymbol + "&tsym=GBP&limit=" + duration + "&aggregate=1&e=CCCAGG";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray arr = response.getJSONArray("Data");
                    Log.i("here", "3");


                    for (int i = 0; i < Integer.parseInt(duration); i++) {
                        JSONObject arr1 = arr.getJSONObject(i);
                        String time = Integer.toString(arr1.getInt("time"));
                        String coinPrice = Integer.toString(arr1.getInt("close"));
                        yValues.add(new Entry(i, Float.parseFloat(coinPrice)));
                        xDATES.add(GetHumanReadableDate(time));
                    }


                } catch (
                        JSONException e)

                {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener()

        {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        mQueue.add(request);


    }

    public class MyXAxisValueFormatter implements IAxisValueFormatter {
        private String[] mValues;

        public MyXAxisValueFormatter(String[] value) {
            this.mValues = value;
        }


        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            try {
                int index = (int) value;
                return mValues[(int) value];
            } catch (Exception e) {
                return "";
            }
        }
    }

}
