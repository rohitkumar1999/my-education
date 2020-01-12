package com.codingblocks.education;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class login extends AppCompatActivity {
    EditText pass,email;
    AppCompatButton button;
   public  Boolean signedin=false;
    TextView noaccount;
    public   void isboolean(Boolean bn)
    {
        this.signedin=bn;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pass=findViewById(R.id.login_password);
        email=findViewById(R.id.login_email);
        button=findViewById(R.id.btn_login);
        noaccount=findViewById(R.id.login_link_signup);


        int randomNumber;
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,


        };
        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String email1=email.getText().toString().toLowerCase();
                String pass1=pass.getText().toString();
                if(TextUtils.isEmpty(email1)&&TextUtils.isEmpty(pass1))
                {
                    Toast.makeText(getApplicationContext(),"Please eneter all the information correct",Toast.LENGTH_SHORT).show();
                    return;
                }


                else  if(email1.equals("neerajpandey@gmail.com")&&pass1.equals("pandey@123")
                        ||email1.equals("rohitkumar@gmail.com")&&pass1.equals("rohit@123")||email1.equals("lovetesh@gmail.com")&&pass1.equals("lovetesh@123"))
                {
                   isboolean(true);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));


                }
            }

        });
        noaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),signup.class));
            }
        });
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
public  class loggedin extends Application {
        private boolean signedin=false;

    public boolean isSignedin() {
        return signedin;
    }

    public void setSignedin(boolean signedin) {
        this.signedin = signedin;
    }
}
}
