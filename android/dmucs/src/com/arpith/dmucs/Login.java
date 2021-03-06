package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Login extends SwipeBackActivity {
	String User;
	int Password;

	EditText uid;
	EditText pass;
	
	String ip, EnteredPassword;
	
	ImageButton ib_menu;

	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	boolean d;

	private static String url_account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		d = false;

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
		ib_menu = (ImageButton)findViewById(R.id.ib_logo_menu);
		
		ib_menu.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				openOptionsMenu();
			}
		});

		b.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				Intent i = new Intent(Login.this, MainActivity.class);
				startActivity(i);

				finish();
				return true;
			}
		});
		;

		b.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SharedPreferences getIP = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				ip = getIP.getString("ip", "");
				url_account = "http://"+ip+"/arpith/dmucs/login.php";
				
				User = uid.getText().toString();
				EnteredPassword = pass.getText().toString();

				Log.d("hashedPass", "" + EnteredPassword.hashCode());
				new CheckPassword().execute();
			}
		});
		
		Button signup = (Button) findViewById(R.id.signup);
		signup.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i =new Intent(Login.this,SignUp.class);
				startActivity(i);
				//finish();
			}
		});
	}

	class CheckPassword extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Login.this);
			pDialog.setMessage("Loading...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			String n1 = User;

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone", n1));

			JSONObject json = jsonParser.makeHttpRequest(url_account, "GET",
					params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");
				Password = json.getInt("password");
				if (success == 1) {
					d = true;
				} else {
					d = false;
					Password = 1;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			if (Password == EnteredPassword.hashCode()) {

				SharedPreferences uname = getSharedPreferences("user", 0);
				SharedPreferences.Editor unameEdit = uname.edit();
				unameEdit.putBoolean("first", false);
				unameEdit.putString("name", User);
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

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.advanced:
			Intent i = new Intent(Login.this, APreference.class);
			startActivity(i);
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onResume() {
		super.onResume();
		SwipeBackLayout mSwipeBackLayout;
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
	}

}
