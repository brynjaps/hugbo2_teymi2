package is.hi.gordon;

/**
 * Created by Karítas Sif og Brynja Pálína on 9.4.2018.
 *
 * Þessi klassi á að sjá um tengingu við api-ið okkar
 */
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import is.hi.gordon.User;


public class ApiActivity extends AppCompatActivity {
    private User mUser;
    public static final String TAG = ApiActivity .class.getSimpleName();
    private double zeroScore = 0;


    @BindView(R.id.textScore)
    TextView mtextScore;

    @BindView(R.id.buttonScore)
    Button mButtonScore;

    EditText mEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        ButterKnife.bind(this);

        ((Button) findViewById(R.id.buttonScore)).setOnClickListener(new View.OnClickListener(){
        @Override
            public void onClick(View v) {
                getUser();
                Log.d("UserCall", "Usercall");
            }
        });

        getUser();
    }


    private void getUser() {
        String scoreUrl = "https://gordonveftjon.herokuapp.com/api/questions/";
        Log.d("scoreUrl", scoreUrl);
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
                        String jsonData = response.body().string();
                        Log.v(TAG, jsonData);
                        if (response.isSuccessful()) {
                            System.out.println(jsonData);
                            mUser = parseScoreDetails(jsonData);
                            //We are not on main thread
                            //Need to call this method and pass a new Runnable thread
                            //to be able to update the view.
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Call the method to update the view.
                                    updateDisplay();
                                }
                            });
                        } else {
                            alertUserAboutError();
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } catch (JSONException e) {
                        Log.e(TAG, "JSON caught: ", e);
                    }
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

    /**
     * updates the display
     */
    private void updateDisplay() {
        mtextScore.setText(mUser.getScore());
        Log.d("update", "update");
    }

    private User parseScoreDetails(String jsonData) throws JSONException{
        User user = new User();

        mEmail = (EditText)findViewById(R.id.email);

        CharSequence emailText = mEmail.getText();

        String email = emailText.toString();

        user.setScore(getScore(jsonData, email));
        Log.d("parseScoreDetails","parseScore");
       // user.setCompany(getCompany(jsonData));
       // user.setDepartment(getDepartment(jsonData));
       // user.setScore(getScore(jsonData));


        return user;
    }


    private String getScore(String jsonData, String email) throws JSONException {
        JSONArray user = new JSONArray(jsonData);

        User[] users = new User[user.length()];
        String score;

        for(int i=0; i<user.length();i++)
        {
            Log.d("lykkja 1", "lykkja 1");
            JSONObject jsonUser = user.getJSONObject(i);
            User use = new User();

            use.setEmail(jsonUser.getString("email"));
            use.setCompany(jsonUser.getString("company"));
            use.setDepartment(jsonUser.getString("department"));
            use.setScore(jsonUser.getString("score"));
            users[i] = use;
        }

        for(int i=0; i<users.length; i++)
        {
            Log.d("lykkja 2", "lykkja 2");
            Log.d("lykkja users", users[i].getEmail());
            Log.d("lykkja email", email);
            if(users[i].getEmail().equals(email))
            {
                Log.d("lykkja 3", "lykkja 3");
                score = users[i].getScore();
                return score;
            }
            //bæta við else seinna, villuskilaboð
        }

        return "villa";

        /*String email = user.getString("email");
        System.out.println("email" + jsonData);
        return email;*/
    }

    public String post(String url, String data) throws IOException {
            OkHttpClient client = new OkHttpClient();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, data);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception caught: ", e);
        }
        return "villa";
    }
}
