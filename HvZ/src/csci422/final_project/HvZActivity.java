package csci422.final_project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HvZActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv = new TextView(this); 
        tv.setText("Humans Versus Zombies Android Application");
        setContentView(tv);
    }
}