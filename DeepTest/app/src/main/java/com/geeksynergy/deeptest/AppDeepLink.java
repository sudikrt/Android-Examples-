package com.geeksynergy.deeptest;

import com.airbnb.deeplinkdispatch.DeepLinkSpec;

/**
 * Created by Foolish Guy on 2/23/2017.
 */

@DeepLinkSpec(prefix = { "app://wyke" })
public @interface AppDeepLink {
    String[] value();
}