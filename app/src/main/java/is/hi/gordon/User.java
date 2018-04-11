package is.hi.gordon;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by Karítas Sif on 10.4.2018.
 *
 * A model class for the table User
 */

// Þarf að skilgreina scorið sem við ættlum að ná í hérna

@Entity(tableName = "user")
public class User {

    @PrimaryKey
    private String mEmail;

    @ColumnInfo(name="company")
    private String mCompany;

    @ColumnInfo(name="department")
    private String mDepartment;

    @ColumnInfo(name="score")
    private String mScore;

    public User() {
        mEmail = "";
        mCompany = "";
        mDepartment = "";
        mScore = "";
    }

    public User (String email, String company, String department, String score) {
        mEmail = email;
        mCompany = company;
        mDepartment = department;
        mScore = score;
    }



    public String getEmail() { return mEmail; }

    public void setEmail(String email) { mEmail = email;}

    public String getCompany() { return mCompany;}

    public void setCompany (String company) { mCompany = company;}

    public String getDepartment() { return mDepartment; }

    public void setDepartment (String department) { mDepartment = department; }

    public String getScore() { return mScore; }

    public void setScore (String score) { mScore = score; }

}
