package csci422.final_project;

import java.io.*;

import csci422.final_project.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class HvZ_phoneActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		final String FILENAME = "PlayerCodeFile";

		File code = new File(FILENAME);
		if(!code.exists()) {
			try {
				code.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			AlertDialog.Builder alert = new AlertDialog.Builder(this);

			alert.setTitle("Enter Player Code");
			alert.setMessage("Please enter your player code.");

			// Set an EditText view to get user input 
			final EditText input = new EditText(this);
			alert.setView(input);

			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog, int whichButton) {
					String value = input.getText().toString();
					if(value.length()!=5)
					{
						//do some sort of error check here to be sure that there are only 5 numbers
					}
					else {
						FileOutputStream fos;
						try {
							fos = openFileOutput(FILENAME, Context.MODE_PRIVATE);
							fos.write(value.getBytes());
							fos.close();
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					System.out.println(value);
				}
			});
			alert.show();
		}
		else {
			try {
				FileInputStream fis = openFileInput(FILENAME);
				int num = fis.read();
				System.out.println(num);
				fis.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		//code used to load website for player list. Needs to be put into menuclickactivity.java folder...if possible
		final Button playerButton = (Button) findViewById(R.id.button1);
		playerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
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