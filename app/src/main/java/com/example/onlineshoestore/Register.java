package com.example.onlineshoestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {
EditText mFullName, mEmail,mPassword,mContact,mAddress;

Button mRegisterBtn;
TextView mLoginBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFullName= findViewById(R.id.fullName);
        mEmail= findViewById(R.id.email);
        mPassword= findViewById(R.id.password);
        mContact= findViewById(R.id.contact);
        mAddress= findViewById(R.id.address);
        mRegisterBtn= findViewById(R.id.button);
        mLoginBtn= findViewById(R.id.login);



        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = mFullName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String password= mPassword.getText().toString().trim();
                String address = mAddress.getText().toString().trim();
                String contact = mContact.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is required");
                    return;
                }
            else    if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is required");
                    return;
                }

             else   if(password.length()<6){
                    mPassword.setError("Password must be greater than 6 characters");
                    return;
                }
             else   if(TextUtils.isEmpty(fullname)){
                    mFullName.setError("Full Name is required");
                    return;
                }
              else  if(TextUtils.isEmpty(address)){
                    mAddress.setError("Address is required");
                    return;
                }
             else   if(TextUtils.isEmpty(contact)){
                    mContact.setError("Contact is required");
                    return;
                }

             else{
                 do_signup(fullname,email,password,address,contact);
                }


            }



        });


    }


    public void do_signup(String fname,String email ,String password,String address,String contact) {

        ApiInterface mApiService = this.getInterfaceService();

        Call<String> responseBodyCall = mApiService.Register(fname,email,password,address,contact);

        responseBodyCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if(response.body().equals("User has Registered")){
                    startActivity(new Intent(Register.this,Home.class));
                    Toast.makeText(Register.this, "Successfully Signup", Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(Register.this, response.body(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(Register.this, t.toString(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private ApiInterface getInterfaceService() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.0.28:3000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final ApiInterface mInterfaceService = retrofit.create(ApiInterface.class);
        return mInterfaceService;
    }
}