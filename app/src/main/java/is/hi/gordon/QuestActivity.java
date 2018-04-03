package is.hi.gordon;

import java.util.ArrayList;
import java.util.List;

import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


/**
 * Created by brynj on 13/03/2018.
 *
 * Class shows each question on there own and the options for answers. It also keeps track of
 * the score for all the questions answered.
 */

public class QuestActivity extends Activity{
    List<Question> questList;
    int score = 0;
    int questScore = 0;
    int questId = 0;
    int prevQuestId = 0;
    int totalQuest = 28;

    Question currentQuest;
    Question prevQuest;
    TextView numbQuest;
    TextView questLabel;
    RadioButton always, usually, sometimes, rarely, never;
    Button nextButton;
    Button prevButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //initialize the database helper
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest);
        final DatabaseHelper dbHelp = new DatabaseHelper(this);

        //gets all questions and puts them in the list
        questList = dbHelp.getAllQuest();

        //gets the id of question number 1
        currentQuest = questList.get(questId);

        numbQuest = (TextView)findViewById(R.id.numb_quest);
        questLabel = (TextView)findViewById(R.id.quest_label);
        always = (RadioButton)findViewById(R.id.always);
        usually = (RadioButton)findViewById(R.id.usually);
        sometimes = (RadioButton)findViewById(R.id.sometimes);
        rarely = (RadioButton)findViewById(R.id.rarely);
        never = (RadioButton)findViewById(R.id.never);
        nextButton = (Button)findViewById(R.id.btn_next);
        prevButton = (Button)findViewById(R.id.btn_back);

        Log.d("questId","questId at first= "+questId);

        //changes to the next question
        NextQuest();

        Log.d("questId","questId after first nextquest= "+questId);

        //when next button ("næsta") is clicked
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

                //score from question
                questScore = dbHelp.usersScore(answerString,currentQuest.getId());

                //add the score from option picked to the score the player has
                score += questScore;

                Log.d("questScore","questScore in next= "+questScore);

                Log.d("score","score= "+score);

                Log.d("questId","questId áður en valið= "+questId);

                //if we haven't gone through every question we go to the next one, else send your
                //score to the result page
                if(questId < 2) { //will be changed to 28, but currently only 2 questions in database
                    currentQuest = questList.get(questId);
                    Log.d("questId","questId 1.0= "+questId);
                    NextQuest();
                    Log.d("questId","questId= "+questId);
                } else {
                    Log.d("questId","eftir 2 questId= "+questId);
                    Intent intent = new Intent(QuestActivity.this, ResultActivity.class);
                    Bundle bundle = new Bundle();
                    //put the users score into a bundle
                    bundle.putInt("score", score);
                    //put the bundle with the score to the next Intent
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //when prev button ("fyrri") is clicked
        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("questScore","questScore in prev= "+questScore);

                //minus the score from option last picked from the score the player has
                score = score - questScore;

                Log.d("prevScore","score in prev= "+questScore);

                //if we haven't gone through every question we go to the next one, else send your
                //score to the result page
                if(questId > 1) {
                    questId = questId - 2;
                    Log.d("prevQuestId","prevQuestId= "+questId);
                    currentQuest = questList.get(questId);
                    PreviousQuest();
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
    private void NextQuest() {
        numbQuest.setText(currentQuest.getId() + "/" + totalQuest);
        questLabel.setText(currentQuest.getId()+ ". " + currentQuest.getQuestTitle());
        Log.d("questId","questId í fyrsta nextquest= "+questId);
        questId++;
        Log.d("questId","questId í fyrsta nextquest (eftir hækkun)= "+questId);
        /*if(questId > 1) {
            prevButton.setEnabled(true);
        } else {
            prevButton.setEnabled(false);
        }*/
    }

    //changes to the previous question
    private void PreviousQuest() {
        numbQuest.setText(currentQuest.getId() + "/" + totalQuest);
        Log.d("","currentQuestID= "+currentQuest.getId());
        questLabel.setText(currentQuest.getId()+ ". " + currentQuest.getQuestTitle());
        /*if(questId > 1) {
            prevButton.setEnabled(true);
        } else {
            prevButton.setEnabled(false);
        }*/
    }
}
