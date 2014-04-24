package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class WriteWithoutDuplicateDatabase extends SwipeBackActivity {

	ImageView rs_tick;
	Button rs_main, b_map;
	TextView result;

	String query, query_noDup, text, lat, lng, incident, showmap;
	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static String url_query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write_query);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "");

		rs_tick = (ImageView) findViewById(R.id.rs_tick);
		rs_main = (Button) findViewById(R.id.rs_main);
		b_map = (Button) findViewById(R.id.b_map);
		result = (TextView) findViewById(R.id.rs_result);

		rs_tick.setAlpha(0);
		url_query = "http://";
		url_query += ip;
		url_query += "/arpith/dmucs/WriteDisaster.php";

		Bundle e = getIntent().getExtras();
		query = e.getString("query");
		query_noDup = e.getString("query_noDup");
		text = e.getString("text");
		lat = e.getString("lat");
		lng = e.getString("lng");
		incident = e.getString("incident");
		showmap = e.getString("showmap");

		b_map.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(WriteWithoutDuplicateDatabase.this,
						Maps.class);
				startActivity(i);
			}
		});

		Log.d("Query", query + lat + lng + incident);

		new AsyncWriteDatabase().execute();

	}

	class AsyncWriteDatabase extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(WriteWithoutDuplicateDatabase.this);
			pDialog.setMessage("Writing to Database...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("query", query));
			params.add(new BasicNameValuePair("queryNoDup", query_noDup));
			params.add(new BasicNameValuePair("lat", lat));
			params.add(new BasicNameValuePair("lng", lng));
			params.add(new BasicNameValuePair("incident", incident));

			JSONObject json = jsonParser.makeHttpRequest(url_query, "GET",
					params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				text = json.getString("message");
				Log.d("test", incident + text);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();

			result.setText(text);

			rs_tick.setAlpha(150);

			rs_main.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Intent i = new Intent(WriteWithoutDuplicateDatabase.this,
							MainActivity.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
				}
			});
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
