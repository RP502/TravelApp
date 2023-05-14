package com.example.travelapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.travelapp.R;
import com.example.travelapp.model.UserModel;
import com.example.travelapp.retrofit.ApiTravel;
import com.example.travelapp.retrofit.RetrofitClient;
import com.example.travelapp.utils.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.HttpException;

public class LoginActivity extends AppCompatActivity {
    TextView gotoRegister, forgotPassword;
    EditText inputEmail, inputPass;
    Button btnLogin;
    ApiTravel apiTravel;
    CompositeDisposable compositeDisposable;
    boolean isLogin = false;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initView();
        initControl();
    }

    private void initControl() {
        gotoRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPassActivity.class);
                startActivity(intent);
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email = inputEmail.getText().toString().trim();
                String str_password = inputPass.getText().toString().trim();
                if (TextUtils.isEmpty(str_email)) {
                    Toast.makeText(getApplicationContext(), "You have not entered your Email", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(str_email)) {
                    Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str_password)) {
                    Toast.makeText(getApplicationContext(), "You have not entered your Password", Toast.LENGTH_SHORT).show();
                } else if (!isValidPassword(str_password)) {
                    Toast.makeText(getApplicationContext(), "Invalid password", Toast.LENGTH_SHORT).show();
                } else {
                    //save
                    Paper.book().write("email", str_email);
                    Paper.book().write("password", str_password);
                    if (user != null){
                        login(str_email, str_password);
                    }else {
                        firebaseAuth.signInWithEmailAndPassword(str_email,str_password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()){
                                            login(str_email, str_password);
                                        }
                                    }
                                });
                    }


                }
            }
        });

    }

    private void initView() {
        Paper.init(this);
        apiTravel = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiTravel.class);
        gotoRegister = findViewById(R.id.gotoRegister);
        forgotPassword = findViewById(R.id.forgotPassword);
        inputEmail = findViewById(R.id.inputEmail);
        inputPass = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        firebaseAuth = FirebaseAuth.getInstance();
        user= firebaseAuth.getCurrentUser();

        compositeDisposable = new CompositeDisposable();
        if (Paper.book().read("email") != null && Paper.book().read("password") != null) {
            inputEmail.setText(Paper.book().read("email"));
            inputPass.setText(Paper.book().read("password"));
            if (Paper.book().read("isLogin") != null) {
                boolean flag = Paper.book().read("isLogin");
                if (flag) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //login(Paper.book().read("email"),Paper.book().read("password"));
                        }
                    }, 1000);
                }
            }
        }
    }

    private void login(String email, String pass) {

        ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("Logging in...");
        progressDialog.show();

        compositeDisposable.add(apiTravel.login(email, pass)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(userModel -> {
                    progressDialog.dismiss();
                    if (userModel.isSuccess()) {
                        isLogin = true;
                        Paper.book().write("isLogin", isLogin);
                        Utils.user_current = userModel.getResult().get(0);
                        // save information of user
                        Paper.book().write("user", userModel.getResult().get(0));
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }, throwable -> {
                    progressDialog.dismiss();
                    if (throwable instanceof HttpException) {
                        HttpException httpException = (HttpException) throwable;
                        int statusCode = httpException.code();
                        if (statusCode == 401) {
                            Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Server error: " + httpException.message(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Network error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Add your own password validation rules here
        return password != null && password.length() >= 8;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Utils.user_current.getEmail() != null && Utils.user_current.getPassword() != null) {
            inputEmail.setText(Utils.user_current.getEmail());
            inputPass.setText(Utils.user_current.getPassword());
        }
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}