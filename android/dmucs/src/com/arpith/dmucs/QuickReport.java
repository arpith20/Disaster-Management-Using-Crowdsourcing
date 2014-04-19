package com.arpith.dmucs;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.makeramen.segmented.SegmentedRadioGroup;
import com.parse.ParsePush;

public class QuickReport extends Activity implements OnMapClickListener,
		OnCheckedChangeListener {

	Button Submit_Report;

	private GoogleMap map;
	LatLng currentLocation;

	String lat, lng, phone, no_casualty = "0", you = "1", missing = "0", inc,
			comment;

	SegmentedRadioGroup segmentText_cas;
	SegmentedRadioGroup segmentText_you;
	SegmentedRadioGroup segmentText_missing;

	AutoCompleteTextView incident;

	EditText et_comment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quick_report);
		
		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));

		segmentText_cas = (SegmentedRadioGroup) findViewById(R.id.id_casuality);
		segmentText_cas.setOnCheckedChangeListener(this);
		segmentText_you = (SegmentedRadioGroup) findViewById(R.id.id_you);
		segmentText_you.setOnCheckedChangeListener(this);
		segmentText_missing = (SegmentedRadioGroup) findViewById(R.id.id_missing);
		segmentText_missing.setOnCheckedChangeListener(this);

		et_comment = (EditText) findViewById(R.id.et_comment);

		incident = (AutoCompleteTextView) findViewById(R.id.autocomplete_incident);
		String[] incidents = getResources().getStringArray(R.array.incidents);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, incidents);
		incident.setAdapter(adapter);

		SharedPreferences uname = getSharedPreferences("user", 0);
		phone = uname.getString("name", "0");

		Submit_Report = (Button) findViewById(R.id.qr_submit);
		Submit_Report.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SubmitScore.submitscore(getBaseContext(), SwarmConsts.Scores.QUICK_REPORT);
				inc = incident.getText().toString();
				comment = et_comment.getText().toString();
				
				SharedPreferences debug = PreferenceManager
						.getDefaultSharedPreferences(getBaseContext());
				Boolean dummy_notification = debug.getBoolean(
						"debug_notification", true);
				if (dummy_notification) {
					show_notification();
				} else {
					JSONObject data;
					try {
						data = new JSONObject();
						data.put("action", "com.arpith.dmucs.UPDATE_STATUS");
						data.put("name",
								"A disaster has been reported near you");
						data.put("location", "Test");
						ParsePush push = new ParsePush();
						push.setChannel("disaster");
						push.setData(data);
						push.sendInBackground();
					} catch (JSONException e) {
						Log.d("json", "error");
						e.printStackTrace();
					}
				}
				

				Intent i = new Intent(QuickReport.this,
						WriteQueryDatabase.class);
				i.putExtra(
						"query",
						"insert into report (phone,incident,no_casualty,you,lat,lng,comments) values ('"
								+ phone
								+ "','"
								+ inc
								+ "','"
								+ no_casualty
								+ "','"
								+ you
								+ "','"
								+ lat
								+ "','"
								+ lng
								+ "','" + comment + "');");
				i.putExtra("text", "Incident reported successfully");
				startActivity(i);
			}
		});

		location();

	}

	private void show_notification() {
		Intent intent = new Intent(getBaseContext(), DMUCSNotification.class);
		PendingIntent pIntent = PendingIntent.getActivity(getBaseContext(), 0,
				intent, 0);

		 Notification n = new Notification.Builder(getBaseContext())
		 .setContentTitle(inc+" has been reported")
		 .setContentText("Please be cautious")
		 .setSmallIcon(R.drawable.ic_launcher).setContentIntent(pIntent)
		 .setStyle(new Notification.BigTextStyle().bigText("Description:\n"+comment))
		 .setAutoCancel(true).addAction(R.drawable.eye, "View", pIntent)
		 .build();
		
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		notificationManager.notify(0, n);

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

		Marker incident = map.addMarker(new MarkerOptions().position(POINT)
				.icon(BitmapDescriptorFactory
						.fromResource(R.drawable.incidentmarker)));

		// moves camera to specified location
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(POINT, 15), 2000,
				null);

	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		if (group == segmentText_cas) {
			if (checkedId == R.id.b1) {
				no_casualty = "1";
			} else if (checkedId == R.id.b2) {
				no_casualty = "50";
			} else if (checkedId == R.id.b3) {
				no_casualty = "200";
			} else if (checkedId == R.id.b4) {
				no_casualty = "-1";
			}
		} else if (group == segmentText_you) {
			if (checkedId == R.id.b1) {
				you = "1";
			} else if (checkedId == R.id.b2) {
				you = "0";
			}
		} else if (group == segmentText_missing) {
			if (checkedId == R.id.b1) {
				missing = "1";
			} else if (checkedId == R.id.b2) {
				missing = "0";
			}
		}

	}
}
