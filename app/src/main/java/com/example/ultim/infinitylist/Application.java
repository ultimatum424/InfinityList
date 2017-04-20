package com.example.ultim.infinitylist;

import android.content.Intent;
import android.widget.Toast;

import com.vk.sdk.VKAccessToken;
import com.vk.sdk.VKAccessTokenTracker;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

/**
 * Created by Ultim on 19.04.2017.
 */

public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String[] fingerprints = VKUtil.getCertificateFingerprint(this, this.getPackageName());
        VKSdk.initialize(this);
    }
}

