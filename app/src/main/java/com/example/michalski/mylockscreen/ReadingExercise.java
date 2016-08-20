package com.example.michalski.mylockscreen;

import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

public class ReadingExercise implements PossibleQuestions {
    Pictures pictures;
    @Override
    public void Init(AppCompatActivity activity) {
        activity.setContentView(R.layout.reading);
        pictures = new Pictures();
    }

    @Override
    public void Execute(AppCompatActivity activity, int randomNumber, PossibleAnswers answers) {
        ImageButton topButton = (ImageButton)activity.findViewById(R.id.imageButton);
        ImageButton mediumButton = (ImageButton)activity.findViewById(R.id.imageButton2);
        ImageButton bottomButton = (ImageButton)activity.findViewById(R.id.imageButton3);

        topButton.setImageResource(R.drawable.domek);
        mediumButton.setImageResource(R.drawable.pies);
        bottomButton.setImageResource(R.drawable.rower);
    }
}
