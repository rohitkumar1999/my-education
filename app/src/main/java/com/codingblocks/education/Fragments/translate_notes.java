package com.codingblocks.education.Fragments;


import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import com.obsez.android.lib.filechooser.ChooserDialog;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;


/**
 * A simple {@link Fragment} subclass.
 */
public class translate_notes extends Fragment {

    private static final int FILE_REQUEST_CODE = 121 ;
    Button btnSelect  ;
    TextView textView ;


    public translate_notes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_translate_notes, container, false) ;
        btnSelect = view.findViewById(R.id.frag_translate_notes_select_files) ;
        textView = view.findViewById(R.id.textview) ;

        btnSelect.setOnClickListener(new View.OnClickListener() {
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
                                    textView.setText(parsedText);
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
        return view;
    }
    @Nullable
    public static String getFileExtFromBytes(File f) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(f);
            byte[] buf = new byte[5]; //max ext size + 1
            fis.read(buf, 0, buf.length);
            StringBuilder builder = new StringBuilder(buf.length);
            for (int i=1;i<buf.length && buf[i] != '\r' && buf[i] != '\n';i++) {
                builder.append((char)buf[i]);
            }
            return builder.toString().toLowerCase();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
