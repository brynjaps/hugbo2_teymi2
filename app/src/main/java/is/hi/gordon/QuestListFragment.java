package is.hi.gordon;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by brynj on 26/02/2018.
 */

public class QuestListFragment extends Fragment {

    private static final String SAVED_SUBTITLE_VISIBLE = "subtitle";

    private RecyclerView mQuestRecyclerView;
    private QuestAdapter mAdapter;
    private boolean mSubtitleVisible;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_question_list, container, false);

        mQuestRecyclerView = (RecyclerView) view
                .findViewById(R.id.quest_recycler_view);
        mQuestRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        if (savedInstanceState != null) {
            mSubtitleVisible = savedInstanceState.getBoolean(SAVED_SUBTITLE_VISIBLE);
        }



        updateUI();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_quest_list, menu);

        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
        if (mSubtitleVisible) {
            subtitleItem.setTitle(R.string.hide_subtitle);
        } else {
            subtitleItem.setTitle(R.string.show_subtitle);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_quest:
                Question question = new Question();
                // Ná í safnklasann og bæta við Question hlut
                QuestLab questLab = QuestLabDB.get(getActivity()).getQuestLab();
                questLab.addQuest(question);
                Intent intent = QuestPagerActivity
                        .newIntent(getActivity(), question.getId());
                startActivity(intent);
                return true;
            case R.id.show_subtitle:
                mSubtitleVisible = !mSubtitleVisible;
                getActivity().invalidateOptionsMenu();
                updateSubtitle();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateSubtitle() {
        // Ná í safnklasann og telja crime
        QuestLab questLab = QuestLabDB.get(getActivity()).getQuestLab();
        int questCount = questLab.getQuest().size();
        String subtitle = getString(R.string.subtitle_format, questCount);

        if (!mSubtitleVisible) {
            subtitle = null;
        }

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setSubtitle(subtitle);
    }

    private void updateUI() {
        // Ná í safnklasann og ná í öll question
        QuestLab questionLab = QuestLabDB.get(getActivity()).getQuestLab();
        List<Question> questions = questionLab.getQuest();

        if (mAdapter == null) {
            mAdapter = new QuestAdapter(questions);
            mQuestRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        updateSubtitle();
    }

    private class QuestHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        private Question mQuestion;

        private TextView mTitleTextView;

        public QuestHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_question, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.quest_title_label);
        }

        public void bind(Question question) {
            mQuestion = question;
            mTitleTextView.setText(mQuestion.getQuestTitle());
        }

        @Override
        public void onClick(View view) {
            Intent intent = QuestPagerActivity.newIntent(getActivity(), mQuestion.getId());
            startActivity(intent);
        }
    }

    private class QuestAdapter extends RecyclerView.Adapter<QuestHolder> {

        private List<Question> mQuestions;

        public QuestAdapter(List<Question> crimes) {
            mQuestions = crimes;
        }

        @Override
        public QuestHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new QuestHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(QuestHolder holder, int position) {
            Question question = mQuestions.get(position);
            holder.bind(question);
        }

        @Override
        public int getItemCount() {
            return mQuestions.size();
        }
    }
}
