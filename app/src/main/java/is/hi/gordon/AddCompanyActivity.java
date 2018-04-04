package is.hi.gordon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

/**
 * Created by brynj on 04/04/2018.
 *
 * A class that makes it possible for admin to add companies to the company list
 *
 */

public class AddCompanyActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_result);

        //when button is clicked the user goes back to the admin page
        ((Button) findViewById(R.id.goBackBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCompanyActivity.this, CompanyListActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.activity_quest, menu);
        return true;
    }
}
