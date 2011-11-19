package csci422.final_project;

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

		try {
			URL url;
			url = new URL("http://inside.mines.edu/~kraber/cgi-bin/playerTable.cgi");

			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String str = "";
			while((str=br.readLine()) != null){
				System.out.println(str);
			}
			br.close();

		}
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("ERROR");
			e.printStackTrace();
		}
		try {
			URL playerListURL = new URL("http://inside.mines.edu/~cloew/cgi-bin/first.cgi");
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
		
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://inside.mines.edu/~cloew/cgi-bin/first.cgi"));
		startActivity(i);
	}

}
