package csci422.final_project.map;

import android.content.res.Resources;

import com.google.android.maps.GeoPoint;

import csci422.final_project.R;

public class FlareOverlay extends HvZOverlay
{
	public FlareOverlay(GeoPoint gp, Resources r) {
		super(gp, r);
		super.id = R.drawable.flare_t;
	}
}
