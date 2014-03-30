package com.arpith.dmucs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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

		// TODO remove the following two lines
		SharedPreferences uname = getSharedPreferences("user", 0);
		boolean first = uname.getBoolean("first", true);
		if (!first) {
			Intent i = new Intent(Login.this, MainActivity.class);
			startActivity(i);
			finish();
		}
		uid = (EditText) findViewById(R.id.uid);
		pass = (EditText) findViewById(R.id.pass);
		Button b = (Button) findViewById(R.id.btnLogin);

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String user = uid.getText().toString();
				String password = pass.getText().toString();

				if (password.matches(correctPass)) {

					SharedPreferences uname = getSharedPreferences("user", 0);
					SharedPreferences.Editor unameEdit = uname.edit();
					unameEdit.putBoolean("first", false);
					unameEdit.putString("name", user);
					unameEdit.commit();
					
					Intent i = new Intent(Login.this, MainActivity.class);
					startActivity(i);
					
					finish();

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
