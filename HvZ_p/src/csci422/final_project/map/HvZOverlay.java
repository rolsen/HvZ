package csci422.final_project.map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;

public class HvZOverlay extends Overlay {
	protected GeoPoint point;
	protected Resources resources;
	protected int id;

	public HvZOverlay(GeoPoint gp, Resources r) {
		point = gp;
		resources = r;
	}

	public boolean draw(Canvas canvas, MapView mapView, 
			boolean shadow, long when)
	{
		if (point == null) {
			System.out.println("Error in draw, GeoPoint is null");
			return false;
		}

		super.draw(canvas, mapView, shadow);

		// Translate the GeoPoint to screen pixels
		Point screenPts = new Point();
		mapView.getProjection().toPixels(point, screenPts);

		// Add the flare
		Bitmap bmp = BitmapFactory.decodeResource(resources, id);
		canvas.drawBitmap(bmp, screenPts.x, screenPts.y, null);
		return true;
	}
}
