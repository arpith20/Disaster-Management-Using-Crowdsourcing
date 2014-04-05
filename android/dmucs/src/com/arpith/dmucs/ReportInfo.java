package com.arpith.dmucs;

import java.util.ArrayList;
import java.util.List;

import me.imid.swipebacklayout.lib.SwipeBackLayout;
import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

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

	String incident,reportedby,casualties,description,reportedon, lng,lat,rectified;

	// Progress Dialog
	private ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();

	private static String url_account;
	
	private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reportinfo);

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

			
			new Async_MissingInfo().execute();
			while (!d)
				;

			// Do after loading
			TextView tv_incident = (TextView) findViewById(R.id.incident);
			TextView tv_reportedby = (TextView) findViewById(R.id.reportedby);
			TextView tv_reportedon = (TextView) findViewById(R.id.reportedon);
			TextView tv_casualties = (TextView) findViewById(R.id.casualties);
			TextView tv_description = (TextView) findViewById(R.id.description);
			TextView tv_rectified = (TextView) findViewById(R.id.rectified);

			tv_incident.setText(incident);
			tv_reportedby.setText(reportedby);
			tv_reportedon.setText(reportedon);
			tv_rectified.setText(rectified);
			tv_description.setText(description);
			tv_casualties.setText(casualties);
			
			LatLng reportlocation = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
			map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
					.getMap();
			Marker ReportLocation = map.addMarker(new MarkerOptions()
			.position(reportlocation)
			.icon(BitmapDescriptorFactory
					.fromResource(R.drawable.currentmarker)));
			map.animateCamera(CameraUpdateFactory.newLatLngZoom(reportlocation, 9), 4000,
					null);
		}

	}

	class Async_MissingInfo extends AsyncTask<String, String, String> {
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
			String n1 = "8105581711";

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("phone", n1));

			// getting JSON Object
			// Note that create product url accepts POST method
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
