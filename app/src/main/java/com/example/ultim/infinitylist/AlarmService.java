package com.example.ultim.infinitylist;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ultim on 27.04.2017.
 */

public class AlarmService extends BroadcastReceiver {
    FileManager fileManager;
    int newDate = 0;
    int lastDate = 0;
    @Override
    public void onReceive(Context context, Intent intent) {
        fileManager = new FileManager(context);
        if(!MainActivity.active){
            getNewFeed(context);
        }
        Intent alarm = new Intent();
        //Intent alarm = new Intent(context, AlarmService.class);
        alarm.setAction(Application.INTENT_NAME);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarm, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), Application.UPDATE_TIME, pendingIntent);
    }

    private void getNewFeed(final Context context){
        VKRequest request = new VKRequest("newsfeed.get", VKParameters.from(VKApiConst.FILTERS, "post", VKApiConst.COUNT, 1));
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                newDate = (new Gson().fromJson(response.json.toString(), NewsFeed.class)).getDate();
                lastDate = fileManager.getLastData();
                if (newDate > lastDate){
                    Intent notificationIntent = new Intent(context, MainActivity.class);
                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                            | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingSelectIntent = PendingIntent.getActivity(context, 0,
                            notificationIntent, 0);

                    Notification mBuilder = new NotificationCompat.Builder(context)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("VK")
                            .setContentIntent(pendingSelectIntent)
                            .setAutoCancel(true)
                            .setContentText("Новые записи").build();

                    NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                    mNotificationManager.notify(1, mBuilder);
                }
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }
        });
    }
}
