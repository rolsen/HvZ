package csci422.final_project;

import csci422.final_project.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class HvZ_phoneActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		//code used to load website for player list. Needs to be put into menuclickactivity.java folder...if possible
		final Button playerButton = (Button) findViewById(R.id.button1);
		playerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				/*Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://inside.mines.edu/~mmazzocc/players.html"));
				startActivity(i);*/
				Intent i = new Intent(HvZ_phoneActivity.this, players.class);
				startActivity(i);
			}
		});
		
		final Button killButton = (Button) findViewById(R.id.button2);
		killButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				/*Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://inside.mines.edu/~mmazzocc/report"));
				startActivity(i);*/
				Intent i = new Intent(HvZ_phoneActivity.this, report.class);
				startActivity(i);
			}
		});
		
	}
}