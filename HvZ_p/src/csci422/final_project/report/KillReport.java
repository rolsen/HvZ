package csci422.final_project.report;

import csci422.final_project.exception.HumanIdException;
import csci422.final_project.exception.ZombieIdException;
import csci422.final_project.profile.Profile;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

public class KillReport {
	
	String human, zombie;
	int day, month;
	int hour, minute;
	String ap;
	

	public KillReport(EditText zombieCode, EditText humanCode, DatePicker date, TimePicker time)
	{
		// Get zombie and human codes
		zombie = zombieCode.getText().toString();
		human = humanCode.getText().toString();
		
		// Get date
		day = date.getDayOfMonth();
		month = date.getMonth();
		
		// Get time
		hour = time.getCurrentHour();
		minute = time.getCurrentMinute();
		ap = " ";
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
	}
	
	public void validateParams() throws HumanIdException, ZombieIdException {
		if (!Profile.validId(zombie)) {
			throw(new ZombieIdException());
		}
		
		if (!Profile.validId(human)) {
			throw(new HumanIdException());
		}
	}
	
	public String getKillParams() {
		String[] args = getArgs();
		String params = "z_code="+args[0]+ "&h_code="+args[1] + "&hour="+args[2]+"&minute="+args[3] + "&AP="+args[4] 
				+ "&month="+args[5]+"&day="+args[6];
		return params;
	}
	
	public String[] getArgs() {
		String[] s = {zombie, human, String.valueOf(hour), String.valueOf(minute), ap, 
				String.valueOf(month), String.valueOf(day)};
		return s;
	}
	
}
