package com.e.thesmitstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Seller_dashboard extends AppCompatActivity {

    LinearLayout add_item,inventory,orders,profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_dashboard);

        add_item=findViewById(R.id.seller_dashboard_additem);
        inventory=findViewById(R.id.seller_dashboard_inventory);
        orders=findViewById(R.id.seller_dashboard_orders);
        profile=findViewById(R.id.seller_dashboard_profile);

        add_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Seller_dashboard.this,Seller_add_item_form.class);
                startActivity(intent);
            }
        });


        inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Seller_dashboard.this,Seller_inventory.class);
                startActivity(intent);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Seller_dashboard.this,Seller_orders.class);
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Seller_dashboard.this,Seller_profile.class);
                startActivity(intent);
            }
        });


    }
}
