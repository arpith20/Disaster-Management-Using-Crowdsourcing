package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;

public class ReliefFragment extends Fragment {

	ListAdapter adapter;
	ListView lv;
	String pid, name, lat, lng, address;

	// Progress Dialog
	private ProgressDialog pDialog;

	// Creating JSON Parser object
	JSONParser jParser = new JSONParser();

	ArrayList<HashMap<String, String>> locationList;

	private static String url_all_reports;

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_LOC = "locations";

	JSONArray locations = null;

	String within;
	EditText et_within;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_relief, container,
				false);

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getActivity());
		String ip = getIP.getString("ip", "192.168.43.111");
		url_all_reports = "http://" + ip + "/arpith/dmucs/ReliefList.php";
		// Hashmap for ListView
		locationList = new ArrayList<HashMap<String, String>>();

		new LoadAllLocations().execute();

		// Get listview
		lv = (ListView) rootView.findViewById(R.id.list);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				String pid = ((TextView) view.findViewById(R.id.pid)).getText()
						.toString();
				String name = ((TextView) view.findViewById(R.id.person_name))
						.getText().toString();
				String lat = ((TextView) view.findViewById(R.id.lat)).getText()
						.toString();
				String lng = ((TextView) view.findViewById(R.id.lng)).getText()
						.toString();
				String address = ((TextView) view.findViewById(R.id.address))
						.getText().toString();
				// Starting new intent
				Intent in = new Intent(getActivity(), ReliefInfo.class);
				// sending pid to next activity
				in.putExtra("pid", pid);
				in.putExtra("name", name);
				in.putExtra("lat", lat);
				in.putExtra("lng", lng);
				in.putExtra("address", address);
				startActivity(in);
			}
		});

		Button new_b = (Button) rootView.findViewById(R.id.rel_create);
		new_b.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), ReliefAdd.class);
				startActivity(i);
			}
		});

		Bundle b = getActivity().getIntent().getExtras();
		et_within = (EditText) rootView.findViewById(R.id.et_within);
		within = b.getString("within");
		et_within.setText(within);

		Button resubmit = (Button) rootView.findViewById(R.id.b_restart);
		resubmit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				startActivity(getActivity().getIntent().putExtra("within",
						et_within.getText().toString()));
				getActivity().finish();
			}
		});

		EditText search = (EditText) rootView.findViewById(R.id.et_search);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				((SimpleAdapter) ReliefFragment.this.adapter).getFilter()
						.filter(s);

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		return rootView;
	}

	class LoadAllLocations extends AsyncTask<String, String, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Loading Locations. Please wait...");
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
			Log.d("All Locations: ", json.toString());

			try {
				// Checking for SUCCESS TAG
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					locations = json.getJSONArray(TAG_LOC);

					for (int i = 0; i < locations.length(); i++) {
						JSONObject c = locations.getJSONObject(i);

						// Storing each json item in variable
						pid = c.getString("pid");
						name = c.getString("name");
						lat = c.getString("lat");
						lng = c.getString("lng");
						address = c.getString("address");

						LocationManager locationManager = (LocationManager) getActivity()
								.getSystemService(Context.LOCATION_SERVICE);

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
							map.put("pid", pid);
							map.put("name", name);
							map.put("lat", lat);
							map.put("lng", lng);
							map.put("loc", loc);
							map.put("address", address);

							// adding HashList to ArrayList
							locationList.add(map);
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
			getActivity().runOnUiThread(new Runnable() {
				public void run() {
					/**
					 * Updating parsed JSON data into ListView
					 * */
					adapter = new SimpleAdapter(getActivity(), locationList,
							R.layout.relief_view, new String[] { "pid", "name",
									"lat", "lng", "loc", "address" },
							new int[] { R.id.pid, R.id.person_name, R.id.lat,
									R.id.lng, R.id.location, R.id.address });
					lv.setAdapter(adapter);
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
