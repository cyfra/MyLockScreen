package com.example.michalski.mylockscreen;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

public class TypeText implements PossibleQuestions {
    Pictures pictures;
    @Override
    public void Init(AppCompatActivity activity) {
        pictures = new Pictures();
        activity.setContentView(R.layout.type_answer);
    }

    @Override
    public void Execute(AppCompatActivity activity, final PossibleAnswers answers) {
        Random r = new Random();
        final String selected_key = pictures.pictures_keys.get(r.nextInt(pictures.pictures_keys.size()));
        ImageView view = (ImageView)activity.findViewById(R.id.imageView);
        view.setImageResource(pictures.pictures.get(selected_key));

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
