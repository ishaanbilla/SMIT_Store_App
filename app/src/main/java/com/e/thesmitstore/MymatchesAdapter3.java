package com.e.thesmitstore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MymatchesAdapter3 extends RecyclerView.Adapter<MymatchesAdapter3.MymatchesHolder> {


    private Context context3;
    ArrayList<Mymatches3> data3;

    public MymatchesAdapter3(Context context, ArrayList<Mymatches3> data) {
        this.context3 = context;
        this.data3 = data;
    }

    @NonNull
    @Override
    public MymatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_card,parent,false);
        MymatchesAdapter3.MymatchesHolder viewholder=new MymatchesAdapter3.MymatchesHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MymatchesHolder holder, final int position) {

          holder.name.setText(data3.get(position).getName());

//        holder.price.setText(data3.get(position).getPrice());
//
//        holder.name.setText(data3.get(position).getName());
//        holder.unit.setText(data3.get(position).getUnit());


//        try {
//            String url=data.get(position).getImage_link();
//            Picasso.get().load(url).into(holder.imageView);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        holder.item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent
//            }
//        });
//        holder.add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Shared_data shared_data=new Shared_data(context);
//                String phone=shared_data.getPhone();
//                FirebaseFirestore mydb=FirebaseFirestore.getInstance();
//                final Map<String,Object> add_data=new HashMap<>();
//                add_data.put("product_id",data.get(position).getProduct_id().toString());
//                add_data.put("quantity","1");
//                add_data.put("price",data.get(position).getPrice());
//                add_data.put("name",data.get(position).getName());
//                add_data.put("unit",data.get(position).getUnit());
//                add_data.put("item_image",data.get(position).getImage_link());
//
//                mydb.collection("customer").document(phone).collection("cart").document(data.get(position).getProduct_id()).set(add_data).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//
//                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//            }
//        });








    }

    @Override
    public int getItemCount() {
        return data3.size();
    }

    public class MymatchesHolder extends RecyclerView.ViewHolder{


            TextView name;


        public MymatchesHolder(@NonNull View itemView) {
            super(itemView);


            name=itemView.findViewById(R.id.inventory_card_name);






        }
    }


}
