package com.example.raheem.scrambling;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class changePassword extends AppCompatActivity {


    EditText newPass ,newPass1,pass ;
    String stroldpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
    newPass=findViewById(R.id.newPass);
        newPass1=findViewById(R.id.newPass1);

        pass=findViewById(R.id.pass);



        SharedPreferences sharedPref =getSharedPreferences("passInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editPass = sharedPref.edit();


         stroldpass =sharedPref.getString("pass","");
        editPass.apply();


    }


    public void BtnChangeClick(View view){


        if ( pass.getText().toString().equals("")){

            Toast.makeText(changePassword.this,
                    "الرجاء ادخال كلمة المرور القديمة", Toast.LENGTH_LONG).show();
        }
        else if(!(stroldpass.equals( pass.getText().toString()))){

            Toast.makeText(changePassword.this,
                    "كلمة المرور الفديمة غير صحيحة", Toast.LENGTH_LONG).show();
        }
        else if( (newPass.getText().toString().equals(""))
                || newPass1.getText().toString().equals("")){

            if (newPass.getText().toString().equals("")){
                Toast.makeText(changePassword.this,
                        "الرجاء ادخال كلمة المرور الجديدة", Toast.LENGTH_LONG).show();
            }
            if(newPass1.getText().toString().equals("")){

                Toast.makeText(changePassword.this,
                        "الرجاء تأكيد كلمة المرور الجديدة", Toast.LENGTH_LONG).show();

            }


        }
        else if (!(newPass.getText().toString().equals(newPass1.getText().toString()))){

            Toast.makeText(changePassword.this,
                    "كلمة المرور الجديدة غير مطابفة", Toast.LENGTH_LONG).show();
        }
        else {
            SharedPreferences sharedPref = getSharedPreferences("passInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editPass = sharedPref.edit();


            editPass.putString("pass", newPass.getText().toString());


            editPass.apply();

            Toast.makeText(changePassword.this,
                    " تم تغيير كلمة المرور بنجاح ", Toast.LENGTH_LONG).show();


            Intent intent = new Intent(this, login1.class);
            startActivity(intent);

        }











    }




}
