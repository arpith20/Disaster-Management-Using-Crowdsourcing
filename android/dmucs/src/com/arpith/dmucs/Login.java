package com.arpith.dmucs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Login extends Activity {
	EditText uid;
	EditText pass;
	String correctPass = "test";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		//TODO remove the following two lines
		Intent i = new Intent(Login.this, MainActivity.class);
		startActivity(i);

		uid = (EditText) findViewById(R.id.uid);
		pass = (EditText) findViewById(R.id.pass);
		Button b = (Button) findViewById(R.id.btnLogin);

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String user = uid.getText().toString();
				String password = pass.getText().toString();

				if (password.matches(correctPass)) {
					
					//TODO: login should not be displayed again. set preference?
					
					Intent i = new Intent(Login.this, MainActivity.class);
					startActivity(i);

				} else {
					AlertDialog.Builder alertDialog = new AlertDialog.Builder(
							Login.this);
					alertDialog.setTitle("Incorrect Username or password");
					alertDialog.setNeutralButton("OK", null);
					alertDialog.show();
				}
			}
		});
	}

	

}
