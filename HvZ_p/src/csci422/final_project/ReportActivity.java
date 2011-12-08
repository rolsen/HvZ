package csci422.final_project;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import csci422.final_project.R;
import csci422.final_project.exception.HumanIdException;
import csci422.final_project.exception.ZombieIdException;
import csci422.final_project.profile.Profile;
import csci422.final_project.report.KillReport;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.*;

public class ReportActivity extends Activity {
	
	private static final String ZOMBIE = "Please input valid Zombie player code.";
	private static final String HUMAN = "Please input valid Human player code.";
	private static final String NO_RESPONSE = "No response from server.";
	
	private static final String INVALID_HUMAN_KILL = "Invalid Kill\nOnly zombies can enter kills.";
	private static final String INVALID_CANNIBALISM = "Invalid Kill\nCannot cannibalize yourself.";
	private static final String INVALID_ZOMBIE = "Invalid Kill\nCannot kill another zombie.";
	private static final String INVALID_REPEAT = "Invalid Kill\nHuman target has already been killed.";
	private static final String INVALID_CODE = "Invalid Kill\nUnknown player code.";
	private static final String INVALID_TIME = "Invalid Kill\nInvalid time\nKills must be reported" +
			" within three days of the kill.";
	private static final String INVALID_DEAD = "Invalid Kill\nCannot kill dead or exempt player.";
	
	private static final String CONFIRMED = "Kill Confirmed.";
	
	
	private static final Map<String, String> toasts = new HashMap<String, String>();
	static {
		toasts.put("<span class=F3>KILL INVALID: Only Zombies Can Enter Kills</span>",
				INVALID_HUMAN_KILL);
		toasts.put("<span class=F3>KILL INVALID: Attempted Self Cannibalism*</span>", 
				INVALID_CANNIBALISM);
		toasts.put("<span class=F3>KILL INVALID: A Zombie Ate A Zombie</span>", 
				INVALID_ZOMBIE);
		toasts.put("<span class=F3>KILL INVALID: Repeat Kill</span>", 
				INVALID_REPEAT);
		toasts.put("<span class=F3>KILL INVALID: Unrecognized Player Code</span>", 
				INVALID_CODE);
		toasts.put("<span class=F3>KILL INVALID: Incorrect Time</span>", 
				INVALID_TIME);
		toasts.put("<span class=F3>KILL INVALID: Dead or Exempt Player</span>", 
				INVALID_DEAD);
		
		toasts.put("<span class=F3>KILL CONFIRMED</span>", 
				CONFIRMED);
	}
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.report);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_hvz);
		Profile profile = Profile.getInstance(getApplicationContext());
		

		final EditText zombieCode = (EditText) findViewById(R.id.zombie);
		final EditText humanCode = (EditText) findViewById(R.id.human);
		final TimePicker time = (TimePicker) findViewById(R.id.time);
		final DatePicker date = (DatePicker) findViewById(R.id.date);
		final Button report = (Button) findViewById(R.id.reportKill);
		
		//Set Zombie Code to user ID
		zombieCode.setText(profile.getId());
		
		TextView tz = (TextView)findViewById(R.id.zLabel);
		tz.setPadding(0, 0, 20, 0);
		
		TextView th = (TextView)findViewById(R.id.hLabel);
		th.setPadding(0, 0, 20, 0);
		
		report.setOnClickListener(new View.OnClickListener() {
			public void onClick(View V) {
				Profile profile = Profile.getInstance(getApplicationContext());
				
				KillReport killReport = new KillReport(zombieCode, humanCode, date, time);
				
				try {
					killReport.validateParams();
				}
				catch (HumanIdException e) {
					Toast.makeText(getApplicationContext(), HUMAN, Toast.LENGTH_LONG).show();
				}
				catch (ZombieIdException e) {
					Toast.makeText(getApplicationContext(), ZOMBIE, Toast.LENGTH_LONG).show();
				}
				
				
				String params = killReport.getKillParams();
				URL killURL;
				try {
					killURL = new URL(profile.getReportKillURL() + "?" + params);
					
					BufferedReader in = new BufferedReader(
							new InputStreamReader(killURL.openStream()));
					String inputLine;
					while ((inputLine = in.readLine()) != null) {
						//System.out.println(inputLine);
						if (toasts.containsKey(inputLine)) {
							String message = toasts.get(inputLine);
							Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
							if (message.equals(CONFIRMED)) {
								backToMainMenu();
							}
							break;
						}
					}
					
					in.close();
				} catch (MalformedURLException e) {
					Toast.makeText(getApplicationContext(), NO_RESPONSE, Toast.LENGTH_LONG).show();
				} catch (IOException e) {
					Toast.makeText(getApplicationContext(), NO_RESPONSE, Toast.LENGTH_LONG).show();
				}
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

	public void backToMainMenu() {
		this.finish();
	}
}
