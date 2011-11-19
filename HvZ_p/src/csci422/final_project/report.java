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
			public void onClick(View V) {
				// Perform action on clicks
				int zombie = Integer.parseInt(zombieCode.getText().toString());
				int human = Integer.parseInt(humanCode.getText().toString());
				int hour = time.getCurrentHour();
				int min = time.getCurrentMinute();
				String ap = " ";
				if(hour < 12) {
					ap = "AM";
					if (hour==0){ 
						hour = 12;
					}
				}
				else {
					ap = "PM";
					if(hour > 12){
						hour -=12;
					}
				}
				int day = date.getDayOfMonth();
				int month = date.getMonth();
				int year = date.getYear();
				String[] args= {String.valueOf(zombie), String.valueOf(human), String.valueOf(hour), String.valueOf(min), ap, String.valueOf(month), String.valueOf(day), String.valueOf(year)}; 
				CgiReport report = new CgiReport("CgiGet", args, "GET");
				report.reportKill();
			}

		});
		Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://inside.mines.edu/~kraber/report"));
		startActivity(i);
	}

}
