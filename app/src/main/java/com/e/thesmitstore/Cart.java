package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Mymatches2> info;
    FirebaseFirestore mydb;
    BottomNavigationView mainnavi;
    String phone;

    int total_bill=0;
    int price;
    int quan;

    TextView mrp,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView=findViewById(R.id.cart_recycler);
        mydb= FirebaseFirestore.getInstance();
        Log.d("262626","task1");
        info=new ArrayList<>();
        mrp=findViewById(R.id.cart_textview_mrp);
        total=findViewById(R.id.cart_textview_total);
        mainnavi=(BottomNavigationView)findViewById(R.id.bottomnav);
        Shared_data shared_data=new Shared_data(this);
        Log.d("262626","task2");
        phone=shared_data.getPhone();

        mainnavi.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){



                    case R.id.wishlist:
                        Intent intent2=new Intent(Cart.this, Wishlist.class);
                        startActivity(intent2);

                        finish();

                        return true;

                    case R.id.store:
                        Intent intent3=new Intent(Cart.this, Dashboard.class);
                        startActivity(intent3);

                        finish();

                        return true;




                    case R.id.account:
                        Intent intent4=new Intent(Cart.this , Account.class);
                        startActivity(intent4);

                        finish();

                        return true;





                    default:
                        return false;





                }
            }


        });


        mydb.collection("customer").document(phone).collection("cart").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.d("262626","task3");


                if(task.isSuccessful()){
                    Log.d("262626","task4");

                    for(QueryDocumentSnapshot doc:task.getResult())
                    {
                        Log.d("262626","task5");
                        Mymatches2 mymatches2=new Mymatches2();
                        Log.d("262626","task7");
                        mymatches2.setPrice(doc.getData().get("price").toString());
                        mymatches2.setQuantity_number(doc.getData().get("quantity").toString());
                        mymatches2.setName(doc.getData().get("name").toString());
                        mymatches2.setUnit(doc.getData().get("unit").toString());
                        mymatches2.setImage_link(doc.getData().get("item_image").toString());
                        mymatches2.setProduct_id(doc.getData().get("product_id").toString());

                        Log.d("262626","task6");

                        quan=Integer.valueOf(doc.getData().get("quantity").toString());
                        price=Integer.valueOf(doc.getData().get("price").toString());
                        total_bill=total_bill+(quan*price);

                        info.add(mymatches2);
                    }
                    mrp.setText(Integer.toString(total_bill));
                    total.setText(Integer.toString(total_bill));
                    Log.d("checkdata","task1");
                    recyclerView.setHasFixedSize(true);
                    Log.d("262626","task8");
                    layoutManager=new LinearLayoutManager(Cart.this);
                    recyclerView.setLayoutManager(layoutManager);
                    Log.d("262626","task9");
                    MymatchesAdapter2 mymatchesAdapter2=new MymatchesAdapter2(Cart.this,info);
                    recyclerView.setAdapter(mymatchesAdapter2);
                    Log.d("262626","task10");


                }



            }
        });





    }


}
