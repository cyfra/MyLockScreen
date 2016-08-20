package com.example.michalski.mylockscreen;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TypeText implements PossibleQuestions {
    Map<String, Integer> pictures;
    ArrayList<String> pictures_keys;
    @Override
    public void Init(AppCompatActivity activity) {
        activity.setContentView(R.layout.type_answer);
        pictures = new HashMap<>();
        pictures.put("dom", R.drawable.domek);
        pictures_keys = new ArrayList<>(pictures.keySet());
    }

    @Override
    public void Execute(AppCompatActivity activity, final PossibleAnswers answers) {
        Random r = new Random();
        final String selected_key = pictures_keys.get(r.nextInt(pictures_keys.size()));
        ImageView view = (ImageView)activity.findViewById(R.id.imageView);
        view.setImageResource(pictures.get(selected_key));

        final EditText resultText = (EditText)activity.findViewById(R.id.imageName);
        resultText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equalsIgnoreCase(selected_key)) {
                    answers.Correct();
                }
            }
        });

        answers.Correct();
    }
}
