package is.hi.gordon;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by brynj on 13/03/2018.
 *
 * Class shows each question on there own and the options for answers. It also keeps track of
 * the score for all the questions answered.
 */

public class QuestActivity extends Activity{
    Question[] questList;
    Question[] questGet;
    int scoreUser = 0;
    int score = 0;
    int questId = 1;
    int totalQuest = 28;
    int currentQuestInt = 0;
    String[] newUser;

    String currentQuest;
    TextView numbQuest;
    TextView questLabel;
    RadioButton always, usually, sometimes, rarely, never;
    Button nextButton;

    public static final String TAG = ApiActivity .class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize the database helper
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        final DatabaseHelper dbHelp = new DatabaseHelper(this);

        numbQuest = (TextView)findViewById(R.id.numb_quest);
        questLabel = (TextView)findViewById(R.id.quest_label);
        always = (RadioButton)findViewById(R.id.always);
        usually = (RadioButton)findViewById(R.id.usually);
        sometimes = (RadioButton)findViewById(R.id.sometimes);
        rarely = (RadioButton)findViewById(R.id.rarely);
        never = (RadioButton)findViewById(R.id.never);
        nextButton = (Button)findViewById(R.id.btn_next);

        //gets all questions and puts them in the list
        questList = getQuestion();

        if (questList == null) {
            return;
        }

        Log.d("getQuestions","getQuestions: " + questList);

        //gets the id of question number 1
        //Log.d("questList","questList: " + questList[0].getNumber());


        currentQuest = questList[0].getNumber();
        currentQuestInt = Integer.parseInt(currentQuest);

        Bundle bundle = getIntent().getExtras();
        final String[] newUser = bundle.getStringArray("newUser");

        //changes to the next question
        ChangeQuest();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RadioGroup radioGroup = (RadioGroup)findViewById(R.id.options);
                RadioButton answer = (RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());

                //get the text from the radio button id
                CharSequence answerText = answer.getText();

                //change answerText to String
                String answerString = answerText.toString();

                //changes from icelandic names to english
                if(answerString.equals("Alltaf")) {
                    answerString = "always";
                }

                if(answerString.equals("Venjulega")) {
                    answerString = "usually";
                }

                if(answerString.equals("Stundum")) {
                    answerString = "sometimes";
                }

                if(answerString.equals("Sjaldan")) {
                    answerString = "rarely";
                }

                if(answerString.equals("Aldrei")) {
                    answerString = "never";
                }

                //add the score from option picked to the score the player has
                score += getUserScore(answerString, String.valueOf(currentQuestInt));

                //if we haven't gone through every question we go to the next one, else send your
                //score to the result page
                if(currentQuestInt < questList.length) { //will be changed to 28, but currently only 2 questions in database
                    ChangeQuest();
                } else {
                    Intent intent = new Intent(QuestActivity.this, ResultActivity.class);
                    Bundle bundle = new Bundle();
                    //put the users score into a bundle
                    bundle.putInt("score", score);

                    bundle.putStringArray("newUser", newUser);

                    //put the bundle with the score to the next Intent
                    intent.putExtras(bundle);

                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //inflate the menu, this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.activity_quest, menu);
        return true;
    }

    //changes to the next question
    private void ChangeQuest() {
        numbQuest.setText(currentQuestInt + "/" + totalQuest);
        questLabel.setText(currentQuestInt+ ". " + questList[currentQuestInt].getQuestTitle());
        currentQuestInt++;
    }

    //gets all the questions
    private Question[] getQuest(String jsonData) throws JSONException {
        Log.d("another success", "another success");
        JSONArray quest = new JSONArray(jsonData);

        Log.d("quest", "quest:" + quest);

        Question[] questions = new Question[quest.length()];

        for(int i=0; i<quest.length();i++)
        {
            Log.d("lykkja","lykkja");
            JSONObject jsonUser = quest.getJSONObject(i);
            Question question = new Question();
            question.setQuestTitle(jsonUser.getString("question"));
            question.setNumber(jsonUser.getString("number"));
            question.setAlways(jsonUser.getString("always"));
            question.setUsually(jsonUser.getString("usually"));
            question.setSometimes(jsonUser.getString("sometimes"));
            question.setRarely(jsonUser.getString("rarely"));
            question.setNever(jsonUser.getString("never"));
            questions[i] = question;
            Log.d("lykkja 2", "lykkja búin");
        }
        Log.d("questions", "array questions: " + questions);
        Log.d("asList","asList: " + Arrays.asList(questions));
        return questions;
    }

    public Question[] getQuestion() {
        String scoreUrl = "https://gordonveftjon.herokuapp.com/api/questionstext/";
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
                    Log.d("fail", "getQu-fail");
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
                            Log.d("success", "success");
                            System.out.println(jsonData);
                            questGet = getQuest(jsonData);
                            //parseScoreDetails(jsonData);
                            //We are not on main thread
                            //Need to call this method and pass a new Runnable thread
                            //to be able to update the view.
                           /* runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.d("4th succcess", "4th success");
                                    //Call the method to update the view.
                                    try {
                                        Log.d("3rd success","3rd success");
                                        //questGet = getQuest(jsonData);
                                        Log.d("getQuest", "getQuest: " + questGet);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });*/
                        } else {
                            Log.d("else fail", "getQu-else fail");
                        }
                    } catch (IOException e) {
                        Log.e(TAG, "Exception caught: ", e);
                    } /*catch (JSONException e) {
                        Log.e(TAG, "JSON caught: ", e);
                    }*/ catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else {
            //  Toast.makeText(this, R.string.network_unavailable_message, Toast.LENGTH_LONG).show();
        }
        return questGet;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isAvailable = false;
        if(networkInfo!= null && networkInfo.isConnected()) isAvailable = true;
        Log.d("isNetworkAvailable","available");
        return isAvailable;
    }


    private Integer getUserScore(final String answerString, final String number) {
        String scoreUrl = "https://gordonveftjon.herokuapp.com/api/questionstext";
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
                                        scoreUser = getAnswer(jsonData, answerString, number);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } else {

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

        return  scoreUser;
    }

    //gets the score of a Company and returns its median of the scores
    private Integer getAnswer(String jsonData, String answerString, String number) throws JSONException {
        JSONArray quest = new JSONArray(jsonData);

        Question[] questions = new Question[quest.length()];

        for(int i=0; i<quest.length();i++)
        {
                JSONObject jsonUser = quest.getJSONObject(i);
                Question question = new Question();
                question.setQuestTitle(jsonUser.getString("question"));
                question.setNumber(jsonUser.getString("number"));
                question.setAlways(jsonUser.getString("always"));
                question.setUsually(jsonUser.getString("usually"));
                question.setSometimes(jsonUser.getString("sometimes"));
                question.setRarely(jsonUser.getString("rarely"));
                question.setNever(jsonUser.getString("never"));
                questions[i] = question;
        }

        for(int i=0; i<questions.length; i++)
        {
            if(questions[i].getNumber().equals(number))
            {
                if(answerString.equals("always")) {
                    String alwaysAnswer = questions[i].getAlways();
                    int alwaysInt = Integer.parseInt(alwaysAnswer);
                    return alwaysInt;
                }
                if(answerString.equals("usually")) {
                    String usuallyAnswer = questions[i].getUsually();
                    int usuallyInt = Integer.parseInt(usuallyAnswer);
                    return usuallyInt;
                }
                if(answerString.equals("sometimes")) {
                    String sometimesAnswer = questions[i].getSometimes();
                    int sometimesInt = Integer.parseInt(sometimesAnswer);
                    return sometimesInt;
                }
                if(answerString.equals("rarely")) {
                    String rarelyAnswer = questions[i].getRarely();
                    int rarelyInt = Integer.parseInt(rarelyAnswer);
                    return rarelyInt;
                }
                if(answerString.equals("never")) {
                    String neverAnswer = questions[i].getNever();
                    int neverInt = Integer.parseInt(neverAnswer);
                    return neverInt;
                }
            }
        }
        return 0;
    }

}
