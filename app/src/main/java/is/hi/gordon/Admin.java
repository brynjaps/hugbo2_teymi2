package is.hi.gordon;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by brynj on 03/04/2018.
 *
 * Módel klasi fyrir Admin. Hlutir geymdir í töflunni admin
 *
 */


@Entity(tableName="admin")
public class Admin {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Integer mId;
    @ColumnInfo(name = "username")
    private String mUsername;
    @ColumnInfo(name = "password")
    private String mPassword;


    public Admin() {
        mId = 0;
        mUsername = "";
        mPassword = "";

    }

    public Admin (String username, String password) {
        mUsername = username;
        mPassword = password;
    }

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {mId = id;}

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

}