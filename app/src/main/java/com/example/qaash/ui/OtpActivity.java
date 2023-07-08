package com.example.qaash.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.qaash.SessionManager;
import com.example.qaash.databinding.ActivityOtpBinding;
import com.example.qaash.model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import in.aabhasjindal.otptextview.OTPListener;

public class OtpActivity extends AppCompatActivity {

    ActivityOtpBinding binding;
    String verificatonID, number, randomUUId, email, name;
    PhoneAuthProvider.ForceResendingToken ResendToken;
    DatabaseReference databaseRef;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        databaseRef = FirebaseDatabase.getInstance().getReference();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sign In ");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);


        verificatonID = getIntent().getStringExtra("verificationID");
        number = (getIntent().getStringExtra("number"));
        name = (getIntent().getStringExtra("name"));
        email = (getIntent().getStringExtra("email"));

        randomUUId = UUID.randomUUID().toString();

        SessionManager sessionManager = new SessionManager(this);

        sessionManager.UserId(randomUUId);
        binding.number.setText(number);


        countDownTimer();
        OtpListner();



        binding.btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OtpAgain();
                OtpListner();

                countDownTimer();
            }
        });


        binding.btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.btnResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                countDownTimer();

                OtpListner();


            }
        });
    }


    private void countDownTimer() {
        new CountDownTimer(60000, 1000) {

            @Override
            public void onTick(long l) {
                binding.counter.setText(String.valueOf(l / 1000));

            }

            @Override
            public void onFinish() {
                binding.btnResend.setVisibility(View.VISIBLE);

            }
        }.start();
    }

    private void OtpListner() {
        binding.otpView.setOtpListener(new OTPListener() {
            @Override
            public void onInteractionListener() {


            }

            @Override
            public void onOTPComplete(String otp) {

                if (verificatonID != null) {
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificatonID, otp);
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                progressDialog.show();
                                sendDataToFirebase();


                            } else {
                                Toast.makeText(OtpActivity.this, "verification code invalid", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(OtpActivity.this, "Exception" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(OtpActivity.this, "Exception" + e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });


    }

    private void sendDataToFirebase() {


        Users users = new Users(name, email, number);
        databaseRef.child("user").child(randomUUId).setValue(users)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        progressDialog.dismiss();
                      Intent intent  = new Intent(OtpActivity.this,MapsActivity.class);

                        intent.putExtra("randomUUID", randomUUId);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(OtpActivity.this, "Try Again...\n something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void OtpAgain() {

        PhoneAuthProvider.getInstance().verifyPhoneNumber("+92" + number
                , 60l
                , TimeUnit.SECONDS
                , OtpActivity.this
                , new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    @Override
                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                    }

                    @Override
                    public void onVerificationFailed(@NonNull FirebaseException e) {

                    }

                    @Override
                    public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {


                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                        binding.otpView.requestFocusOTP();
                        startActivity(new Intent(OtpActivity.this, MapsActivity.class));
                    }
                });
    }
}