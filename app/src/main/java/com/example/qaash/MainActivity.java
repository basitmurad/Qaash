package com.example.qaash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import com.example.qaash.model.NetworkUtils;
import com.example.qaash.ui.MapsActivity;
import com.example.qaash.ui.PrivacyPolicyActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_GPS = 1001;
    private static final int REQUEST_LOCATION_PERMISSION = 1002;
    private AlertDialog gpsDialog;
    private SessionManager sessionManager;
    private FusedLocationProviderClient mFusedLocationClient;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sessionManager = new SessionManager(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);


        checkGpsStatus();
    }

    private void checkGpsStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        boolean isGpsEnabled = locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (isGpsEnabled) {
            // GPS is enabled, proceed with your app's logic
//            Toast.makeText(this, "GPS is enabled", Toast.LENGTH_SHORT).show();
            getLastLocation();
            dismissGpsDialog();
        } else {
            // GPS is not enabled, show a dialog to enable it
            showEnableGpsDialog();
        }
    }

    private void showEnableGpsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enable GPS")
                .setMessage("GPS is required to use this app. Please enable GPS.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Open location settings to enable GPS
                        Intent intent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivityForResult(intent, REQUEST_ENABLE_GPS);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Close the app or perform any other action
                        finish();
                    }
                })
                .setCancelable(false);

        gpsDialog = builder.show(); // Assign the dialog instance to the variable
    }

    private void dismissGpsDialog() {
        if (gpsDialog != null && gpsDialog.isShowing()) {
            gpsDialog.dismiss();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ENABLE_GPS) {
            checkGpsStatus(); // Check GPS status after returning from the location settings screen
        } else {
            Toast.makeText(this, "GPS is required to use this app.", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    @SuppressLint("MissingPermission")
    public void getLastLocation() {
        mFusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {

                Location location = task.getResult();
                if (location != null) {
                    requestNewLocation();
                }
            }
        });

    }

    @SuppressLint("MissingPermission")
    private void requestNewLocation() {

        LocationRequest locationRequest = new LocationRequest();
//        LocationRequest mLocationRequest = new LocationRequest();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5);
        locationRequest.setFastestInterval(0);
        locationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            //    Toast.makeText(MainActivity2.this, ""+mLastLocation.getLatitude()   + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();

            sessionManager.UserLongitude(String.valueOf(mLastLocation.getLongitude()));
            sessionManager.UserLatitude(String.valueOf(mLastLocation.getLatitude()));
            sessionManager.StoredData(true);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    if (firebaseUser != null) {
                        startActivity(new Intent(MainActivity.this, MapsActivity.class));
                    } else {
                        startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
                    }

                }
            }, 1500);

//            startActivity(new Intent(MainActivity.this, PrivacyPolicyActivity.class));
//            Toast.makeText(MainActivity.this, "latitide and longitude are" + mLastLocation.getLatitude() + mLastLocation.getLongitude(), Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetDialog();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetDialog();
        } else {

            getLastLocation();
//            ActivityHandlerMethod();

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNoInternetDialog();
        }
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
}
