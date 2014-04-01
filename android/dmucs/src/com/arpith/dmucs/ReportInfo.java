package com.arpith.dmucs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class ReportInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportinfo);
		
		Bundle b = getIntent().getExtras();
		Toast.makeText(getBaseContext(), b.getString("pid"), Toast.LENGTH_SHORT).show();
	}
}
