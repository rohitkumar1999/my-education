package com.codingblocks.education.Fragments;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.TextView;


import com.codingblocks.education.Fragments.personal_data;
import com.codingblocks.education.Fragments.start_chapter;
import com.codingblocks.education.Fragments.study_result;
import com.codingblocks.education.Fragments.translate_notes;
import com.codingblocks.education.Fragments.view_notes;
import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
import com.codingblocks.education.speakssspdf;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;

import static com.codingblocks.education.Fragments.translate_notes.getFileExtFromBytes;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class home extends Fragment implements View.OnClickListener {

CircleImageView imageView;
TextView name,userid;
    public home() {
        // Required empty public constructor
    }

    LinearLayout personal,start_chapter1,view_notes1,study_result1,translate,test_knowledge;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragmentSu
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        View view= inflater.inflate(R.layout.fragment_home, container, false);


imageView=view.findViewById(R.id.dash_profile);
name=view.findViewById(R.id.dash_name);
userid=view.findViewById(R.id.dash_id);

        personal=view.findViewById(R.id.personal_data);
        start_chapter1=view.findViewById(R.id.start_chapter);
        view_notes1=view.findViewById(R.id.view_notes);

        translate=view.findViewById(R.id.translate_notes);
imageView.setImageDrawable(getResources().getDrawable(R.drawable.neeraj));
name.setText("neeraj pandey");
userid.setText("110245");


        personal.setOnClickListener(this);
        start_chapter1.setOnClickListener(this);
        view_notes1.setOnClickListener(this);

        translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ChooserDialog(getActivity())
                        .withStartFile(Environment.getExternalStorageDirectory().getAbsolutePath())
                        .withFilter(false, false, "pdf", "txt", "doc","docx")
                        .withChosenListener(new ChooserDialog.Result() {
                            @Override
                            public void onChoosePath(String path, File pathFile) {
                                Toast.makeText(getContext(),getFileExtFromBytes(pathFile),Toast.LENGTH_SHORT).show();
                                try {
                                    String parsedText="";

                                    PdfReader reader = new PdfReader(pathFile.getPath());
                                    int n = reader.getNumberOfPages();
                                    for (int i = 0; i <n ; i++) {
                                        parsedText   = parsedText+ PdfTextExtractor.getTextFromPage(reader, i+1).trim()+"\n"; //Extracting the content from the different pages
                                    }
                                    Fragment fragment = new speakssspdf() ;
                                    Bundle b = new Bundle() ;
                                    b.putString("text",parsedText);
                                    fragment.setArguments(b);
                                    MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container,fragment).addToBackStack(null).commit() ;


                                    reader.close();
                                } catch (Exception e) {
                                    System.out.println(e);
                                }                            }
                        })
                        // to handle the back key pressed or clicked outside the dialog:
                        .withOnCancelListener(new DialogInterface.OnCancelListener() {
                            public void onCancel(DialogInterface dialog) {
                                Log.d("CANCEL", "CANCEL");
                                dialog.cancel(); // MUST have
                            }
                        })
                        .build()
                        .show();





            }
        });


        return  view;
    }
    Fragment fragment = null ;

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.personal_data :
                fragment=new personal_data();
                break;

            case R.id.start_chapter :
                fragment=new start_chapter();
                break;
            case R.id.view_notes:
                fragment=new view_notes();
                break;







        }

        MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container, fragment).addToBackStack(null).commit();
    }
}