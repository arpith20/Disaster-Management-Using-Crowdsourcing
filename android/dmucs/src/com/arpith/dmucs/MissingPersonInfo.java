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
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

public class MissingPersonInfo extends SwipeBackActivity {
	Boolean d;
	int success;

	String n, p, h, c, lat, lng, r, description, dr;

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static String url_account;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_missing_person_info);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "");
		if (ip.matches("")) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					MissingPersonInfo.this);
			alertDialog.setTitle("IP Address is not set");
			alertDialog.setNeutralButton("OK", null);
			alertDialog.show();
		} else {

			url_account = "http://";
			url_account += ip;
			url_account += "/arpith/dmucs/missing_info.php";

			d = false;

			ImageView imageView = (ImageView) findViewById(R.id.account_photo);
			Ion.with(this)
					.load("http://" + ip
							+ "/arpith/dmucs/profile/9968035735.jpg")
					.withBitmap().resize(512, 512).centerInside()
					.intoImageView(imageView);

			ImageView imageView2 = (ImageView) findViewById(R.id.account_photo_big);
			Ion.with(this)
					.load("http://" + ip
							+ "/arpith/dmucs/profile/9968035735.jpg")
					.withBitmap().resize(1024, 1024).centerInside()
					.intoImageView(imageView2);

			new Async_FindPerson().execute();
			while (!d)
				;

			// Do after loading
			TextView name = (TextView) findViewById(R.id.account_name);
			TextView phone = (TextView) findViewById(R.id.account_phoneno);
			TextView reportedon = (TextView) findViewById(R.id.account_dob);
			TextView loc = (TextView) findViewById(R.id.m_lastseen);
			TextView dress = (TextView) findViewById(R.id.m_dress);
			TextView desc = (TextView) findViewById(R.id.m_desc);

			name.setText(n);
			phone.setText(p);
			reportedon.setText(r);
			loc.setText("(" + lat + ", " + lng + ")");
			dress.setText(dr);
			desc.setText(description);
		}

	}

	class Async_FindPerson extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MissingPersonInfo.this);
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
			JSONObject json = jsonParser.makeHttpRequest(url_account, "POST",
					params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getInt("success");
				n = json.getString("name");
				p = json.getString("phone");
				r = json.getString("reportedon");
				dr = json.getString("dress");
				lat = json.getString("lat");
				lng = json.getString("lng");
				description = json.getString("description");
				if (success == 1) {
					d = true;
				} else {
					d = true;
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
