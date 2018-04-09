package is.hi.gordon;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * Created by brynj on 14/03/2018.
 *
 * The class shows results of user on the result page
 */

public class ResultActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //get text view
        TextView textScore = (TextView)findViewById(R.id.score);

        //number of questions
        int questNumb = 28;

        //highest score for each question
        int highScore = 5;

        //highest total score from answering all 28 questions
        int highTotal = questNumb * highScore;

        //get score
        Bundle bundle = getIntent().getExtras();
        int score = bundle.getInt("score");

        //change score to String
        String scoreString = Integer.toString(score);

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

}
