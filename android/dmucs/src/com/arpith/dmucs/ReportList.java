package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class ReportList extends ListActivity {

	ListAdapter adapter;
	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> reportsList;

	private static String url_all_reports;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_REPORTS = "reports";

	JSONArray reports = null;

	String within;
	EditText et_within;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportlist);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "192.168.43.111");
		
		//if duplicate values are alloud by the user
		Boolean duplicate = getIP.getBoolean("duplicate", true);
		if (duplicate)
			url_all_reports = "http://" + ip + "/arpith/dmucs/DisasterList.php";
		else
			url_all_reports = "http://" + ip
					+ "/arpith/dmucs/DisasterList_NoDup.php";
		
		// Hashmap for ListView
		reportsList = new ArrayList<HashMap<String, String>>();

		new LoadAllReports().execute();

		// Get listview
		ListView lv = getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String uid = ((TextView) view.findViewById(R.id.uid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						ReportInfo.class);
				// sending uid to next activity
				in.putExtra("uid", uid);
				startActivity(in);
			}
		});

		Bundle b = getIntent().getExtras();
		et_within = (EditText) findViewById(R.id.et_within);
		within = b.getString("within");
		et_within.setText(within);

		Button resubmit = (Button) findViewById(R.id.b_restart);
		resubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(getIntent().putExtra("within",
						et_within.getText().toString()));
				finish();
			}
		});

		EditText search = (EditText) findViewById(R.id.et_search);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				((SimpleAdapter) ReportList.this.adapter).getFilter().filter(s);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

	}

	class LoadAllReports extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReportList.this);
			pDialog.setMessage("Loading Reports. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_reports, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("All Reports: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					reports = json.getJSONArray(TAG_REPORTS);

					for (int i = 0; i < reports.length(); i++) {
						JSONObject c = reports.getJSONObject(i);

						// Storing each json item in variable
						String uid = c.getString("uid");
						String incident = c.getString("incident");
						String lat = c.getString("lat");
						String lng = c.getString("lng");
						String report_time = c.getString("report_time");
						String comments = c.getString("comments");

						LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

						Criteria criteria = new Criteria();
						String provider = locationManager.getBestProvider(
								criteria, true);
						Location lastKnownLocation = locationManager
								.getLastKnownLocation(provider);

						LatLng currentLocation = new LatLng(
								lastKnownLocation.getLatitude(),
								lastKnownLocation.getLongitude());
						String loc = distance(lat, lng,
								currentLocation.latitude,
								currentLocation.longitude, "K");
						if (Integer.parseInt(loc) <= Integer.parseInt(within)) {

							// creating new HashMap
							HashMap<String, String> map = new HashMap<String, String>();

							// adding each child node to HashMap key => value
							map.put("uid", uid);
							map.put("incident", incident);
							map.put("loc", loc);
							map.put("report_time", report_time);
							map.put("comments", comments);

							// adding HashList to ArrayList
							reportsList.add(map);
						}
					}
				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					adapter = new SimpleAdapter(ReportList.this, reportsList,
							R.layout.report_view, new String[] { "uid",
									"incident", "loc", "report_time",
									"comments" }, new int[] { R.id.uid,
									R.id.incident, R.id.location, R.id.time,
									R.id.comments });
					setListAdapter(adapter);
				}
			});

		}

	}

	private String distance(String lat1s, String lon1s, double lat2,
			double lon2, String unit) {
		double lat1 = Double.parseDouble(lat1s);
		double lon1 = Double.parseDouble(lon1s);
		double theta = lon1 - lon2;
		double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
				+ Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
				* Math.cos(deg2rad(theta));
		dist = Math.acos(dist);
		dist = rad2deg(dist);
		dist = dist * 60 * 1.1515;
		if (unit == "K") {
			dist = dist * 1.609344;
		} else if (unit == "N") {
			dist = dist * 0.8684;
		}
		return ((int) dist + "");
	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}