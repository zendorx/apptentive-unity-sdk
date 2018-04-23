package com.nikaent.apptentiveproxy;

import android.app.Activity;

import org.json.JSONObject;
import com.nikaent.apptentive.ApptentiveHandler;
import static com.unity3d.player.UnityPlayer.currentActivity;

public class ApptentiveProxy {
    private static final String TAG = "ApptentiveProxy";
    private static String listenerName;

    static ApptentiveProxy INSTANCE = null;
    private static Activity activity;
    private static ApptentiveHandler handler;
    public ApptentiveProxy()
    {

    }

    public static ApptentiveProxy instance()
    {
        if (INSTANCE == null)
            INSTANCE = new ApptentiveProxy();

        return INSTANCE;
    }

    public static void initialize(final String APP_KEY, final String APP_SIG, final  String unityListenerName, boolean debugLogging)
    {
        //L.isDebug = debugLogging;
        //L.i(TAG, "initialize unity proxy");
        //L.i(TAG, "Listener name: " + unityListenerName);

        handler = null;
        listenerName = unityListenerName;
        activity = currentActivity;

        //Appte

        //ApptentiveHandler g;

        activity.runOnUiThread(new Runnable() {
            public void run() {
                handler = ApptentiveHandler.Initialize(activity.getApplication(), activity, APP_KEY, APP_SIG);
            }});
    }
    // ************************************

    private boolean isReady()
    {
        return handler != null;
    }

    public void setUserData(final String json_data)
    {
        if (!isReady())
        {
            return;
        }

        handler.setUserData(json_data);
    }

    public boolean canShowMessageCenter()
    {
        if (!isReady())
        {
            return false;
        }

        return handler.canShowMessageCenter();
    }

    public void showMessageCenterWithData(final String jsonData)
    {
        if (!isReady())
        {
            return;
        }

        handler.showMessageCenterWithData(jsonData);
    }

    public void showMessageCenter()
    {
        if (!isReady())
        {
            return;
        }

        handler.showMessageCenter();
    }

    public void engage(final String eventID)
    {
        if (!isReady())
        {
            return;
        }

        handler.engage(eventID);
    }

    public void showHiddenText(final String text)
    {
        if (!isReady())
        {
            return;
        }

        handler.showHiddenText(text);
    }

    public int getUnreadMessageCount()
    {
        if (!isReady())
        {
            return 0;
        }

        return handler.getUnreadMessageCount();
    }


    //Public methods ----------------------



    // ------------------------------------
    // ************************************







    //Listener's callbacks  example----------------

    /*@Override
    public void onSomethingHappened(Data data) {
        unityCallOnSpecialOfferDisappeared(data.string);
    }*/

    /*void unityCallSomethingHappened(final String someString)
    {
        L.i(TAG, "unityCallSomethingHappened " + someString);
        UnityPlayer.UnitySendMessage(listenerName, "Internal_unityCallSomethingHappened", someString);
    }*/
    // ************************************
}
