package csci422.final_project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import csci422.final_project.R;
import csci422.final_project.profile.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.View.OnFocusChangeListener;
import android.widget.*;

public class ReportActivity extends Activity {
	
	private static final String ZOMBIE = "Please input valid Zombie player code.";
	private static final String HUMAN = "Please input valid Human player code.";
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.report);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_hvz);
		Profile profile = Profile.getInstance();
		

		final EditText zombieCode = (EditText) findViewById(R.id.zombie);
		final EditText humanCode = (EditText) findViewById(R.id.human);
		final TimePicker time = (TimePicker) findViewById(R.id.time);
		final DatePicker date = (DatePicker) findViewById(R.id.date);
		final Button report = (Button) findViewById(R.id.reportKill);
		
		//Set Zombie Code to user ID
		zombieCode.setText(profile.getId());
		
		//remove text from human when selected
		humanCode.setOnFocusChangeListener(new OnFocusChangeListener()
		{
			public void onFocusChange(View V, boolean hasFocus) {
				if(hasFocus==true){
					if(humanCode.getText().toString().compareTo("Human Player Code")==0){
						humanCode.setText("");
					}
				}
			}
		});
		
		
		
		
		
		report.setOnClickListener(new View.OnClickListener() {
			public void onClick(View V) {
				// Perform action on clicks
				int zombie=0;
				int human=0;
				try {
				zombie = Integer.parseInt(zombieCode.getText().toString());
				}
				catch (NumberFormatException e){
					Toast.makeText(getApplicationContext(), ZOMBIE, Toast.LENGTH_LONG).show();
				}
				if(zombie >99999 || zombie < 10000){
					Toast.makeText(getApplicationContext(), ZOMBIE, Toast.LENGTH_LONG).show();
				}
				try {
				human = Integer.parseInt(humanCode.getText().toString());
				}
				catch (NumberFormatException e){
					Toast.makeText(getApplicationContext(), HUMAN, Toast.LENGTH_LONG).show();
				}
				if(human >99999 || human < 10000){
					Toast.makeText(getApplicationContext(), HUMAN, Toast.LENGTH_LONG).show();
				}
				int hour = time.getCurrentHour();
				int min = time.getCurrentMinute();
				String ap = " ";
				if(hour < 12) {
					ap = "AM";
					if (hour==0){ 
						hour = 12;
					}
				}
				else {
					ap = "PM";
					if(hour > 12){
						hour -=12;
					}
				}
				int day = date.getDayOfMonth();
				int month = date.getMonth();
				int year = date.getYear();
				String[] args= {String.valueOf(zombie), String.valueOf(human), String.valueOf(hour), String.valueOf(min), ap, String.valueOf(month), String.valueOf(day), String.valueOf(year)};
				CgiReport report = new CgiReport("CgiGet", args, "GET");
				report.reportKill();
			}

		});
	}
	public String getInternalCacheDirectory() {
	    String cacheDirPath = null;
	    File cacheDir = getCacheDir();
	    if (cacheDir != null) {
	        cacheDirPath = cacheDir.getPath();
	    }
	    return cacheDirPath;        
	}
}
