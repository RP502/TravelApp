package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;
import com.google.gson.Gson;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class UpdateProfileActivity extends AppCompatActivity {
    EditText edtEmail, edtPhone, edtName, edtAddress;
    Toolbar toolbar;
    Button btnUpdate;
    ApiTravel apiTravel;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);
        initView();
        initControl();
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

        edtEmail.setText(Utils.user_current.getEmail());
        edtPhone.setText(Utils.user_current.getPhone());
        edtName.setText(Utils.user_current.getName());
        edtAddress.setText(Utils.user_current.getAddress());

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_address = edtAddress.getText().toString().trim();
                String str_Name = edtName.getText().toString().trim();
                String str_phone = edtPhone.getText().toString().trim();
                String str_Email = edtEmail.getText().toString().trim();

                compositeDisposable.add(apiTravel.updateInfo(str_Name, str_Email, str_phone, str_address)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                userModel -> {
                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                                },
                                throwable -> {
                                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                }));
            }
        });
    }

    private void initView() {
        btnUpdate = findViewById(R.id.btnUpdateInfor);
        toolbar = findViewById(R.id.toolbarPayment);
        edtEmail = findViewById(R.id.edtEmail);
        edtPhone = findViewById(R.id.edtPhoneNumber);
        edtName = findViewById(R.id.edtName);
        edtAddress = findViewById(R.id.edtAddress);
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }

}


