package com.example.qaash.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorInflater;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.qaash.R;
import com.example.qaash.databinding.ActivityOrderBinding;

public class OrderActivity extends AppCompatActivity {

    ActivityOrderBinding binding;
    String price, name ,cityName;
    Double currentPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Assuming you have a RadioGroup named radioGroup

        name = getIntent().getStringExtra("name");
        price = getIntent().getStringExtra("price");
        cityName = getIntent().getStringExtra("cityName");


          currentPrice= Double.parseDouble(price);
          binding.price.setText(String.valueOf(currentPrice));

binding.btnBack.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
        binding.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(binding.quantity.getText().toString());

                if (quantity == 1) {
                    Toast.makeText(OrderActivity.this, "Minimum Quantity Reached", Toast.LENGTH_SHORT).show();
                } else {
                    quantity--;
                    binding.quantity.setText(String.valueOf(quantity));


                    double minusPrice = currentPrice*quantity;

                    // Update the price in the TextView
                    binding.price.setText(String.valueOf(minusPrice));

                }

                Animation animation = AnimationUtils.loadAnimation(OrderActivity.this, R.anim.buttoneffect);
                binding.minus.startAnimation(animation);
            }
        });

        binding.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = Integer.parseInt(binding.quantity.getText().toString());
                quantity++;
                binding.quantity.setText(String.valueOf(quantity));

                double addPrice = currentPrice*quantity;


                binding.price.setText(String.valueOf(addPrice));

                Animation animation = AnimationUtils.loadAnimation(OrderActivity.this, R.anim.buttoneffect);
                binding.add.startAnimation(animation);
            }
        });


        binding.appCompatButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                    // Get the quantity
                    int quantity12 = Integer.parseInt(binding.quantity.getText().toString());

                   double totalprice = (quantity12 * Integer.parseInt(price))+50;



                    Intent intent = new Intent(OrderActivity.this,PaymentActivity.class);
                    intent.putExtra("totalPrice" ,totalprice);
                    intent.putExtra("cityName",cityName);
                    intent.putExtra("name",name);


                    startActivity(intent);


                    }





        });
    }




}