package com.example.abdulmustafamemon.myapplication;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingFragment extends PreferenceFragment {

    private final  static String beep="beep";
    private final  static String frontCamera="frontCamera";
    private final  static String flashLight="flashLight";
    private final  static String vibration="vibration";
    private final  static String autoFocus="autoFocus";

    @Override
    public void onCreate(Bundle savedInstanceState){
        PreferenceManager manager = getPreferenceManager();
        super.onCreate(savedInstanceState);


        addPreferencesFromResource(R.xml.settings);
    }

}
