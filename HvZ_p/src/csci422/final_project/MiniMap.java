package csci422.final_project;

import android.os.Bundle;
import android.util.Log;

import com.google.android.maps.MapActivity;

public class MiniMap extends MapActivity {
//	MapView mapView;
//	MapController mapController;
//
//	int DEFAULT_ZOOM_LEVEL = 19;
//	int KAFADAR_LAT = 39751265;
//	int KAFADAR_LNG = -105221450;
//	int CH_LAT = 39751702;
//	int CH_LNG = -105222700;
//	int A_POINT_LAT = 39751202;
//	int A_POINT_LNG = -105220700;
//
//	class FlareOverlay extends com.google.android.maps.Overlay
//	{
//		GeoPoint point;
//		
//		public FlareOverlay(GeoPoint gp) {
//			point = gp;
//		}
//		
//		@Override
//		public boolean draw(Canvas canvas, MapView mapView, 
//				boolean shadow, long when)
//		{
//			super.draw(canvas, mapView, shadow);
//
//			//---translate the GeoPoint to screen pixels---
//			Point screenPts = new Point();
//			if (point == null) {
//				point = new GeoPoint(KAFADAR_LAT, KAFADAR_LNG);
//			}
//			mapView.getProjection().toPixels(point, screenPts);
//
//			//---add the marker---
//			Bitmap bmp = BitmapFactory.decodeResource(
//					getResources(), R.drawable.flare_t);
//			canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);
//			return true;
//		}
//	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mini_map);

		Log.w("fixing", "HAI this works");

//		mapView = (MapView) findViewById(R.id.mapView);  
//		mapView.setBuiltInZoomControls(true);
//		mapView.displayZoomControls(true);
//		mapView.setSatellite(true);
//
//		mapController = mapView.getController();
//
//		GeoPoint kafadar = new GeoPoint(KAFADAR_LAT,KAFADAR_LNG);
//
//		mapController.animateTo(kafadar);
//		mapController.setZoom(DEFAULT_ZOOM_LEVEL);
//		
//		displayFlares();
//
//        final Button button = (Button) findViewById(R.id.flare);
//        button.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                shootFlare();
//            }
//        });

	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

//	public Location getUserLocation() {
//		// Acquire a reference to the system Location Manager
//		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
//
//		// TODO: This just sets the location for testing purposes to be in Kafadar (CSM)
//		// GPS_PROVIDER is necessary for setTestProviderLocation and getLastKnowLocation to work together
//		// Begin TEMP
//		Location kafadarLocation = new Location(LocationManager.GPS_PROVIDER);
//		kafadarLocation.setLatitude(KAFADAR_LAT);
//		kafadarLocation.setLongitude(KAFADAR_LNG);
//		locationManager.setTestProviderLocation (LocationManager.GPS_PROVIDER, kafadarLocation);
//		// End TEMP
//
//		return locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
//	}
//	
//	public void displayFlares() {
//		List<GeoPoint> list = getFlareLocations();
//		List<Overlay> listOfOverlays = mapView.getOverlays();
//		listOfOverlays.clear();
//		
//		for (GeoPoint point : list) {			
//			FlareOverlay mapOverlay = new FlareOverlay(point);
//			listOfOverlays.add(mapOverlay);
//		}
//		
//		mapView.invalidate(); // Calls onDraw()
//	}
//	
//	public List<GeoPoint> getFlareLocations() {
//		List<GeoPoint> list = new ArrayList<GeoPoint>();
//		
//		// TODO: Get actual locations from server
//		// Begin TEMP
//		GeoPoint near_ch = new GeoPoint(CH_LAT,CH_LNG);
//		list.add(near_ch);
//		
//
//		GeoPoint a_point = new GeoPoint(A_POINT_LAT,A_POINT_LNG);
//		list.add(a_point);
//		// End TEMP
//		
//		return list;
//	}
	
	public void shootFlare() {
		// TODO: send Flare data to server, update screen
		Log.w("Unimplemented", "todo implement shootFlare");
		//Toast t = Toast.makeText(getApplicationContext(), "Unimplemented", Toast.LENGTH_LONG);
		//t.show();
	}
}