package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.adapter.ArticlesAdapter;
import com.example.travelapp.adapter.NewTourAdapter;
import com.example.travelapp.adapter.TypeServiceAdapter;
import com.example.travelapp.model.Articles;
import com.example.travelapp.model.NewTour;
import com.example.travelapp.model.NewTourModel;
import com.example.travelapp.model.TypeService;
import com.example.travelapp.model.TypeServiceModel;
import com.example.travelapp.model.User;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.nex3z.notificationbadge.NotificationBadge;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    RecyclerView rcViewMain,rcViewMain2;
    List<TypeService> arrTypeService;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiTravel apiTravel;
    List<NewTour> arrNewTour;
    List<Articles> arrNewArticles;
    NewTourAdapter newTourAdapter;
    ArticlesAdapter articlesAdapter;
    NotificationBadge badge;
    EditText searchView;
    TextView txtSeeAllTour,txtSeeAllArticles;
    ImageView imgHome, imgMainTour, imgMainHotel, imgMainCart, imgMainAccount;
    ConstraintLayout csNews;
    private Timer timer;
    private TimerTask timerTask;
    private int scrollAmount = 1080; // Adjust this value to control the scroll speed


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);

        getToken();
        mapping();
        getEventClick();
        if (isConnected(this)) {
            getNewArticles();
            getNewTour();
        } else {
            Toast.makeText(this, "Không có kết nối Internet, vui lòng kết nối!", Toast.LENGTH_SHORT).show();
        }
    }

    private void getToken() {
        FirebaseMessaging.getInstance().getToken()
                .addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        if (!TextUtils.isEmpty(s)) {
                            compositeDisposable.add(apiTravel.updateToken(Utils.user_current.getId(), s)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(
                                            messageModel -> {

                                            }, throwable -> {
                                                Log.d("log", throwable.getMessage());
                                            }
                                    )
                            );
                        }
                    }
                });
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

        txtSeeAllTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tour = new Intent(getApplicationContext(), TourActivity.class);
                tour.putExtra("t_service_id", 2);
                startActivity(tour);
            }
        });

        txtSeeAllArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articles = new Intent(getApplicationContext(), ArticlesActivity.class);
                articles.putExtra("a_category_id", 2);
                startActivity(articles);
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

        csNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articles = new Intent(getApplicationContext(), ArticlesActivity.class);
                articles.putExtra("a_category_id", 2);
                startActivity(articles);
            }
        });


    }

    private void getNewTour() {
        compositeDisposable.add(apiTravel.getNewTour().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        NewTourModel -> {
                            if (NewTourModel.isSuccess()) {
                                arrNewTour = NewTourModel.getResult();
                                newTourAdapter = new NewTourAdapter(getApplicationContext(), arrNewTour);
                                rcViewMain.setAdapter(newTourAdapter);
                            }
                        }
                ));
    }

    private void getNewArticles() {
        compositeDisposable.add(apiTravel.getNewArticles()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articlesModel -> {
                    if (articlesModel.isSuccess()) {
                        arrNewArticles = articlesModel.getResult();
                        articlesAdapter = new ArticlesAdapter(getApplicationContext(), arrNewArticles);
                        rcViewMain2.setAdapter(articlesAdapter);
                        startScrolling(); // Start scrolling after setting the adapter
                    }
                }));
    }

    private void mapping() {
        Paper.init(this);
        if (Paper.book().read("user") != null) {
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }

        rcViewMain = findViewById(R.id.rcViewMain);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        rcViewMain.setLayoutManager(layoutManager);
        rcViewMain.setHasFixedSize(true);

        rcViewMain2 = findViewById(R.id.rcViewMain2);
        rcViewMain2.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        rcViewMain2.setLayoutManager(layoutManager1);
        badge = findViewById(R.id.menu_quantity);

        searchView = findViewById(R.id.searchView);

        arrTypeService = new ArrayList<>();
        arrNewTour = new ArrayList<>();
        arrNewArticles = new ArrayList<>();

        if (Utils.arrCart == null) {
            Utils.arrCart = new ArrayList<>();
        } else {
            int total_item = 0;
            for (int i = 0; i < Utils.arrCart.size(); i++) {
                total_item += Utils.arrCart.get(i).getCart_quantity();
            }
//            badge.setText(String.valueOf(total_item));
        }

        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(intent);
            }
        });



        imgHome = findViewById(R.id.imgHome);
        imgMainTour = findViewById(R.id.imgMainTour);
        imgMainHotel = findViewById(R.id.imgMainHotel);
        imgMainCart = findViewById(R.id.imgMainCart);
        imgMainAccount = findViewById(R.id.imgMainAccount);
        txtSeeAllTour = findViewById(R.id.txtSeeAllTour);
        txtSeeAllArticles = findViewById(R.id.txtSeeAllArticles);

        csNews = findViewById(R.id.csNews);
    }

    private boolean isConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void startScrolling() {
        stopScrolling(); // Stop scrolling if it's already running

        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Scroll to the right
                        rcViewMain2.smoothScrollBy(scrollAmount, 0);

                        // Check if reached the end of the RecyclerView
                        if (!rcViewMain2.canScrollHorizontally(1)) {
                            // Reset scroll position to start over
                            rcViewMain2.scrollToPosition(0);
                        }
                    }
                });
            }
        };

        // Schedule the timer to run every second
        timer.scheduleAtFixedRate(timerTask, 0, 2000);
    }
    private void stopScrolling() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (timerTask != null) {
            timerTask.cancel();
            timerTask = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.arrCart != null) {
            int total_item = 0;
            for (int i = 0; i < Utils.arrCart.size(); i++) {
                total_item += Utils.arrCart.get(i).getCart_quantity();
            }
//            badge.setText(String.valueOf(total_item));
        }
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }
}
