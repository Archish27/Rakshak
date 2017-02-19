package com.markdevelopers.rakshak;

/**
 * Created by Belal on 03/11/16.
 */

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;


/**
 * Created by Ravi on 31/03/15.
 */

public class NotificationManager {


    public static final int ID_SMALL_NOTIFICATION = 235;

    private Context mCtx;
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

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(mCtx);
        Notification notification;
        notification = mBuilder
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(mCtx.getResources(), R.mipmap.ic_launcher))
                .setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(title)
                .setContentText(message)
                .setVibrate(pattern)
                .setSound(ringtone)
                .build();

        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        android.app.NotificationManager notificationManager = (android.app.NotificationManager) mCtx.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(ID_SMALL_NOTIFICATION, notification);
//        }
    }

}
