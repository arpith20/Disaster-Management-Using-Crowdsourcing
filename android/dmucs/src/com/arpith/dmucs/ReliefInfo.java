package com.arpith.dmucs;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
	TextView tv_address, tv_vote;

	String lat, lng, uid;
	String vote;
	String c_lat, c_lng;

	private GoogleMap map;
	
	Button b_plus, b_minus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reliefinfo);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

		tv_pid = (TextView) findViewById(R.id.r_phone);
		tv_name = (TextView) findViewById(R.id.r_name);
		tv_address = (TextView) findViewById(R.id.r_address);
		tv_vote = (TextView) findViewById(R.id.tv_vote);

		Bundle b = getIntent().getExtras();
		uid = b.getString("uid");
		
		tv_pid.setText(b.getString("pid"));
		tv_name.setText(b.getString("name"));
		tv_address.setText(b.getString("address"));
		
		vote = b.getString("vote");
		tv_vote.setText(vote);

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
		
		b_plus = (Button) findViewById(R.id.plus);
		b_plus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vote  = (Integer.parseInt(vote)+1)+"";
				tv_vote.setText(vote);
				
				String query = "update donate_location set vote=vote+1 where uid='"
						+ uid + "'";
				Intent i = new Intent(ReliefInfo.this,
						WriteQueryDatabase.class);
				i.putExtra("query", query);
				i.putExtra("text", "Plus voted");
				startActivity(i);
			}
		});
		b_minus = (Button) findViewById(R.id.minus);
		b_minus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				vote  = (Integer.parseInt(vote)-1)+"";
				tv_vote.setText(vote);
				
				String query = "update donate_location set vote=vote-1 where uid='"
						+ uid + "'";
				Intent i = new Intent(ReliefInfo.this,
						WriteQueryDatabase.class);
				i.putExtra("query", query);
				i.putExtra("text", "You have voted down this report");
				startActivity(i);
			}
		});
	}

}
