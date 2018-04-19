using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ApptentiveAndroid : Apptentive {

#if UNITY_ANDROID && !UNITY_EDITOR
	private static AndroidJavaObject apptentive = null;

	protected override void Initialize()
	{
		if (apptentive != null)
		return;
        
		Debug.Log("ApptentiveAndroid: Initialize");
        

		using(var pluginClass = new AndroidJavaClass("com.nikaent.apptentiveproxy.ApptentiveProxy"))
		{
			apptentive = pluginClass.CallStatic<AndroidJavaObject>("instance");
			pluginClass.CallStatic("initialize", APP_KEY, APP_SIG, this.gameObject.name, DEBUG_LOGGING);
		}
		
	}

	bool checkInstance(string from)
	{
		if (apptentive == null)
		{
			Debug.LogError("ApptentiveAndroid instance is null ( " + from + ")");
			return false;
		}

		return true;
	}

	public override bool CanShowMessageCenter()
	{   
		if (checkInstance("PushReacted"))
		{
			return apptentive.Call<bool>("canShowMessageCenter");
		}
		
		return false;
	}
	
	public override void ShowMessageCenterWithData(string json)
	{   
		if (checkInstance("ShowMessageCenterWithData"))
		{
			apptentive.Call("ShowMessageCenterWithData", json);
		}
	}
	
	public override void ShowMessageCenter()
	{   
		if (checkInstance("ShowMessageCenter"))
		{
			apptentive.Call("ShowMessageCenter");
		}
	}
	
	public override void Engage(string eventID)
	{   
		if (checkInstance("Engage"))
		{
			apptentive.Call("engage", eventID);
		}
	}
	
	public override void ShowHiddenText(string text)
	{   
		if (checkInstance("ShowHiddenText"))
		{
			apptentive.Call("showHiddenText", text);
		}
	}
	
	public override int GetUnreadMessageCount()
	{   
		if (checkInstance("GetUnreadMessageCount"))
		{
			return apptentive.Call<int>("getUnreadMessageCount");
		}
		
		return 0;
	}
#endif
}
