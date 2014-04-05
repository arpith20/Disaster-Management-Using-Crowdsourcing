package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Spinner;

public class QuickReport extends Activity implements OnMapClickListener{
	
	Button Submit_Report;
	
	private GoogleMap map;
	LatLng currentLocation;
	
	String lat, lng, phone;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_report);
		
		AutoCompleteTextView incident = (AutoCompleteTextView)findViewById(R.id.autocomplete_incident);
		String[] incidents = getResources().getStringArray(R.array.incidents);
		ArrayAdapter<String> adapter = 
		        new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, incidents);
		incident.setAdapter(adapter);
		
		Submit_Report=(Button) findViewById(R.id.qr_submit);
		Submit_Report.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent i = new Intent (QuickReport.this, WriteQueryDatabase.class);
				startActivity(i);
			}
		});
		
		SharedPreferences uname = getSharedPreferences("user", 0);
		phone = uname.getString("name", "0");
		
		location();
		
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
		
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 14), 2000,
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
