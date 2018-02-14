package is.hi.gordon;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by brynj on 14/02/2018.
 */
@Entity
public class Questions {
    @PrimaryKey
    private int qid;

    @ColumnInfo(name = "questions")
    private String questions;

    @ColumnInfo(name = "always")
    private int always;

    @ColumnInfo(name = "usually")
    private int usually;

    @ColumnInfo(name = "sometimes")
    private int sometimes;

    @ColumnInfo(name = "rarely")
    private int rarely;

    @ColumnInfo(name = "never")
    private int never;

}
