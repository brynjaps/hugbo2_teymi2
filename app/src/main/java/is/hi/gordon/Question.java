package is.hi.gordon;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by brynj on 26/02/2018.
 *
 * Módel klasi fyrir Question. Hlutir geymdir í töflunni questions
 *
 */

@Entity(tableName="questions")
public class Question {

    @PrimaryKey
    @NonNull
    private String mNumber;
    @ColumnInfo(name = "question")
    private String mQuestTitle;
    @ColumnInfo(name = "always")
    private String mAlways;
    @ColumnInfo(name = "usually")
    private String mUsually;
    @ColumnInfo(name="sometimes")
    private  String mSometimes;
    @ColumnInfo(name="rarely")
    private String mRarely;
    @ColumnInfo(name="never")
    private  String mNever;

    public Question() {
        mQuestTitle = "";
        mNumber = "";
        mAlways = "";
        mUsually ="";
        mSometimes = "";
        mRarely = "";
        mNever = "";
    }

    public Question (String question, String number, String always, String usually, String sometimes, String
                     rarely, String never) {
        mQuestTitle = question;
        mNumber = number;
        mAlways = always;
        mUsually = usually;
        mSometimes = sometimes;
        mRarely = rarely;
        mNever = never;
    }

    public String getQuestTitle() {
        return mQuestTitle;
    }

    public void setQuestTitle(String question) {
        mQuestTitle = question;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getAlways() {
        return mAlways;
    }

    public void setAlways(String always) {
        mAlways = always;
    }

    public String getUsually() {
        return mUsually;
    }

    public void setUsually(String usually) {
        mUsually = usually;
    }

    public String getSometimes() {
        return mSometimes;
    }

    public void setSometimes(String sometimes) {
        mSometimes = sometimes;
    }

    public String getRarely() {
        return mRarely;
    }

    public void setRarely(String rarely) {
        mRarely = rarely;
    }

    public String getNever() {
        return mNever;
    }

    public void setNever(String never) {
        mNever = never;
    }

}
