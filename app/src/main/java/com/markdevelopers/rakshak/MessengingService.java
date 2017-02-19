package com.markdevelopers.rakshak;

import android.content.Intent;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.markdevelopers.rakshak.news.NewsFeedActivity;

import org.json.JSONArray;
import org.json.JSONObject;

public class MessengingService extends FirebaseMessagingService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private static final String TAG = "GCMIntentService";
    static JSONArray user = null;

//    public MessengingService() {
//        super(CommonUtilities.SENDER_ID);
//    }

    /**
     * Method called on device registered
     **/
//    @Override
//    protected void onRegistered(Context context, String registrationId) {
//        Log.i(TAG, "Device registered: regId = " + registrationId);
//        // displayMessage(context, "Your device registred with GCM", "GCM", "0");
//        ServerUtilities.register(context, registrationId);
//    }

    /**
     * Method called on device un registred
     */
//    @Override
//    protected void onUnregistered(Context context, String registrationId) {
//        Log.i(TAG, "Device unregistered");
//        // displayMessage(context, getString(R.string.gcm_unregistered), "GCM", "0");
//        ServerUtilities.unregister(context, registrationId);
//    }

    /**
     * Method called on Receiving a new message
     */
//    @Override
//    protected void onMessageRece(Context context, Intent intent) {
//
//    }
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                sendPushNotification(json);
            } catch (Exception e) {
            }
        }
    }

    /**
     * Method called on receiving a deleted message
     */
    private void sendPushNotification(JSONObject json) {
        //optionally we can display the json into log

        try {
            //getting the json data
            JSONObject data = json.getJSONObject("data");

            //creating MyNotificationManager object
            NotificationManager mNotificationManager = new NotificationManager(getApplicationContext());

            //creating an intent for the notification
            Intent messageIntent = new Intent(getApplicationContext(), NewsFeedActivity.class);
            messageIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            String title = data.getString("title");
            String message = data.getString("message");
            String status = data.getString("status");
            String did = data.getString("did");
            mNotificationManager.showMessageNotification(title, message, messageIntent, status, did);
        } catch (Exception e) {

        }
    }


}
