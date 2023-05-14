package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.model.Articles;
import com.example.travelapp.model.Hotel;

import java.text.DecimalFormat;

public class DetailsArticlesActivity extends AppCompatActivity {
    ImageView img_articles;
    TextView txt_title, tv_Content;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_articles);
        initView();
        ActionToolBar();
        initData();
    }

    private void initData() {
        Articles articles = (Articles) getIntent().getSerializableExtra("detailsArticles");
        txt_title.setText(articles.getA_title());
        tv_Content.setText(articles.getA_content());
        Glide.with(getApplicationContext()).load(articles.getA_avatar()).into(img_articles);
    }

    private void initView() {
        txt_title = findViewById(R.id.articles_title);
        img_articles = findViewById(R.id.articles_image);
        tv_Content = findViewById(R.id.articles_content);
        toolbar = findViewById(R.id.toolbarOut);

    }


    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }
}
