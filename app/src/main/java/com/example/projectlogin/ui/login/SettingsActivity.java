package com.example.projectlogin.ui.login;
import android.os.Bundle;
import com.example.projectlogin.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.idFrameLayout, new SettingsFragment())
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            if (findViewById(R.id.idFrameLayout) != null) {
                if (savedInstanceState != null) {
                    return;
                }

            }
        }
    }
    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.preferences, rootKey);
            SwitchPreferenceCompat darkModeSwitch = (SwitchPreferenceCompat) findPreference("darkmode");
            assert darkModeSwitch != null;
            darkModeSwitch.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
                        setPreferencesFromResource(R.xml.preferences, rootKey);
                        SwitchPreferenceCompat darkModeSwitch = (SwitchPreferenceCompat) findPreference("darkmode");
                        darkModeSwitch.setChecked(false);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    } else {
                        setPreferencesFromResource(R.xml.preferences, rootKey);
                        SwitchPreferenceCompat darkModeSwitch = (SwitchPreferenceCompat) findPreference("darkmode");
                        darkModeSwitch.setChecked(true);
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    }
                    return false;
                }
            });
        }
    }
}
