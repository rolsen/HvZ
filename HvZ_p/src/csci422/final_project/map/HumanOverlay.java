package csci422.final_project.map;

import android.content.res.Resources;

import com.google.android.maps.GeoPoint;

import csci422.final_project.R;

public class HumanOverlay extends HvZOverlay {

	public HumanOverlay(GeoPoint gp, Resources r) {
		super(gp, r);
		super.id = R.drawable.human;
	}
}
