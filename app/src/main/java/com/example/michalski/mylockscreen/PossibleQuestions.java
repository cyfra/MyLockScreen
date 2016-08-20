package com.example.michalski.mylockscreen;

import android.support.v7.app.AppCompatActivity;

interface PossibleQuestions {
    void Init(AppCompatActivity activity);
    void Execute(AppCompatActivity activity, final PossibleAnswers answers);
}