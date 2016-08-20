package com.example.michalski.mylockscreen;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Pictures {
    public Map<String, Integer> pictures;
    public ArrayList<String> pictures_keys;

    Pictures() {
        pictures = new HashMap<>();
        pictures.put("auto", R.drawable.auto);
        pictures.put("dom", R.drawable.domek);
        pictures.put("kot", R.drawable.kot);
        pictures.put("lalka", R.drawable.lalka);
        pictures.put("pies", R.drawable.pies);
        pictures.put("pilka", R.drawable.pilka);
        pictures.put("rower", R.drawable.rower);
        pictures_keys = new ArrayList<>(pictures.keySet());
    }
}
