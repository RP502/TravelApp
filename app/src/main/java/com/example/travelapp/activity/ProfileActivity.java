package com.example.travelapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.travelapp.R;
import com.example.travelapp.model.User;
import com.example.travelapp.utils.Utils;
import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfileActivity extends AppCompatActivity {
    LinearLayout lnBookings, lnLogout;
    TextView txtEditProfile, txtNameProfile;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ImageView imgHome, imgMainTour, imgMainHotel, imgMainCart, imgMainAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Paper.init(this);
        if (Paper.book().read("user") != null) {
            User user = Paper.book().read("user");
            Utils.user_current = user;
        }

        initView();
        getClickEvent();
    }

    private void getClickEvent() {
        lnBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewBookActivity.class);
                startActivity(intent);
            }
        });
        lnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().delete("user");
                FirebaseAuth.getInstance().signOut();
                Intent logout = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(logout);
                finish();
            }
        });

        txtEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UpdateProfileActivity.class);
                startActivity(intent);
            }
        });
        




    }

    private void initView() {
        lnBookings = findViewById(R.id.lnBookings);
        lnLogout = findViewById(R.id.lnLogout);
        txtEditProfile = findViewById(R.id.txtEditProfile);
        txtNameProfile = findViewById(R.id.txtNameProfile);
        if (Utils.user_current != null) {
            txtNameProfile.setText(Utils.user_current.getName());
        }

        imgHome = findViewById(R.id.imgHome);
        imgMainTour = findViewById(R.id.imgMainTour);
        imgMainHotel = findViewById(R.id.imgMainHotel);
        imgMainCart = findViewById(R.id.imgMainCart);
        imgMainAccount = findViewById(R.id.imgMainAccount);


        imgHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(home);
            }
        });

        imgMainTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent tour = new Intent(getApplicationContext(), TourActivity.class);
                tour.putExtra("t_service_id", 2);
                startActivity(tour);
            }
        });

        imgMainHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent hotel = new Intent(getApplicationContext(), HotelActivity.class);
                hotel.putExtra("h_service_id", 3);
                startActivity(hotel);
            }
        });
        imgMainCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cart = new Intent(getApplicationContext(), CartActivity.class);
                startActivity(cart);
            }
        });

        imgMainAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent account = new Intent(getApplicationContext(), ProfileActivity.class);
                startActivity(account);
            }
        });
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }



}
