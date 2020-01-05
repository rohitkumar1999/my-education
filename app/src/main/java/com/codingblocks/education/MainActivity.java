package com.codingblocks.education;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
LinearLayout  personal,start_chapter,view_notes,study_result,translate,test_knowledge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        personal=findViewById(R.id.personal_data);
        start_chapter=findViewById(R.id.start_chapter);
        view_notes=findViewById(R.id.study_result);
        study_result=findViewById(R.id.study_result);
        translate=findViewById(R.id.translate_notes);
        test_knowledge=findViewById(R.id.test);
    }
}
