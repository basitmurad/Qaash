package com.example.qaash.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.qaash.SessionManager;
import com.example.qaash.databinding.ActivityMapsBinding;
import com.example.qaash.model.NetworkUtils;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity  {

    ActivityMapsBinding binding;

    private GoogleMap MygoogleMap;


    SessionManager sessionManager;
    List<LatLng> gilgitCoordinates;
    List<LatLng> uni;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//
        sessionManager = new SessionManager(this);


        // university Qaid azam
//        LatLng topLeft = new LatLng(33.7530, 74.2892);       // Top left corner
//            LatLng topRight = new LatLng(33.7530, 73.1588);      // Top right corner
//            LatLng bottomRight = new LatLng(33.7435, 73.1588);   // Bottom right corner
//            LatLng bottomLeft = new LatLng(33.7435, 73.1225);
//            LatLngBounds serviceArea = new LatLngBounds(bottomLeft, topRight);

        //for gilgit
        LatLng topLeft = new LatLng(35.9295, 74.2892);       // Top left corner
        LatLng topRight = new LatLng(35.9295, 74.3295);      // Top right corner
        LatLng bottomRight = new LatLng(35.9095, 74.3295);   // Bottom right corner
        LatLng bottomLeft = new LatLng(35.9095, 74.2892);
        LatLngBounds serviceArea = new LatLngBounds(bottomLeft, topRight);

//        35.907426350173424, 74.36168286939404

        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                MygoogleMap = googleMap;


                // for Islamabad

//                LatLng location = new LatLng(33.7530, 73.1225);
//
//                MarkerOptions markerOptions = new MarkerOptions()
//                        .position(new LatLng(33.7530, 73.1225))
//                        .title("position");
//                googleMap.addMarker(markerOptions);
//
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));
//                uni = new ArrayList<>();
//                uni.add(new LatLng(33.7530, 73.1225));
//                uni.add(new LatLng(33.7530, 73.1588));
//                uni.add(new LatLng(33.7435, 73.1588));
//                uni.add(new LatLng(33.7435, 73.1225));
//
//                PolygonOptions polygonOptions = new PolygonOptions()
//                        .addAll(uni)
//                        .strokeColor(Color.RED);
//                googleMap.addPolygon(polygonOptions);
//
//
//
//                if (!serviceArea.contains(new LatLng(Double.parseDouble(sessionManager.fetchUserLatitude()), Double.parseDouble(sessionManager.fetchUserLongitude())))) {
//                    Toast.makeText(MapsActivity.this, "User is outside the region", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle(" User Outside the region");
//                    builder.setMessage("There is no service in your area");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            finishAffinity();
//                        }
//                    });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.setCancelable(false);
//                    dialog.show();
//
//                } else {
//                    Toast.makeText(MapsActivity.this, "User within the region", Toast.LENGTH_SHORT).show();
//
//                    binding.btnContinue.setVisibility(View.VISIBLE);
//                    binding.btnContinue.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(MapsActivity.this, DashboardActivity.class));
//                        }
//                    });
//
//
//                }
//


                // for gilgit


//


                LatLng location = new LatLng(35.9208, 74.3142);
                MarkerOptions markerOptions = new MarkerOptions()
                        .position(new LatLng(35.9208, 74.3142))
                        .title("position");
                googleMap.addMarker(markerOptions);

                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 13));

                gilgitCoordinates = new ArrayList<>();
                gilgitCoordinates.add(new LatLng(35.9295, 74.2892));  // Top left corner
                gilgitCoordinates.add(new LatLng(35.9295, 74.3295));  // Top right corner
                gilgitCoordinates.add(new LatLng(35.9095, 74.3295));  // Bottom right corner
                gilgitCoordinates.add(new LatLng(35.9095, 74.2892));  //Bottom left cooner
                PolygonOptions polygonOptions = new PolygonOptions()
                        .addAll(gilgitCoordinates)
                        .strokeColor(Color.RED);
                googleMap.addPolygon(polygonOptions);

                    binding.btnContinue.setVisibility(View.VISIBLE);
                    binding.btnContinue.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(MapsActivity.this, DashboardActivity.class));
                        }
                    });

//                if (!serviceArea.contains(new LatLng(Double.parseDouble(sessionManager.fetchUserLatitude()), Double.parseDouble(sessionManager.fetchUserLongitude())))) {
//                    Toast.makeText(MapsActivity.this, "User is outside the region", Toast.LENGTH_SHORT).show();
//                    AlertDialog.Builder builder = new AlertDialog.Builder(MapsActivity.this);
//                    builder.setTitle(" User Outside the region");
//                    builder.setMessage("There is no service in your area");
//                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
//                            finishAffinity();
//                        }
//                    });
//
//                    AlertDialog dialog = builder.create();
//                    dialog.setCancelable(false);
//                    dialog.show();
//
//                } else {
//                    Toast.makeText(MapsActivity.this, "User within the region", Toast.LENGTH_SHORT).show();
//
//                    binding.btnContinue.setVisibility(View.VISIBLE);
//                    binding.btnContinue.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            startActivity(new Intent(MapsActivity.this, DashboardActivity.class));
//                        }
//                    });


               // }
                }
        });






    }

    @Override
    protected void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    binding.mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    binding.mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }



    private void showNoInternetDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finishAffinity();
                    }
                })
                .setCancelable(false)
                .show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}