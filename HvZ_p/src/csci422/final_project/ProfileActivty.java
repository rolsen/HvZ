package csci422.final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import csci422.final_project.profile.Profile;

public class ProfileActivty extends Activity {
	
	// Saved profile id warning
		private static final String SAVED = "Player Code saved.";
	
	// Invalid profile id warning
	private static final String WARNING = "Invalid Player Code.\n" +
			"Code must be 5 numbers.";
	
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.profile);
		Profile profile = Profile.getInstance();
		
		final EditText idTextbox = (EditText) findViewById(R.id.playerId);
		idTextbox.setText(profile.getId());
		
		final Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Profile profile = Profile.getInstance();
				final EditText idTextbox = (EditText) findViewById(R.id.playerId);
				
				profile.setId(idTextbox.getText().toString());
				
				// Check for valid Id
				if (profile.validId()) {
					profile.saveId();
					Toast.makeText(getApplicationContext(), SAVED, Toast.LENGTH_LONG).show();
				}
				else {
					Toast.makeText(getApplicationContext(), WARNING, Toast.LENGTH_LONG).show();
					profile.reset();
				}
			}
		});
		
	}

}

