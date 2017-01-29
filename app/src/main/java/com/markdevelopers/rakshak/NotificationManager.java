package com.markdevelopers.rakshak;

/**
 * Created by Belal on 03/11/16.
 */

import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by Ravi on 31/03/15.
 */

public class NotificationManager {


    public static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;
    public static final String ACTION_1 = "Yes";

    public NotificationManager(Context mCtx) {
        this.mCtx = mCtx;
    }

    public void showMessageNotification(String title, String message, Intent intent, String status, String did) {

        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        mCtx,
                        ID_SMALL_NOTIFICATION,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        long[] pattern = new long[0];
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(mCtx);

        String vibrateStringSM = sharedPrefs.getString("vibrate_sm", "1");
        Uri ringtone = Uri.parse(sharedPrefs.getString("sm_notification_ringtone", Settings.System.DEFAULT_NOTIFICATION_URI.toString()));
        switch (vibrateStringSM) {
            case "1":
                pattern = new long[]{1000, 1000, 1000, 1000};
                break;
            case "2":
                pattern = new long[]{500, 500, 500, 500};
                break;
            case "3":
                pattern = new long[]{200, 200, 200, 200};
                break;
            case "4":
                pattern = new long[]{0, 0, 0, 0};
                break;

        }
        if (sharedPrefs.getBoolean("mute_all", false)) {
            pattern = new long[]{0, 0, 0, 0};
            ringtone = null;
        }

//        if (status.equals("DisasterReport")) {
//            Intent action1Intent = new Intent(mCtx, NotificationActionService.class)
//                    .setAction(ACTION_1);
//
//            PendingIntent res =
//                    PendingIntent.getActivity(
//                            mCtx,
//                            ID_SMALL_NOTIFICATION,
//                            action1Intent,
//                            PendingIntent.FLAG_UPDATE_CURRENT
//                    );
//            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
//            Notification notification;
//            notification = mBuilder.setSmallIcon(R.mipmap.ic_launcher)
//                    .setTicker(title).setWhen(0)
//                    .setAutoCancel(true)
//                    .setContentIntent(resultPendingIntent)
//                    .setContentTitle(title)
//                    .setSmallIcon(R.mipmap.ic_launcher)
//                    .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
//                    .setContentText(message)
//                    .setVibrate(pattern)
//                    .setSound(ringtone)
//                    .addAction(new NotificationCompat.Action(R.drawable.cancel, "No", null))
//                    .addAction(new NotificationCompat.Action(R.drawable.yes, "Yes", res))
//                    .build();
//
//            notification.flags |= Notification.FLAG_AUTO_CANCEL;
//
//            android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
//            notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
//        } else {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder
                .setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setContentText(message)
                .setVibrate(pattern)
                .setSound(ringtone)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
//        }
    }

    //The method will return Bitmap from an image URL
    private Bitmap getBitmapFromURL(String strURL) {
        try {
            URL url = new URL(strURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static class NotificationActionService extends IntentService {
        public NotificationActionService() {
            super(NotificationActionService.class.getSimpleName());
        }

        @Override
        protected void onHandleIntent(Intent intent) {
            String action = intent.getAction();
            if (ACTION_1.equals(action)) {

            }
        }
    }
}