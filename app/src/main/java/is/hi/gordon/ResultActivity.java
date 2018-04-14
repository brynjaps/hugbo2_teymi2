package is.hi.gordon;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import java.io.IOException;



/**
 * Created by brynj on 14/03/2018.
 *
 * The class shows results of user on the result page
 */

public class ResultActivity extends Activity {

    public static final String TAG = ApiActivity .class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //get text view
        TextView textScore = (TextView)findViewById(R.id.scoreText);

        //number of questions
        int questNumb = 28;

        //highest score for each question
        int highScore = 5;

        //highest total score from answering all 28 questions
        int highTotal = questNumb * highScore;

        //get score
        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");
        String[] newUser = bundle.getStringArray("newUser");

        //change score to String
        String scoreString = Integer.toString(score);

        newUser[3] = scoreString;

        String jsonObject = makeJsonObject(newUser);

        String url = "https://gordonveftjon.herokuapp.com/api/questions/";

        ApiActivity api = new ApiActivity();

        ResultActivity result = new ResultActivity();

        try {
            String x = result.post("https://gordonveftjon.herokuapp.com/api/questions", jsonObject);
            Log.d("xyxyx", x);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //display score
        textScore.setText(scoreString + " af " + highTotal +" stigum mögulegum");

        //when button is pressed the app closes
        ((Button) findViewById(R.id.close_app)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //exits app
                System.exit(0);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.activity_quest, menu);
        return true;
    }

    public String makeJsonObject(String[] newUser) {
        JSONObject user = new JSONObject();

        try {
            user.put("email", newUser[0]);
            user.put("company", newUser[1]);
            user.put("department", newUser[2]);
            user.put("score", newUser[3]);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String userString = user.toString();
        Log.d("json", userString);
        return userString;
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();

    public String post(String url, String json) throws IOException {
        //OkHttpClient client = new OkHttpClient();
        //MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
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

                        //We are not on main thread
                        //Need to call this method and pass a new Runnable thread
                        //to be able to update the view.
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //Call the method to update the view.
                            //    updateDisplay();
                            }
                        });
                    } else {
                        //alertUserAboutError();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "Exception caught: ", e);
                }
            }
        });
        /*try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }*/

       /* try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            System.out.println(response.body().string());

            //return response.body().string();
        }
        catch (IOException e) {
            Log.e(TAG, "Exception caught: ", e);
        }
        return "villa";*/
       return "sucdess";
    }
}
