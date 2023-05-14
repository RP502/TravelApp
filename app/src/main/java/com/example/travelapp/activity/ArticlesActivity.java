package com.example.travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.travelapp.R;
import com.example.travelapp.adapter.ArticlesAdapter;

import com.example.travelapp.adapter.HotelAdapter;
import com.example.travelapp.model.Articles;

import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ArticlesActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiTravel apiTravel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int a_category_id;
    ArticlesAdapter articlesAdapter;
    List<Articles> articlesList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    ImageView imgHome, imgMainTour, imgMainHotel, imgMainCart, imgMainAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);
        a_category_id = getIntent().getIntExtra("a_category_id",2);

        mapping();
        ActionToolBar();
        getData(page);
        addEventLoad();
        getEventClick();
    }



    private void addEventLoad() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!isLoading) {
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == articlesList.size() - 1) {
                        isLoading = true;
                        loadMore();
                    }
                }
            }
        });
    }

    private void loadMore() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                articlesList.add(null);
                articlesAdapter.notifyItemInserted(articlesList.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                articlesList.remove(articlesList.size() - 1);
                articlesAdapter.notifyItemRemoved(articlesList.size());
                page = page + 1;
                getData(page);
                articlesAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 1000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiTravel.getArticles(page, a_category_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        articlesModel -> {
                            if (articlesModel.isSuccess()) {
                                if (articlesAdapter == null) {
                                    articlesList = articlesModel.getResult();
                                    articlesAdapter = new ArticlesAdapter(getApplicationContext(), articlesList);
                                    recyclerView.setAdapter(articlesAdapter);
                                } else {
                                    int location = articlesList.size() - 1;
                                    int quantityAdd = articlesModel.getResult().size();
                                    for (int i = 0; i < quantityAdd; i++) {
                                        articlesList.add(articlesModel.getResult().get(i));
                                    }
                                    articlesAdapter.notifyItemRangeInserted(location, quantityAdd);
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Out Of Data", Toast.LENGTH_SHORT).show();

                                isLoading=true;
                            }
                        }
                ));
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }



    private void mapping() {
        toolbar = findViewById(R.id.toolbarArticle);
        recyclerView = findViewById(R.id.rViewArticles);

        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        articlesList = new ArrayList<>();
        imgHome = findViewById(R.id.imgHome);
        imgMainTour = findViewById(R.id.imgMainTour);
        imgMainHotel = findViewById(R.id.imgMainHotel);
        imgMainCart = findViewById(R.id.imgMainCart);
        imgMainAccount = findViewById(R.id.imgMainAccount);
    }

    public  void getEventClick() {
        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });

        imgMainTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tour = new Intent(getApplicationContext(), TourActivity.class);
                tour.putExtra("t_service_id", 2);
                startActivity(tour);
            }
        });

        imgMainHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotel = new Intent(getApplicationContext(), HotelActivity.class);
                hotel.putExtra("h_service_id", 3);
                startActivity(hotel);
            }
        });
        imgMainCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });

        imgMainAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Paper.book().read("user") == null){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(intent);
                }
            }
        });


    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

}