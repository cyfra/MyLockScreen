package com.example.michalski.mylockscreen;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

class SimpleAddOrDelete implements PossibleQuestions{
    int first, second, result;
    public enum MyOperation { ADD, SUBTRACT}
    MyOperation operation;
    public void Init(AppCompatActivity activity) {
        activity.setContentView(R.layout.activity_main);
        Random r = new Random();
        first = r.nextInt(5) + 1;
        String operationChar;
        //if (r.nextInt(2) == 0) {
            operation = MyOperation.ADD;
            second = r.nextInt(5) + 1;
            result = first + second;
            operationChar = "+";
        /*} else {
            operation = MyOperation.SUBTRACT;
            second = r.nextInt(first + 1);
            result = first - second;
            operationChar = "-";
        }*/
        ((TextView)activity.findViewById(R.id.firstPart)).setText(Integer.toString(first));
        ((TextView)activity.findViewById(R.id.secondPart)).setText(Integer.toString(second));
        ((TextView)activity.findViewById(R.id.operationPart)).setText(operationChar);
    }

    public void Execute(AppCompatActivity activity, final PossibleAnswers answers) {
        final EditText resultText = (EditText)activity.findViewById(R.id.resultText);
        assert resultText != null;
        resultText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                int localresult;
                try {
                    localresult = Integer.parseInt(s.toString());
                } catch (NumberFormatException e) {
                    localresult = -1;
                }

                if (result == localresult) {
                    answers.Correct();
                } else {
                    answers.Incorrect();
                }
            }
        });
    }
}
