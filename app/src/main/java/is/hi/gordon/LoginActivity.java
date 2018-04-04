package is.hi.gordon;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

/**
 * Created by brynj on 03/04/2018.
 */

public class LoginActivity extends AppCompatActivity {

    String [] userArray;
    EditText username;
    EditText password;
    Admin currentUser;
    TextView error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        //when login button is clicked the database is copied into the app and lets know when it is done
        ((Button) findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseHelper dbHelp = new DatabaseHelper(LoginActivity.this);
                try {
                    dbHelp.createDataBase();
                } catch (IOException ioe) {
                    throw new Error("Unable to create database");
                }
                try {
                    dbHelp.openDataBase();
                } catch (SQLException sqle) {
                    throw sqle;
                }

                //lets know when data has successfully been imported to app
                Toast.makeText(LoginActivity.this, "Successfully Imported", Toast.LENGTH_SHORT).show();

                username = (EditText)findViewById(R.id.user);
                password = (EditText)findViewById(R.id.pass);

                //get the username user put in
                CharSequence userText = username.getText();

                //change userText to String
                String user = userText.toString();

                //get the password user put in
                CharSequence passwordText = password.getText();

                //change passwordText to String
                String pass = passwordText.toString();

                //gets all users that match the one put in and also the password put in
                userArray = dbHelp.getAdmin(user, pass);

                //when data from database has been successfully imported go to AdminActivity class
                if(userArray.length > 0) {
                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    error = (TextView)findViewById(R.id.ErrorLogin);
                    error.setText("Vitlaust notendanafn eða lykilorð, vinsamlegast reyndu aftur");
                    finish();
                }
            }
        });

    }

}
