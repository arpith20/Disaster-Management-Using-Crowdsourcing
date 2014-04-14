package com.arpith.dmucs;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class Maps extends Activity {

	private int userIcon, policeIcon, hospitalIcon, fireIcon, otherIcon;

	private GoogleMap theMap;

	private LocationManager locMan;

	private Marker userMarker;

	// places of interest
	private Marker[] placeMarkers;
	// max
	private final int MAX_PLACES = 20;
	// marker options
	private MarkerOptions[] places;

	double lat, lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_maps);

		// get drawable IDs
		userIcon = R.drawable.yellow_point;
		policeIcon = R.drawable.police;
		hospitalIcon = R.drawable.hospital;
		fireIcon = R.drawable.fire;
		otherIcon = R.drawable.purple_point;

		// find out if we already have it
		if (theMap == null) {
			// get the map
			theMap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.map)).getMap();
			// check in case map/ Google Play services not available
			if (theMap != null) {
				// ok - proceed
				theMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
				// create marker array
				placeMarkers = new Marker[MAX_PLACES];
				// update location
				updatePlaces();
			}

		}
	}

	private void updatePlaces() {
		// get location manager
		locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// get last location
		Location lastLoc = locMan
				.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		lat = lastLoc.getLatitude();
		lng = lastLoc.getLongitude();
		// create LatLng
		LatLng lastLatLng = new LatLng(lat, lng);

		// remove any existing marker
		if (userMarker != null)
			userMarker.remove();
		// create and set marker properties
		userMarker = theMap.addMarker(new MarkerOptions().position(lastLatLng)
				.title("You are here")
				.icon(BitmapDescriptorFactory.fromResource(userIcon))
				.snippet("Your last recorded location"));
		// move to location
		theMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 13),
				2000, null);
		theMap.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {

			@Override
			public void onInfoWindowClick(Marker arg0) {
				// TODO Auto-generated method stub
				String place_lat = arg0.getPosition().latitude + "";
				String place_lng = arg0.getPosition().longitude + "";
				String uri = String.format(Locale.ENGLISH,
						"http://maps.google.com/maps?saddr=" + lat + "," + lng
								+ "&daddr=" + place_lat + "," + place_lng + "");
				Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
				startActivity(intent);
			}
		});

		// build places query string
		String latVal = String.valueOf(lat);
		String lngVal = String.valueOf(lng);
		String placesSearchStr;
		try {
			placesSearchStr = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="
					+ URLEncoder.encode(latVal, "UTF-8")
					+ ","
					+ URLEncoder.encode(lngVal, "UTF-8")
					+ "&radius="
					+ URLEncoder.encode("5000", "UTF-8")
					+ "&sensor="
					+ URLEncoder.encode("true", "UTF-8")
					+ "&types="
					+ URLEncoder.encode("police|hospital|fire", "UTF-8")
					+ "&key="
					+ URLEncoder.encode(
							"AIzaSyBHJhFFsLlCHxE6m2PIAlEOpNIKjzACnPI", "UTF-8");
			new GetPlaces().execute(placesSearchStr);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private class GetPlaces extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... placesURL) {
			// fetch places

			// build result as string
			StringBuilder placesBuilder = new StringBuilder();
			// process search parameter string(s)
			for (String placeSearchURL : placesURL) {
				HttpClient placesClient = new DefaultHttpClient();
				try {
					// try to fetch the data

					// HTTP Get receives URL string
					HttpGet placesGet = new HttpGet(placeSearchURL);
					// execute GET with Client - return response
					HttpResponse placesResponse = placesClient
							.execute(placesGet);
					// check response status
					StatusLine placeSearchStatus = placesResponse
							.getStatusLine();
					// only carry on if response is OK
					if (placeSearchStatus.getStatusCode() == 200) {
						// get response entity
						HttpEntity placesEntity = placesResponse.getEntity();
						// get input stream setup
						InputStream placesContent = placesEntity.getContent();
						// create reader
						InputStreamReader placesInput = new InputStreamReader(
								placesContent);
						// use buffered reader to process
						BufferedReader placesReader = new BufferedReader(
								placesInput);
						// read a line at a time, append to string builder
						String lineIn;
						while ((lineIn = placesReader.readLine()) != null) {
							placesBuilder.append(lineIn);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			return placesBuilder.toString();
		}

		// process data retrieved from doInBackground
		protected void onPostExecute(String result) {
			// parse place data returned from Google Places
			// remove existing markers
			if (placeMarkers != null) {
				for (int pm = 0; pm < placeMarkers.length; pm++) {
					if (placeMarkers[pm] != null)
						placeMarkers[pm].remove();
				}
			}
			try {
				// parse JSON

				// create JSONObject, pass stinrg returned from doInBackground
				JSONObject resultObject = new JSONObject(result);
				// get "results" array
				JSONArray placesArray = resultObject.getJSONArray("results");
				// marker options for each place returned
				places = new MarkerOptions[placesArray.length()];
				// loop through places
				for (int p = 0; p < placesArray.length(); p++) {
					// parse each place
					// if any values are missing we won't show the marker
					boolean missingValue = false;
					LatLng placeLL = null;
					String placeName = "";
					String vicinity = "";
					int currIcon = otherIcon;
					try {
						// attempt to retrieve place data values
						missingValue = false;
						// get place at this index
						JSONObject placeObject = placesArray.getJSONObject(p);
						// get location section
						JSONObject loc = placeObject.getJSONObject("geometry")
								.getJSONObject("location");
						// read lat lng
						placeLL = new LatLng(Double.valueOf(loc
								.getString("lat")), Double.valueOf(loc
								.getString("lng")));
						// get types
						JSONArray types = placeObject.getJSONArray("types");
						// loop through types
						for (int t = 0; t < types.length(); t++) {
							// what type is it
							String thisType = types.get(t).toString();
							// check for particular types - set icons
							if (thisType.contains("police")) {
								currIcon = policeIcon;
								break;
							} else if (thisType.contains("hospital")) {
								currIcon = hospitalIcon;
								break;
							} else if (thisType.contains("fire")) {
								currIcon = fireIcon;
								break;
							}
						}
						// vicinity
						vicinity = placeObject.getString("vicinity");
						// name
						placeName = placeObject.getString("name");
					} catch (JSONException jse) {
						Log.v("PLACES", "missing value");
						missingValue = true;
						jse.printStackTrace();
					}
					// if values missing we don't display
					if (missingValue)
						places[p] = null;
					else
						places[p] = new MarkerOptions()
								.position(placeLL)
								.title(placeName)
								.icon(BitmapDescriptorFactory
										.fromResource(currIcon))
								.snippet(vicinity);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (places != null && placeMarkers != null) {
				for (int p = 0; p < places.length && p < placeMarkers.length; p++) {
					// will be null if a value was missing
					if (places[p] != null)
						placeMarkers[p] = theMap.addMarker(places[p]);

				}
			}

		}
	}
}
