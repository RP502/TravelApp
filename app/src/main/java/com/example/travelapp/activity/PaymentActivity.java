package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class PaymentActivity extends AppCompatActivity {
    Toolbar toolbar;
    TextView txtName, txtPrice, txtDate, txtQuantity, txtTotal;
    Button btnGoPayment;
    EditText edtEmail, edtPhone, edtName, edtNote, edtAddress;
    ApiTravel apiTravel;
    long sub_total;
    int total_item;
    CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);

        initView();
        countItem();
        initControl();
    }

    private void countItem() {
        total_item = 0;
        for (int i = 0; i < Utils.arrGetCar.size(); i++) {
            total_item += Utils.arrGetCar.get(i).getCart_quantity();
        }
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
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");

        sub_total = getIntent().getLongExtra("subTotal", 0);


        txtTotal.setText(decimalFormat.format(sub_total));
        txtName.setText(getIntent().getStringExtra("cartName"));
//

        edtEmail.setText(Utils.user_current.getEmail());
        edtPhone.setText(Utils.user_current.getPhone());
        edtName.setText(Utils.user_current.getName());


        btnGoPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_address = edtAddress.getText().toString().trim();
                String str_Name = edtName.getText().toString().trim();
                String str_phone = edtPhone.getText().toString().trim();
                String str_Email = edtEmail.getText().toString().trim();
                String str_Note = edtNote.getText().toString().trim();

                if (Utils.arrGetCar == null || Utils.arrGetCar.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Your cart is empty", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(str_Name)) {
                    Toast.makeText(getApplicationContext(), "You have not entered your Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_phone)) {
                    Toast.makeText(getApplicationContext(), "You have not entered your Phone", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_Email)) {
                    Toast.makeText(getApplicationContext(), "You have not entered your Email ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_Note)) {
                    Toast.makeText(getApplicationContext(), "You have not entered your Note ", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_address)) {
                    Toast.makeText(getApplicationContext(), "You have not entered your Address", Toast.LENGTH_SHORT).show();
                } else {

                    int id = Utils.user_current.getId();

                    Log.d("test", new Gson().toJson(Utils.arrGetCar));

                    compositeDisposable.add(apiTravel.createBook( id, str_Name, str_Email, str_phone, str_address, str_Note, total_item, String.valueOf(sub_total), new Gson().toJson(Utils.arrGetCar))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                        Utils.arrGetCar.clear();
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    },
                                    throwable -> {
                                        Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }));
                }


            }
        });


    }

    private void initView() {


        toolbar = findViewById(R.id.toolbarPayment);
        txtName = findViewById(R.id.checkoutName);
        txtPrice = findViewById(R.id.checkoutPrice);
        txtDate = findViewById(R.id.checkoutDate);
        txtQuantity = findViewById(R.id.checkoutQuantity);
        txtTotal = findViewById(R.id.checkoutTotal);
        btnGoPayment = findViewById(R.id.btnGoPayment);

        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        edtName = findViewById(R.id.edtName);
        edtNote = findViewById(R.id.edtNote);
        edtAddress = findViewById(R.id.edtAddress);


    }


    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}