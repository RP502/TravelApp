package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.travelapp.R;
import com.example.travelapp.model.Hotel;
import com.example.travelapp.model.NewTour;

import java.text.DecimalFormat;

public class DetailsHotelActivity extends AppCompatActivity {
    ImageView img_Hotel;
    TextView tv_Name, tv_Description,tv_Address,tv_Price;
    Toolbar toolbarDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_hotel);

        initView();
        ActionToolBar();
        initData();
    }

    private void initData() {
        Hotel hotel = (Hotel) getIntent().getSerializableExtra("detailsHotel");
        tv_Name.setText(hotel.getH_name());
        tv_Description.setText(hotel.getH_description());
//        tour_location.setText(tour.getT_location_id());

        tv_Address.setText(hotel.getH_address());

        Glide.with(getApplicationContext()).load(hotel.getH_image()).into(img_Hotel);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tv_Price.setText("From: " + decimalFormat.format(Double.parseDouble(hotel.getH_price())) + "đ");



    }

    private void initView() {
        tv_Address = findViewById(R.id.hotel_address);
        img_Hotel = findViewById(R.id.hotel_image);
        tv_Price = findViewById(R.id.hotel_price_details);
        tv_Name = findViewById(R.id.hotel_name);
        tv_Description = findViewById(R.id.hotel_description);
        toolbarDetails = findViewById(R.id.toolbarDetails);
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
}