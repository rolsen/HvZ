package java_cgi;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class LoadTestData {
  private static final String URL = "http://inside.mines.edu/~rolsen/cgi-bin/trackerReport.cgi";
  private static final int NO_ERROR = 0;
  private static final int ERROR = 1;

  private static final int BASE_LAT = 39751265;
  private static final int BASE_LNG = -105221450;

  private static Random rn;

  public static void main (String[] args) {
    rn = new Random();
    LoadTestData.reportTestPlayers();
  }

  public static void reportTestPlayers() {
		String params, pid, lat, lng;
		int direction;

		for (int i = 1; i < 5; i++) {
		  pid = String.format("%d%d%d%d%d", i, i, i, i, i);
      direction = getDirection();
      lat = String.format("%d", (BASE_LAT + direction * (i * 500)));
      direction = getDirection();
      lng = String.format("%d", (BASE_LNG + direction * (i * 500)));
			params = String.format("pid=%s&lat=%s&lng=%s", pid, lat, lng);

			reportPlayer(params);
		}

    pid = "10003";
    lat = "39751202";
    lng = "-105227700";
		params = String.format("pid=%s&lat=%s&lng=%s", pid, lat, lng);
		reportPlayer(params);

    pid = "10004";
    lat = "39741202";
    lng = "-105227700";
		params = String.format("pid=%s&lat=%s&lng=%s", pid, lat, lng);
		reportPlayer(params);
	}

	private static void reportPlayer(String params) {
		try {
			// Send data
			URL url = new URL(URL);
			OutputStreamWriter wr;
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");

		  wr = new OutputStreamWriter(conn.getOutputStream());
		  wr.write(params);
	    wr.flush();
			wr.close();

			// Get the response
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				System.out.printf("reply line: %s\n", line);
			}
			rd.close();

		}
		catch (Exception e) {
			System.out.printf("Error reaching server: %s\n", e.getMessage());
			System.exit(ERROR);
		}
  }

	public static int getDirection() {
    if (rn.nextInt() % 2 == 0) {
      return 1;
    }
    else {
      return -1;
    }
  }
}