package com.codingblocks.education;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.education.Fragments.personal_data;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class signup extends AppCompatActivity {
    Button signup;
    Spinner  designation,Class;
    AutoCompleteTextView language;
    EditText name,email,pass,repass,mobileno;
    CircleImageView imageView;
    public static ArrayList<String> arrayList=new ArrayList<>();
    Integer REQUEST_CAMERA=1,SELECT_FILE=0;
    Image image;
    TextView signtologin;
    public  Bitmap mybitmap;
    Uri myuri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup=findViewById(R.id.signup_button);
        designation=findViewById(R.id.signup_spinner_designation);
        Class=findViewById(R.id.signup_class_spinner);
        name=findViewById(R.id.signup_fullname);
        email=findViewById(R.id.signup_mail_id);
        pass=findViewById(R.id.signup_pass);
        imageView=findViewById(R.id.signup_profile);
        repass=findViewById(R.id.sigup_repass);
        language=findViewById(R.id.signup_language);
        mobileno=findViewById(R.id.signup_mobile_number);
        signtologin=findViewById(R.id.signuptologin);

        String pathToPicture;
        final String[] languages={"Hindi","Marathi","Bengali","English","Telugu","Urdu","Kannada","Tamil","Gujrati"};
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter(this,android.R.layout.select_dialog_item,languages);
        AutoCompleteTextView actv= (AutoCompleteTextView)findViewById(R.id.signup_language);
        actv.setThreshold(1);//will start working from first character
        actv.setAdapter(arrayAdapter);//setting the adapter data into the AutoCompleteTextView
        signtologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),login.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = pass.getText().toString();
                String repass1 = repass.getText().toString();
                String mydesignation = designation.getSelectedItem().toString();
                String myclass = Class.getSelectedItem().toString();
                String mobileno1 = mobileno.getText().toString();
                String language1 = language.getText().toString();
//                if (TextUtils.isEmpty(name1) || TextUtils.isEmpty(email1) || TextUtils.isEmpty(pass1) || TextUtils.isEmpty(repass1) || TextUtils.isEmpty(mobileno1) ||TextUtils.isEmpty(language1)) {
//                    Toast.makeText(getApplicationContext(), "Please enter all fields correctly", Toast.LENGTH_LONG).show();
//return;
//
//                } else if (!pass1.equals(repass1)) {
//                    Toast.makeText(getApplicationContext(), "password does not match", Toast.LENGTH_LONG).show();
//                    repass.requestFocus();
//                    return;
//                } else {

                arrayList.add(name1);
                arrayList.add(email1);
                arrayList.add(mydesignation);
                arrayList.add(myclass);
                arrayList.add(mobileno1);
                arrayList.add(language1);
                arrayList.add(myclass);

//i
//              Bundle args=new Bundle();
//              args.putString("name",name1);
//              args.putString("designation",mydesignation);
//              args.putString("class",myclass);
//              args.putString("email",email1);
//                args.putString("pass",pass1);
//                args.putString("repass",repass1);
//                args.putString("mobileno",mobileno1);
//                args.putString("language",language1);
//             personal_data personal_data1=new personal_data();
//         personal_data1.setArguments(args);args
                //    getSupportFragmentManager().beginTransaction().replace(R.id.new_container,personal_data1).commit();
//                Log.d("data", args.getString("name"));
//                Log.d("data", args.getString("email"));
//                Log.d("data", args.getString("pass"));
//                Log.d("data", name1);
//                Log.d("data", mydesignation);
//                Log.d("data", myclass);
//                Log.d("data", email1);
//                Log.d("data",pass1);
//                Log.d("data", repass1);
//                Log.d("data", mobileno1);


                startActivity(new Intent(signup.this, MainActivity.class));

            }
            //}
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });

    }
    public void selectimage()
    {
        final CharSequence[] items={"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(signup.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(items[i].equals("Camera"))
                {
                    Intent  intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);


                }
                else if(items[i].equals("Gallery"))
                {
                    Intent  intent=new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");

                    startActivityForResult(intent.createChooser(intent,"Select file"),SELECT_FILE);
                }
                else if(items[i].equals("Cancel"))
                {
                    dialogInterface.dismiss();

                }
            }
        });
        builder.show();
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.RECORD_AUDIO

        };

        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bundle b=new Bundle();
        if(resultCode== Activity.RESULT_OK)
        {

            if(requestCode==REQUEST_CAMERA)
            {
                Bundle bundle=data.getExtras();
                final Bitmap bitmap= (Bitmap) bundle.get("data");
                imageView.setImageBitmap(bitmap);
                b.putParcelable("bitmapimage",bitmap);
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                byte[] byteArray = stream.toByteArray();
//                Bundle b = new Bundle();
//                b.putByteArray("image",byteArray);

                personal_data    data1=new personal_data();
                data1.setArguments(b);
            }
            else if(requestCode==SELECT_FILE)
            {
                Uri selectedImageUri=data.getData();
                imageView.setImageURI(selectedImageUri);



            }
        }
    }

}
