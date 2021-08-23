package com.example.arjunv.lpg;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.util.UidVerifier;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerifyPhoneActivity extends AppCompatActivity{
    Toolbar toolbar;

    private String verificationId;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();
    private ProgressBar progressBar;
    private EditText editText;
    private String phonenumber;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_phone);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);





        progressBar=findViewById(R.id.progressBar2);
        editText=findViewById(R.id.editTextOtp);
        phonenumber = getIntent().getStringExtra("phonenumber");

        sendVerificationCode(phonenumber);




        findViewById(R.id.buttonLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String code=editText.getText().toString().trim();
                if(code.isEmpty()||code.length()<6){
                    editText.setError("Enter Code...");
                    editText.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                verifyCode(code);

            }
        });



    }






    private void verifyCode(String code){
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {

                            Intent intent = new Intent(VerifyPhoneActivity.this, ProfileActivity.class);
                            intent.putExtra("phoneNumber", phonenumber);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{

                            Toast.makeText(VerifyPhoneActivity.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


        private void sendVerificationCode (String number){
            progressBar.setVisibility(View.VISIBLE);
            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    number,
                    60,
                    TimeUnit.SECONDS,
                    TaskExecutors.MAIN_THREAD,
                    mCallBack
            );
        }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId=s;

        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code=phoneAuthCredential.getSmsCode();
            if(code != null){
                editText.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerifyPhoneActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };
}
