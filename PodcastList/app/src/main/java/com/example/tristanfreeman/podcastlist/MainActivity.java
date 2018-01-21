package com.example.tristanfreeman.podcastlist;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;

private String url = "http://feeds.feedburner.com/blogspot/AndroidDevelopersBackstage?format=xml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setHasFixedSize(true);

    if (!isNetworkAvailable()){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Unable to load data. Please check your connection.")
                .setTitle("Network Error")
                .setCancelable(false)
                .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }else if (isNetworkAvailable()) {
        loadFeedData();
    }


    }

    public void loadFeedData(){
        RssFeedProvider feedProvider = new RssFeedProvider(this);
        feedProvider.execute();
        feedProvider.onFinish(new RssFeedProvider.OnTaskCompleted() {
            @Override
            public void onTaskCompleted(ArrayList<RssItem> list) {
                adapter = new ListAdapter(list, R.layout.row_layout, MainActivity.this);
                recyclerView.setAdapter(adapter);
            }
        });

    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}








