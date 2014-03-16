package com.arpith.dmucs;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends Activity implements OnMapClickListener,
		LocationListener {
	private LocationManager locationManager;
	private String provider;

	private GoogleMap map;

	TextView tv_loc;

	LatLng currentLocation;

	Boolean interact = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		// Get the location manager
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		provider = locationManager.getBestProvider(criteria, false);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(provider);

		tv_loc = (TextView) findViewById(R.id.location);

		currentLocation = new LatLng(lastKnownLocation.getLatitude(),
				lastKnownLocation.getLongitude());
		tv_loc.setText("Latitude:" + currentLocation.latitude + ", Longitude:"
				+ currentLocation.longitude);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		if (interact)
			map.setOnMapClickListener(this);

		Marker CurrentLocation = map.addMarker(new MarkerOptions()
				.position(currentLocation)
				.title("Current Location")
				.snippet("This is where you are right now!")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.currentmarker)));

		// Move the camera instantly to hamburg with a zoom of 15.
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15));

		// Zoom in, animating the camera.
		map.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
	}

	public void makeUseOfNewLocation(Location location) {
		currentLocation = new LatLng(location.getLatitude(),
				location.getLongitude());
		tv_loc.setText("Latitude:" + currentLocation.latitude + ", Longitude:"
				+ currentLocation.longitude);
		Marker CurrentLocation = map.addMarker(new MarkerOptions()
				.position(currentLocation)
				.title("Current Location")
				.snippet("This is where you are right now!")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.currentmarker)));
	}

	@Override
	public void onMapClick(LatLng POINT) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4);
		tv_loc.setText("Incident coordinates:\n" + "("
				+ df.format(POINT.latitude) + ", " + df.format(POINT.longitude)
				+ ")");

		// clears previously selected point
		map.clear();

		Marker incident = map.addMarker(new MarkerOptions()
				.position(POINT)
				.title("Incident Report")
				.snippet("Click here submit results!")
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.incidentmarker)));
		incident.showInfoWindow();

		// moves camera to specified location
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(POINT, 15), 2000,
				null);

		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				// TODO
			}
		});
	}

	/* Request updates at startup */
	@Override
	protected void onResume() {
		super.onResume();
		locationManager.requestLocationUpdates(provider, 400, 1, this);
	}

	@Override
	protected void onPause() {
		super.onPause();
		locationManager.removeUpdates(this);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	public void onLocationChanged(Location location) {
		makeUseOfNewLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

}
