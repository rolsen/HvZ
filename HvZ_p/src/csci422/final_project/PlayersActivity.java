package csci422.final_project;


import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

import csci422.final_project.R;
import csci422.final_project.profile.Profile;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.*;
import android.widget.TableRow.LayoutParams;
import android.widget.TableRow;
import android.widget.TableLayout;
import android.widget.TextView;

public class PlayersActivity extends Activity {
	public void onCreate(Bundle b) {
		super.onCreate(b);
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.players);
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_hvz);
		Profile profile = Profile.getInstance();

		try {
			URL playerListURL = new URL(profile.getPlayerListURL());
			
		    
			BufferedReader in = new BufferedReader(
					new InputStreamReader(
							playerListURL.openStream()));

			String inputLine;
			TableLayout tl = (TableLayout)findViewById(R.id.table);
			TableRow t = new TableRow(this);
			t.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));

			TextView c = new TextView(this);
			TextView s = new TextView(this);
			TextView f = new TextView(this);
			TextView k = new TextView(this);

			c.setText("Code Name");
			s.setText("Status");
			f.setText("Last Feed");
			k.setText("Kills");
			
			c.setTypeface(null, Typeface.BOLD);
			s.setTypeface(null, Typeface.BOLD);
			f.setTypeface(null, Typeface.BOLD);
			k.setTypeface(null, Typeface.BOLD);

			t.addView(c);
			t.addView(s);
			t.addView(f);
			t.addView(k);
			
			View v = new View(this);
			v.setBackgroundColor(Color.WHITE);
			
			tl.addView(t,new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			tl.addView(v, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 3));
			while ((inputLine = in.readLine()) != null){
				//System.out.println(inputLine);
				String [] parts = inputLine.split("\\|");
				if(parts.length==4){
					TableRow tr = new TableRow(this);
					tr.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					TextView code = new TextView(this);
					TextView stat = new TextView(this);
					TextView feed = new TextView(this);
					TextView kills = new TextView(this);


					if(parts[0].length()>20) {
						code.setText(parts[0].substring(0, 20));
					}
					else {
						code.setText(parts[0]);
					}
					stat.setText(parts[1]);
					feed.setText(parts[2]);
					kills.setText(parts[3]);
					
					if(parts[1].equals("Z")) {
						code.setTextColor(Color.RED);
						stat.setTextColor(Color.RED);
						feed.setTextColor(Color.RED);
						kills.setTextColor(Color.RED);
					}
					else if (parts[1].equals("D")){
						code.setTextColor(Color.DKGRAY);
						stat.setTextColor(Color.DKGRAY);
						feed.setTextColor(Color.DKGRAY);
						kills.setTextColor(Color.DKGRAY);
					}

					//System.out.println(parts[0] + parts[1] + parts[2] + parts[3]);
					//tr.removeAllViews();
					tr.addView(code);
					tr.addView(stat);
					tr.addView(feed);
					tr.addView(kills);
					
					
					
					View v1 = new View(this);
					v1.setBackgroundColor(Color.WHITE);

					/* Add row to TableLayout. */
					tl.addView(tr,new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
					tl.addView(v1, new TableLayout.LayoutParams(LayoutParams.FILL_PARENT, 1));
				}
			}

			in.close();
		}
		catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
