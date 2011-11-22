package csci422.final_project;

import java.io.*;
import java.net.*;

import csci422.final_project.profile.Profile;

//This appears in Core Web Programming from
//Prentice Hall Publishers, and may be freely used
//or adapted. 1997 Marty Hall, hall@apl.jhu.edu.

public class CgiReport extends CgiGet {

	public CgiReport(String name, String[] args,
			String type) {
		super(name, args, type);
	}

	public String reportKill() {
		Profile profile = Profile.getInstance();
		
		String result = "error";
		
		try {
			URL kill = new URL(profile.getReportKillURL());
			URLConnection connection = kill.openConnection();
			connection.setDoOutput(true);
			PrintStream out = new PrintStream(connection.getOutputStream());
			out.println("z_code="+args[0]+ " h_code="+args[1] + " hour="+args[2]+" minute="+args[3] + " AP="+args[4] + " month="+args[5]+" day="+args[6]);
			
			out.close();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			result = in.readLine();
			in.close();		
			
		} catch (MalformedURLException e) {
			System.out.println("CONNECTION ERROR1");
			System.out.println("z_code="+args[0]+ " h_code="+args[1] + " hour="+args[2]+" minute="+args[3] + " AP="+args[4] + " month="+args[5]+" day="+args[6]);
		} catch (IOException e) {
			System.out.println("CONNECTION ERROR2");
			System.out.println("z_code="+args[0]+ " h_code="+args[1] + " hour="+args[2]+" minute="+args[3] + " AP="+args[4] + " month="+args[5]+" day="+args[6]);
		}
		return result;
	}
}

