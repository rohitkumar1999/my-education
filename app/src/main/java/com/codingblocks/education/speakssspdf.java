package com.codingblocks.education;


import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.media.AudioManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.education.Fragments.home;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.modeldownload.FirebaseModelDownloadConditions;
import com.google.firebase.ml.naturallanguage.FirebaseNaturalLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslateLanguage;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslator;
import com.google.firebase.ml.naturallanguage.translate.FirebaseTranslatorOptions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class speakssspdf extends Fragment {
    ImageButton backButton,listenButton ;
    TextView chapterName,chapterScannedTreanslatedNotes ;
    public static TextToSpeech tts ;
    FirebaseTranslatorOptions options;





    @Override
    public void onDestroy() {
        tts.stop() ;
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_fragment, container, false);
      //  String value = getArguments().getString("notes");
        String parsedText = getArguments().getString("text") ;
     //   String chap_name = getArguments().getString("chaptername");

        backButton = view.findViewById(R.id.frag_translate_notes_back_arrow);
        listenButton = view.findViewById(R.id.frag_translate_notes_mic);
        chapterName = view.findViewById(R.id.frag_translate_notes_file_name);
        chapterScannedTreanslatedNotes = view.findViewById(R.id.frag_translate_notes_notes);
        chapterName.setText("No name");
        String clear_notes = parsedText ;
        chapterScannedTreanslatedNotes.setText(clear_notes);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new home();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container, fragment, null).commit();

            }
        });
        Boolean bn = false;

        listenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                options = new FirebaseTranslatorOptions.Builder()
                        .setSourceLanguage(FirebaseTranslateLanguage.EN)
                        .setTargetLanguage(FirebaseTranslateLanguage.HI)
                        .build();


                final FirebaseTranslator englishGermanTranslator =
                        FirebaseNaturalLanguage.getInstance().getTranslator(options);
                FirebaseModelDownloadConditions conditions = new FirebaseModelDownloadConditions.Builder()
                        .build();
                Log.d("checking  model", "after translator code");
                for (int i = 0; i < 9; i++) {
                    String forrealtts = clear_notes.substring(i, (i + 1) * 100);
                    englishGermanTranslator.downloadModelIfNeeded(conditions)
                            .addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void v) {
                                            Log.d("checking  model", "downloadeddd");

                                            englishGermanTranslator.translate(clear_notes)
                                                    .addOnSuccessListener(
                                                            new OnSuccessListener<String>() {
                                                                @Override
                                                                public void onSuccess(@NonNull String translatedText) {
                                                                    Log.d("checking  model", translatedText);
                                                                    tts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {
                                                                        @Override
                                                                        public void onInit(int status) {
                                                                            // TODO Auto-generated method stub
                                                                            if (status == TextToSpeech.SUCCESS) {
                                                                                int result = tts.setLanguage(Locale.forLanguageTag("hin"));

                                                                                tts.setSpeechRate((float) 0.98);
                                                                                if (result == TextToSpeech.LANG_MISSING_DATA ||
                                                                                        result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                                                    Log.e("error", "This Language is not supported");
                                                                                } else {
                                                                                    String text;
                                                                                    text = translatedText;
                                                                                    HashMap<String, String> params = new HashMap<>();
                                                                                    if (text == null || "".equals(text)) {
                                                                                        text = "Content not available";
                                                                                        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                                                                                    } else
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
        });

        return view;
    }



}


