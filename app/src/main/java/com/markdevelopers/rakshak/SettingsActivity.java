package com.markdevelopers.rakshak;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;

public class SettingsActivity extends PreferenceActivity {
    int x, y;
    String fontStr;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        final SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);

        Preference dialogPreference = (Preference) getPreferenceScreen().findPreference("reset_default_list");
        dialogPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                new AlertDialog.Builder(SettingsActivity.this)

                        .setMessage(R.string.reset_all_settings)
                        .setPositiveButton(R.string.reset, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                SharedPreferences.Editor prefs = sharedPrefs.edit();
                                prefs.putString("sm_notification_ringtone", Settings.System.DEFAULT_NOTIFICATION_URI.toString());
                                prefs.putString("vibrate_sm", "1");
                                prefs.apply();

                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });
    }
}

