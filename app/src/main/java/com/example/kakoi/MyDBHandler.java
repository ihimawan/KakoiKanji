package com.example.kakoi;

/**
 * Created by IndriHimawan on 9/30/15. UPDATE
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.Random;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questionsnew2.db"; //name of database
    public static final String TABLE_QUESTIONS = "questions"; //name of the table
    public static final String COLUMN_ID = "id"; //name of the column containing ID
    public static final String COLUMN_ENGLISHWORD = "englishword"; //name of column containing the englishwords
    public static final String COLUMN_ANSWER = "answer"; //name of column containing the answer

    //just a constructor for the database
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    //create the database
    public void onCreate(SQLiteDatabase db) {
        //
        String query = "CREATE TABLE " + TABLE_QUESTIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ENGLISHWORD + " TEXT, " +
                COLUMN_ANSWER + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    //upgrading
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    //function to add question
    public void addQuestion(Questions question){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENGLISHWORD, question.get_englishword());
        values.put(COLUMN_ANSWER, question.get_answer());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_QUESTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }


    //function to delete question
    public void deleteQuestion(String englishWord){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_QUESTIONS + " WHERE " + COLUMN_ENGLISHWORD + "=\"" + englishWord + "\";");
    }

    //printing the english words in the database (DEBUGGING PURPOSES)
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        int position = 0;
        c.moveToPosition(position);

        while (position<4){
            if(c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD))!= null){
                String posString = Integer.toString(position);
                dbString += c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD));
                dbString += posString;
                dbString += "\n";
            }
            c.moveToPosition(++position);
        }

        db.close();
        return dbString;
    }

    //printing all the answers in the database (DEBUGGING PURPOSES)
    public String databaseToStringAnswer(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        int position = 0;
        c.moveToPosition(position);

        while (position<4){
            if(c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD))!= null){
                String posString = Integer.toString(position);
                dbString += c.getString(c.getColumnIndex(COLUMN_ANSWER));
                dbString += posString;
                dbString += "\n";
            }
            c.moveToPosition(++position);
        }

        db.close();
        return dbString;

    }

    //to check if it is correct answer
    public boolean isCorrectAnswer(int position, String answerChoice){
        boolean flag = false;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToPosition(position);

        if((c.getString(c.getColumnIndex(COLUMN_ANSWER))).equalsIgnoreCase(answerChoice)) {
            flag = true;
        }

        c.close();
        db.close();
        return flag;

    }

    //get the question given the position of the word.
    public String getQuestion (int position){

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);

        c.moveToPosition(position);

        return c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD));
    }

    //chooses a random row in the database and returns the random row number.
    public int generateRandomQuestion(){
        Random rand = new Random();
        int n = rand.nextInt(4); // Gives n such that 0 <= n < 4
        return n;
    }

}
