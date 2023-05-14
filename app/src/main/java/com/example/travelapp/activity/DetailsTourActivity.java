package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.adapter.CommentAdapter;
import com.example.travelapp.model.Cart;
import com.example.travelapp.model.CommentModel;
import com.example.travelapp.model.NewTour;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.utils.Utils;
import com.nex3z.notificationbadge.NotificationBadge;

import java.text.DecimalFormat;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailsTourActivity extends AppCompatActivity {
    TextView tour_name, tour_price, tour_location, tour_duration, tour_transportation, tour_description;
    Button book_now;
    Spinner spinner;
    ImageView imageView;
    Toolbar toolbarDetails;
    NewTour tour;
    NotificationBadge badge;
    ApiTravel apiTravel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_tour);

        initView();
        ActionToolBar();
        initData();
        initControl();
//        getComment();
    }

//    private void getComment() {
//        // Create Retrofit instance
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("https://example.com/") // Replace with your API base URL
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//                .build();
//
//        // Create ApiService instance
//        ApiService apiService = retrofit.create(ApiService.class);
//
//        // Make the API call
//        apiService.getComment()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<CommentModel>() {
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//                        // Show loading indicator if needed
//                    }
//
//                    @Override
//                    public void onNext(@NonNull CommentModel commentModel) {
//                        List<Comment> commentList = commentModel.getComments();
//
//                        // Set up and attach the comment adapter to the RecyclerView
//                        CommentAdapter commentAdapter = new CommentAdapter(DetailsTourActivity.this, commentList);
//                        RecyclerView recyclerView = findViewById(R.id.rcComment);
//                        recyclerView.setAdapter(commentAdapter);
//                    }
//
//                    @Override
//                    public void onError(@NonNull Throwable e) {
//                        // Handle error
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        // Hide loading indicator if needed
//                    }
//                });
//    }


    private void initControl() {
        book_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        if (Utils.arrCart.size() > 0) {
            boolean flag = false;
            int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
            if (quantity <= 0) {
                // input validation: spinner selection is not valid
                return;
            }
            for (int i = 0; i < Utils.arrCart.size(); i++) {
                if (Utils.arrCart.get(i).getId() == tour.getId()) {
                    Utils.arrCart.get(i).setCart_quantity(quantity + Utils.arrCart.get(i).getCart_quantity());
                    long price = Long.parseLong(tour.getT_price_adults());
                    Utils.arrCart.get(i).setCart_price(price);
                    flag = true;
                }
            }
            if (!flag) {
                long price = Long.parseLong(tour.getT_price_adults()) * quantity;
                Cart cart = new Cart();
                cart.setCart_name(tour.getT_title());
                cart.setCart_price(price);
                cart.setImg_cart(tour.getT_image());
                cart.setCart_quantity(quantity);
                cart.setId(tour.getId());
                Utils.arrCart.add(cart);
            }
        } else {
            int quantity = Integer.parseInt(spinner.getSelectedItem().toString());
            if (quantity <= 0) {
                // input validation: spinner selection is not valid
                return;
            }
            long price = Long.parseLong(tour.getT_price_adults()) * quantity;
            Cart cart = new Cart();
            cart.setCart_name(tour.getT_title());
            cart.setCart_price(price);
            cart.setImg_cart(tour.getT_image());
            cart.setCart_quantity(quantity);
            cart.setId(tour.getId());
            Utils.arrCart.add(cart);
        }

        badge.setText(String.valueOf(Utils.arrCart.size()));
    }

    private void initData() {
        tour = (NewTour) getIntent().getSerializableExtra("details");
        tour_name.setText(tour.getT_title());
        tour_description.setText(tour.getT_description());
//        tour_location.setText(tour.getT_location_id());

        tour_transportation.setText(tour.getT_move_method());
        tour_duration.setText(tour.getT_schedule());
        Glide.with(getApplicationContext()).load(tour.getT_image()).into(imageView);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tour_price.setText("From: " + decimalFormat.format(Double.parseDouble(tour.getT_price_adults())));
        Integer[] number = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> adapterSpin = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, number);
        spinner.setAdapter(adapterSpin);

    }

    private void initView() {
        tour_name = findViewById(R.id.tour_name);
        tour_price = findViewById(R.id.tour_price_details);
        tour_description = findViewById(R.id.tour_description);
        tour_location = findViewById(R.id.tour_location);
        tour_duration = findViewById(R.id.tour_duration);
        tour_transportation = findViewById(R.id.tour_transportation);
        book_now = findViewById(R.id.book_now_button);
        spinner = findViewById(R.id.sniper);
        imageView = findViewById(R.id.tour_image);
        toolbarDetails = findViewById(R.id.toolbarDetails);
        badge = findViewById(R.id.menu_quantity);
        if (Utils.arrCart == null) {
            badge.setText(String.valueOf(Utils.arrCart.size()));
        }
        FrameLayout frameLayout = findViewById(R.id.frame_cart);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });

        if (Utils.arrCart != null) {
            int total_item = 0;
            for (int i = 0; i < Utils.arrCart.size(); i++) {
                total_item += Utils.arrCart.get(i).getCart_quantity();
            }
            badge.setText(String.valueOf(total_item));
        }
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarDetails);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDetails.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.arrCart != null) {
            int total_item = 0;
            for (int i = 0; i < Utils.arrCart.size(); i++) {
                total_item += Utils.arrCart.get(i).getCart_quantity();
            }
            badge.setText(String.valueOf(total_item));
        }
    }
}