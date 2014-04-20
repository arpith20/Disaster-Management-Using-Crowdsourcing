package com.arpith.dmucs;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Splash extends Activity {
	
	boolean flag = true;
	String versionName=null;
	TextView app_version;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		
		PreferenceManager.setDefaultValues(this, R.xml.mypreference, false);
		PreferenceManager.setDefaultValues(this, R.xml.advancepreference, false);
		
		SharedPreferences getPrefs = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		boolean startup = getPrefs.getBoolean("splash", false);
		
		if (!startup) {
			Intent openMainPage = new Intent("android.intent.action.LOGIN");
			startActivity(openMainPage);
		} else {
			app_version=(TextView)findViewById(R.id.AppVersion);
			try {
				versionName = this.getPackageManager().getPackageInfo(
						this.getPackageName(), 0).versionName;
			} catch (NameNotFoundException e) {
				e.printStackTrace();
			}
			app_version.setText("Version: "+versionName);
			Thread timer = new Thread() {
				public void run() {
					try {
						sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally {
						Intent openMainPage = new Intent(
								"android.intent.action.LOGIN");
						startActivity(openMainPage);
					}
				}
			};
			timer.start();
		}
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		finish();
	}
}
