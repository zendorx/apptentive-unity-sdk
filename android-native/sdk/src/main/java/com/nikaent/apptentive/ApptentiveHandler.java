package com.nikaent.apptentive;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.module.messagecenter.UnreadMessagesListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ApptentiveHandler {

    private String                      TAG = "Apptentive";
    private static String               VERSION_SDK = "0.0.1";
    private static ApptentiveHandler    INSTANCE;
    private final Application           application;
    private final Activity              activity;
    private String                      appKey;
    private String                      appSig;
    private int                         unreadMessageCount;
    private UnreadMessagesListener      unreadMessageCountListener;
    private boolean                     canShowMessageCenter;

    public void Initialize(final Application application, final Activity activity, final String APP_KEY, final String APP_SIG)
    {
        if (INSTANCE == null)
        {
            INSTANCE = new ApptentiveHandler(application, activity, APP_KEY, APP_SIG);
        }
        else
        {
            L.e(TAG, "SDK already initialized.");
        }
    }

    private ApptentiveHandler(final Application application, final Activity activity, final String APP_KEY, final String APP_SIG)
    {
        L.i(TAG, "Initializing ApptentiveHandler [" + VERSION_SDK + " android");
        this.unreadMessageCount = 0;
        this.canShowMessageCenter = false;
        this.application = application;
        this.activity = activity;
        this.appKey = APP_KEY;
        this.appSig = APP_SIG;

        Apptentive.register(application, APP_KEY, APP_SIG);

        unreadMessageCountListener = new UnreadMessagesListener() {
            @Override
            public void onUnreadMessageCountChanged(int unreadMessages) {
                unreadMessageCount = unreadMessages;
            }
        };

        Apptentive.addUnreadMessagesListener(unreadMessageCountListener);

        Apptentive.canShowMessageCenter(new Apptentive.BooleanCallback() {
            @Override
            public void onFinish(boolean canShowMessageCenter) {
                ApptentiveHandler.this.canShowMessageCenter = canShowMessageCenter;
            }});

    }

    /*json format:
    * {
    *   [
    *       {"key":"value"},
    *       {"key2":"value2"},
    *       {"key3":"value3"}
    *   ]
    * }
    */

    public boolean canShowMessageCenter()
    {
        return canShowMessageCenter;
    }

    public void showMessageCenterWithData(final String jsonData)
    {
        if (!canShowMessageCenter)
            return;

        try {
            Map<String, Object> customData = new HashMap<>();
            JSONObject object = new JSONObject(jsonData);
            Iterator<String> keysItr = object.keys();
            while(keysItr.hasNext()) {
                String key = keysItr.next();
                String value = object.getString(key);
                customData.put(key, value);
            }

            showMessageCenterWithData(customData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void showMessageCenterWithData(final Map<String, Object> customData)
    {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Apptentive.showMessageCenter(activity, customData);
            }});
    }

    public void showMessageCenter()
    {
        if (!canShowMessageCenter)
            return;

        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Apptentive.showMessageCenter(activity);
            }});
    }

    public void engage(final String eventID)
    {
        Apptentive.engage(activity, eventID);
    }

    public void showHiddenText(final String text)
    {
        Apptentive.sendAttachmentText(text);
    }

    public int getUnreadMessageCount()
    {
        return unreadMessageCount;
    }
}
