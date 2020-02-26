package com.e.thesmitstore;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class Reset_Password extends AppCompatActivity {


    EditText newpassword,confirmpassword;
    Button changepassword;
    TextView backtologin;
    FirebaseFirestore mydb;
    boolean isInternetPresent=false;

    String phonenumber;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset__password);



        newpassword=(EditText)findViewById(R.id.resetpass_et_newpassword);
        confirmpassword=(EditText)findViewById(R.id.resetpass_et_confirmpassword);
        changepassword=(Button)findViewById(R.id.resetpass_button_changepassword);
        backtologin=(TextView)findViewById(R.id.resetpass_tv_backtologin);
        progressBar=findViewById(R.id.reset_password_progressbar);

        phonenumber=getIntent().getStringExtra("phonenumber_for_resetpage");
        Log.d("***",phonenumber);

        Log.d("***","task1");

        mydb=FirebaseFirestore.getInstance();
//        final DocumentReference docref=mydb.collection("UserRegg").document(phonenumber);
//        docref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
////                if(task.isSuccessful()){
////
////                    DocumentSnapshot doc=task.getResult();
////                    if(doc.exists()){
////
////                        Toast.makeText(resetpassword.this, "got the value", Toast.LENGTH_SHORT).show();
////
////
////
////                    }
////                }
//
//
//            }
//        });


        changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("***","task2");
                changepassword.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);

                mydb.collection("customer").document(phonenumber).update("password",newpassword.getText().toString())


                        .addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {
                                progressBar.setVisibility(View.GONE);
                                changepassword.setVisibility(View.VISIBLE);
                                Toast.makeText(Reset_Password.this, "Password Changed Successfully", Toast.LENGTH_SHORT).show();
                                Log.d("#1254", "success");

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressBar.setVisibility(View.GONE);
                        changepassword.setVisibility(View.VISIBLE);
                        Toast.makeText(Reset_Password.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();

                        Log.d("#1254", "fail");

                    }
                });
            }
        });


        findViewById(R.id.resetpass_tv_backtologin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Reset_Password.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }







    boolean validinput(){
        boolean result=true;

        if(newpassword.getText().toString().isEmpty()){
            newpassword.setError("Required Field");
            result=false;

        }else{
            newpassword.setError(null);
        }
        if (confirmpassword.getText().toString().isEmpty()){
            confirmpassword.setError("Required Field");
            result=false;
        }else if(!confirmpassword.getText().toString().matches(newpassword.getText().toString())){
            confirmpassword.setError("Password Did Not Match");
            result=false;
        }
        else {
            confirmpassword.setError(null);
        }

        return result;

    }
}
