package com.arpith.dmucs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReliefAdd extends Activity implements OnMapClickListener{
	EditText et_name;
	EditText et_address;
	
	TextView tv_phone;
	
	private GoogleMap map;
	LatLng currentLocation;
	
	String name, address, phone, lat, lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_relief_add);
		
		et_name = (EditText)findViewById(R.id.ra_name);
		et_address = (EditText)findViewById(R.id.ra_address);
		tv_phone = (TextView)findViewById(R.id.ra_phone);
		
		SharedPreferences uname = getSharedPreferences("user", 0);
		phone = uname.getString("name", "0");
		tv_phone.setText(phone);
		
		location();
		
		Button b_submit = (Button)findViewById(R.id.ra_submit);
		b_submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				SubmitScore.submitscore(getBaseContext(), SwarmConsts.Scores.NEW_DONATE_LOC);

				name=et_name.getText().toString();
				address=et_address.getText().toString();
				Intent i = new Intent(ReliefAdd.this, WriteQueryDatabase.class);
				i.putExtra("query", "insert into donate_location values ('"+phone+"','"+name+"','"+address+"','"+lat+"','"+lng+"')");
				i.putExtra("text", "Your location has been successfully recorded");
				startActivity(i);
			}
		});
		
	}
	
	private void location() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(provider);
		currentLocation = new LatLng(lastKnownLocation.getLatitude(),
				lastKnownLocation.getLongitude());

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setOnMapClickListener(this);
		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 13), 2000,
				null);
	}

	@Override
	public void onMapClick(LatLng POINT) {
		map.clear();
		lng=String.valueOf(POINT.longitude);
		lat=String.valueOf(POINT.latitude);

		Marker incident = map.addMarker(new MarkerOptions()
				.position(POINT)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.incidentmarker)));
		
		// moves camera to specified location
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(POINT, 15), 2000,
				null);

	}
}
