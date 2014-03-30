package com.arpith.dmucs;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ReportDisaster2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_disaster2);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.report_disaster2, menu);
		return true;
	}

}
