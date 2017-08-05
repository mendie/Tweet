package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {



    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        // Find the listview
        lvTweets = (ListView) findViewById(R.id.lvTweets);
        // CREATE THE TWEETS TO ADD IN THE ADAPTER
        tweets= new ArrayList<>();
        // Create the arraylist (data source)
        aTweets = new TweetsArrayAdapter(this, tweets);
        // Connect adapter to list view
       lvTweets.setAdapter(aTweets);
        //Get the client
        client = TwitterApplication.getRestClient(); // singleton client
        populateTimeline();


        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                loadNextDataFromApi(page);
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_timeline, menu);
        return true;
    }

    //Send an API request to get the timeline json
    //Fill the listview by creating the tweet objects from the json
    private void populateTimeline() {
       client.getHomeTimeline(new JsonHttpResponseHandler() {
           //SUCCESS
           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
               Log.d("DEBUG", json.toString());
               //DESERIALIZE JSON
               //CREATE MODELS AND ADD THEM TO THE ADAPTER
               //LOAD THE MODEL DATA INTO LISTVIEW
               aTweets.addAll(Tweet.fromJSONArray(json));
                Log.d("DEBUG", aTweets.toString());
           }


           //FAILURE
           @Override
           public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
              Log.d("DEBUG", errorResponse.toString());
           }

       });
    }

    public void loadNextDataFromApi (int offset){

        populateTimeline();
    }

}

