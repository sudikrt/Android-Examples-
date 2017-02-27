package com.geeksynergy.deeptest;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.airbnb.deeplinkdispatch.DeepLink;

/**
 * Created by Foolish Guy on 2/23/2017.
 */
@WebDeepLink({"/users/{id}", "/users"})

public class Second extends Activity {
    private static final String ACTION_DEEP_LINK_METHOD = "deep_link_method";
    private static final String ACTION_DEEP_LINK_COMPLEX = "deep_link_complex";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.hel);

        Intent intent = getIntent();
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            Bundle parameters = intent.getExtras();

            String idString = parameters.getString("id");
            if (!TextUtils.isEmpty(idString)) {
                textView.setText(idString);
            } else {
                textView.setText("no id in the deeplink");
            }
        }
        if (intent.hasExtra("data")) {
            textView.setText(String.valueOf(intent.getStringExtra("data")));
        }

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("wykedl://sec/123"));
                startActivity(intent);
            }
        });
    }

    @DeepLink("wykedl://second")
    public static Intent intentForDeepLinkMethod (Context context) {
        Intent intent = new Intent(context, Second.class);
        intent.putExtra("data", "hi");
        intent.setAction(ACTION_DEEP_LINK_METHOD);
        return intent;
    }

    @DeepLink("wykedl://sec/{data}")
    public static TaskStackBuilder intentForDeepLinkMethod (Context context, Bundle bundle) {
        Intent detailsIntent =
                new Intent(context, MainActivity.class).setAction(ACTION_DEEP_LINK_COMPLEX);
        Intent parentIntent =
                new Intent(context, Second.class).setAction(ACTION_DEEP_LINK_COMPLEX);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(context);
        taskStackBuilder.addNextIntent(parentIntent);
        taskStackBuilder.addNextIntent(detailsIntent);
        return taskStackBuilder;
    }

}
