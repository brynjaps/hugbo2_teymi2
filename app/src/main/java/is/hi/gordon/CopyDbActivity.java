package is.hi.gordon;

import android.database.Cursor;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;


/**
 * Created by brynj on 08/03/2018.
 */



public class CopyDbActivity extends AppCompatActivity {

    Cursor c = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ((Button) findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper myDbHelper = new DatabaseHelper(CopyDbActivity.this);
                try {
                    myDbHelper.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    myDbHelper.openDataBase();
                } catch (SQLException sqle) {
                    throw sqle;
                }
                Toast.makeText(CopyDbActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();
                c = myDbHelper.query("questions", null, null, null, null, null, null);
                if (c.moveToFirst()) {
                    do {
                        Toast.makeText(CopyDbActivity.this,
                                "_id: " + c.getString(0) + "\n" +
                                        "question: " + c.getString(1) + "\n" +
                                        "always: " + c.getString(2) + "\n" +
                                        "usually:  " + c.getString(3) + "\n" +
                                        "sometimes:  " + c.getString(4) + "\n" +
                                        "rarely:  " + c.getString(5) + "\n" +
                                        "never:  " + c.getString(6),
                                Toast.LENGTH_LONG).show();
                    } while (c.moveToNext());
                }
            }
        });

    }


}
