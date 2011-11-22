package csci422.final_project;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

	int DEFAULT_ZOOM_LEVEL = 18;
	int KAFADAR_MICRO_LAT = 39751265;
	int KAFADAR_MICRO_LNG = -105221450;
	int CH_MICRO_LAT = 39751702;
	int CH_MICRO_LNG = -105222700;
	int A_POINT_MICRO_LAT = 39751202;
	int A_POINT_MICRO_LNG = -105220700;
	int BROWN_MICRO_LAT = 39749784;
	int BROWN_MICRO_LNG = -105221247;

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
				Log.wtf("Unimplemented", "Error in draw flare");
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
		setContentView(R.layout.mini_map);

		mapView = (MapView) findViewById(R.id.mapView);  
		mapView.setBuiltInZoomControls(true);
		mapView.displayZoomControls(true);
		mapView.setSatellite(true);

		mapController = mapView.getController();

		
		//GeoPoint brown = new GeoPoint(BROWN_LAT,BROWN_LNG); // TODO: make this the user location
		GeoPoint brown = geoPointFromLocation(getUserLocation());
		
		mapController.animateTo(brown);
		mapController.setZoom(DEFAULT_ZOOM_LEVEL);
		
		displayFlares();

        final Button button = (Button) findViewById(R.id.flare);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                shootFlare();
            }
        });
        
    	Log.w("debug", "Null time?");

        Bundle b = getIntent().getExtras();
        if ((b != null) && b.getBoolean("shootFlare")) {
        	Log.w("HAI", "Found the boolean");
        	shootFlare();
        }
        
		Log.w("debug", "MiniMapActivity has finished onCreate");
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public GeoPoint geoPointFromLocation(Location l) {
		int lat = (int) (l.getLatitude() * 1e6);
		int lng = (int) (l.getLongitude() * 1e6);
		GeoPoint gp = new GeoPoint(lat, lng);
		
		// Log.w("geoToLoc", String.format("The lat: was %f now %d, the lng: was %f now %d", l.getLatitude(), lat, l.getLongitude(), lng));
		return gp;
	}

	public Location getUserLocation() {
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

		try {
			// Acquire a reference to the system Location Manager
			Log.w("debug", "getUserLocation stub, setting user location to Brown Building");
	
			// TODO: This just sets the location for testing purposes to be in Kafadar (CSM)
			// GPS_PROVIDER is necessary for setTestProviderLocation and getLastKnowLocation to work together
			// Begin TEMP
			Location userLocation = new Location(LocationManager.GPS_PROVIDER);
			userLocation.setLatitude(BROWN_MICRO_LAT / 1e6);
			userLocation.setLongitude(BROWN_MICRO_LNG / 1e6);
			locationManager.setTestProviderLocation (LocationManager.GPS_PROVIDER, userLocation);
			// End TEMP
	
			return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		catch (IllegalArgumentException e) {
			Log.w("debug", "getUserLocation detects emulator");
			
			Location userLocation = new Location(LocationManager.PASSIVE_PROVIDER);
			userLocation.setLatitude(BROWN_MICRO_LAT / 1e6);
			userLocation.setLongitude(BROWN_MICRO_LNG / 1e6);
			
			return userLocation;
		}
	}
	
	public void displayFlares() {
		List<GeoPoint> list = getFlareLocations();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		
		for (GeoPoint point : list) {
			FlareOverlay mapOverlay = new FlareOverlay(point);
			listOfOverlays.add(mapOverlay);
		}
		
		mapView.invalidate(); // Calls onDraw()
	}
	
	public List<GeoPoint> getFlareLocations() {
		List<GeoPoint> list = new ArrayList<GeoPoint>();
		
		// TODO: Get actual locations from server
		// Begin TEMP
		GeoPoint near_ch = new GeoPoint(CH_MICRO_LAT,CH_MICRO_LNG);
		list.add(near_ch);
		

		GeoPoint a_point = new GeoPoint(A_POINT_MICRO_LAT,A_POINT_MICRO_LNG);
		list.add(a_point);
		// End TEMP
		
		return list;
	}
	
	public void shootFlare() {
		// TODO: send Flare data to server, update screen
		Log.w("Unimplemented", "Placeholder flare");
		
		List<GeoPoint> list = getFlareLocations();
		List<Overlay> listOfOverlays = mapView.getOverlays();
		listOfOverlays.clear();
		
		for (GeoPoint point : list) {
			FlareOverlay mapOverlay = new FlareOverlay(point);
			listOfOverlays.add(mapOverlay);
		}
		
		GeoPoint brown_point = new GeoPoint(BROWN_MICRO_LAT, BROWN_MICRO_LNG);
		FlareOverlay mapOverlay = new FlareOverlay(brown_point);
		listOfOverlays.add(mapOverlay);
		
		mapView.invalidate(); // Calls onDraw()
		
		Toast t = Toast.makeText(getApplicationContext(), "You have fired your flare gun!", Toast.LENGTH_LONG);
		t.show();
	}
}