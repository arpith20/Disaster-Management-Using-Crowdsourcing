package com.arpith.dmucs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MissingPersonFound extends Activity implements OnMapClickListener {

	String phone, found_by, f_lat, f_lng, more_info;
	EditText et_description;

	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_missing_person_found);

		Bundle b = getIntent().getExtras();
		phone = b.getString("phone");

		SharedPreferences uname = getSharedPreferences("user", 0);
		found_by = uname.getString("name", "");

		Log.d("INformation", phone + "--------" + found_by);
		et_description = (EditText) findViewById(R.id.f_description);

		location();

		Button b_submit = (Button) findViewById(R.id.f_submit);
		b_submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.d("INformation", "Pressed");
				sendSMS(phone);

				SubmitScore.submitscore(getBaseContext(),
						SwarmConsts.Scores.MISSING_FOUND);

				more_info = et_description.getText().toString();

				Intent i = new Intent(MissingPersonFound.this,
						WriteQueryDatabase.class);
				i.putExtra("query", "update missing set found='1', found_by='"
						+ found_by + "', f_lat='" + f_lat + "', f_lng='"
						+ f_lng + "', more_info='" + more_info
						+ "' where phone='" + phone + "'");
				i.putExtra("text", "Done!");
				startActivity(i);
			}
		});
	}

	private void sendSMS(String to) {
		SmsManager smsManager = SmsManager.getDefault();
		smsManager
				.sendTextMessage(
						to,
						null,
						"The person you reported as missing has been found. Please contact "+found_by+" or open the app for more details",
						null, null);

	}

	private void location() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(provider);
		LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(),
				lastKnownLocation.getLongitude());

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setOnMapClickListener(this);
		map.animateCamera(
				CameraUpdateFactory.newLatLngZoom(currentLocation, 15), 2000,
				null);
	}

	@Override
	public void onMapClick(LatLng POINT) {
		map.clear();
		f_lng = String.valueOf(POINT.longitude);
		f_lat = String.valueOf(POINT.latitude);

		Marker incident = map.addMarker(new MarkerOptions()
				.position(POINT)
				.title("Missong Person Report")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.incidentmarker)));
		incident.showInfoWindow();

		// moves camera to specified location
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(POINT, 15), 2000,
				null);

	}
}
