package com.example.michalski.mylockscreen;

import android.support.v7.app.AppCompatActivity;

interface PossibleQuestions {
    void Init(AppCompatActivity activity);
    // randomNumber: between 0 and 1<<10
    void Execute(AppCompatActivity activity, int randomNumber, final PossibleAnswers answers);
}