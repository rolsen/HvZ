package csci422.final_project.map;

import android.content.res.Resources;

import com.google.android.maps.GeoPoint;

import csci422.final_project.R;

public class ZombieOverlay extends HvZOverlay {

	public ZombieOverlay(GeoPoint gp, Resources r) {
		super(gp, r);
		super.id = R.drawable.zombie;
		System.out.printf("in zombie: %d\n", R.drawable.zombie);
	}
}
