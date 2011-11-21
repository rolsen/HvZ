package csci422.final_project;

import java.io.*;
import java.net.*;

//This appears in Core Web Programming from
//Prentice Hall Publishers, and may be freely used
//or adapted. 1997 Marty Hall, hall@apl.jhu.edu.

public class CgiReport extends CgiGet {

	public CgiReport(String name, String[] args,
			String type) {
		super(name, args, type);
	}

	public String reportKill() {
		String result = "error";
		try {
			URL kill = new URL("http://inside.mines.edu/~mmazzocc/cgi-bin/report.cgi");
			URLConnection connection = kill.openConnection();
			connection.setDoOutput(true);
			PrintStream out = new PrintStream(connection.getOutputStream());
			out.println("z_code="+args[0]+ " h_code="+args[1] + " hour="+args[2]+" minute="+args[3] + " AP="+args[4] + " month="+args[5]+" day="+args[6]);
			
			out.close();
			DataInputStream in = new DataInputStream(connection.getInputStream());
			result = in.readLine();
			in.close();		
			
		} catch (MalformedURLException e) {
			System.out.println("CONNECTION ERROR");
			System.out.println("z_code="+args[0]+ " h_code="+args[1] + " hour="+args[2]+" minute="+args[3] + " AP="+args[4] + " month="+args[5]+" day="+args[6]);
		} catch (IOException e) {
			System.out.println("CONNECTION ERROR");
			System.out.println("z_code="+args[0]+ " h_code="+args[1] + " hour="+args[2]+" minute="+args[3] + " AP="+args[4] + " month="+args[5]+" day="+args[6]);
		}
		return result;
	}
}

