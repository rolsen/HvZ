package csci422.final_project;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import csci422.final_project.R;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class menuclickactivity extends Activity {
	/** Called when the activity is first created. */
	PopupWindow pw;
	LinearLayout layout;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		pw = new PopupWindow(this);
		layout = new LinearLayout(this);
		//click-handlers for buttons    
		final Button playerButton = (Button) findViewById(R.id.button1);

		//View playerButton = findViewById(R.id.button1);
		playerButton.setOnClickListener(new OnClickListener() { 
			public void onClick(View v) {
				switch (v.getId()) {
				case R.id.button1:
					System.out.println("ROAR!!!");
					Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://inside.mines.edu/~cloew/cgi-bin/first.cgi"));
					startActivity(i);
				}
			}
		});
	}
}
