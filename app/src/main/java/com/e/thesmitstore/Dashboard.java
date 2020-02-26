package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Dashboard extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Mymatches> info;
    FirebaseFirestore mydb;

    BottomNavigationView mainnavi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        recyclerView=findViewById(R.id.dashboard_recycler);
        mydb=FirebaseFirestore.getInstance();

        info=new ArrayList<>();

        mainnavi=(BottomNavigationView)findViewById(R.id.bottomnav);


        mainnavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){



                    case R.id.wishlist:
                        Intent intent2=new Intent(Dashboard.this, Wishlist.class);
                        startActivity(intent2);

                        finish();

                        return true;



                    case R.id.cart:
                        Intent intent3=new Intent(Dashboard.this , Cart.class);
                        startActivity(intent3);

                        finish();

                        return true;


                    case R.id.account:
                        Intent intent4=new Intent(Dashboard.this , Account.class);
                        startActivity(intent4);

                        finish();

                        return true;





                    default:
                        return false;





                }
            }


        });



        mydb.collection("product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    Log.d("checkdata","task22");

                    for(QueryDocumentSnapshot doc:task.getResult())
                    {

                        Mymatches mymatches=new Mymatches();
                        mymatches.setPrice(doc.getData().get("price").toString());
                        mymatches.setName(doc.getData().get("name").toString());
                        mymatches.setUnit(doc.getData().get("unit").toString());
                        mymatches.setImage_link(doc.getData().get("item_image").toString());
                        mymatches.setProduct_id(doc.getData().get("product_id").toString());





                        info.add(mymatches);
                    }
                    Log.d("checkdata","task1");
                    recyclerView.setHasFixedSize(true);
                    layoutManager=new LinearLayoutManager(Dashboard.this);
                    recyclerView.setLayoutManager(layoutManager);

                    MymatchesAdapter mymatchesAdapter=new MymatchesAdapter(Dashboard.this,info);
                    recyclerView.setAdapter(mymatchesAdapter);


                }



            }
        });









    }
}
