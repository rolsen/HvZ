package csci422.final_project.profile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

/**
 * This class holds profile data for the current user
 * ID and server URL
 * 
 * Handles changes to these variables for preservation
 * 
 * @author cloew
 *
 */
public class Profile {
	// The Singleton
	private static Profile profile;
	
	// Variables for URLs and actions
	private static final String DEFAULT_SERVER_URL = "http://inside.mines.edu/~cloew/";
	private static final String PLAYER_LIST_ACTION = "cgi-bin/playerList_Android.cgi";
	private static final String REPORT_KILL_ACTION = "cgi-bin/report.cgi"; 
	
	// Profile Data Save Location
	private static final String FILENAME = "PlayerCodeFile";
	
	//
	private static final ArrayList<String> DIGITS = new ArrayList<String>();
	
	static {
		for (int i = 0; i < 10; i++) {
			DIGITS.add(String.valueOf(i));
		}
	}
	
	String id;
	String currentURL;
	
	private Profile() {
		reset();
		
		// Read from file to get ID
		File profileData = new File(FILENAME);
		if (profileData.exists()) {
			// Read the id and URL
			// id =
			// currentURL = 
		}
	}
	
	public static Profile getInstance() {
		if (profile == null) {
			profile = new Profile();
		}
		return profile;
			
	}
	
	/**
	 * Return if the id is valid
	 * 
	 * @return
	 */
	public boolean validId() {
		return validId(id);
	}
	
	public static boolean validId(String id) {
		boolean valid;
		
		// Check length is 5
		valid = id.length() == 5;
		
		// Check only contains digits
		for (char c : id.toCharArray()) {
			valid = valid && DIGITS.contains(String.valueOf(c));
		}

		
		return valid;
	}
	
	/**
	 * Resets id and current URL to defaults
	 */
	public void reset() {
		id = "";
		currentURL = DEFAULT_SERVER_URL;
	}
	
	public String getId() {
		return id;
	}
	
	/**
	 * Changes ID to the new ID and preserves it on the system
	 * 
	 * @param newId
	 */
	public void setId(String newId) {
		id = newId;
	}
	
	public void saveId() {
		// Logic to save to file
	}

	public String getPlayerListURL() {
		return currentURL + PLAYER_LIST_ACTION;
	}
	
	public String getReportKillURL() {
		return currentURL + REPORT_KILL_ACTION;
	}
}
