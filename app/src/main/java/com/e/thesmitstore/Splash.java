package com.e.thesmitstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class Splash extends AppCompatActivity {

    Button signin,signup,seller_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        Log.d("11111","task1");

        signin=findViewById(R.id.splash_button_signin);
        signup=findViewById(R.id.splash_button_signup);
        seller_login=findViewById(R.id.splash_button_seller_login);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Splash.this,Registration.class);
                startActivity(intent);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent intent=new Intent(Splash.this,Login.class);
                startActivity(intent);






            }
        });




        seller_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Splash.this,Seller_login.class);
                startActivity(intent);
            }
        });




    }


    @Override
    protected void onStart() {
        super.onStart();

            Shared_data shared_data=new Shared_data(Splash.this);
            if(shared_data.getSeller_status().equals("yes"))
            {
                Intent intent=new Intent(Splash.this,Seller_dashboard.class);
                startActivity(intent);
                finish();
            }
            else
            {
                Shared_data sharedlogin=new Shared_data(Splash.this);
                if(sharedlogin.getPhone()!=""){
                    Intent intent= new Intent(Splash.this,Dashboard.class);
                    startActivity(intent);
                    finish();
                }
            }







    }
}
