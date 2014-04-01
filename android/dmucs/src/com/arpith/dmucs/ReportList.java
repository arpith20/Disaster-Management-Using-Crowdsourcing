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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class ReportList extends ListActivity {

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> reportsList;

	// url to get all products list
	private static String url_all_reports;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_REPORTS = "reports";

	// products JSONArray
	JSONArray products = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportlist);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "192.168.43.111");
		url_all_reports = "http://" + ip + "/arpith/dmucs/DisasterList.php";
		// Hashmap for ListView
		reportsList = new ArrayList<HashMap<String, String>>();

		// Loading products in Background Thread
		new LoadAllReports().execute();

		// Get listview
		ListView lv = getListView();

		// on seleting single product
		// launching Edit Product Screen
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();

				// Starting new intent
				Intent in = new Intent(getApplicationContext(),
						ReportInfo.class);
				// sending pid to next activity
				in.putExtra("pid", pid);
				startActivity(in);
			}
		});

	}

	class LoadAllReports extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReportList.this);
			pDialog.setMessage("Loading products. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_reports, "GET",
					params);

			// Check your log cat for JSON reponse
			Log.d("All Products: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// products found
					// Getting Array of Products
					products = json.getJSONArray(TAG_REPORTS);

					// looping through All Products
					for (int i = 0; i < products.length(); i++) {
						JSONObject c = products.getJSONObject(i);

						// Storing each json item in variable
						String pid = c.getString("pid");
						String incident = c.getString("incident");
						String lat = c.getString("lat");
						String lng = c.getString("lng");
						String report_time = c.getString("report_time");
						String comments = c.getString("comments");
						
						LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

						Criteria criteria = new Criteria();
						String provider = locationManager.getBestProvider(criteria, true);
						Location lastKnownLocation = locationManager
								.getLastKnownLocation(provider);

						

						LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(),
								lastKnownLocation.getLongitude());
						String loc = distance(lat, lng, currentLocation.latitude, currentLocation.longitude, "K");
						
						// creating new HashMap
						HashMap<String, String> map = new HashMap<String, String>();

						// adding each child node to HashMap key => value
						map.put("pid", pid);
						map.put("incident", incident);
						map.put("loc", loc);
						map.put("report_time", report_time);
						map.put("comments", comments);

						// adding HashList to ArrayList
						reportsList.add(map);
					}
				} else {
					// no reports found
					// Launch Add New quickreport Activity
					Intent i = new Intent(getApplicationContext(),
							QuickReport.class);
					// Closing all previous activities
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
					startActivity(i);
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
			// dismiss the dialog after getting all products
			pDialog.dismiss();
			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					ListAdapter adapter = new SimpleAdapter(ReportList.this,
							reportsList, R.layout.report_view, new String[] {
									"pid", "incident","loc","report_time","comments" }, new int[] { R.id.pid,
									R.id.incident,R.id.location,R.id.time,R.id.comments });
					setListAdapter(adapter);
				}
			});

		}

	}
	
	private String distance(String lat1s, String lon1s, double lat2, double lon2,
			String unit) {
		double lat1=Double.parseDouble(lat1s);
		double lon1=Double.parseDouble(lon1s);
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
		return ((int)dist+"");
	}
	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180.0 / Math.PI);
	}
}