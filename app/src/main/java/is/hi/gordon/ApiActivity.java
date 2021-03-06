package is.hi.gordon;

/**
 * Created by Karítas Sif og Brynja Pálína on 9.4.2018.
 *
 * Þessi klasi á að sjá um tengingu við api-ið okkar
 */
import android.content.Context;
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
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


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
            }
        });
    }


    private void getUser() {
        String scoreUrl = "https://gordonveftjon.herokuapp.com/api/questions/";
        if (isNetworkAvailable()) {
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
                                        updateScore(jsonData);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {

                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    }
                }
            });
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo!= null && networkInfo.isConnected()) isAvailable = true;
        return isAvailable;
    }

    /**
     * updates the display
     */
    private void updateScore(String jsonData) throws JSONException {
        if(parseScoreDetails(jsonData) == 0) {
            mtextScore.setText("Fyrirtæki/Deild er ekki til");
        } else mtextScore.setText(String.valueOf(parseScoreDetails(jsonData)));
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
