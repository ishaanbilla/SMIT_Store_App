package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Seller_login extends AppCompatActivity {

    Button signin,create;
    EditText phone,password;
    ProgressBar progressBar;
    FirebaseFirestore mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);

        signin=findViewById(R.id.seller_login_button_signin);
        phone=findViewById(R.id.seller_login_edittext_phone);
        password=findViewById(R.id.seller_login_edittext_password);
        progressBar=findViewById(R.id.seller_login_progressbar);

        mydb=FirebaseFirestore.getInstance();



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidlogin()){
                    signin.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    mydb.collection("seller").document(phone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot documentSnapshot=task.getResult();
                                if(documentSnapshot.exists())
                                {

                                        String pass=documentSnapshot.getData().get("seller_password").toString();

                                        if (pass.equals(password.getText().toString()))
                                        {
                                            Shared_data shared_data=new Shared_data(Seller_login.this);
                                            shared_data.setPhone(phone.getText().toString());
                                            shared_data.setSeller_status("yes");


                                            Intent intent=new Intent(Seller_login.this,Seller_dashboard.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            progressBar.setVisibility(View.GONE);
                                            signin.setVisibility(View.VISIBLE);
                                            Toast.makeText(Seller_login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                        }

                                   
                                }
                                else {
                                    progressBar.setVisibility(View.GONE);
                                    signin.setVisibility(View.VISIBLE);
                                    Toast.makeText(Seller_login.this, "User not registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });







                }
                else {
                    Toast.makeText(Seller_login.this, "Check Entered Details", Toast.LENGTH_SHORT).show();
                }





            }
        });


    }



    boolean isValidlogin(){
        boolean result=true;
        if(phone.getText().toString().isEmpty()){
            phone.setError("Empty Field");
            result=false;
        }
        else if (phone.getText().toString().length()<10){

            phone.setError("Invalid Number");
            result=false;
        }
        else {
            phone.setError(null);
        }

        if (password.getText().toString().isEmpty()){
            password.setError("Field Required");
            result=false;
        }
        else {
            password.setError(null);
        }

        return result;
    }
}
