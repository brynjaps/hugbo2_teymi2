package is.hi.gordon;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.Update;

import java.util.List;
import java.util.UUID;

/**
 * Created by brynj on 26/02/2018.
 */

/**
 * Safnklasi fyrir Question
 * Getur náð í Questions, náð í eina Question, bætt við Question, uppfært Question og eytt út Question
 *
 */
@Dao
public interface QuestLab {
    @Query("SELECT * FROM questions")
    List<Question> getQuest();

    @Query("SELECT * FROM questions WHERE mId = :id")
    Question getQuest (Integer id);

    @Insert
    void insertAll(Question... question);

    @Delete
    void delete(Question question);

    @Update
    void updateQuestions(Question... question);

    @Insert
    void addQuest(Question question);

    @Update
    void updateQuest(Question question);
}
