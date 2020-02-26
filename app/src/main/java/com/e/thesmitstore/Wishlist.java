package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Wishlist extends AppCompatActivity {

    BottomNavigationView mainnavi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);


        mainnavi=(BottomNavigationView)findViewById(R.id.bottomnav);


        mainnavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){



                    case R.id.store:
                        Intent intent1=new Intent(Wishlist.this , Dashboard.class);
                        startActivity(intent1);

                        finish();

                        return true;


                    case R.id.cart:
                        Intent intent3=new Intent(Wishlist.this , Cart.class);
                        startActivity(intent3);

                        finish();

                        return true;


                    case R.id.account:
                        Intent intent4=new Intent(Wishlist.this , Account.class);
                        startActivity(intent4);

                        finish();

                        return true;





                    default:
                        return false;





                }
            }


        });



    }
}
