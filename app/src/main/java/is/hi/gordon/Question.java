package is.hi.gordon;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import java.util.UUID;

/**
 * Created by brynj on 26/02/2018.
 */


/***
 * Módel klasi fyrir Question. Hlutir geymdir í töflunni questions
 */
@Entity(tableName="questions")
public class Question {

    @PrimaryKey
    @NonNull
    private UUID mId;
    @ColumnInfo(name = "questTitle")
    private String mQuestTitle;
    @ColumnInfo(name = "always")
    private Integer mAlways;
    @ColumnInfo(name = "usually")
    private Integer mUsually;
    @ColumnInfo(name="sometimes")
    private  Integer mSometimes;
    @ColumnInfo(name="rarely")
    private Integer mRarely;
    @ColumnInfo(name="never")
    private  Integer mNever;

    public Question() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID uuid) {mId = uuid;}

    public String getQuestTitle() {
        return mQuestTitle;
    }

    public void setQuestTitle(String question) {
        mQuestTitle = question;
    }

    public Integer getAlways() {
        return mAlways;
    }

    public void setAlways(Integer always) {
        mAlways = always;
    }

    public Integer getUsually() {
        return mUsually;
    }

    public void setUsually(Integer usually) {
        mUsually = usually;
    }

    public Integer getSometimes() {
        return mSometimes;
    }

    public void setSometimes(Integer sometimes) {
        mSometimes = sometimes;
    }

    public Integer getRarely() {
        return mRarely;
    }

    public void setRarely(Integer rarely) {
        mRarely = rarely;
    }

    public Integer getNever() {
        return mNever;
    }

    public void setNever(Integer never) {
        mNever = never;
    }

}
