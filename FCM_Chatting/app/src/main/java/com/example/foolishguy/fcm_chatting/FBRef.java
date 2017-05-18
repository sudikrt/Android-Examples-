package com.example.foolishguy.fcm_chatting;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;

import static com.example.foolishguy.fcm_chatting.dbRefSpace.setDataBase;

/**
 * Created by Foolish Guy on 1/18/2017.
 */

public class FBRef {
    private static final String TAG = "FireDB";

    private static Boolean initDBcalledAlready = false;
    public static Boolean initDB() {
        if (!initDBcalledAlready && false) //&& false This never gets executed (preventing persistence)
        {
            FirebaseDatabase.getInstance().setPersistenceEnabled(false);
            initDBcalledAlready = true;
        }
        setDataBase(FirebaseDatabase.getInstance());
        if (dbRefSpace.hasUserSignedIn) {
            Log.d(TAG, "initDB: You have secure Access");
        } else
            Log.d(TAG, "initDB: You don't have secure Access");
        return true;
    }
}
