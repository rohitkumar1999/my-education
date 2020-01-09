package com.codingblocks.education;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.codingblocks.education.Fragments.home;
import com.codingblocks.education.Fragments.personal_data;
import com.codingblocks.education.Fragments.start_chapter;
import com.codingblocks.education.Fragments.study_result;
import com.codingblocks.education.Fragments.test_knowledge;
import com.codingblocks.education.Fragments.translate_notes;
import com.codingblocks.education.Fragments.view_notes;

public class MainActivity extends AppCompatActivity   {
    public static FragmentManager fragmentManager ;
    public  static MyAppDatabaseClass myappdatabaseclass ;
    public static AudioManager m_amAudioManager  ;
   public static SharedPreferences pref ;  // 0 - for private mode
    public static SharedPreferences.Editor editor ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        myappdatabaseclass = Room.databaseBuilder(getApplicationContext(),MyAppDatabaseClass.class,"education.db")
                .allowMainThreadQueries().build() ;
        fragmentManager = getSupportFragmentManager() ;
        m_amAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        m_amAudioManager.setMode(AudioManager.MODE_NORMAL);
        m_amAudioManager.setSpeakerphoneOn(true);

        if(findViewById(R.id.new_container)!=null)
        {
            if(savedInstanceState!=null)
            {
                return ;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction() ;
            home home = new home() ;
            fragmentTransaction.add(R.id.new_container,home,null).commit() ;

        }
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


}