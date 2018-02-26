package is.hi.gordon;

/**
 * Created by brynj on 26/02/2018.
 */

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

/***
 * Abstract klasi sem setur upp gagnagrunninn og segir hvaða módel hluti þú geymir
 * í gagnagrunninum, hér Crime.
 * Leyfir þér að ná í safnklasa fyrir Crime (hér CrimeLab)
 *
 */
@Database(entities = {Question.class}, version = 1, exportSchema=false)
public abstract class AppDataBase extends RoomDatabase {
    public abstract QuestLab questLab();
}
