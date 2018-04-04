package is.hi.gordon;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.ArrayList;

/**
 * Created by brynj on 08/03/2018.
 *
 * A class that creates functions to be used for the database and sets basic things for the database
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //path to database
    String DB_PATH = null;

    //database name
    private static final String DB_NAME = "gordondb";

    private SQLiteDatabase gordonDB;
    private final Context myContext;

    public DatabaseHelper (Context context) {
        super(context, DB_NAME, null, 2);
        this.myContext = context;
        this.DB_PATH = "/data/data/" + context.getPackageName() + "/" + "databases/";
        Log.e("Path 1", DB_PATH);
    }

    //creates database by copying database if database does not exist yet
    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist) {
        }
        else {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException e) {
                throw new Error("Error copying database");
            }
        }
    }

    //checks if database is there or not, returns true if it is, else false
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
        } catch (SQLiteException e) {
        }
        if (checkDB != null) {
            checkDB.close();
        }
        return checkDB != null ? true : false;
    }

    //copies database from file in the folder assets
    private void copyDataBase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        String outFileName = DB_PATH + DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[10];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    //opens database from given database path with given database name
    public void openDataBase() throws SQLException {
        String myPath = DB_PATH + DB_NAME;
        gordonDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
    }

    //closes database
    @Override
    public synchronized void close() {
        if (gordonDB != null)
            gordonDB.close();
        super.close();
    }

    @Override
    public void onCreate (SQLiteDatabase db) {
    }

    //upgrades database
    @Override
    public void onUpgrade (SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion)
            try {
                copyDataBase();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    //add a new question
    public void addQuest(Question quest) {
        //virkni kemur seinna
    }

    //get all questions
    public List<Question> getAllQuest () {
        List<Question> questList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        //select all from table
        Cursor cursor = db.query("questions", null, null, null, null, null, null);

        //looping through all the rows and adding to questList
        if(cursor.moveToFirst()) {
            do {
                Question quest = new Question();
                quest.setId(cursor.getInt(0));
                quest.setQuestTitle(cursor.getString(1));
                quest.setAlways(cursor.getInt(2));
                quest.setUsually(cursor.getInt(3));
                quest.setSometimes(cursor.getInt(4));
                quest.setRarely(cursor.getInt(5));
                quest.setNever(cursor.getInt(6));
                questList.add(quest);
            } while (cursor.moveToNext());
        }
        return questList;
    }

    //get score of users answer
    public int usersScore(String answer, int id) {

        //query to get the database object from the answer, given parameter, column in the questions
        // table with an id that equals the id given
        String query = "SELECT " + answer + " FROM questions WHERE  _id = " + id;

        //get a readable database
        SQLiteDatabase db = this.getReadableDatabase();

        //create a cursor to handle the raw database objects
        Cursor cursor = db.rawQuery(query, null);

        //take database objects from the cursor and put into an array
        cursor.moveToFirst();
        ArrayList<String> answerScore = new ArrayList<String>();
        while(!cursor.isAfterLast()) {
            answerScore.add(cursor.getString(cursor.getColumnIndex(answer)));
            cursor.moveToNext();
        }
        cursor.close();
        String [] answerArray = answerScore.toArray(new String[answerScore.size()]);

        //creates integer array
        int[] intArray = new int[answerArray.length];

        //parses the integer for each string
        for(int i = 0; i < intArray.length; i++) {
            intArray[i] = Integer.parseInt(answerArray[i]);
        }

        //int we want to have the answer in
        int intAnswer = 0;

        //takes all the integers in the array and sums them up in one variable
        for(int i = 0; i < intArray.length; i++) {
            intAnswer += intArray[i];
        }

        return intAnswer;
    }
}
