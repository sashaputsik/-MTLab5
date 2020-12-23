package com.labs.newsapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NewsClickListener {

    private static final String TAG = "myLogs";
    private ProgressBar progressBar;
    private RecyclerView slider_news;
    private List<Article> articles;
    private String apikey = "69698df82a724ba9b3979013183abb34";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        slider_news = findViewById(R.id.rv_news);
        loadJSON();
    }

    private void loadJSON() {
        try {
            Service apiService = Client.GetClient().create(Service.class);
            Observable<NewsResponse> call = apiService.getTopHeadlines("ru","general",apikey);
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<NewsResponse>() {
                        @Override
                        public void onSubscribe(@NonNull Disposable d) {

                        }

                        @Override
                        public void onNext(NewsResponse newsResponse) {
                        articles = newsResponse.getArticles();
                        slider_news.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
                        slider_news.setAdapter(new NewsAdapter(getApplicationContext(),articles,MainActivity.this));
                            if (articles.isEmpty())
                            progressBar.setVisibility(View.VISIBLE);
                        else
                            progressBar.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onError(@NonNull Throwable e) {
                            Log.d(TAG, "error " + e.getMessage());
                            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } catch (Exception e) {
            Log.d(TAG, "error " + e.getMessage());
            Toast.makeText(getApplicationContext(), "Err: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNewsClick(Article clickedDataItem) {
        String news = new Gson().toJson(clickedDataItem);
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra("News", news);
        startActivity(intent);
    }
}