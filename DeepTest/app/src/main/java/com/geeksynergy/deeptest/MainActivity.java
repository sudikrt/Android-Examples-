package com.geeksynergy.deeptest;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.airbnb.deeplinkdispatch.DeepLink;

@DeepLink({"http://wyke.in/mytask/{id}", "https://wyke.in/mytask/{id}", "wyke://wyke.in/mytask/{id}"})
@AppDeepLink("/data/{id}")
public class MainActivity extends AppCompatActivity {

    private static final String ACTION_DEEP_LINK_METHOD = "deep_link_method";
    private static final String ACTION_DEEP_LINK_COMPLEX = "deep_link_complex";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv = (TextView) findViewById(R.id.hel);
        Intent intent = getIntent();
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            Bundle parameters = intent.getExtras();
            String idString = parameters.getString("id");
            tv.setText(idString);
        }
    }

}
