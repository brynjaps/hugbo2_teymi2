package is.hi.gordon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;
import java.util.UUID;

/**
 * Created by brynj on 26/02/2018.
 */

public class QuestionFragment  extends Fragment {

    private static final String ARG_QUEST_ID = "question_id";

    private Question mQuestion;
    private TextView mQuestField;
    private Button mAlwaysButton;
    private Button mUsuallyButton;
    private Button mSometimesButton;
    private Button mRarelyButton;
    private Button mNeverButton;

    public static QuestionFragment newInstance(UUID questionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_QUEST_ID, questionId);

        QuestionFragment fragment = new QuestionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID questionId = (UUID) getArguments().getSerializable(ARG_QUEST_ID);
        // Ná í safnklasann úr gagnagrunni og ná í ákveðið question
        QuestLab questLab = QuestLabDB.get(getActivity()).getQuestLab();
        mQuestion = questLab.getQuest(questionId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_question, container, false);

        mQuestField = (TextView) v.findViewById(R.id.quest_title_label);
        mQuestField.setText(mQuestion.getQuestTitle());
        mQuestField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mQuestion.setQuestTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mAlwaysButton = (Button) v.findViewById(R.id.quest_always);
        mAlwaysButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*Gerist eitthvað*/
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        // Uppfæra gagnagrunninn með Question hlutnum sem var uppfærður í þessu Fragmenti
        QuestLab questLab = QuestLabDB.get(getActivity()).getQuestLab();
        questLab.updateQuest(mQuestion);
    }
}
