package com.geeksynergy.deeptest;

import android.app.Activity;
import android.os.Bundle;

import com.airbnb.deeplinkdispatch.DeepLinkHandler;

/**
 * Created by Foolish Guy on 2/23/2017.
 */

@DeepLinkHandler( {SimpleModule.class, LibraryDeepLinkModule.class})
public class DeepLinkActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DeepLinkDelegate deepLinkDelegate =
                new DeepLinkDelegate(new SimpleModuleLoader(), new LibraryDeepLinkModuleLoader());

        deepLinkDelegate.dispatchFrom(this);

        finish();
    }
}
