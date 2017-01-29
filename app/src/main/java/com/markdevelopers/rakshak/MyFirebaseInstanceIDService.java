package com.markdevelopers.rakshak;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.markdevelopers.rakshak.data.local.SharedPreferenceManager;

/**
 * Created by Belal on 03/11/16.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";


    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
       Log.d(TAG, "Refreshed token: " + refreshedToken);
        storeToken(refreshedToken);
    }



    private void storeToken(String token) {
        //saving the token on shared preferences
        new SharedPreferenceManager(getApplicationContext()).saveDeviceToken(token);
    }


}