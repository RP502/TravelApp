package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ResetPassActivity extends AppCompatActivity {
    EditText email;
    Button btnSendMail;
    ApiTravel apiTravel;
    ProgressBar progressBar;

    CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pass);

        initControl();
        initView();
    }

    private void initControl() {
        btnSendMail = findViewById(R.id.btnForgetPass);
        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = email.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)){
                    Toast.makeText(getApplicationContext(),"You have not entered email",Toast.LENGTH_SHORT).show();
                }else {
                    progressBar.setVisibility(View.VISIBLE);
                    compositeDisposable.add(apiTravel.resetPass( str_email)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(userModel -> {
                                if (userModel.isSuccess()) {
                                    Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getApplicationContext(), userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                progressBar.setVisibility(View.INVISIBLE);
                            }, throwable -> {
                                Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.INVISIBLE);
                            }));

                }
            }
        });
    }

    private void initView() {
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);

        email = findViewById(R.id.forgotMail);
        progressBar = findViewById(R.id.progressbar2);


        compositeDisposable = new CompositeDisposable();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();

    }
}