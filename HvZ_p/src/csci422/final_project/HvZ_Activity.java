package csci422.final_project;

import java.io.*;

import csci422.final_project.R;
import csci422.final_project.profile.Profile;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HvZ_Activity extends Activity {
	private static final String WARNING = "Invalid Player Code.\n" +
			"Please go to your Profile to update.";
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// code used to create the activity for retrieving player info
		final Button playerButton = (Button) findViewById(R.id.player_list);
		playerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(HvZ_Activity.this, PlayersActivity.class);
				startActivity(i);
			}
		});

		//code used to create the activity for opening screen to report a kill
		final Button killButton = (Button) findViewById(R.id.report_kill);
		killButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(HvZ_Activity.this, ReportActivity.class);
				startActivity(i);
			}
		});
		
		//code used to create the activity for flare gun 
		//Rory, uncomment this for the flare gun, copy/modify for minimap.
		//Also, you might have to add the activity in AndroidManifest.xml,
		//the other examples in the file should be sufficient 
		final Button miniMapButton = (Button) findViewById(R.id.mini_map);
		miniMapButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Log.w("Unimplemented", "mini_map hello");
				Intent i = new Intent(HvZ_Activity.this, MiniMap.class);
				startActivity(i);
			}
		});
		
		// Create Flare Activity
		final Button flareButton = (Button) findViewById(R.id.shoot_flare);
		flareButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				unimplemented();
			}
		});
		
		// Creates Strategy Map Activity
		final Button strategyButton = (Button) findViewById(R.id.strategy_map);
		strategyButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				unimplemented();
			}
		});
		
		// Creates Profile Activity
		final Button profileButton = (Button) findViewById(R.id.profile);
		profileButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(HvZ_Activity.this, ProfileActivty.class);
				startActivity(i);
			}
		});
		
	}
	
	public void unimplemented() {
		System.out.println("Currently unimplemented.");
		Toast.makeText(getApplicationContext(), "Not currently implemented.", Toast.LENGTH_SHORT).show();
	}
	public String getInternalCacheDirectory() {
	    String cacheDirPath = null;
	    File cacheDir = getCacheDir();
	    if (cacheDir != null) {
	        cacheDirPath = cacheDir.getPath();
	    }
	    return cacheDirPath;        
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Profile profile = Profile.getInstance();
		
		if (!profile.validId()) {
			Toast.makeText(getApplicationContext(), WARNING, Toast.LENGTH_LONG).show();
		}
	}
}
