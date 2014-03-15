package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.R.bool;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ReportServer extends Activity {
	String phone, incident, lat, lng, damage, no_casualty;
	String you, comments, done, modified_time;
	Boolean d;
	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	// url to create new product
	private static String url_report_disaster;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_server);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "");
		if (ip.matches("")) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					ReportServer.this);
			alertDialog.setTitle("IP Address is not set");
			alertDialog.setNeutralButton("OK", null);
			alertDialog.show();
		} else {
			ImageView rs_tick=(ImageView)findViewById(R.id.rs_tick);
			Button rs_again = (Button) findViewById(R.id.rs_again);
			Button rs_main = (Button) findViewById(R.id.rs_main);
			TextView result = (TextView) findViewById(R.id.rs_result);
			
			rs_tick.setAlpha(0);
			url_report_disaster = "http://";
			url_report_disaster += ip;
			url_report_disaster += "/arpith/dmucs/report_disaster.php";

			Toast.makeText(getBaseContext(), "writing " + url_report_disaster,
					Toast.LENGTH_SHORT).show();
			
			d = false;
			new CreateNewProduct().execute();
			while (!d)
				;

			// Do after report has been submitted
			
			
			result.setText("Report Successfully Submitted");
			rs_tick.setAlpha(150);

			rs_again.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(getBaseContext(), QuickReport.class);
					startActivity(i);
					finish();
				}
			});
			
			rs_main.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					finish();
				}
			});

		}

	}

	class CreateNewProduct extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReportServer.this);
			pDialog.setMessage("Reporting...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			Random g = new Random();
			int x = g.nextInt(90000000);
			phone = "" + x;
			String n1 = "insert into report (phone,incident,lat,lng,damage,no_casualty,you,comments,done,modified_time) values ('"
					+ phone + "',1,12.859753,77.663641,10,0,0,NULL,0,NULL)";

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("query", n1));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_report_disaster,
					"POST", params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");
				if (success == 1) {
					d = true;
					// Toast.makeText(getBaseContext(), "Incident Reported",
					// Toast.LENGTH_LONG).show();
				} else {
					// failed to create product
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}