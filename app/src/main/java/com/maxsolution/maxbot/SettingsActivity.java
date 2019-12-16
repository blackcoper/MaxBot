package com.maxsolution.maxbot;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Abdul on 9/26/2017.
 */

public class SettingsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Bundle bundle = new Bundle();
//        bundle.putBoolean("force_base_url", getIntent().getBooleanExtra("force_base_url",false) );
        SettingsFragment settingsFragment = new SettingsFragment();
//        settingsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, settingsFragment)
                .commit();
    }
}