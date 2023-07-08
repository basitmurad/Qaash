package com.example.qaash.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.qaash.R;
import com.example.qaash.databinding.ActivityPhoneNumberBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneNumberActivity extends AppCompatActivity {

    ActivityPhoneNumberBinding binding;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPhoneNumberBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        progressDialog = new ProgressDialog(this);

        binding.btnBack.setOnClickListener(v -> finish());

        binding.otpButton.setOnClickListener(v -> CheckValidaion());
    }


        public void CheckValidaion()
    {
        if(binding.edittextNumber.getText().toString().isEmpty() ||binding.editTextEmail.getText().toString().isEmpty() || binding.editTextName.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Enter number", Toast.LENGTH_SHORT).show();
        }
        else if(binding.edittextNumber.getText().toString().trim().length()!=10) {

            Toast.makeText(this, "Invalid Number", Toast.LENGTH_SHORT).show();
        }
        else
        {

            progressDialog.setTitle("Sending Otp");
            progressDialog.setMessage("PLease Wait");
            progressDialog.setCancelable(false);
            progressDialog.show();
            String number = binding.edittextNumber.getText().toString();
            String email = binding.editTextEmail.getText().toString();
            String name = binding.editTextName.getText().toString();
            PhoneAuthProvider.getInstance().verifyPhoneNumber("+92" + number,
                    60l,
                    TimeUnit.SECONDS, PhoneNumberActivity.this,
                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                        @Override
                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {



                        }

                        @Override
                        public void onVerificationFailed(@NonNull FirebaseException e) {

                            Log.d("Exception",e.getMessage());
                            Toast.makeText(PhoneNumberActivity.this, "error"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                            progressDialog.dismiss();

                            Intent intent = new Intent(PhoneNumberActivity.this,OtpActivity.class);
                            intent.putExtra("verificationID" ,s);
                            intent.putExtra("number","+92"+number);
                            intent.putExtra("email",email);
                            intent.putExtra("name",name);

                            startActivity(intent);


                        }
                    });
        }

    }
}