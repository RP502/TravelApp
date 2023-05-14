package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.adapter.BookOrderAdapter;
import com.example.travelapp.adapter.HotelAdapter;
import com.example.travelapp.model.BookOrderModel;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ViewBookActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ApiTravel apiTravel;
    RecyclerView recyclerView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_book);

            initView();
            initToolbar();
            getOrder();
    }

    private void getOrder() {
        compositeDisposable.add(apiTravel.viewBookOrder(Utils.user_current.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        bookOrderModel -> {
                            BookOrderAdapter adapter = new BookOrderAdapter(getApplicationContext(), bookOrderModel.getResult());
                            recyclerView.setAdapter(adapter);
                        },throwable -> {

                        }
                ));
    }

    private void initToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        recyclerView = findViewById(R.id.rcViewBook);
        toolbar = findViewById(R.id.toolbarBook);
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

    }
}