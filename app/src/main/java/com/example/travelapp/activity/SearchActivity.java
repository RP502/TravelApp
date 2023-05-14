package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.adapter.HotelAdapter;
import com.example.travelapp.adapter.NewTourAdapter;
import com.example.travelapp.adapter.TourAdapter;
import com.example.travelapp.adapter.TypeServiceAdapter;
import com.example.travelapp.model.NewTour;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    ApiTravel apiTravel;
    EditText edtSearch;
    List<NewTour> arrNewTour;
    NewTourAdapter newTourAdapter;
    TourAdapter tourAdapter;
    HotelAdapter hotelAdapter;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        ActionToolBar();
    }


    private void initView() {
        arrNewTour = new ArrayList<>();
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);
        toolbar = findViewById(R.id.toolbarSearch);
        edtSearch= findViewById(R.id.edtSearch);
        recyclerView = findViewById(R.id.rvViewSearch);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                    if (charSequence.length()==0){
                        arrNewTour.clear();
                        tourAdapter = new TourAdapter(getApplicationContext(), arrNewTour);
                        recyclerView.setAdapter(tourAdapter);
                    }
                getDataSearch(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {


            }


        });
    }

    private void getDataSearch(String s) {
        arrNewTour.clear();

        compositeDisposable.add(apiTravel.search(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                       newTourModel -> {
                           if (newTourModel.isSuccess()){
                               arrNewTour = newTourModel.getResult();
                               tourAdapter = new TourAdapter(getApplicationContext(), newTourModel.getResult());
                               recyclerView.setAdapter(tourAdapter);
                           }

                       }, throwable -> {
                            Toast.makeText(getApplicationContext(), throwable.getMessage(),Toast.LENGTH_SHORT).show();
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

}