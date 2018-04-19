using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using JsonUtils = NIKAMINIJSON.MiniJSON;

/*TODO
wrap all public methods - Apptentive.Instance.Method => Apptentive.Mehtod
*/


#if UNITY_EDITOR
using ApptentiveType = ApptentiveEditor;
#elif UNITY_ANDROID
using ApptentiveType = ApptentiveAndroid;
#elif UNITY_IOS
using ApptentiveType = ApptentiveIOS;
#else 
using ApptentiveType = Apptentive;
#endif

public class Apptentive : MonoBehaviour {

	protected Apptentive() {}

	protected virtual void Initialize() {}
	public static string APP_KEY = "";
	public static string APP_SIG = "";
	public static bool DEBUG_LOGGING = true;


	private void ErrMessage(string funcName)
	{
		Debug.Log ("[NIKAENT] " + funcName + " is not supported on this platform");
	}

	public virtual bool CanShowMessageCenter()
	{
		ErrMessage("CanShowMessageCenter");
		return false;
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
	public virtual void ShowMessageCenterWithData(string json)
	{
		ErrMessage("ShowMessageCenterWithData");
	}
	
	public virtual void ShowMessageCenter()
	{
		ErrMessage("ShowMessageCenter");
	}
	
	public virtual void Engage(string eventID)
	{
		ErrMessage("engage");
	}
	
	public virtual void ShowHiddenText(string text)
	{
		ErrMessage("showHiddenText");
	}
	
	public virtual int GetUnreadMessageCount()
	{
		ErrMessage("GetUnreadMessageCount");
		return 0;
	}
	
	//Private methods * * * * * * * * * * * * * * * * * * * * * * * * * * * * * 

	/*private void Internal_OnSomethingHappened(string json)
	{
		//call delegate method
	}
	*/
	
	//Singlton ----------------------------------------------------------------------------------------------------
	private static ApptentiveType _instance;
	private static object _lock = new object();
	
	
	public static void Init()
	{
		Apptentive instance = Apptentive.Instance;
	}
	
	public static ApptentiveType Instance
	{
		get
		{
			if (applicationIsQuitting) {
				Debug.LogWarning("[Singleton] Instance '"+ typeof(ApptentiveType) +
					"' already destroyed on application quit." +
					" Won't create again - returning null.");
				return null;
			}

			lock(_lock)
			{
				if (_instance == null)
				{
					_instance = (ApptentiveType) FindObjectOfType(typeof(ApptentiveType));

					if ( FindObjectsOfType(typeof(ApptentiveType)).Length > 1 )
					{
						Debug.LogError("[Singleton] Something went really wrong " +
							" - there should never be more than 1 singleton!" +
							" Reopening the scene might fix it.");
						return _instance;
					}

					if (_instance == null)
					{
						GameObject singleton = new GameObject();
						singleton.name = "(singleton) "+ typeof(ApptentiveType).ToString();
						_instance = singleton.AddComponent<ApptentiveType>();
						_instance.Initialize ();

						DontDestroyOnLoad(singleton);

						Debug.Log("[Singleton] An instance of " + typeof(ApptentiveType) + 
							" has been created with DontDestroyOnLoad.");
					} else {
						Debug.Log("[Singleton] Using instance already created: " +
							_instance.gameObject.name);
					}
				}

				return _instance;
			}
		}
	}


	private static bool applicationIsQuitting = false;
	/// <summary>
	/// When Unity quits, it destroys objects in a random order.
	/// In principle, a Singleton is only destroyed when application quits.
	/// If any script calls Instance after it have been destroyed, 
	///   it will create a buggy ghost object that will stay on the Editor scene
	///   even after stopping playing the Application. Really bad!
	/// So, this was made to be sure we're not creating that buggy ghost object.
	/// </summary>
	public void OnDestroy () {
		applicationIsQuitting = true;
	}
}
