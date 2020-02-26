package com.e.thesmitstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    EditText phone,password;
    Button signin,createaccount;
    TextView forgot;

    FirebaseFirestore mydb;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Log.d("55555","task1");
        phone=findViewById(R.id.login_edittext_phone);
        password=findViewById(R.id.login_edittext_password);
        signin=findViewById(R.id.login_button_signin);
        createaccount=findViewById(R.id.login_button_createaccount);
        forgot=findViewById(R.id.login_textview_forgot);
        progressBar=findViewById(R.id.login_progressbar);


        mydb=FirebaseFirestore.getInstance();

        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Login.this,Registration.class);
                startActivity(intent);
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Forgot_Password.class);
                startActivity(intent);
            }
        });

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isValidlogin()){
                    signin.setVisibility(View.GONE);
                    progressBar.setVisibility(View.VISIBLE);

                    mydb.collection("customer").document(phone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if(task.isSuccessful())
                            {
                                DocumentSnapshot documentSnapshot=task.getResult();
                                if(documentSnapshot.exists())
                                {
                                    if(documentSnapshot.getData().get("otp").toString().equals("verified"))
                                    {
                                        String pass=documentSnapshot.getData().get("password").toString();

                                        if (pass.equals(password.getText().toString()))
                                        {
                                            Shared_data shared_data=new Shared_data(Login.this);
                                            shared_data.setPhone(phone.getText().toString());

                                            Intent intent=new Intent(Login.this,Dashboard.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            progressBar.setVisibility(View.GONE);
                                            signin.setVisibility(View.VISIBLE);
                                            Toast.makeText(Login.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else
                                    {
                                        progressBar.setVisibility(View.GONE);
                                        signin.setVisibility(View.VISIBLE);
                                        Toast.makeText(Login.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    progressBar.setVisibility(View.GONE);
                                    signin.setVisibility(View.VISIBLE);
                                    Toast.makeText(Login.this, "User not registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    });







                }
                else {
                    Toast.makeText(Login.this, "Check Entered Details", Toast.LENGTH_SHORT).show();
                }





            }
        });
    }




    boolean isValidlogin(){
        boolean result=true;
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

        return result;
    }
}
