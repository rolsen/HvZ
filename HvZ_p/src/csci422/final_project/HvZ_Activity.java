package csci422.final_project;

import java.io.*;

import csci422.final_project.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class HvZ_Activity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final String FILENAME = "PlayerCodeFile";

		int len = 1024;
		byte[] buffer = new byte[len];
		try {
			System.out.println("HELLO");
			FileInputStream fis = openFileInput(FILENAME);
			int nrb = fis.read(buffer, 0, len);
			while (nrb != -1) {
				nrb = fis.read(buffer, 0, len);
			}
			System.out.println(buffer.toString());
			fis.close();
		} catch (FileNotFoundException e) {
			final AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Enter Player Code");
			alert.setMessage("Please enter your player code.");

			// Set an EditText view to get user input
			final EditText input = new EditText(this);
			alert.setView(input);
			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int whichButton) {
							int value = Integer.parseInt(input.getText().toString());
							if (value == 0 || value < 9999 || value > 100000) {
								removeDialog(1);
								Toast.makeText(getApplicationContext(), "Invalid Player Code. Please go to your account to update.", Toast.LENGTH_LONG).show();
								Handler handler = new Handler();
								handler.post(new Runnable() { 
									public void run() { showDialog(1);
									}
								});
							}
							else

							System.out.println(value);
						}
					});
			alert.show();

		} catch (IOException e) {
			System.out.println("FAILED");
		}

		// code used to load website for player list. Needs to be put into
		// menuclickactivity.java folder...if possible
		final Button playerButton = (Button) findViewById(R.id.button1);
		playerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				Intent i = new Intent(HvZ_Activity.this, players.class);
				startActivity(i);
			}
		});

		final Button killButton = (Button) findViewById(R.id.button2);
		killButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				/*
				 * Intent i = new Intent(Intent.ACTION_VIEW,
				 * Uri.parse("http://inside.mines.edu/~mmazzocc/report"));
				 * startActivity(i);
				 */
				Intent i = new Intent(HvZ_Activity.this, report.class);
				startActivity(i);
			}
		});
	}
}
