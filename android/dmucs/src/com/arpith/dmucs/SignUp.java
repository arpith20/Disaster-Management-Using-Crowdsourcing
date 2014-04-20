package com.arpith.dmucs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
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

public class SignUp extends Activity implements OnMapClickListener
		 {

	Button b_submit;;

	private GoogleMap map;
	LatLng currentLocation;

	String phone, password, name, dob, email, lat,lng;

	EditText et_phone, et_password, et_name, et_dob, et_email, et_lat,et_lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		
		et_phone = (EditText) findViewById(R.id.phone);
		et_password = (EditText) findViewById(R.id.password);
		et_name = (EditText) findViewById(R.id.name);
		et_dob = (EditText) findViewById(R.id.dob);
		et_email = (EditText) findViewById(R.id.email);

		
		b_submit = (Button) findViewById(R.id.submit);
		b_submit.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				phone = et_phone.getText().toString();
				password = et_password.getText().toString().hashCode()+"";
				name = et_name.getText().toString();
				dob = et_dob.getText().toString();
				email = et_email.getText().toString();
				
				SharedPreferences uname = getSharedPreferences("user", 0);
				SharedPreferences.Editor unameEdit = uname.edit();
				unameEdit.putBoolean("first", false);
				unameEdit.putString("name", phone);
				unameEdit.commit();
				
				String query = "insert into main_details (phone,password,name,dob,email) values ('"+phone+"','"+password+"','"+name+"','"+dob+"','"+email+"');|insert into location values ('"+phone+"','"+lat+"','"+lng+"');";
				
				Intent i = new Intent(SignUp.this,WriteQueryDatabase.class);
				i.putExtra("query", query);
				i.putExtra("text", "Signed up");
				startActivity(i);
			}
		});

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
		
		lng = String.valueOf(currentLocation.longitude);
		lat = String.valueOf(currentLocation.latitude);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setOnMapClickListener(this);

		map.animateCamera(
				CameraUpdateFactory.newLatLngZoom(currentLocation, 14), 2000,
				null);
	}

	@Override
	public void onMapClick(LatLng POINT) {
		map.clear();
		lng = String.valueOf(POINT.longitude);
		lat = String.valueOf(POINT.latitude);

		Marker home = map.addMarker(new MarkerOptions().position(POINT)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.home)));

		// moves camera to specified location
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(POINT, 15), 2000,
				null);

	}
}
