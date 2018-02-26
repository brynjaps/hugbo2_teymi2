package is.hi.gordon;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.List;
import java.util.UUID;

/**
 * Created by brynj on 26/02/2018.
 */

public class QuestPagerActivity extends AppCompatActivity {

    private static final String EXTRA_QUEST_ID =
            "is.hi.gordon.question_id";

    private ViewPager mViewPager;
    private List<Question> mQuestion;

    public static Intent newIntent(Context packageContext, UUID questionId) {
        Intent intent = new Intent(packageContext, QuestPagerActivity.class);
        intent.putExtra(EXTRA_QUEST_ID, questionId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quest_pager);

        UUID crimeId = (UUID) getIntent()
                .getSerializableExtra(EXTRA_QUEST_ID);

        mViewPager = (ViewPager) findViewById(R.id.quest_view_pager);

        QuestLab questLab = QuestLabDB.get(this).getQuestLab();
        mQuestion = questLab.getQuest();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {

            @Override
            public Fragment getItem(int position) {
                Question question = mQuestion.get(position);
                return QuestionFragment.newInstance(question.getId());
            }

            @Override
            public int getCount() {
                return mQuestion.size();
            }
        });

        for (int i = 0; i < mQuestion.size(); i++) {
            if (mQuestion.get(i).getId().equals(crimeId)) {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
