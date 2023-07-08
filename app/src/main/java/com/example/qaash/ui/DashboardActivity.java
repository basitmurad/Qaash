package com.example.qaash.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.qaash.CustumAdapter;
import com.example.qaash.R;
import com.example.qaash.SessionManager;
import com.example.qaash.databinding.ActivityDashboardBinding;
import com.example.qaash.model.ItemDetail;
import com.example.qaash.model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;

    CustumAdapter custumAdapter;
    ArrayList<ItemDetail> list;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    SessionManager sessionManager;
    String currentUserId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sessionManager = new SessionManager(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


        currentUserId = sessionManager.fetchUserId();
//        Toast.makeText(this, "" + currentUserId, Toast.LENGTH_SHORT).show();
        getDataFromFirebase();

        SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String imageUriString = preferences.getString("imageUri", null);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);

            // Set the image URI in the ImageView using Glide
            Glide.with(this)
                    .load(imageUri)
                    .into(binding.profileImage);
        }

        binding.profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent, 1);
            }
        });




        binding.searchview.clearFocus();

        list = new ArrayList<>();
        list.add(new ItemDetail("Benson", "30", R.drawable.basen));
        list.add(new ItemDetail("Gold Leaf", "30", R.drawable.gold_flake));
        list.add(new ItemDetail("Gold Flake", "30", R.drawable.gold_flake));
        list.add(new ItemDetail("Embassy", "30", R.drawable.embacy));
        list.add(new ItemDetail("Morven Gold", "30", R.drawable.marlboro_gold));
        list.add(new ItemDetail("Gold Street", "30", R.drawable.gold_street));
        list.add(new ItemDetail("Capstan", "30", R.drawable.capistan));
        list.add(new ItemDetail("Tander", "30", R.drawable.tender));
        list.add(new ItemDetail("Kisan", "30", R.drawable.kisan));
        list.add(new ItemDetail("Red & white", "30", R.drawable.red_and_white));
        list.add(new ItemDetail("Mond", "30", R.drawable.mond));
        list.add(new ItemDetail("Malbro gold", "30", R.drawable.marlboro_gold));
        list.add(new ItemDetail("Dunhil", "30", R.drawable.dunhil));
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        custumAdapter = new CustumAdapter(list, this);
        binding.recycler.setAdapter(custumAdapter);
        binding.searchview.setQueryHint("Search here");
        binding.searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterData(newText);
                return true;
            }
        });



    }

    private void getDataFromFirebase() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersRef = database.getReference("user").child("");

        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ; // Replace with the ID of the current user
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Users user = snapshot1.getValue(Users.class);
                    String userId = snapshot1.getKey(); // Assuming the key represents the user ID

                    if (userId.equals(currentUserId)) {
                        String name = user.getUserName();
//                        Toast.makeText(DashboardActivity.this, "" + name, Toast.LENGTH_SHORT).show();
                        binding.userName.setText(name);
                        break; // Exit the loop once the current user is found
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle error
            }
        });

    }


    private void filterData(String query) {
        ArrayList<ItemDetail> filteredList = new ArrayList<>();
        for (ItemDetail item : list) {
            if (item.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(item);
            }
            custumAdapter.filterList(filteredList);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

      finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();

            // Save the image URI to SharedPreferences
            SharedPreferences preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("imageUri", imageUri.toString());
            editor.apply();

            // Set the selected image in the ImageView
            Glide.with(this)
                    .load(imageUri)
                    .into(binding.profileImage);
        }
    }


    public void buttonBack(View view) {
        finish();
    }
}
