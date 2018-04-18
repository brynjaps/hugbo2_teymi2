package is.hi.gordon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Created by Karítas og Brynja  on 03/04/2018.
 *
 * A class that makes sure that login is valid
 *
 */

public class LoginActivity extends AppCompatActivity {

    EditText mUsername;
    EditText mPassword;
    TextView error;

    public static final String TAG = LoginActivity .class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);


        ((Button) findViewById(R.id.login_button)).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                getLogin();
                Log.d("UserCall", "Usercall");
            }
        });

    }
    // gets user name and password from user table on api
    private void getLogin() {
        String scoreUrl = "https://gordonveftjon.herokuapp.com/api/user/";
        if(isNetworkAvailable()) {
            // toggleRefresh();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(scoreUrl)
                    .build();

            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                        }
                    });
                    try {
                        final String jsonData = response.body().string();
                         Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            System.out.println(jsonData);
                            //parseScoreDetails(jsonData);
                            //We are not on main thread
                            //Need to call this method and pass a new Runnable thread
                            //to be able to update the view.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Call the method to update the view.
                                    try {
                                        parseUserInfoDetails(jsonData);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } /*catch (JSONException e) {
                        Log.e(TAG, "JSON caught: ", e);
                    }*/
                }
            });
        }
        else {
            //  Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {

        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo!= null && networkInfo.isConnected()) isAvailable = true;
        Log.d("isNetworkAvailable","available");
        return isAvailable;
    }

    private void alertUserAboutError() {
        // AlertDialogFragment dialog = new AlertDialogFragment();
        //  dialog.show(getFragmentManager(), "error_dialog");
    }


    private Integer parseUserInfoDetails(String jsonData) throws JSONException{

        mUsername = (EditText)findViewById(R.id.user);

        CharSequence userText = mUsername.getText();

        String user = userText.toString();

        mPassword = (EditText)findViewById(R.id.pass);

        CharSequence passwordText = mPassword.getText();

        String password = passwordText.toString();

        return getUsernamePassword(jsonData, user, password);
    }

    //gets the user name and password from user table and looks if its correct
    private Integer getUsernamePassword(String jsonData, String user, String password) throws JSONException {
        JSONArray userInfo = new JSONArray(jsonData);
        Admin[] usersInfo = new Admin[userInfo.length()];

        String admin = "admin";
        String gordonUser = "gordon";

        for(int i=0; i<userInfo.length();i++)
        {
            JSONObject jsonUser = userInfo.getJSONObject(i);
            Admin use = new Admin();

            use.setUsername(jsonUser.getString("username"));
            use.setPassword(jsonUser.getString("password"));
            usersInfo[i] = use;
        }

        for(int i=0; i<usersInfo.length; i++)
        {

            if(usersInfo[i].getUsername().equals(user) && usersInfo[i].getPassword().equals(password)) {
                if (usersInfo[i].getUsername().equals(admin)) {
                    Intent intent = new Intent(LoginActivity.this, ApiActivity.class);
                    startActivity(intent);
                    finish();
                } else if (usersInfo[i].getUsername().equals(gordonUser)) {
                    Intent intent = new Intent(LoginActivity.this, newUserActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    error = (TextView)findViewById(R.id.ErrorLogin);
                    error.setText("Vitlaust notendanafn eða lykilorð, vinsamlegast reyndu aftur");
                    finish();
                }
            }
        }

        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.activity_quest, menu);
        return true;
    }

}
