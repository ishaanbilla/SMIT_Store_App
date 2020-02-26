package com.e.thesmitstore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    EditText name,phone,password,confirm_password;
    Button signin,signup;
    ProgressBar pBar;

    FirebaseFirestore mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name=findViewById(R.id.registration_edit_text_name);
        phone=findViewById(R.id.registration_edit_text_phone);
        password=findViewById(R.id.registration_edit_text_password);
        confirm_password=findViewById(R.id.registration_edit_text_confirm_password);
        signin=findViewById(R.id.registration_button_signin);
        signup=findViewById(R.id.registration_button_signup);

        pBar=findViewById(R.id.progress_circular);
        mydb=FirebaseFirestore.getInstance();

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Registration.this,Login.class);
                startActivity(intent);
                finish();
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                if (validRegistration())
                {
                    signup.setVisibility(View.GONE);
                    pBar.setVisibility(View.VISIBLE);


                    final Map<String,Object> user=new HashMap<>();
                    user.put("name",name.getText().toString());

                    user.put("phone",phone.getText().toString());

                    user.put("password",password.getText().toString());

                    user.put("otp","no");


                    mydb.collection("customer").document(phone.getText().toString()).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Shared_data shared_data=new Shared_data(Registration.this);
                            shared_data.setPhone(phone.getText().toString());


                            String phonenumber=phone.getText().toString().trim();
                            String phonenumberwithcode="+91"+phonenumber;

                            Intent intent=new Intent(Registration.this, OTP.class);
                            intent.putExtra("phonenumber",phonenumberwithcode);
                            intent.putExtra("phone1",phonenumber);

                            pBar.setVisibility(View.GONE);
                            signup.setVisibility(View.VISIBLE);
                            startActivity(intent);



                        }
                    });



                }








            }
        });



    }









    boolean validRegistration(){
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


        if(phone.getText().toString().isEmpty()){
            phone.setError("Empty Field");
            result=false;
        }
        else if (phone.getText().toString().length()<10){

            phone.setError("Invalid Number");
            result=false;
        }
        else {
            phone.setError(null);
        }



        if (password.getText().toString().isEmpty()){
            password.setError("Field Required");
            result=false;
        }
        else {
            password.setError(null);
        }

        if (confirm_password.getText().toString().isEmpty()){
            confirm_password.setError("Field Required");
            result=false;
        }
        else {
            confirm_password.setError(null);
        }

        if (!confirm_password.getText().toString().equals(password.getText().toString())){
            confirm_password.setError("Password Did Not Match");
            result=false;
        }
        else {
            confirm_password.setError(null);
        }

        return result;
    }


}
