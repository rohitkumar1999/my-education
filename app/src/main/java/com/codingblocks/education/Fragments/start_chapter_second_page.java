package com.codingblocks.education.Fragments;


import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
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
import android.widget.TextView;
import android.widget.ToggleButton;

import com.codingblocks.education.EntityClasses.Notes;
import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
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
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class start_chapter_second_page extends Fragment implements
        RecognitionListener {

    TextView chapter_name,translated_notes ;
    ToggleButton listen ;
    Button btn_done_save_notes ;
    FloatingActionButton scanqr ;
    Boolean flag = false ;
    ArrayList<String> matches ;
    TextToSpeech tts;
    String translatedinput,scannedinput ;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    FirebaseTranslatorOptions options =
            new FirebaseTranslatorOptions.Builder()
                    .setSourceLanguage(FirebaseTranslateLanguage.EN)
                    .setTargetLanguage(FirebaseTranslateLanguage.HI)
                    .build();


    public start_chapter_second_page() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start_chapter_second_page, container, false);
        speech = SpeechRecognizer.createSpeechRecognizer(getContext());
        speech.setRecognitionListener(this);

        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);


        final String value = getArguments().getString("chapterName");
        final String value1 = getArguments().getString("chapeterSubject") ;

        chapter_name = view.findViewById(R.id.frag_start_chapter_second_txtview_chapter_name) ;
        translated_notes = view.findViewById(R.id.frag_start_chapter_second_txtview_notes) ;
        listen = view.findViewById(R.id.frag_start_chapter_second_tglbtn_lisening) ;
        btn_done_save_notes = view.findViewById(R.id.frag_start_chapter_second_button_done) ;
        scanqr = view.findViewById(R.id.frag_start_chapter_second_float_button_qrcode) ;
        chapter_name.setText(value);
         listen.setOnClickListener(new View.OnClickListener() {
             @RequiresApi(api = Build.VERSION_CODES.P)
             @Override
             public void onClick(View v) {
                     Log.d("check",MainActivity.m_amAudioManager.isMicrophoneMute()+"") ;

               //  speech.startListening(recognizerIntent);

             }
         });
        btn_done_save_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notes notes = new Notes()  ;
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

            }
        });




        return view ;
    }

    @Override
    public void onReadyForSpeech(Bundle params) {

    }

    @Override
    public void onBeginningOfSpeech() {

    }

    @Override
    public void onRmsChanged(float rmsdB) {

    }

    @Override
    public void onBufferReceived(byte[] buffer) {

    }

    @Override
    public void onEndOfSpeech() {
        listen.setChecked(false);

    }

    @Override
    public void onError(int error) {
        String errorMessage = getErrorText(error);

        listen.setChecked(false);
    }

    @Override
    public void onResults(Bundle results) {
        matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);


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

                                englishGermanTranslator.translate(matches.get(0))
                                        .addOnSuccessListener(
                                                new OnSuccessListener<String>() {
                                                    @Override
                                                    public void onSuccess(@NonNull String translatedText) {
                                                        Log.d("checking  model", translatedText);
                                                        translated_notes.setText(translatedText);
                                                        translatedinput+=translatedText ;
                                                        tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

                                                            @Override
                                                            public void onInit(int status) {
                                                                // TODO Auto-generated method stub
                                                                if (status == TextToSpeech.SUCCESS) {
                                                                    int result = tts.setLanguage(Locale.forLanguageTag("hin"));
                                                                    tts.setSpeechRate((float)0.98) ;
                                                                    if (result == TextToSpeech.LANG_MISSING_DATA ||
                                                                            result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                                        Log.e("error", "This Language is not supported");
                                                                    } else {
                                                                        String text ;
                                                                        text = translated_notes.getText().toString();
                                                                        if(text==null||"".equals(text))
                                                                        {
                                                                            text = "Content not available";
                                                                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                                                        }else
                                                                            tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);

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

    @Override
    public void onPartialResults(Bundle partialResults) {

    }

    @Override
    public void onEvent(int eventType, Bundle params) {

    }
    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }
}
