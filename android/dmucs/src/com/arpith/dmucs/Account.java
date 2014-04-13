package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Account extends Activity {
	Boolean d;
	int success;

	String name, email, phone, dob, h_lat, h_lng, points, n1;
	String name2;
	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static String url_account;
	
	private GoogleMap map1;
	private GoogleMap map2;

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

			SharedPreferences uname = getSharedPreferences("user", 0);
			points = uname.getInt("points", 0) + "";
			TextView pnts = (TextView) findViewById(R.id.account_points);
			pnts.setText(points);
			n1 = uname.getString("name", "0");

			url_account = "http://";
			url_account += ip;
			url_account += "/arpith/dmucs/account_info.php";
			
			ImageView imageView = (ImageView) findViewById(R.id.account_photo);
			Ion.with(this)
					.load("http://" + ip
							+ "/arpith/dmucs/profile/"+n1+".jpg")
					.withBitmap().resize(512, 512).centerInside()
					.intoImageView(imageView);

			new Async_Account().execute();
		}
	}

	void doAfterLoad() {
		map1 = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map1)).getMap();
		map2 = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.map2)).getMap();
		
		LatLng homelocation = new LatLng(Double.parseDouble(h_lat),
				Double.parseDouble(h_lng));
		Marker HomeLocation = map1.addMarker(new MarkerOptions().position(
				homelocation).icon(
				BitmapDescriptorFactory
						.fromResource(R.drawable.currentmarker)));
		map1.animateCamera(
				CameraUpdateFactory.newLatLngZoom(homelocation, 15),
				4000, null);
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(provider);
		LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(),
				lastKnownLocation.getLongitude());
		Marker CurrentLocation = map2.addMarker(new MarkerOptions().position(
				currentLocation).icon(
				BitmapDescriptorFactory
						.fromResource(R.drawable.currentmarker)));
		map2.animateCamera(
				CameraUpdateFactory.newLatLngZoom(currentLocation, 15),
				4000, null);
		
		// Do after loading
		TextView tv_name = (TextView) findViewById(R.id.account_name);
		TextView tv_phone = (TextView) findViewById(R.id.account_phoneno);
		TextView tv_dob = (TextView) findViewById(R.id.account_dob);
		TextView tv_h_loc = (TextView) findViewById(R.id.account_home);
		

		tv_name.setText(name);
		tv_phone.setText(phone);
		tv_dob.setText(dob);
		tv_h_loc.setText("(" + h_lat + ", " + h_lng + ")");

		

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

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone", n1));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_account, "GET",
					params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getInt("success");
				name = json.getString("name");
				phone = json.getString("phone");
				dob = json.getString("dob");
				h_lat = json.getString("h_lat");
				h_lng = json.getString("h_lng");
				email = json.getString("email");
				points = json.getString("points");
				if (success == 1) {
					d = true;
				} else {
					d = false;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			doAfterLoad();
			pDialog.dismiss();
		}

	}
}
