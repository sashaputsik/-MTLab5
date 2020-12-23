package com.labs.newsapi;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private Article article;
    private ImageView newsBackground, backDetail;
    private TextView newsTitle, newsOverview, newsDate;
    private Button watchOnBrowser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        Intent intent = getIntent();
        Type newsType = new TypeToken<Article>() {}.getType();
        String newsStringRecvFromAct = intent.getStringExtra("News");
        article =  new Gson().fromJson(newsStringRecvFromAct,newsType);
        newsBackground = findViewById(R.id.detail_news_cover);
        backDetail = findViewById(R.id.back_detail);
        newsTitle = findViewById(R.id.detail_news_title);
        newsOverview = findViewById(R.id.detail_news_desc);
        newsDate = findViewById(R.id.detail_news_date);
        watchOnBrowser = findViewById(R.id.readOnBrowser);

        initViews();

    }

    private void initViews() {
        backDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        String backgroung = article.getUrlToImage();
        String title = article.getTitle();
        String content = article.getDescription();
        String date = article.getPublishedAt();
        try {
            date = convertDate(date);
        } catch (ParseException e) {
        e.printStackTrace();
        }


        Glide.with(this).load(backgroung).placeholder(R.drawable.loading).into(newsBackground);
        newsTitle.setText(title);
        newsOverview.setText(content);
        newsDate.setText(date);
        watchOnBrowser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
    }

    private String convertDate(String date) throws ParseException {
        SimpleDateFormat spf=new SimpleDateFormat("yyyy-mm-dd");
        Date newDate=spf.parse(date);
        spf= new SimpleDateFormat("dd.mm.yyyy");
        return spf.format(newDate);
    }

}
