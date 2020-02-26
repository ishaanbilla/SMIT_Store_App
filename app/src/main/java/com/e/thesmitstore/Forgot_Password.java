package com.e.thesmitstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Forgot_Password extends AppCompatActivity {


    Button send;
    EditText phonenumber;
    FirebaseFirestore mydb;
    String user_phone_number;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);

        progressBar=findViewById(R.id.forgot_password_progressbar);

        send=(Button)findViewById(R.id.forgotpassword_button_send);
        phonenumber=(EditText)findViewById(R.id.forgotpassword_et_phonenumber);

        mydb=FirebaseFirestore.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                Log.d("789456","task1");

                if (!phonenumber.getText().toString().isEmpty()) {

                    Log.d("789456","task2");

                    mydb.collection("customer").document(phonenumber.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                            if (task.isSuccessful()) {
                                DocumentSnapshot documentSnapshot = task.getResult();

                                Log.d("789456","task3");

                                if (documentSnapshot.exists()) {
                                    Log.d("789456","task4");
                                    user_phone_number = "+91" + documentSnapshot.getId();
                                    Log.d("789456","task5");
                                    Intent intent = new Intent(Forgot_Password.this, Forgot_Password_otp.class);
                                    Log.d("789456","task6");
                                    intent.putExtra("phonenumber", user_phone_number);
                                    Log.d("789456","task7");
                                    intent.putExtra("phonenumber_for_resetpage", documentSnapshot.getId());
                                    Log.d("789456","task8");
                                    startActivity(intent);
                                    Log.d("789456","task9");

                                } else {
                                    progressBar.setVisibility(View.GONE);
                                    send.setVisibility(View.VISIBLE);
                                    Toast.makeText(Forgot_Password.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                                }
                            }

                        }
                    });
                }
                else {
                    send.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(Forgot_Password.this, "Enter Phone Number", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}
