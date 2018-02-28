package is.hi.gordon;

import android.support.v4.app.Fragment;

/**
 * Created by brynj on 26/02/2018.
 */

public class QuestListActivity extends SingleFragmentActivity{
    @Override
    protected Fragment createFragment() {
        return new QuestListFragment();
    }
}

