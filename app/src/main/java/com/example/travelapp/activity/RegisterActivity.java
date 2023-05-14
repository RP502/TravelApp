package com.example.travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.adapter.TourAdapter;
import com.example.travelapp.model.NewTourModel;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class RegisterActivity extends AppCompatActivity {
    EditText regisEmail, regisPass1, regisPass2, resPhone, regisName;
    Button btnRegister;
    ApiTravel apiTravel;
    FirebaseAuth firebaseAuth;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
        initControl();
    }

    private void initControl() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String str_email = regisEmail.getText().toString().trim();
        String str_phone = resPhone.getText().toString().trim();
        String str_password = regisPass1.getText().toString().trim();
        String str_repass = regisPass2.getText().toString().trim();
        String str_name = regisName.getText().toString().trim();

        if (TextUtils.isEmpty(str_email)) {
            Toast.makeText(getApplicationContext(), "You have not entered your Email", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_name)) {
            Toast.makeText(getApplicationContext(), "You have not entered your Name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_phone)) {
            Toast.makeText(getApplicationContext(), "You have not entered your Phone", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_password)) {
            Toast.makeText(getApplicationContext(), "You have not entered your Password", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_repass)) {
            Toast.makeText(getApplicationContext(), "You have not entered your Repeat Password", Toast.LENGTH_SHORT).show();
        } else {
            if (str_password.equals(str_repass)) {
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.createUserWithEmailAndPassword(str_email,str_password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
                                    if (user != null){
                                        postData( str_name,  str_email, str_phone,  str_password, user.getUid());
                                    }
                                }else {
                                    Toast.makeText(getApplicationContext(),"Email already exists or failed",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            } else {
                Toast.makeText(getApplicationContext(), "Re Pass is not match", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void postData(String str_name, String str_email,String str_phone, String str_password, String uid){
        compositeDisposable.add(apiTravel.register(str_name, str_email, str_phone, str_password, uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    if (userModel.isSuccess()) {
                        Utils.user_current.setEmail(str_email);
                        Utils.user_current.setPassword(str_password);
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, throwable -> {
                    Toast.makeText(getApplicationContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
                }));
    }

    private void initView() {
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);


        regisEmail = findViewById(R.id.reEmail);
        regisPass1 = findViewById(R.id.regisPass);
        regisPass2 = findViewById(R.id.regisPass2);
        resPhone = findViewById(R.id.resPhone);
        regisName = findViewById(R.id.regisName);
        btnRegister = findViewById(R.id.btnRegister);

        compositeDisposable = new CompositeDisposable();

    }
}