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
    private static final String DATABASE_NAME = "highscorenew14.db"; //name of database
    public static final String TABLE_HIGHSCORE = "highscore"; //name of the table
    public static final String COLUMN_ID = "id"; //name of the column containing ID
    public static final String COLUMN_VALUES = "highscore_store"; //name of column containing the highscore
    //public static final String COLUMN_ANSWER = "answer"; //name of column containing the answer

    //just a constructor for the database

    // 11/9/15 Pujan Ban
    //changed the database here memeber definition here as recomended by the android lint
    public HighscoreDB(Context context, String name) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
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

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_HIGHSCORE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    //function to delete the previous high score
    public void deleteHighScore(int id){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_HIGHSCORE + " WHERE " + COLUMN_ID + "=\"" + id + "\";");
    }

    //get the high score
    public int getHighScore(){

        int result;

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HIGHSCORE + " WHERE 1";
        Cursor c = db.rawQuery(query, null);

        c.moveToLast();
        int colIndex = c.getColumnIndex(COLUMN_VALUES);
        result = c.getInt(colIndex);
        c.close();
        return result;
    }

    public String databaseToInt(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HIGHSCORE + " WHERE 1";

        Cursor c = db.rawQuery(query, null);

        c.moveToLast();
        int colIndex = c.getColumnIndex(COLUMN_VALUES);
        dbString += c.getString(colIndex);
        c.close();
        return dbString;
    }

    public String databaseToInt2(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_HIGHSCORE + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToLast();

        int colIndex = c.getColumnIndex(COLUMN_VALUES);

        dbString += c.getString(colIndex);
        c.close();
        db.close();
        return dbString;
    }
}
