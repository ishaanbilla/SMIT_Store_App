package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class Account extends AppCompatActivity {

    Button logout;
    BottomNavigationView mainnavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        logout=findViewById(R.id.account_logout);



        mainnavi=(BottomNavigationView)findViewById(R.id.bottomnav);



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared_data sharedlogin=new Shared_data(Account.this);
                sharedlogin.removeUser();

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Account.this,Splash.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });


        mainnavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){



                    case R.id.store:
                        Intent intent2=new Intent(Account.this, Dashboard.class);
                        startActivity(intent2);

                        finish();

                        return true;



                    case R.id.cart:
                        Intent intent3=new Intent(Account.this , Cart.class);
                        startActivity(intent3);

                        finish();

                        return true;


                    case R.id.wishlist:
                        Intent intent5=new Intent(Account.this , Wishlist.class);
                        startActivity(intent5);

                        finish();

                        return true;





                    default:
                        return false;





                }
            }


        });


    }
}
