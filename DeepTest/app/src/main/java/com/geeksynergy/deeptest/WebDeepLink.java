package com.geeksynergy.deeptest;

import com.airbnb.deeplinkdispatch.DeepLinkSpec;

/**
 * Created by Foolish Guy on 2/23/2017.
 */


@DeepLinkSpec(prefix = { "http://wyke.in", "https://wyke.in" })
public @interface WebDeepLink {
    String[] value();
}