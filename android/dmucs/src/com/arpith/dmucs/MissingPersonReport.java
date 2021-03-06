package com.arpith.dmucs;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MissingPersonReport extends Activity implements
		OnItemClickListener, OnItemSelectedListener, OnMapClickListener {

	String query, lng, lat;
	TextView tv_phone;
	EditText et_desc, et_dress;
	Button Send;
	// Initialize variables

	AutoCompleteTextView textView = null;
	private ArrayAdapter<String> adapter;

	// Store contacts values in these arraylist
	public static ArrayList<String> phoneValueArr = new ArrayList<String>();
	public static ArrayList<String> nameValueArr = new ArrayList<String>();

	EditText toNumber = null;
	String toNumberValue = "";

	private GoogleMap map;
	LatLng currentLocation;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		setContentView(R.layout.activity_missing_person_report);

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		
		location();

		Send = (Button) findViewById(R.id.Send);
		tv_phone = (TextView) findViewById(R.id.phone);
		et_dress = (EditText) findViewById(R.id.et_dress);
		et_desc = (EditText) findViewById(R.id.et_description);

		// Initialize AutoCompleteTextView values

		textView = (AutoCompleteTextView) findViewById(R.id.toNumber);

		// Create adapter
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line,
				new ArrayList<String>());
		textView.setThreshold(1);

		// Set adapter to AutoCompleteTextView
		textView.setAdapter(adapter);
		textView.setOnItemSelectedListener(this);
		textView.setOnItemClickListener(this);

		// Read contact data and add data to ArrayAdapter
		// ArrayAdapter used by AutoCompleteTextView

		readContactData();

		/********** Button Click pass textView object ***********/
		Send.setOnClickListener(BtnAction(textView));
	}

	private void location() {
		LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		String provider = locationManager.getBestProvider(criteria, true);
		Location lastKnownLocation = locationManager
				.getLastKnownLocation(provider);
		currentLocation = new LatLng(lastKnownLocation.getLatitude(),
				lastKnownLocation.getLongitude());
		
		lng=String.valueOf(currentLocation.longitude);
		lat=String.valueOf(currentLocation.latitude);

		map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		map.setOnMapClickListener(this);
		map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 15), 2000,
				null);
	}

	private OnClickListener BtnAction(final AutoCompleteTextView toNumber) {
		return new OnClickListener() {

			public void onClick(View v) {
				SubmitScore.submitscore(getBaseContext(), SwarmConsts.Scores.MISSING_REPORT);

				String NameSel = "";
				NameSel = toNumber.getText().toString();

				Toast.makeText(getBaseContext(),
						"Reporting " + NameSel + " as missing.",
						Toast.LENGTH_LONG).show();

				String dress = et_dress.getText().toString();
				String desc = et_desc.getText().toString();

				SharedPreferences uname = getSharedPreferences("user", 0);
				String phone_by = uname.getString("name", "0");

				Intent i = new Intent(MissingPersonReport.this,
						WriteQueryDatabase.class);
				i.putExtra("query",
						"insert into missing(Name,phone,phone_by,dress,description,lat,lng) values ('"
								+ NameSel + "','" + toNumberValue + "','"
								+ phone_by + "','" + dress + "','" + desc
								+ "','"+lat+"','"+lng+"');");
				i.putExtra("text", NameSel
						+ " successfully reported as missing");
				startActivity(i);
			}
		};
	}

	// Read phone contact name and phone numbers

	private void readContactData() {

		try {

			/*********** Reading Contacts Name And Number **********/

			String phoneNumber = "";
			ContentResolver cr = getBaseContext().getContentResolver();

			// Query to get contact name

			Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
					null, null, null);

			// If data data found in contacts
			if (cur.getCount() > 0) {

				Log.i("AutocompleteContacts", "Reading   contacts........");

				int k = 0;
				String name = "";

				while (cur.moveToNext()) {

					String id = cur.getString(cur
							.getColumnIndex(ContactsContract.Contacts._ID));
					name = cur
							.getString(cur
									.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

					// Check contact have phone number
					if (Integer
							.parseInt(cur.getString(cur
									.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {

						// Create query to get phone number by contact id
						Cursor pCur = cr
								.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
										null,
										ContactsContract.CommonDataKinds.Phone.CONTACT_ID
												+ " = ?", new String[] { id },
										null);
						int j = 0;

						while (pCur.moveToNext()) {
							// Sometimes get multiple data
							if (j == 0) {
								// Get Phone number
								phoneNumber = ""
										+ pCur.getString(pCur
												.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

								// Add contacts names to adapter
								adapter.add(name);

								// Add ArrayList names to adapter
								phoneValueArr.add(phoneNumber.toString());
								nameValueArr.add(name.toString());

								j++;
								k++;
							}
						} // End while loop
						pCur.close();
					} // End if

				} // End while loop

			} // End Cursor value check
			cur.close();

		} catch (Exception e) {
			Log.i("AutocompleteContacts", "Exception : " + e);
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
			long arg3) {
		// TODO Auto-generated method stub
		// Log.d("AutocompleteContacts", "onItemSelected() position " +
		// position);
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub

		InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub

		// Get Array index value for selected name
		int i = nameValueArr.indexOf("" + arg0.getItemAtPosition(arg2));

		// If name exist in name ArrayList
		if (i >= 0) {

			// Get Phone Number
			toNumberValue = phoneValueArr.get(i);

			InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
			if (toNumberValue.length() > 10)
				toNumberValue = toNumberValue.substring(3);
			tv_phone.setText("Phone: " + toNumberValue);
			// Toast.makeText(
			// getBaseContext(),
			// "Position:" + arg2 + " Name:"
			// + arg0.getItemAtPosition(arg2) + " Number:"
			// + toNumberValue, Toast.LENGTH_LONG).show();
			//
			// Log.d("AutocompleteContacts",
			// "Position:" + arg2 + " Name:"
			// + arg0.getItemAtPosition(arg2) + " Number:"
			// + toNumberValue);

		}

	}

	@Override
	public void onMapClick(LatLng POINT) {
		map.clear();
		lng=String.valueOf(POINT.longitude);
		lat=String.valueOf(POINT.latitude);

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