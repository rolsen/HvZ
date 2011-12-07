package csci422.final_project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
		requestWindowFeature(Window.FEATURE_LEFT_ICON);
		setContentView(R.layout.profile);		
		setFeatureDrawableResource(Window.FEATURE_LEFT_ICON, R.drawable.ic_hvz);
		Profile profile = Profile.getInstance(getApplicationContext());
		
		final EditText idTextbox = (EditText) findViewById(R.id.playerId);
		idTextbox.setText(profile.getId());
		
		TextView t = (TextView)findViewById(R.id.labelId);
		t.setPadding(0, 0, 20, 0);
		
		final Button saveButton = (Button) findViewById(R.id.save);
		saveButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Profile profile = Profile.getInstance(getApplicationContext());
				final EditText idTextbox = (EditText) findViewById(R.id.playerId);
				
				profile.setId(idTextbox.getText().toString());
				
				// Check for valid Id
				if (profile.validId()) {
					profile.saveId(getApplicationContext());
					Toast.makeText(getApplicationContext(), SAVED, Toast.LENGTH_LONG).show();
					backToMainMenu();
				}
				else {
					Toast.makeText(getApplicationContext(), WARNING, Toast.LENGTH_LONG).show();
					profile.reset();
				}
			}
		});
	}

	public void backToMainMenu() {
		this.finish();
	}

}

