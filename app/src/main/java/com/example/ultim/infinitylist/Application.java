package com.example.ultim.infinitylist;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.widget.Toast;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

/**
 * Created by Ultim on 19.04.2017.
 */

public class Application extends android.app.Application {

    public static final int UPDATE_TIME = 30000;
    public static final String INTENT_NAME = "ultim.intent.action.Alarm";
    public static final int SIZE_FEED = 10;

    VKAccessTokenTracker vkAccessTokenTracker = new VKAccessTokenTracker() {
        @Override
        public void onVKAccessTokenChanged(VKAccessToken oldToken, VKAccessToken newToken) {
            if (newToken == null) {
                // VKAccessToken is invalid
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        vkAccessTokenTracker.startTracking();
        VKSdk.initialize(this);
        // Init Picasso
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this,Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

        // ALARM

        Intent alarm = new Intent();
       // Intent alarm = new Intent(getBaseContext(), AlarmService.class);
        alarm.setAction(INTENT_NAME);
        //Intent alarm = new Intent(INTENT_NAME);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 0, alarm, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setWindow(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), UPDATE_TIME, pendingIntent);
    }
}

