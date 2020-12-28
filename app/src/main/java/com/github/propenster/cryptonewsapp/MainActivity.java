package com.github.propenster.cryptonewsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.github.propenster.cryptonewsapp.Adapters.MyCustomAdapter;
import com.github.propenster.cryptonewsapp.Models.MySingleton;
import com.github.propenster.cryptonewsapp.Models.NewsModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView newsListView;
    ArrayList<NewsModel> newsArrayList;
    MyCustomAdapter myCustomAdapter;
    String API_URL;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsListView = (ListView)findViewById(R.id.newsListView);
        newsArrayList = new ArrayList<NewsModel>();
        API_URL = getResources().getString(R.string.CRYPTO_NEWS_API_URL);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        myCustomAdapter = new MyCustomAdapter(MainActivity.this, this.newsArrayList);

        displayNews();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "You have clicked my FLoating Action Button", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void displayNews() {
        //RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        //MySingleton mySingleton = MySingleton.getInstance(MainActivity.this);
        //make the request to the endpoint here...
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, API_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //we got a valid OK response from the Request
                //Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_LONG).show();
                try {
                    JSONArray articlesJSONObjectFromAPI = response.getJSONArray("articles");
                    //Toast.makeText(MainActivity.this, articlesJSONObjectFromAPI.toString(), Toast.LENGTH_LONG).show();
                    for(int i=0; i<articlesJSONObjectFromAPI.length(); i++){
                        JSONObject singleNewsJsonObject = articlesJSONObjectFromAPI.getJSONObject(i);
                        NewsModel singleCryptoNewsItem = new NewsModel();
                        singleCryptoNewsItem.setTitle(singleNewsJsonObject.getString("title"));
                        singleCryptoNewsItem.setDescription(singleNewsJsonObject.getString("description"));
                        singleCryptoNewsItem.setContent(singleNewsJsonObject.getString("content"));
                        singleCryptoNewsItem.setUrl(singleNewsJsonObject.getString("url"));
                        singleCryptoNewsItem.setImage(singleNewsJsonObject.getString("image"));
                        singleCryptoNewsItem.setPublishedAt(singleNewsJsonObject.getString("publishedAt"));
                        singleCryptoNewsItem.setSourceName(singleNewsJsonObject.getJSONObject("source").getString("name"));

                        newsArrayList.add(singleCryptoNewsItem);
                        //Toast.makeText(MainActivity.this, newsArrayList.toString(), Toast.LENGTH_SHORT).show();
                    }
                    newsListView.setAdapter(myCustomAdapter);
                    myCustomAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        //requestQueue.add(jsonObjectRequest);
        // Add a request (in this example, called stringRequest) to your RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }
}