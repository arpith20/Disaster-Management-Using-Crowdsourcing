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
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class Account extends SwipeBackActivity {
	String phone, incident, lat, lng, damage, no_casualty;
	String you, comments, done, modified_time;
	Boolean d;
	int success;
	
	String n,p,dob,h,c,h_lat,h_lng,c_lat,c_lng,points;
	
	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static String url_account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "");
		if (ip.matches("")) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					Account.this);
			alertDialog.setTitle("IP Address is not set");
			alertDialog.setNeutralButton("OK", null);
			alertDialog.show();
		} else {

			url_account = "http://";
			url_account += ip;
			url_account += "/arpith/dmucs/account_info.php";

			Toast.makeText(getBaseContext(), "writing " + url_account,
					Toast.LENGTH_SHORT).show();

			//d = false;
			new Async_Account().execute();
			//while (!d)
				;

			// Do after loading
			TextView name = (TextView)findViewById(R.id.account_name);
			TextView phone = (TextView)findViewById(R.id.account_phoneno);
			TextView db = (TextView)findViewById(R.id.account_dob);
			TextView h_loc = (TextView)findViewById(R.id.account_home);
			TextView c_loc = (TextView)findViewById(R.id.account_current);
			TextView pnts = (TextView)findViewById(R.id.account_points);
			
			name.setText(n);
			phone.setText(p);
			db.setText(dob);
			h_loc.setText("("+h_lat+", "+h_lng+")");
			c_loc.setText("("+c_lat+", "+c_lng+")");
			pnts.setText(points);
			
			Toast.makeText(getBaseContext(), "" + success, Toast.LENGTH_SHORT)
					.show();

		}

	}

	class Async_Account extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Account.this);
			pDialog.setMessage("Loading...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			String n1 = "8105581711";

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone", n1));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_account,
					"GET", params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getInt("success");
				n=json.getString("name");
				p=json.getString("phone");
				dob=json.getString("dob");
				h_lat=json.getString("h_lat");
				h_lng=json.getString("h_lng");
				c_lat=json.getString("c_lat");
				c_lng=json.getString("c_lng");
				c=json.getString("name");
				points=json.getString("points");
				if (success == 1) {
					d = true;
				} else {
					d=true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}

	}
	
	@Override
    protected void onResume() {
        super.onResume();
        SwipeBackLayout mSwipeBackLayout;
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
    }
}
