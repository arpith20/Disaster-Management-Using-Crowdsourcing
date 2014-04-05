package com.arpith.dmucs;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ReliefInfo extends Activity {

	TextView tv_pid;
	TextView tv_name;
	TextView tv_address;

	String lat, lng;
	String c_lat, c_lng;

	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reliefinfo);

		tv_pid = (TextView) findViewById(R.id.r_phone);
		tv_name = (TextView) findViewById(R.id.r_name);
		tv_address = (TextView) findViewById(R.id.r_address);

		Bundle b = getIntent().getExtras();
		tv_pid.setText(b.getString("pid"));
		tv_name.setText(b.getString("name"));
		tv_address.setText(b.getString("address"));

		lat = b.getString("lat");
		lng = b.getString("lng");

		LatLng location = new LatLng(Double.parseDouble(lat),
				Double.parseDouble(lng));
		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		Marker Location = map.addMarker(new MarkerOptions().position(location)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.currentmarker)));
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15),
				4000, null);
		
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(provider);
		c_lat=String.valueOf(lastKnownLocation.getLatitude());
		c_lng=String.valueOf(lastKnownLocation.getLongitude());
	
		Button gmaps = (Button) findViewById(R.id.r_map);
		gmaps.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String uri = String.format(Locale.ENGLISH, "http://maps.google.com/maps?saddr="+c_lat+","+c_lng+"&daddr="+lat+","+lng+"");
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});
	}

}
