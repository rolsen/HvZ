package csci422.final_project.map;

import android.content.res.Resources;

import com.google.android.maps.GeoPoint;

import csci422.final_project.R;

public class YouOverlay extends HvZOverlay {
	public YouOverlay(GeoPoint gp, Resources r) {
		super(gp, r);
		super.id = R.drawable.you;
	}
}
