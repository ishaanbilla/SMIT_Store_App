package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Seller_inventory extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Mymatches3> info;
    FirebaseFirestore mydb;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_inventory);

        recyclerView=findViewById(R.id.seller_inventory_recycler);
        mydb=FirebaseFirestore.getInstance();

        info=new ArrayList<>();


        mydb.collection("product").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                if(task.isSuccessful()){
                    Log.d("checkdata","task22");

                    for(QueryDocumentSnapshot doc:task.getResult())
                    {

                        Mymatches3 mymatches3=new Mymatches3();

                        mymatches3.setName(doc.getData().get("name").toString());






                        info.add(mymatches3);
                    }
                    Log.d("checkdata","task1");
                    recyclerView.setHasFixedSize(true);
                    layoutManager=new LinearLayoutManager(Seller_inventory.this);
                    recyclerView.setLayoutManager(layoutManager);

                    MymatchesAdapter3 mymatchesAdapter3=new MymatchesAdapter3(Seller_inventory.this,info);
                    recyclerView.setAdapter(mymatchesAdapter3);


                }



            }
        });
    }
}
