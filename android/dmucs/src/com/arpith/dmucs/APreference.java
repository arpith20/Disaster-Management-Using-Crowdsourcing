package com.arpith.dmucs;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class APreference extends PreferenceActivity {
	  @SuppressWarnings("deprecation")
	@Override
	  public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.advancepreference);
	  }
	} 

