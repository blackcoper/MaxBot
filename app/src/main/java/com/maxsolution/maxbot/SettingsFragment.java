package com.maxsolution.maxbot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceCategory;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;


/**
 * Created by Abdul on 9/26/2017.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{
    public static final String TAG = SettingsFragment.class.getSimpleName();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); i++) {
            pickPreferenceObject(getPreferenceScreen().getPreference(i));
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    private void openPreference(String key) {
        PreferenceScreen preferenceScreen = getPreferenceScreen();
        final ListAdapter listAdapter = preferenceScreen.getRootAdapter();

        final int itemsCount = listAdapter.getCount();
        int itemNumber;
        for (itemNumber = 0; itemNumber < itemsCount; ++itemNumber) {
            if (listAdapter.getItem(itemNumber).equals(findPreference(key))) {
                preferenceScreen.onItemClick(null, null, itemNumber, 0);
                break;
            }
        }
    }
    private void pickPreferenceObject(Preference p) {
        if (p instanceof PreferenceCategory) {
            PreferenceCategory cat = (PreferenceCategory) p;
            for (int i = 0; i < cat.getPreferenceCount(); i++) {
                pickPreferenceObject(cat.getPreference(i));
            }
        } else {
            initSummary(p);
        }
    }

    private void initSummary(Preference p) {
        if (p instanceof EditTextPreference) {
            EditTextPreference editTextPref = (EditTextPreference) p;
            p.setSummary(editTextPref.getText());
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        // Set up a listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onPause() {
        super.onPause();
        // Unregister the listener whenever a key changes
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if (s.equals("pref_access_token")) {
            Preference connectionPref = findPreference(s);
            connectionPref.setSummary(sharedPreferences.getString(s, ""));
            if(MainActivity.get()!=null)MainActivity.get().updateAccessToken();
        }
    }
}