package com.example.qaash.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.qaash.databinding.ActivityTermsAndConditionBinding;

public class TermsAndConditionActivity extends AppCompatActivity {
ActivityTermsAndConditionBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsAndConditionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());






        binding.btnNext.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (!binding.radioButton2.isChecked() && !binding.radioButton.isChecked())
        {

            Toast.makeText(TermsAndConditionActivity.this, "Select Your Age", Toast.LENGTH_SHORT).show();
            return;

        }
        else if (binding.radioButton2.isChecked())
        {
            Toast.makeText(TermsAndConditionActivity.this, "Age Must Be Greater Than 18", Toast.LENGTH_SHORT).show();
        }


        else {
            startActivity(new Intent(TermsAndConditionActivity.this,Login2Activity.class));
        }


        }
});
    }





}