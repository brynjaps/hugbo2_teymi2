package is.hi.gordon;

/**
 * Created by Karítas Sif og Brynja Pálína on 9.4.2018.
 *
 * Þessi klasi á að sjá um tengingu við api-ið okkar
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

    EditText mCompany;


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
                                        updateDisplay(jsonData);
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

    /**
     * updates the display
     */
    private void updateDisplay(String jsonData) throws JSONException {
        mtextScore.setText(String.valueOf(parseScoreDetails(jsonData)));
        Log.d("update", "update");
    }

    private Integer parseScoreDetails(String jsonData) throws JSONException{

        mCompany = (EditText)findViewById(R.id.company);

        CharSequence companyText = mCompany.getText();

        String company = companyText.toString();

        return getScore(jsonData, company);
    }

    //gets the score of a Company and returns its median of the scores
    private Integer getScore(String jsonData, String company) throws JSONException {
        JSONArray user = new JSONArray(jsonData);

        User[] users = new User[user.length()];
        int score = 0;
        String userScore;
        int count = 0;

        for(int i=0; i<user.length();i++)
        {
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
            if(users[i].getCompany().equals(company))
            {
                count = count + 1;
                userScore = users[i].getScore();
                score += Integer.parseInt(userScore);
            }
        }

        if(count == 0) {
            for(int i=0; i<users.length; i++)
            {
                if(users[i].getDepartment().equals(company))
                {
                    count = count + 1;
                    userScore = users[i].getScore();
                    score += Integer.parseInt(userScore);
                }
            }
        }

        if(count > 0) {
            int companyMedian = Math.round(score/count);
            return companyMedian;
        } else {
            return 0;
        }
    }
}
