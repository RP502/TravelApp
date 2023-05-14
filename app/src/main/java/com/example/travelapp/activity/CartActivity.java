package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.travelapp.R;
import com.example.travelapp.adapter.CartAdapter;
import com.example.travelapp.model.Cart;
import com.example.travelapp.model.EventBus.SubTotalEventBus;
import com.example.travelapp.utils.Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    TextView empty_cart, sub_total;
    Toolbar toolbar;
    RecyclerView recyclerView;
    Button btn_checkout;
    CartAdapter cartAdapter;
    long sub_total_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initView();
        initControl();
        if (Utils.arrGetCar != null){
            Utils.arrGetCar.clear();
        }
        caculatortTotal();
    }

    private void caculatortTotal() {
        sub_total_cart = 0;
        for (int i = 0; i < Utils.arrGetCar.size(); i++) {
            sub_total_cart += (Utils.arrGetCar.get(i).getCart_price() * Utils.arrGetCar.get(i).getCart_quantity());
        }
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        sub_total.setText(decimalFormat.format(sub_total_cart));
    }

    private void initControl() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if (Utils.arrCart.size() == 0) {
            empty_cart.setVisibility(View.VISIBLE);
        } else {

            cartAdapter = new CartAdapter(getApplicationContext(), Utils.arrCart);
            recyclerView.setAdapter(cartAdapter);
        }

        btn_checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), PaymentActivity.class);
                intent.putExtra("subTotal", sub_total_cart);
                intent.putExtra("cartName", Utils.arrCart.get(0).getCart_name());
                intent.putExtra("quantity", Utils.arrCart.get(0).getCart_quantity());
                intent.putExtra("idTour", Utils.arrCart.get(0).getId());
                Utils.arrCart.clear();
                startActivity(intent);
            }
        });
    }

    private void initView() {
        empty_cart = findViewById(R.id.empty_cart_text);
        toolbar = findViewById(R.id.toolbar);
        recyclerView = findViewById(R.id.rc_cart_list);
        sub_total = findViewById(R.id.subtotal_value_text);
        btn_checkout = findViewById(R.id.checkout_button);
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void eventSubtotal(SubTotalEventBus eventBus) {
        if (eventBus != null) {
            caculatortTotal();
        }
    }
}