package com.codingblocks.education.Fragments;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.codingblocks.education.EntityClasses.Notes;
import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
import com.codingblocks.education.Recogination.OnSpeechRecognitionListener;
import com.codingblocks.education.Recogination.OnSpeechRecognitionPermissionListener;
import com.codingblocks.education.Recogination.SpeechRecognition;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class start_chapter_second_page extends Fragment  {

    TextView chapter_name,translated_notes ;
    ToggleButton listen ;
    Button btn_done_save_notes ;
    FloatingActionButton scanqr ;
    Boolean flag = false ;
    ArrayList<String> matches ;
    TextToSpeech tts;
    String translatedinput,scannedinput ;
    public static SpeechRecognition speechRecognition  ;
    public static boolean isListening =false;
    public static         String str = "" ;
    public static  Spinner spinner ;
    public static String TAG = "check" ;
    public static AudioManager audioManager ;
    public static     Thread t ;
    FirebaseTranslatorOptions options; 
    public static  String language ;


    public start_chapter_second_page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_chapter_second_page, container, false);

        audioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
        speechRecognition  = new SpeechRecognition(getContext());
        speechRecognition.useGoogleImeRecognition(false,null) ;
        speechRecognition.useOnlyOfflineRecognition(true) ;
        speechRecognition.setSpeechRecognitionPermissionListener(new OnSpeechRecognitionPermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.d("check", "onPermissionGranted: ");
            }

            @Override
            public void onPermissionDenied() {
                Log.d(TAG, "onPermissionDenied: ");
            }
        });
        speechRecognition.setSpeechRecognitionListener(new OnSpeechRecognitionListener() {
            @Override
            public void OnSpeechRecognitionStarted() {
                Log.d(TAG, "OnSpeechRecognitionStarted: ");
            }

            @Override
            public void OnSpeechRecognitionStopped() {
                Log.d(TAG, "OnSpeechRecognitionStopped: ");


            }

            @Override
            public void OnSpeechRecognitionFinalResult(String s) {



            t = new Thread(){
                    @Override
                    public void run() {
                        startspeaking(s);
                    }
                };
                t.start();
               calltoagain(true);


            }

            @Override
            public void OnSpeechRecognitionCurrentResult(String s) {
                Log.d(TAG, "OnSpeechRecognitionCurrentResult: ");


            }

            @Override
            public void OnSpeechRecognitionError(int i, String s) {
                calltoagain(true);

            }
        });




        final String value = getArguments().getString("chapterName");
        final String value1 = getArguments().getString("chapeterSubject") ;

       // chapter_name = view.findViewById(R.id.frag_start_chapter_second_txtview_chapter_name) ;
  //    translated_notes = view.findViewById(R.id.frag_start_chapter_second_txtview_notes) ;
        listen = view.findViewById(R.id.frag_start_chapter_second_tglbtn_lisening) ;
        btn_done_save_notes = view.findViewById(R.id.frag_start_chapter_second_button_done) ;
        scanqr = view.findViewById(R.id.frag_start_chapter_second_float_button_qrcode) ;

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isListening == false) {
                    speechRecognition.startSpeechRecognition();
                    listen.setText("Listening");
                }
                else
                {
                    speechRecognition.stopSpeechRecognition();
                    listen.setText("Start Listening");
                }
            }

        });
        spinner=view.findViewById(R.id.frag_start_chapter_second_spinner);
         language=spinner.getSelectedItem().toString();



//       Toolbar toolbar=view.findViewById(R.id.frag_start_chapter_second_txtview_chapter_name);
////        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//        toolbar.setTitle(value);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
//        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_black_24dp));
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //how to go back write ur code here
//            }
//        });
        btn_done_save_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes notes = new Notes()  ;
                Log.d("show scanned notes",qr_fragment.scannednotes);
                notes.setChapter_name(value);
                notes.setGenerated_notes("Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar,Hello My name is rohit kumar");
                notes.setScanned_notes("Hello He is rohit kumar,Hello He is rohit kumar,Hello He is rohit kumar,Hello He is rohit kumar,Hello He is rohit kumar,Hello He is rohit kumar,Hello He is rohit kumar,Hello He is rohit kumar,Hello He is rohit kumar");
                notes.setScanned_test("Who is rohit kumar?,Who is rohit kumar?Who is rohit kumar?Who is rohit kumar?Who is rohit kumar?,Who is rohit kumar?");
                notes.setSubject(value1);
                MainActivity.myappdatabaseclass.myDaoforchapter().addNotes(notes);

                MainActivity.fragmentManager.popBackStack();
                MainActivity.fragmentManager.beginTransaction().add(R.id.new_container,new home()).addToBackStack(null).commit();


            }
        });
        scanqr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.fragmentManager.beginTransaction().add(R.id.new_container,new qr_fragment()).addToBackStack(null).commit();
              //  Log.d("Checking output for ", MainActivity.pref.getString("Scanned_Notes",null));
            }
        });




        return view ;
    }
    public static void calltoagain(boolean b) {
        speechRecognition.stopSpeechRecognition();
        speechRecognition.startSpeechRecognition();

    }


    public void startspeaking(String str)
    {
        language=spinner.getSelectedItem().toString();

        if(language.equals("Hindi"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.HI)
                    .build();
        else if(language.equals("Gujrati"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.GU)
                    .build();
        else if(language.equals("Marathi"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.MR)
                    .build();
        else if(language.equals("English"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.EN)
                    .build();
        else if(language.equals("Tamil"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.TA)
                    .build();
        else if(language.equals("Telgu"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.TE)
                    .build();
        else if(language.equals("Urdu"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.UR)
                    .build();
        else if(language.equals("Kanada"))
            options =  new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.KN)
                    .build();



        final FirebaseTranslator englishGermanTranslator =
                FirebaseNaturalLanguage.getInstance().getTranslator(options);
        FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                .build();
        Log.d("checking  model", "after translator code");

        englishGermanTranslator.downloadModelIfNeeded(conditions)
                .addOnSuccessListener(
                        new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void v) {
                                flag = true;
                                Log.d("checking  model", "downloadeddd");
                                Log.d("checking  model", flag.toString());

                                englishGermanTranslator.translate(str)
                                        .addOnSuccessListener(
                                                new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(@NonNull String translatedText) {
                                                        Log.d("checking  model", translatedText);
                                                        translatedinput+=translatedText ;
                                                        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

                                                            @Override
                                                            public void onInit(int status) {
                                                                // TODO Auto-generated method stub
                                                                if (status == TextToSpeech.SUCCESS) {
                                                                    int result = tts.setLanguage(Locale.forLanguageTag("hin"))  ;

                                                                    if(language.equals("Hindi"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("hin"));

                                                                    else if(language.equals("Gujrati"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("gu_"));



                                                                    else if(language.equals("Marathi"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("hin"));


                                                                    else if(language.equals("English"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("hin"));


                                                                    else if(language.equals("Tamil"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("ta_IN"));


                                                                    else if(language.equals("Telgu"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("te_"));


                                                                    else if(language.equals("Urdu"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("Ur_IN"));


                                                                    else if(language.equals("Kanada"))
                                                                        result = tts.setLanguage(Locale.forLanguageTag("kn_IN"));




                                                                    tts.setSpeechRate((float)0.98) ;
                                                                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                                                                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                                        Log.e("error", "This Language is not supported");
                                                                    } else {
                                                                        String text ;
                                                                        text = translatedText;
                                                                        HashMap<String, String> params = new HashMap<>();
                                                                        if(text==null||"".equals(text))
                                                                        {
                                                                            text = "Content not available";
                                                                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                                                        }else
                                                                            params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_VOICE_CALL));
                                                                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, params);


                                                                    }
                                                                } else
                                                                    Log.e("error", "Initilization Failed!");
                                                            }
                                                        });
                                                    }
                                                })
                                        .addOnFailureListener(
                                                new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Error.
                                                        // ...
                                                        Log.d("checking  model", "hello");
                                                    }
                                                });
                            }


                        })
                .addOnFailureListener(
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Model couldn’t be downloaded or other internal error.
                                // ...
                                Log.d("checking  model", e.toString());
                            }
                        });

    }


}
