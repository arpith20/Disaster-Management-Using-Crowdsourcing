package com.arpith.dmucs;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SubmitDonation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_submit_donation);
		
		TextView amt = (TextView)findViewById(R.id.amount);
		Bundle e = getIntent().getExtras();
		amt.setText("amount = "+e.getString("amt"));
	}

}
