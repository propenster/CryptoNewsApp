package com.github.propenster.cryptonewsapp.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.propenster.cryptonewsapp.Models.NewsModel;
import com.github.propenster.cryptonewsapp.NewsWebViewActivity;
import com.github.propenster.cryptonewsapp.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyCustomAdapter extends BaseAdapter {

    Activity activity;
    ArrayList<NewsModel> newsModelArrayList;
    LayoutInflater layoutInflater;

    public MyCustomAdapter(Activity activity, ArrayList<NewsModel> newsModelArrayList) {
        this.activity = activity;
        this.newsModelArrayList = newsModelArrayList;
    }

    @Override
    public int getCount() {
        return this.newsModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.newsModelArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (long)position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    if(layoutInflater == null){
        layoutInflater = this.activity.getLayoutInflater();
    }
    if(convertView == null){
        convertView = layoutInflater.inflate(R.layout.custom_news, null);

    }

    //Init all the Views to be passed
        ImageView newsImageView = (ImageView)convertView.findViewById(R.id.newsImageView);
        TextView newsTitleView = (TextView)convertView.findViewById(R.id.newsTitle);
        TextView newsContentSnapshot = (TextView)convertView.findViewById(R.id.newsContentSnapshot);
        TextView newsPublishedDate = (TextView)convertView.findViewById(R.id.newsPublishedDate);
        TextView newsSource = (TextView)convertView.findViewById(R.id.newsSource);
        LinearLayout linearLayout = (LinearLayout)convertView.findViewById(R.id.rootLinearLayout);
        NewsModel newsModel = (NewsModel)this.newsModelArrayList.get(position);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, NewsWebViewActivity.class);
                intent.putExtra("url", newsModel.getUrl());
                activity.startActivity(intent);

            }
        });

        Picasso.get().load(newsModel.getImage()).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(newsImageView);
        newsTitleView.setText(newsModel.getTitle());
        newsContentSnapshot.setText(newsModel.getContent());
        newsPublishedDate.setText(newsModel.getPublishedAt());
        newsSource.setText(newsModel.getSourceName());



        return convertView;
    }
}
