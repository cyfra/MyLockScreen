package com.example.michalski.mylockscreen;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Random;


interface PossibleAnswers {
    public void Correct();
    public void Incorrect();
}

interface PossibleQuestions {
    public void Init(AppCompatActivity activity);

    public void Execute(AppCompatActivity activity, final PossibleAnswers answers);
}

class SimpleAddOrDelete implements PossibleQuestions{
    int first, second, result;
    public enum MyOperation { ADD, SUBTRACT};
    MyOperation operation;
    public void Init(AppCompatActivity activity) {
        activity.setContentView(R.layout.activity_main);
        Random r = new Random();
        first = r.nextInt(5) + 1;
        String operationChar = null;
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
                int localresult = -1;
                try {
                    localresult = Integer.parseInt(s.toString());
                } catch (java.lang.NumberFormatException e) {
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

public class MainActivity extends AppCompatActivity {

    private ComponentName mComponentName;
    private static final int ADMIN_INTENT = 15;
    boolean wasSuccessful = false;

    PossibleQuestions possibleQuestions;
    DevicePolicyManager mDevicePolicyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        possibleQuestions = new SimpleAddOrDelete();
        possibleQuestions.Init(this);

        makeFullScreen();
        startService(new Intent(this,LockScreenService.class));
        mDevicePolicyManager = (DevicePolicyManager)getSystemService(
                Context.DEVICE_POLICY_SERVICE);
        mComponentName = new ComponentName(this, MyAdminReceiver.class);
        if (!mDevicePolicyManager.isAdminActive(mComponentName)) {
            Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, mComponentName);
            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "To run the block");
            startActivityForResult(intent, ADMIN_INTENT);
        }

        wasSuccessful = false;
        possibleQuestions.Execute(this, new PossibleAnswers() {
            @Override
            public void Correct() {
                wasSuccessful = true;
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_LOW_L, 60);
                toneG.startTone(ToneGenerator.TONE_CDMA_MED_L, 60);
                toneG.startTone(ToneGenerator.TONE_CDMA_HIGH_L, 60);
                moveTaskToBack(true);
            }

            @Override
            public void Incorrect() {
                ToneGenerator toneG = new ToneGenerator(AudioManager.STREAM_ALARM, 100);
                toneG.startTone(ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 200);
                wasSuccessful = false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!wasSuccessful) {
            mDevicePolicyManager.lockNow();
        } else {

            Intent intent = new Intent(this, MainActivity.class);
            intent.setAction(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
            AlarmManager alarmManager = (AlarmManager) this.getSystemService(this.ALARM_SERVICE);
            alarmManager.setExact(AlarmManager.ELAPSED_REALTIME, SystemClock.elapsedRealtime() + 1000 * 60 * 5, pendingIntent);
        }
    }


    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        // do nothing.
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADMIN_INTENT) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "Registered As Admin", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getApplicationContext(), "Failed to register as Admin", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
