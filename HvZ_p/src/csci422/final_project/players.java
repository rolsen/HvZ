package csci422.final_project;

import csci422.finalproject.profile.Profile;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

public class players extends Activity {
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.players);
		Profile profile = Profile.getInstance();


		try {
			URL playerListURL = new URL(profile.getPlayerListURL());
			
		    BufferedReader in = new BufferedReader(
			        new InputStreamReader(
			        		playerListURL.openStream()));
	
		    String inputLine;
	
		    while ((inputLine = in.readLine()) != null)
		    	System.out.println(inputLine);
	
		    in.close();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(profile.getPlayerListURL()));//"http://inside.mines.edu/~cloew/cgi-bin/first.cgi"));
		startActivity(i);
	}

}
