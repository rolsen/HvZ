package csci422.final_project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class report extends Activity {
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.report);

		final EditText zombieCode = (EditText) findViewById(R.id.zombie);
		final EditText humanCode = (EditText) findViewById(R.id.human);
		final TimePicker time = (TimePicker) findViewById(R.id.time);
		final DatePicker date = (DatePicker) findViewById(R.id.date);
		final Button report = (Button) findViewById(R.id.reportKill);
		report.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View V) {
				// Perform action on clicks
				String zombie = zombieCode.getText().toString();
				String human = humanCode.getText().toString();
				int hour = time.getCurrentHour();
				int min = time.getCurrentMinute();
				int day = date.getDayOfMonth();
				int month = date.getMonth();
				int year = date.getYear();
				System.out.println("Zombie "+zombie+ " killed " + human + " at " + hour + ":" + min + " on " + month + "/" + day + "/" + year);
			}

		});
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://inside.mines.edu/~kraber/report"));
		startActivity(i);
	}

}
