package com.codingblocks.education.Fragments;


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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.codingblocks.education.MainActivity;
import com.codingblocks.education.R;
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

import static com.codingblocks.education.Fragments.start_chapter_second_page.str;

public class notes_fragment extends Fragment {

    ImageButton backButton,listenButton ;
    TextView chapterName,chapterScannedTreanslatedNotes ;
    public static TextToSpeech  tts ;
    FirebaseTranslatorOptions options;




    public notes_fragment() {

    }

    @Override
    public void onDestroy() {
        tts.stop() ;
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notes_fragment, container, false);
        String value = getArguments().getString("notes");
       // String parsedText = getArguments().getString("text") ;
        String chap_name = getArguments().getString("chaptername");

        backButton = view.findViewById(R.id.frag_translate_notes_back_arrow);
        listenButton = view.findViewById(R.id.frag_translate_notes_mic);
        chapterName = view.findViewById(R.id.frag_translate_notes_file_name);
        chapterScannedTreanslatedNotes = view.findViewById(R.id.frag_translate_notes_notes);
        chapterName.setText(chap_name);
        String clear_notes = value.replaceAll("<Note>?/?", "");
        chapterScannedTreanslatedNotes.setText(clear_notes);
        createPdf(clear_notes);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new home();
                MainActivity.fragmentManager.beginTransaction().replace(R.id.new_container, fragment, null).commit();
                onStop();
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
                for (int i = 0;i<4; ) {
                    String forrealtts = clear_notes.substring(i, (++i) * 100);
                    englishGermanTranslator.downloadModelIfNeeded(conditions)
                            .addOnSuccessListener(
                                    new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void v) {
                                            Log.d("checking  model", "downloadeddd");

                                            englishGermanTranslator.translate(forrealtts)
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

                                                                                tts.setSpeechRate((float) 0.90);
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

    @Override
    public void onStop() {
        super.onStop();
        if(tts != null){
            tts.shutdown();
        }
    }

    private void createPdf(String sometext){
        // create a new document
        PdfDocument document = new PdfDocument();
        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        canvas.drawCircle(50, 50, 30, paint);
        paint.setColor(Color.BLACK);
        canvas.drawText(sometext, 80, 50, paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page
        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 100, paint);
        document.finishPage(page);
        // write the document content
        String directory_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"test-2.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(getContext(), "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(getContext(), "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }


    }


