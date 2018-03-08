package com.example.e610.quranmessenger.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.e610.quranmessenger.Fragments.SettingsAzanFragment;
import com.example.e610.quranmessenger.Fragments.SettingsAzkarFragment;
import com.example.e610.quranmessenger.Fragments.SettingsFragment;
import com.example.e610.quranmessenger.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.PreferencesTheme);
        setContentView(R.layout.activity_settings);
        if(getSupportActionBar()!=null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        Intent intent=getIntent();
        String action=intent.getAction();
        if(action.equals("main_settings")) {
            // addAllSettingFragments(getFragmentManager());
            SettingsFragment settingsFragment = new SettingsFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.setting_container, settingsFragment)
                    .commit();

        }else if(action.equals("azan_settings")){
            SettingsAzanFragment settingsFragment = new SettingsAzanFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.setting_container, settingsFragment)
                    .commit();
        }else if(action.equals("azkar_settings")){
            SettingsAzkarFragment settingsFragment = new SettingsAzkarFragment();
            getFragmentManager().beginTransaction()
                    .replace(R.id.setting_container, settingsFragment)
                    .commit();
        }
    }
}