package com.e.thesmitstore;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MymatchesAdapter2 extends RecyclerView.Adapter<MymatchesAdapter2.MymatchesHolder> {


    private Context context2;
    ArrayList<Mymatches2> data2;

    public MymatchesAdapter2(Context context, ArrayList<Mymatches2> data) {
        this.context2 = context;
        this.data2 = data;
    }

    @NonNull
    @Override
    public MymatchesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card2,parent,false);
        MymatchesAdapter2.MymatchesHolder viewholder=new MymatchesAdapter2.MymatchesHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MymatchesHolder holder, final int position) {

        Log.d("262626","task11");
        Log.d("262626",data2.get(position).getPrice());
        holder.price.setText(data2.get(position).getPrice());
        holder.quantity_number.setText(data2.get(position).getQuantity_number());
        Log.d("262626","task12");
        holder.name.setText(data2.get(position).getName());
        holder.unit.setText(data2.get(position).getUnit());


        try {
            String url=data2.get(position).getImage_link();
            Picasso.get().load(url).into(holder.img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseFirestore mydb=FirebaseFirestore.getInstance();
                final ProgressDialog progressDialog=new ProgressDialog(context2);
                progressDialog.setMessage("Updating");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Shared_data shared_data=new Shared_data(context2);
                final String phone=shared_data.getPhone();
                mydb.collection("customer").document(phone).collection("cart").document(data2.get(position).getProduct_id()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if (documentSnapshot.exists())
                            {
                                int prev=Integer.valueOf(documentSnapshot.getData().get("quantity").toString());
                                int newvalue=prev+1;
                                final String newval=Integer.toString(newvalue);
                                mydb.collection("customer").document(phone).collection("cart").document(data2.get(position).getProduct_id()).update("quantity",newval).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder.quantity_number.setText(newval);
                                        progressDialog.hide();
                                    }
                                });
                            }
                        }

                    }
                });



            }
        });


        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseFirestore mydb=FirebaseFirestore.getInstance();
                final ProgressDialog progressDialog=new ProgressDialog(context2);
                progressDialog.setMessage("Updating");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Shared_data shared_data=new Shared_data(context2);
                final String phone=shared_data.getPhone();
                mydb.collection("customer").document(phone).collection("cart").document(data2.get(position).getProduct_id()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot=task.getResult();
                            if (documentSnapshot.exists()) {
                                int prev = Integer.valueOf(documentSnapshot.getData().get("quantity").toString());
                                int newvalue = prev - 1;
                                if (newvalue == 0) {
                                    holder.card.setVisibility(View.GONE);
                                    holder.quantity_number.setText(Integer.toString(newvalue));

                                    mydb.collection("customer").document(phone).collection("cart").document(data2.get(position).getProduct_id()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.hide();
                                        }
                                    });


                                } else
                                    {


                                final String newval = Integer.toString(newvalue);
                                mydb.collection("customer").document(phone).collection("cart").document(data2.get(position).getProduct_id()).update("quantity", newval).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        holder.quantity_number.setText(newval);
                                        progressDialog.hide();
                                    }
                                });
                                }
                            }
                        }

                    }
                });



            }
        });

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
        return data2.size();
    }

    public class MymatchesHolder extends RecyclerView.ViewHolder{

        TextView price,name,unit,quantity_number;
        ImageView img;

        Button plus,minus;

        RelativeLayout card;



        public MymatchesHolder(@NonNull View itemView) {
            super(itemView);

            price=itemView.findViewById(R.id.card_price);
            name=itemView.findViewById(R.id.card_name);
            unit=itemView.findViewById(R.id.card_unit);
            img=itemView.findViewById(R.id.card_image);
            quantity_number=itemView.findViewById(R.id.quatity_number);

            plus=itemView.findViewById(R.id.card_button_plus);
            minus=itemView.findViewById(R.id.card_button_minus);
            card=itemView.findViewById(R.id.card_relative_layout);








        }
    }


}
