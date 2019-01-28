package com.example.raheem.scrambling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class login1 extends AppCompatActivity {
    EditText password ;
    TextView changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login1);

        password = findViewById(R.id.pass);
        changePass=(TextView)findViewById(R.id.txtChangePass);

/*
        SharedPreferences sharedPref = getSharedPreferences("passInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editPass = sharedPref.edit();


        editPass.putString("pass", "0000");


        editPass.apply();
        */


        changePass.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //perform your action here

                chpass();

            }
        });

    }


   public void chpass(){


       Intent intent = new Intent(this, changePassword.class);
       startActivity(intent);

   }
    public void BtnLoginClick (View v) {
//        saveTempBitmap(bitmap);





String pass=password.getText().toString();



        SharedPreferences sharedPref =getSharedPreferences("passInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editPass = sharedPref.edit();

        String pass2 =sharedPref.getString("pass","");
        editPass.apply();



        if(pass.equals("")){
            Toast.makeText(login1.this,
                    "ارجو ادخال كلمة المرور", Toast.LENGTH_LONG).show();
        }
        else if ( pass.equals( pass2)){

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {


            Toast.makeText(login1.this,
                    "كلمة المرور غير صحيحة", Toast.LENGTH_LONG).show();
        }


    }



}
