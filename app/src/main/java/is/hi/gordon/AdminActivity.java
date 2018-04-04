package is.hi.gordon;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by brynj on 03/04/2018.
 *
 * A class that can only be accessable when admin of app is logged in, has two buttons that lead
 * to two other pages
 *
 */

public class AdminActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin);

        ((Button) findViewById(R.id.skodaListaBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CompanyListActivity.class);
                startActivity(intent);
                finish();
            }
        });

        ((Button) findViewById(R.id.skodaNidurBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, CompanyResultActivity.class);
                startActivity(intent);
                finish();
            }
        });


        ((Button) findViewById(R.id.signOutBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
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
