package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Seller_add_item_form extends AppCompatActivity {

    Button add_photo,submit;
    EditText category,name,price,unit,description;

    FirebaseFirestore mydb;

    ProgressBar progressBar;
    StorageReference storageReference;
    ImageView imageView;

    final int image_request=1;
    Uri imagepath;
    LinearLayout textphoto;
    String product_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add_item_form);

        add_photo=findViewById(R.id.seller_form_button_addphoto);
        submit=findViewById(R.id.seller_form_edit_text_submit);
        category=findViewById(R.id.seller_form_edit_text_category);
        name=findViewById(R.id.seller_form_edit_text_name);
        price=findViewById(R.id.seller_form_edit_text_price);
        unit=findViewById(R.id.seller_form_edit_text_unit);
        description=findViewById(R.id.seller_form_edit_text_description);
        imageView=findViewById(R.id.seller_form_imageview);
        storageReference= FirebaseStorage.getInstance().getReference("Image");

        textphoto=findViewById(R.id.text_to_add_photo);
        mydb=FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progress_circular);


        add_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectimage();
            }
        });






        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isvaliddata())
                {
                    submit.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    Date currentTime = Calendar.getInstance().getTime();
                    String time=currentTime.toString();

                    product_id=time;

                    final Map<String,Object> item=new HashMap<>();
                    item.put("category",category.getText().toString());

                    item.put("name",name.getText().toString());

                    item.put("price",price.getText().toString());

                    item.put("unit",unit.getText().toString());
                    item.put("status","In_stock");
                    item.put("description",description.getText().toString());
                    item.put("product_id",product_id);

                    mydb.collection("product").document(product_id).set(item).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            uploadimage();
                            submit.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(Seller_add_item_form.this, "Product Added", Toast.LENGTH_SHORT).show();
                        }
                    });




                }



            }
        });
    }



    boolean isvaliddata(){
        boolean result=true;

        if(name.getText().toString().isEmpty()){
            name.setError("Empty Field");
            result=false;
        }
        else if(!name.getText().toString().matches(("^[a-zA-Z\\s]*$"))){
            name.setError("Invalid Name");
            result=false;
        }
        else {
            name.setError(null);
        }


        if(price.getText().toString().isEmpty()){
            price.setError("Empty Field");
            result=false;
        }

        else {
            price.setError(null);
        }



        if (category.getText().toString().isEmpty()){
            category.setError("Field Required");
            result=false;
        }
        else {
            category.setError(null);
        }



        return result;
    }



    public void selectimage(){
        try {
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, image_request);
            textphoto.setVisibility(View.GONE);

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        try {


            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==image_request && resultCode==RESULT_OK && data!=null&& data.getData()!=null){
                imagepath=data.getData();
                Bitmap objectBitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);

                imageView.setImageBitmap(objectBitmap);

            }
        }
        catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }


    public void uploadimage(){
        try {
            if(imagepath!=null){

                String nameofimage=product_id+"."+getExtension(imagepath);
                final StorageReference imageref=storageReference.child(nameofimage);

                UploadTask uploadTask=imageref.putFile(imagepath);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            throw task.getException();
                        }
                        return imageref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){

                            String link=task.getResult().toString();
                            mydb.collection("product").document(product_id).update("item_image",link).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });


                        }
                        else if(!task.isSuccessful()){

                        }
                    }
                });
            }
            else {
                Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private  String getExtension(Uri uri){
        try {
            ContentResolver contentResolver=getContentResolver();
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }










}
