package com.arpith.dmucs;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class DMUCSNotification extends Activity {

	Boolean answer = true;
	String uid="1";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dmucs_notification);

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		TextView message = (TextView) findViewById(R.id.tv);
		message.setText("No new notifications");
		Button no = (Button) findViewById(R.id.button1);
		Button yes = (Button) findViewById(R.id.button2);
		if (!answer) {
			no.setText("");
			no.setBackground(new ColorDrawable(Color.TRANSPARENT));
			yes.setText("");
			yes.setBackground(new ColorDrawable(Color.TRANSPARENT));
		} else{
			yes.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent (DMUCSNotification.this,WriteQueryDatabase.class);
					i.putExtra("query", "update survey set yes = yes+1 where uid='"+uid+"';");
					i.putExtra("text", "Survey Updated");
					startActivity(i);
				}
			});
			
			no.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					Intent i = new Intent (DMUCSNotification.this,WriteQueryDatabase.class);
					i.putExtra("query", "update survey set no = no+1 where uid='"+uid+"';");
					i.putExtra("text", "Survey Updated");
					startActivity(i);
					
				}
			});
		}
	}
}
