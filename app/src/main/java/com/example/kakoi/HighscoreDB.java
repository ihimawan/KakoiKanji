package com.example.kakoi;

/**
 * Created by IndriHimawan on 9/30/15
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class HighscoreDB extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "highscorenew10.db"; //name of database
    public static final String TABLE_HIGHSCORE = "highscore"; //name of the table
    public static final String COLUMN_ID = "id"; //name of the column containing ID
    public static final String COLUMN_VALUES = "highscore_store"; //name of column containing the highscore

    //just a constructor for the database
    public HighscoreDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    //create the database
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_HIGHSCORE + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_VALUES + " INTEGER " +
                ");";
        db.execSQL(query);
    }

    @Override
    //upgrading
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HIGHSCORE);
        onCreate(db);
    }

    //function to add the high score
    public void addHighScore(HighScoreItem highScore){
        ContentValues values = new ContentValues();
        values.put(COLUMN_VALUES, highScore.get_values());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_HIGHSCORE, null, values);
        db.close();
    }

    //to count how many rows there are in the database
    public int getProfilesCount() {

        //setting up the query to select all from the database
        String countQuery = "SELECT  * FROM " + TABLE_HIGHSCORE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        //return the number of rows in database
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //get the high score
    public int getHighScore(){

        int result;

        //setting up the query to select all from the database
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HIGHSCORE + " WHERE 1";
        Cursor c = db.rawQuery(query, null);

        //get the last item in the high score database, because that would be the highest score.
        c.moveToLast();
        int colIndex = c.getColumnIndex(COLUMN_VALUES);
        result = c.getInt(colIndex);

        //return the highest score
        return result;
    }

    //displays the high score as a String, instead of a number.
    public String databaseToInt(){
        String dbString = "";

        //setting up the query to select all from the database
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HIGHSCORE + " WHERE 1";
        Cursor c = db.rawQuery(query, null);

        //get the last element in the row, because that would be the highscore.
        c.moveToLast();
        int colIndex = c.getColumnIndex(COLUMN_VALUES);
        dbString += c.getString(colIndex);

        //return highscore as string
        return dbString;
    }

    //function to delete the previous high score (BACKEND PURPOSES)
    public void deleteHighScore(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HIGHSCORE + " WHERE " + COLUMN_ID + "=\"" + id + "\";");
    }

}
