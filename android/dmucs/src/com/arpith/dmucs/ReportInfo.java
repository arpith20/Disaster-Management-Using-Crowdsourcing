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
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
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

public class ReportInfo extends Activity {
	Boolean d;
	int success;

	String incident, reportedby, casualties, description, reportedon, lng, lat,
			rectified, vote;
	String uid;

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static String url_account;

	private GoogleMap map;

	Button b_plus, b_minus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportinfo);

		getActionBar().setBackgroundDrawable(new ColorDrawable(Color.BLACK));
		Bundle b = getIntent().getExtras();
		uid = b.getString("uid");

		SharedPreferences getIP = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		String ip = getIP.getString("ip", "");
		if (ip.matches("")) {
			AlertDialog.Builder alertDialog = new AlertDialog.Builder(
					ReportInfo.this);
			alertDialog.setTitle("IP Address is not set");
			alertDialog.setNeutralButton("OK", null);
			alertDialog.show();
		} else {

			url_account = "http://";
			url_account += ip;
			url_account += "/arpith/dmucs/disaster_info.php";

			d = false;

			ImageView imageView = (ImageView) findViewById(R.id.account_photo);
			Ion.with(this)
					.load("http://" + ip
							+ "/arpith/dmucs/profile/9968035735.jpg")
					.withBitmap().resize(512, 512).centerInside()
					.intoImageView(imageView);

			// new AsyncReportInfo().execute();

			b_plus = (Button) findViewById(R.id.plus);
			b_plus.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					String query = "update report set vote=vote+1 where uid='"
							+ uid + "'";
					Intent i = new Intent(ReportInfo.this,
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
					String query = "update report set vote=vote-1 where uid='"
							+ uid + "'";
					Intent i = new Intent(ReportInfo.this,
							WriteQueryDatabase.class);
					i.putExtra("query", query);
					i.putExtra("text", "You have voted down this report");
					startActivity(i);
				}
			});
		}

	}

	class AsyncReportInfo extends AsyncTask<String, String, String> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(ReportInfo.this);
			pDialog.setMessage("Loading...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		protected String doInBackground(String... args) {

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("uid", uid));

			JSONObject json = jsonParser.makeHttpRequest(url_account, "GET",
					params);

			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				success = json.getInt("success");
				incident = json.getString("incident");
				reportedby = json.getString("pid");
				reportedon = json.getString("report_time");
				casualties = json.getString("no_casualty");
				lat = json.getString("lat");
				lng = json.getString("lng");
				description = json.getString("comments");
				rectified = json.getString("done");
				vote = json.getString("vote");
				if (success == 1) {
					d = true;
				} else {
					d = false;
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(String file_url) {
			pDialog.dismiss();

			TextView tv_incident = (TextView) findViewById(R.id.incident);
			TextView tv_reportedby = (TextView) findViewById(R.id.reportedby);
			TextView tv_reportedon = (TextView) findViewById(R.id.reportedon);
			TextView tv_casualties = (TextView) findViewById(R.id.casualties);
			TextView tv_description = (TextView) findViewById(R.id.description);
			TextView tv_rectified = (TextView) findViewById(R.id.rectified);
			TextView tv_vote = (TextView) findViewById(R.id.tv_vote);

			tv_incident.setText(incident);
			tv_reportedby.setText(reportedby);
			tv_reportedon.setText(reportedon);
			tv_rectified.setText(rectified);
			tv_description.setText(description);
			tv_casualties.setText(casualties);
			tv_vote.setText(vote);

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
		}

	}

	@Override
	protected void onResume() {
		// this is done here instead of onCreate to refresh the vote count
		new AsyncReportInfo().execute();
		super.onResume();
	}

}
