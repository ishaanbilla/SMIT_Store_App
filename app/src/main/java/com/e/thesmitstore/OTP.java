package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;



public class OTP extends AppCompatActivity {

    private  String verificationId;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private EditText editText_otp;
    String phonenumber;
    FirebaseFirestore mydb;
    String phone;
    TextView resend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        mAuth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressbar);
        editText_otp=(EditText) findViewById(R.id.edit_text_otp);
        mydb=FirebaseFirestore.getInstance();
        phonenumber=getIntent().getStringExtra("phonenumber");
        phone=getIntent().getStringExtra("phone1");
        resend=findViewById(R.id.buttonResendOtp);
        sendVerificationCode(phonenumber);


        resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendVerificationCode(phonenumber);
                Toast.makeText(OTP.this, "OTP Send Successfully", Toast.LENGTH_SHORT).show();
            }
        });

        findViewById(R.id.otp_continue).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editText_otp.getText().toString().isEmpty()) {


                    String code = editText_otp.getText().toString().trim();
                    if (code.isEmpty() || code.length() < 6)
                    {
                        editText_otp.setError("Enter OTP");
                        editText_otp.requestFocus();
                    }


                    verifyCode(code);

                }
                else {
                    Toast.makeText(OTP.this, "Enter OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void verifyCode(String code){
        PhoneAuthCredential credential= PhoneAuthProvider.getCredential(verificationId,code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential){
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
//                            Sharedlogin sharedlogin=new Sharedlogin(otppage.this);
//                            sharedlogin.setPhone(phonenumber);
                            final Map<String,Object> user= new HashMap<>();
                            user.put("otp","verified");
                            mydb.collection("customer").document(phone).update(user);
                            Intent intent=new Intent(OTP.this, Dashboard.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }
                        else {
                            Toast.makeText(OTP.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }


    private void sendVerificationCode(String number){
        progressBar.setVisibility(View.VISIBLE);

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                30,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack

        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationId=s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String code=phoneAuthCredential.getSmsCode();
            if(code !=null){
                editText_otp.setText(code);

                verifyCode(code);
            }

        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

            Toast.makeText(OTP.this,e.getMessage(), Toast.LENGTH_LONG).show();

        }
    };





}

