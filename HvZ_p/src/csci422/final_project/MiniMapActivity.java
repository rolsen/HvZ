package csci422.final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class MiniMapActivity extends MapActivity {
	MapView mapView;
	MapController mapController;
	LocationManager locationManager;
	GeoPoint userLocation;

	private static final String NETWORK = LocationManager.NETWORK_PROVIDER;
	private static final String GPS = LocationManager.GPS_PROVIDER;
	private static final int UPDATE_MIN_TIME = 60000;
	private static final int UPDATE_MIN_DIST = 10;

	int DEFAULT_ZOOM_LEVEL = 18;
	int KAFADAR_MICRO_LAT = 39751265;
	int KAFADAR_MICRO_LNG = -105221450;
	int CH_MICRO_LAT = 39751702;
	int CH_MICRO_LNG = -105222700;
	int A_POINT_MICRO_LAT = 39751202;
	int A_POINT_MICRO_LNG = -105220700;
	int BROWN_MICRO_LAT = 39749784;
	int BROWN_MICRO_LNG = -105221247;

	private static final String DEFAULT_SERVER_URL = "http://inside.mines.edu/~rolsen/";
	private static final String REPORT_FLARE_ACTION = "cgi-bin/flareReport.cgi";
	private static final String FLARE_LIST = "cgi-bin/flareList.cgi";

	private static final String GENERAL_ERROR = "Something went wrong.";

	class FlareOverlay extends com.google.android.maps.Overlay
	{
		GeoPoint point;

		public FlareOverlay(GeoPoint gp) {
			point = gp;
		}

		public boolean draw(Canvas canvas, MapView mapView, 
				boolean shadow, long when)
		{
			if (point == null) {
				System.out.println("Error in draw flare, GeoPoint is null");
				return false;
			}

			super.draw(canvas, mapView, shadow);

			// Translate the GeoPoint to screen pixels
			Point screenPts = new Point();
			mapView.getProjection().toPixels(point, screenPts);

			// Add the flare
			Bitmap bmp = BitmapFactory.decodeResource(
					getResources(), R.drawable.flare_t);
			canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);
			return true;
		}
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.mini_map);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_hvz);

		mapView = (MapView) findViewById(R.id.mapView);  
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
		mapView.setSatellite(true);

		mapController = mapView.getController();
		mapController.setZoom(DEFAULT_ZOOM_LEVEL);

		Bundle b = getIntent().getExtras();
		if ((b != null) && b.getBoolean("shootFlare")) {
			shootFlare();
		}
		else {
			drawMap(false);
		}

		final Button button = (Button) findViewById(R.id.flare);
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				shootFlare();
			}
		});

		if (isRealPhone()) {
			initiateLocationListening();
		}

		System.out.println("MiniMapActivity has finished onCreate");
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	public GeoPoint geoPointFromLocation(Location l) {
		int lat = (int) (l.getLatitude() * 1e6);
		int lng = (int) (l.getLongitude() * 1e6);
		GeoPoint gp = new GeoPoint(lat, lng);

		// System.out.println(String.format("The lat: was %f now %d, the lng: was %f now %d", l.getLatitude(), lat, l.getLongitude(), lng));
		return gp;
	}

	public GeoPoint getUserLocation() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		if (isRealPhone()) {
			if (userLocation != null) {
				return userLocation;
			}

			// NETWORK instead of GPS because I'm guessing GPS will take too long,
			// 		especially if the user is inside.
			return geoPointFromLocation(locationManager.getLastKnownLocation(NETWORK));
		}
		else {
			System.out.println("getUserLocation detects emulator instead of real device");

			Location testLocation = new Location(GPS);
			testLocation.setLatitude(BROWN_MICRO_LAT / 1e6);
			testLocation.setLongitude(BROWN_MICRO_LNG / 1e6);

			return geoPointFromLocation(testLocation);
		}
	}

	public void drawMap(boolean flare) {
		userLocation = getUserLocation();

		mapController.animateTo(userLocation);

		List<GeoPoint> list = getFlareLocations();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();

		if (list != null) {
			for (GeoPoint point : list) {
				FlareOverlay mapOverlay = new FlareOverlay(point);
				listOfOverlays.add(mapOverlay);
			}
			System.out.println("list is not null");
		}

		if (flare) {
			String request = String.format("%s?lat=%s&lng=%s",
					getFlareReportURL(), 
					userLocation.getLatitudeE6(), 
					userLocation.getLongitudeE6());
			System.out.printf("req: %s\n", request);
			try {
//				// Construct data
//				String data = URLEncoder.encode("key1", "UTF-8") + "=" + URLEncoder.encode("value1", "UTF-8");
//				data += "&" + URLEncoder.encode("key2", "UTF-8") + "=" + URLEncoder.encode("value2", "UTF-8");

				// Send data
				URL url = new URL(request);
				URLConnection conn = url.openConnection();
				conn.setDoOutput(true);
				OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
				wr.write('0'); // wr.write(data);
				wr.flush();

				// Get the response
				BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String line;
				while ((line = rd.readLine()) != null) {
					System.out.printf("URL line: %s\n", line);
				}
				wr.close();
				rd.close();
			} catch (Exception e) {
				System.out.printf("damn\n");
			}
			FlareOverlay mapOverlay = new FlareOverlay(userLocation);
			listOfOverlays.add(mapOverlay);
		}

		mapView.invalidate(); // Calls onDraw()
	}

	// May return a null List
	public List<GeoPoint> getFlareLocations() {
		List<GeoPoint> list = new ArrayList<GeoPoint>();

		try {
			URL flareListURL = new URL(getFlareListURL());

			BufferedReader in = new BufferedReader(
					new InputStreamReader(flareListURL.openStream()));

			String inputLine;
			String pipeDelimiter = "\\|";
			int lat, lng;

			inputLine = in.readLine();
			while (inputLine != null) {
				if (inputLine.compareTo("<br/>") == 0 || inputLine.compareTo("<br />") == 0) {
					inputLine = in.readLine();
					continue;
				}

				System.out.printf("LINEE: %s\n", inputLine);
				String[] tokens  = inputLine.split(pipeDelimiter);

				if (tokens.length < 3) {
					return list;
				}
				lat = Integer.parseInt(tokens[0]);
				lng = Integer.parseInt(tokens[1]);
				list.add(new GeoPoint(lat, lng));

				inputLine = in.readLine();
			}

			in.close();
		} catch (MalformedURLException e) {
			Toast.makeText(getApplicationContext(), GENERAL_ERROR, Toast.LENGTH_LONG).show();
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(), GENERAL_ERROR, Toast.LENGTH_LONG).show();
		}

		return list;
	}

	public void shootFlare() {
		// TODO: send Flare data to server, update screen
		System.out.println("Placeholder flare");

		boolean flare = true;
		drawMap(flare);

		Toast t = Toast.makeText(getApplicationContext(), "You have fired your flare gun!", Toast.LENGTH_LONG);
		t.show();
	}

	public void updateUserLocation(Location loc) {
		userLocation = geoPointFromLocation(loc);
		drawMap(false);
	}

	public void initiateLocationListening() {
		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
			public void onLocationChanged(Location location) {
				updateUserLocation(location);
			}

			public void onStatusChanged(String provider, int status, Bundle extras) {}

			public void onProviderEnabled(String provider) {}

			public void onProviderDisabled(String provider) {}
		};

		// TODO: The following two lines might need some optimizing
		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(GPS, UPDATE_MIN_TIME, UPDATE_MIN_DIST, locationListener);
		locationManager.requestLocationUpdates(NETWORK, UPDATE_MIN_TIME, UPDATE_MIN_DIST, locationListener);

	}

	public String getFlareReportURL() {
		return DEFAULT_SERVER_URL + REPORT_FLARE_ACTION;
	}

	public String getFlareListURL() {
		return DEFAULT_SERVER_URL + FLARE_LIST;
	}

	public boolean isRealPhone() {
		// This is a shoddy hack of a way to tell whether or not this is running on an
		//		emulator or not, but Android has no official way to do it. For production
		// 		code, remove/comment out all the TelephonyManager stuff, the if statement,
		// 		and the READ_PHONE_STATE permission in the Manifest
		TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
		if (Integer.parseInt(tm.getDeviceId()) != 0) {
			return true;
		}
		return false;
	}
}