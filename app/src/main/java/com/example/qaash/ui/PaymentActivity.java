package com.example.qaash.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.qaash.R;
import com.example.qaash.databinding.ActivityPaymentBinding;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PaymentActivity extends AppCompatActivity {

    ActivityPaymentBinding binding;
    Double totalPrice;
    String cityName, flevername;
    String addressOfBuyer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        totalPrice = getIntent().getDoubleExtra("totalPrice", 0.0);
        cityName = getIntent().getStringExtra("cityName");
        flevername = getIntent().getStringExtra("name");


        binding.btnCashLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.editTextAddress.getText().toString().isEmpty()) {
                    Toast.makeText(PaymentActivity.this, "Enter You address to delivered order", Toast.LENGTH_SHORT).show();
                }
                else {
                    addressOfBuyer =binding.editTextAddress.getText().toString();
                    openWhatsApp();
                }
            }
        });


    }

    private void openWhatsApp() {
//        String phoneNumber = "+923100333318"; // Replace with the desired WhatsApp number

        String phoneNumber = "+923554646040";


        String message = "Total Price: \n" + totalPrice + "\n"
                + "City Name:\n " + cityName + "\n"
                + "Cigarette Name: " + flevername  ;

        String messege1 = "City Name : \n" + cityName + "\n" + "Address of Buyer: \n" + addressOfBuyer + " \n"
                + "Total Price: \n" + totalPrice + "\n" + "Cigarette Name: \n" + flevername;

        Uri uri = Uri.parse("https://api.whatsapp.com/send?phone=" + phoneNumber + "&text=" + Uri.encode(messege1));
        Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(sendIntent);
    }


}