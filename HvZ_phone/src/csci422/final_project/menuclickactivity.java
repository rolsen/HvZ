package csci422.final_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import csci422.final_project.R;
import android.view.View.OnClickListener;

public class menuclickactivity extends Activity implements OnClickListener  {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        //click-handlers for buttons
        View playerButton = findViewById(R.id.button1);
        playerButton.setOnClickListener((android.view.View.OnClickListener) this);
        
    }
	public void onClick(View v) {
    	switch (v.getId()) {
    	case R.id.button1:
    		Intent i = new Intent(this, menuclickactivity.class);
    		break;
    	}
    }
}