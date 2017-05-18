package com.example.foolishguy.fcm_chatting;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Date;

import static com.example.foolishguy.fcm_chatting.FBRef.initDB;

/**
 * Created by Foolish Guy on 1/18/2017.
 */

public class dbRefSpace {
    private static final String TAG = "DB_REF";

    public static String lastFCMtoken = FirebaseInstanceId.getInstance().getToken();
    public static DatabaseReference users;
    public static DatabaseReference chats;
    public static DatabaseReference root;

    public static Boolean hasUserSignedIn = false;
    public static Boolean newSession = false;


    public static FirebaseAuth wFBAuth;
    public static String wUserName;
    public static String wEmail;
    public static String wAuthProvider;
    public static String wUID;
    public static Uri wProfilePicURL;
    public static long lastLogin;


    public static FirebaseDatabase fcmDB;
    public static Boolean isDBaccessible = false;
    public static Boolean iswDBaccessible = false;


    public static void setwAuth(final FirebaseAuth iAuth) {
        iAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                wFBAuth = firebaseAuth;
                if (wFBAuth.getCurrentUser() != null) {
                    Log.i(TAG, "onAuthStateChanged: UserSignedIn Successful");
                    hasUserSignedIn = true;
                    lastLogin = new Date().getTime();
                    newSession = true;
                    wUserName = wFBAuth.getCurrentUser().getDisplayName();
                    wEmail = wFBAuth.getCurrentUser().getEmail();
                    wAuthProvider = wFBAuth.getCurrentUser().getProviderId();
                    wUID = wFBAuth.getCurrentUser().getUid();
                    wProfilePicURL = wFBAuth.getCurrentUser().getPhotoUrl();
                } else {
                    Log.i(TAG, "onAuthStateChanged: UserSignedIn Failed");
                    hasUserSignedIn = false;
                    wUserName = "Guest";
                    wEmail = "guest@mail.in";
                    wAuthProvider = "mail";
                    wUID = "0";
                    wProfilePicURL = Uri.EMPTY;
                    isDBaccessible = false;
                    iswDBaccessible = false;
                }
                initDB();
            }
        });
    }

    public static void setDataBase(FirebaseDatabase database) {
        if (database != null) {
            isDBaccessible = true;
            fcmDB = database;
            root = fcmDB.getReference();
            users = root.child("users");
            chats = root.child("chats");
        }
    }
}
