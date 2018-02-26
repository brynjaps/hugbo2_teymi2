package is.hi.gordon;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

/**
 * Created by brynj on 26/02/2018.
 */

/***
 * Býr til Singleton hlut af gagnagrunni
 * Geymir líka tilvik af safnklasanum fyrir Question
 *
 */

public class QuestLabDB {
    private static QuestLabDB sQuestLab;
    private final QuestLab mQuestLab;

    public static QuestLabDB get(Context context) {
        if (sQuestLab == null) {
            sQuestLab = new QuestLabDB(context);
        }

        return sQuestLab;
    }

    private QuestLabDB(Context context) {
        //  AppDataBase db = Room.databaseBuilder(getApplicationContext(),
        //          AppDataBase.class, "crime-room").build();
        AppDataBase db = Room.databaseBuilder(context.getApplicationContext(),
                AppDataBase.class, "quest-room")
                .allowMainThreadQueries()
                .build();

        Log.i("QuestLabDB", "gagnagrunnur settur upp");
        mQuestLab = db.questLab();

    }

    public QuestLab getQuestLab() {
        return mQuestLab;
    }
}
