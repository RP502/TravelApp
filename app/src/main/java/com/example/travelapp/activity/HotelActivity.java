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
import com.example.travelapp.adapter.HotelAdapter;
import com.example.travelapp.model.Hotel;
import com.example.travelapp.model.HotelModel;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HotelActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiTravel apiTravel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    int page = 1;
    int h_service_id;
    HotelAdapter hotelAdapter;
    List<Hotel> hotelList;
    LinearLayoutManager linearLayoutManager;
    Handler handler = new Handler();
    boolean isLoading = false;
    ImageView imgHome, imgMainTour, imgMainHotel, imgMainCart, imgMainAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotel);
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);
        h_service_id = getIntent().getIntExtra("h_service_id",3);

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
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == hotelList.size() - 1) {
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
                hotelList.add(null);
                hotelAdapter.notifyItemInserted(hotelList.size() - 1);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                hotelList.remove(hotelList.size() - 1);
                hotelAdapter.notifyItemRemoved(hotelList.size());
                page = page + 1;
                getData(page);
                hotelAdapter.notifyDataSetChanged();
                isLoading = false;
            }
        }, 1000);
    }

    private void getData(int page) {
        compositeDisposable.add(apiTravel.getHotel(page, h_service_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        hotelModel -> {
                            if (hotelModel.isSuccess()) {
                                if (hotelAdapter == null) {
                                    hotelList = hotelModel.getResult();
                                    hotelAdapter = new HotelAdapter(getApplicationContext(), hotelList);
                                    recyclerView.setAdapter(hotelAdapter);
                                } else {
                                    int location = hotelList.size() - 1;
                                    int quantityAdd = hotelModel.getResult().size();
                                    for (int i = 0; i < quantityAdd; i++) {
                                        hotelList.add(hotelModel.getResult().get(i));
                                    }
                                    hotelAdapter.notifyItemRangeInserted(location, quantityAdd);
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
        toolbar = findViewById(R.id.toolbarTour);
        recyclerView = findViewById(R.id.rViewHotel);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager); // move this line to here

        hotelList = new ArrayList<>();
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