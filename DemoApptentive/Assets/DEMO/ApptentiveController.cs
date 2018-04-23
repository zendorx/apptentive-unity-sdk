using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ApptentiveController : MonoBehaviour {

	public Text text;

	private string message = "Tech Support";
	void Start () {
		//Apptentive.APP_KEY = "ANDROID-MYTESTAPP-430e3e433934";
		//Apptentive.APP_SIG = "229bcf0502dc6cec115fee1e7c44f682";
		Apptentive.Init();
		
		text.text = message;
	}
	
	public void ShowMessageCenter()
	{
		if (Apptentive.Instance != null)
			Apptentive.Instance.ShowMessageCenter();
	}
	
	
	
	// Update is called once per frame
	void Update () {
		//todo check instance == null
		
		if (Apptentive.Instance == null)
			return;
		
		int count = Apptentive.Instance.GetUnreadMessageCount();
		
		text.text = message;
		if (count != 0)
			text.text = message + " (" + count + ")";
	}
}
