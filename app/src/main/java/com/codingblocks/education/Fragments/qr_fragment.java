package com.codingblocks.education.Fragments;


import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
import com.google.zxing.Result;

public class qr_fragment extends Fragment {
    private CodeScanner mCodeScanner;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Activity activity = getActivity();
        View root = inflater.inflate(R.layout.fragment_qr_fragment, container, false);
        CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(MainActivity.pref.getString("Scanned_Notes","null")==null)
                        {
                            String str  = MainActivity.pref.getString("Scanned_Notes","null") ;
                            String res = result.getText() ;
                            Toast.makeText(getContext(),"scan result "+res,Toast.LENGTH_LONG).show();
                            String starting ;
                            if(res.substring(0,5).equals("<NOTE>")) {
                                starting = res.substring(6, 8);
                                int present = Integer.parseInt(starting.substring(6, 6));
                                int total = Integer.parseInt(starting.substring(8, 8));
                                Log.d("checking qr", present + "" + total +"");
                            }
                            MainActivity.editor.putString("Scanned_Notes",result.getText()) ;
                            Fragment fragment = new start_chapter_second_page() ;
                            MainActivity.fragmentManager.beginTransaction().add(R.id.new_container,new start_chapter_second_page()).addToBackStack(null).commit();
                        }
                        else {
                            String str  = MainActivity.pref.getString("Scanned_Notes","null") ;
                            String res = result.getText() ;
                            Toast.makeText(getContext(),"scan result "+res,Toast.LENGTH_LONG).show();

                            String starting ;
                            if(res.substring(0,5).equals("<NOTE>")) {
                                starting = res.substring(6, 8);
                                int present = Integer.parseInt(starting.substring(6, 6));
                                int total = Integer.parseInt(starting.substring(8, 8));
                                Log.d("checking qr", present + "" + total +"");
                            }
                            MainActivity.editor.putString("Scanned_Notes", str+ result.getText());

                        }
                        }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }
}