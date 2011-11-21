package csci422.final_project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import csci422.finalproject.profile.Profile;

public class profile extends Activity {
	public void onCreate(Bundle b) {
		super.onCreate(b);
		setContentView(R.layout.profile);
		Profile profile = Profile.getInstance();
	}

}
