package com.example.android.dailynews;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

public class settingFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.pref_news);
    }
}
