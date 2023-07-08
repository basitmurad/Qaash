package com.example.qaash.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.qaash.R;

import com.example.qaash.SessionManager;
import com.example.qaash.databinding.ActivityItemDetailBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ItemDetailActivity extends AppCompatActivity   {

    ActivityItemDetailBinding binding;
    BottomSheetDialog bottomSheetDialog;
    String name, price;
    private GestureDetector gestureDetector;

    SessionManager sessionManager;
    Bitmap imageBitmap;

    String cityName;
    private Geocoder geocoder;
    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityItemDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);
        geocoder = new Geocoder(this);


        getLocationName();


        name = getIntent().getStringExtra("Name");
        price = getIntent().getStringExtra("itemPrice");

        int image = getIntent().getIntExtra("image", 0);



        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

            dialoge();

                return true;
            }
        });


        binding.itemImageView.setImageResource(image);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            dialoge();
            }
        }, 600);

        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ItemDetailActivity.this,DashboardActivity.class));
            }
        });


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event) || super.onTouchEvent(event);
    }
    @SuppressLint("MissingInflatedId")
    private void dialoge() {


        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this,R.style.CustomBottomSheetDialog);
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottom_sheet_order_layout,findViewById(R.id.bottm_container));



        AppCompatButton btnContinue = view.findViewById(R.id.btnContinue);

        TextView textView = view.findViewById(R.id.locationMY);
        TextView itemName = view.findViewById(R.id.itemName);
        TextView itemPrice = view.findViewById(R.id.cigeratePrice);

        textView.setText(cityName);
        itemName.setText(name);
       itemPrice.setText(price);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(ItemDetailActivity.this,OrderActivity.class);
                intent.putExtra("name",name);
                intent.putExtra("price",price);

                intent.putExtra("cityName" ,cityName);
                startActivity(intent);

            }
        });
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
    }






    private void getLocationName() {

        geocoder = new Geocoder(this,Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocation(Double.parseDouble(sessionManager.fetchUserLatitude()),
                    Double.parseDouble(sessionManager.fetchUserLongitude()), 1);
            if (!addressList.isEmpty()) {


                Address address = addressList.get(0);
                String addressLine = address.getAddressLine(0);
                cityName = addressLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}