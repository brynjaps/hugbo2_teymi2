package is.hi.gordon;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by brynj on 03/04/2018.
 */

public class AdminActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize the database helper
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);
    }
}
