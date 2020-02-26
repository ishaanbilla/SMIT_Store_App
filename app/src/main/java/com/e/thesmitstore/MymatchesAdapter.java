package com.e.thesmitstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MymatchesAdapter extends RecyclerView.Adapter<MymatchesAdapter.MymatchesHolder> {


    private Context context;
    ArrayList<Mymatches> data;

    public MymatchesAdapter(Context context, ArrayList<Mymatches> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MymatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card,parent,false);
        MymatchesAdapter.MymatchesHolder viewholder=new MymatchesAdapter.MymatchesHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MymatchesHolder holder, final int position) {

        holder.price.setText(data.get(position).getPrice());

        holder.name.setText(data.get(position).getName());
        holder.unit.setText(data.get(position).getUnit());


        try {
            String url=data.get(position).getImage_link();
            Picasso.get().load(url).into(holder.imageView);
        } catch (Exception e) {
            e.printStackTrace();
        }

//        holder.item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent
//            }
//        });
        holder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog=new ProgressDialog(context);
                progressDialog.setMessage("Adding to the cart");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Shared_data shared_data=new Shared_data(context);
                String phone=shared_data.getPhone();
                FirebaseFirestore mydb=FirebaseFirestore.getInstance();
                final Map<String,Object> add_data=new HashMap<>();
                add_data.put("product_id",data.get(position).getProduct_id().toString());
                add_data.put("quantity","1");
                add_data.put("price",data.get(position).getPrice());
                add_data.put("name",data.get(position).getName());
                add_data.put("unit",data.get(position).getUnit());
                add_data.put("item_image",data.get(position).getImage_link());

                mydb.collection("customer").document(phone).collection("cart").document(data.get(position).getProduct_id()).set(add_data).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(context, "Added to cart", Toast.LENGTH_SHORT).show();
                        progressDialog.hide();

                    }
                });
            }
        });








    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MymatchesHolder extends RecyclerView.ViewHolder{

        TextView price,name,unit;
        ImageView imageView;
       Button add;



        public MymatchesHolder(@NonNull View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.item_card_price);
            name=itemView.findViewById(R.id.item_card_name);
            unit=itemView.findViewById(R.id.item_card_unit);
            imageView=itemView.findViewById(R.id.item_card_image);
            add=itemView.findViewById(R.id.item_card_button_add);







        }
    }


}
