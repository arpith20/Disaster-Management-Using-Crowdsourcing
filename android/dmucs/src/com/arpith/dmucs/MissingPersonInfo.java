package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.koushikdutta.ion.Ion;

public class MissingPersonInfo extends Activity {
	Boolean d;
	int success;

	String n, p, h, c, lat, lng, r, description, dr, more_info, found, f_lat,
			f_lng, phone_by;

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static String url_account;

	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_missing_person_info);

		Bundle b = getIntent().getExtras();
		p = b.getString("pid");
		Toast.makeText(getBaseContext(), p, 300).show();

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "");
		if (ip.matches("")) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					MissingPersonInfo.this);
			alertDialog.setTitle("IP Address is not set");
			alertDialog.setNeutralButton("OK", null);
			alertDialog.show();
		} else {

			url_account = "http://";
			url_account += ip;
			url_account += "/arpith/dmucs/missing_info.php";

			d = false;

			ImageView imageView = (ImageView) findViewById(R.id.account_photo);
			Ion.with(this)
					.load("http://" + ip
							+ "/arpith/dmucs/profile/9968035735.jpg")
					.withBitmap().resize(512, 512).centerInside()
					.intoImageView(imageView);

			ImageView imageView2 = (ImageView) findViewById(R.id.account_photo_big);
			Ion.with(this)
					.load("http://" + ip
							+ "/arpith/dmucs/profile/9968035735.jpg")
					.withBitmap().resize(1024, 1024).centerInside()
					.intoImageView(imageView2);

			new Async_FindPerson().execute();
			while (!d)
				;

			// Do after loading
			TextView name = (TextView) findViewById(R.id.account_name);
			TextView phone = (TextView) findViewById(R.id.account_phoneno);
			TextView reportedon = (TextView) findViewById(R.id.account_dob);
			TextView loc = (TextView) findViewById(R.id.m_lastseen);
			TextView dress = (TextView) findViewById(R.id.m_dress);
			TextView desc = (TextView) findViewById(R.id.m_desc);
			TextView tv_more = (TextView) findViewById(R.id.m_moreinfo);

			Button del = (Button) findViewById(R.id.m_del);
			del.setEnabled(false);
			del.setText("You are not permitted to delete this report");

			name.setText(n);
			phone.setText(p);
			reportedon.setText(r);
			loc.setText("(" + lat + ", " + lng + ")");
			dress.setText(dr);
			desc.setText(description);
			tv_more.setText(more_info);

			LatLng reportlocation = new LatLng(Double.parseDouble(lat),
					Double.parseDouble(lng));
			map = ((MapFragment) getFragmentManager()
					.findFragmentById(R.id.map)).getMap();
			Marker ReportLocation = map.addMarker(new MarkerOptions().position(
					reportlocation).icon(
					BitmapDescriptorFactory
							.fromResource(R.drawable.currentmarker)));
			map.animateCamera(
					CameraUpdateFactory.newLatLngZoom(reportlocation, 15),
					4000, null);

			Button f = (Button) findViewById(R.id.m_found);
			if (Integer.parseInt(found) == 1) {
				f.setEnabled(false);
				f.setText("This person has been found");

				LatLng foundlocation = new LatLng(Double.parseDouble(f_lat),
						Double.parseDouble(f_lng));
				Marker FoundLocation = map.addMarker(new MarkerOptions()
						.position(foundlocation)
						.title("This person has been found here")
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.currentmarker)));
				//FoundLocation.showInfoWindow();
				map.animateCamera(CameraUpdateFactory.newLatLngZoom(foundlocation, 17),4000,null);
			}
			f.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent i = new Intent(MissingPersonInfo.this,MissingPersonFound.class);
					i.putExtra("phone", p);
					startActivity(i);
				}
			});

			SharedPreferences uname = getSharedPreferences("user", 0);
			String by = uname.getString("name", "0");
			if (by.matches(phone_by)) {
				del.setEnabled(true);
				del.setText("Delete this report");
				del.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						Intent i = new Intent(MissingPersonInfo.this,
								WriteQueryDatabase.class);
						i.putExtra("query",
								"DELETE FROM missing WHERE phone = '" + p + "'");
						i.putExtra("text", p + " successfully deleted");
						startActivity(i);
					}
				});
			}
		}

	}

	class Async_FindPerson extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MissingPersonInfo.this);
			pDialog.setMessage("Loading...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone", p));

			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_account, "GET",
					params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getInt("success");
				n = json.getString("name");
				p = json.getString("phone");
				phone_by = json.getString("phone_by");
				r = json.getString("reportedon");
				dr = json.getString("dress");
				lat = json.getString("lat");
				lng = json.getString("lng");
				f_lat = json.getString("f_lat");
				f_lng = json.getString("f_lng");
				description = json.getString("description");
				more_info = json.getString("more_info");
				found = json.getString("found");
				if (success == 1) {
					d = true;
				} else {
					d = true;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
		}

	}
}
