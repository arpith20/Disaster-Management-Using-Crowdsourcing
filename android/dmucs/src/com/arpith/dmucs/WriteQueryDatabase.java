package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

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

public class WriteQueryDatabase extends SwipeBackActivity {
	String query;
	Boolean d, suc;
	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	// url to create new product
	private static String url_query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_query);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "");
		if (ip.matches("")) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					WriteQueryDatabase.this);
			alertDialog.setTitle("IP Address is not set");
			alertDialog.setNeutralButton("OK", null);
			alertDialog.show();
		} else {
			ImageView rs_tick = (ImageView) findViewById(R.id.rs_tick);
			Button rs_main = (Button) findViewById(R.id.rs_main);
			TextView result = (TextView) findViewById(R.id.rs_result);

			rs_tick.setAlpha(0);
			url_query = "http://";
			url_query += ip;
			url_query += "/arpith/dmucs/query.php";

			Bundle e = getIntent().getExtras();
			query = e.getString("query");
			String text = e.getString("text");
			// query="insert into donate_money values (\"8105581713\",\"9818000236\",20);|update donate set amount=amount+20 where uniqueid=\"8105581711\";";

			d = false;
			new CreateNewProduct().execute();
			while (!d)
				;

			// Do after query has been submitted

			if (suc)
				result.setText(text);
			else {
				result.setText("Something went wrong..");
			}
			rs_tick.setAlpha(150);

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
			pDialog = new ProgressDialog(WriteQueryDatabase.this);
			pDialog.setMessage("Reporting...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("query", query));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_query, "POST",
					params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt("success");
				if (success == 1) {
					d = true;
					suc = true;
					// Toast.makeText(getBaseContext(), "Incident Reported",
					// Toast.LENGTH_LONG).show();
				} else {
					d = true;
					suc = false;
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

	@Override
	protected void onResume() {
		super.onResume();
		SwipeBackLayout mSwipeBackLayout;
		mSwipeBackLayout = getSwipeBackLayout();
		mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
	}
}