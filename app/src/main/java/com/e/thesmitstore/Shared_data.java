package com.e.thesmitstore;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ScrollView;

public class Shared_data {

    Context context;


    public void removeUser(){
        sharedPreferences.edit().clear().commit();
    }

    public String getPhone() {
        phone=sharedPreferences.getString("phone","");
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
        sharedPreferences.edit().putString("phone",phone).commit();
    }

    private String phone;

    public String getSeller_status() {
        seller_status=sharedPreferences.getString("seller_status","");
        return seller_status;
    }

    public void setSeller_status(String seller_status) {
        this.seller_status = seller_status;
        sharedPreferences.edit().putString("seller_status",seller_status).commit();
    }

    private String seller_status;
    SharedPreferences sharedPreferences;


    public Shared_data(Context context){
        this.context=context;

        sharedPreferences=context.getSharedPreferences("phone",Context.MODE_PRIVATE);
        sharedPreferences=context.getSharedPreferences("seller_status",Context.MODE_PRIVATE);
    }
}
