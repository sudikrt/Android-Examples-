package com.geeksynergy.deeptest;

import android.app.Application;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import com.airbnb.deeplinkdispatch.DeepLinkHandler;

/**
 * Created by Foolish Guy on 2/23/2017.
 */

public class AppLink extends Application{
    @Override public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(DeepLinkHandler.ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(new DeepLinkReceiver(), intentFilter);
    }
}
