package com.example.kakoi;

/**
 * Created by IndriHimawan on 9/30/15.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

import java.util.Random;

/* this is the class where all the database operations happen
TODO: NEED TO WORK ON HOW TO DO THE HIGHSCORE THING.
 */
public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questions.db"; //name of database
    public static final String TABLE_QUESTIONS = "questions"; //name of the table
    public static final String COLUMN_ID = "id"; //name of the column containing ID
    public static final String COLUMN_ENGLISHWORD = "englishword"; //name of column containing the englishwords

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
                COLUMN_ENGLISHWORD + " TEXT " +
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
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    //function to delete question
    public void deleteQuestion(String englishWord){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_QUESTIONS + " WHERE " + COLUMN_ENGLISHWORD + "=\"" + englishWord + "\";");
    }

    //printing the strings in the database
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        int position = 0;
        c.moveToPosition(position);

        while (!c.isAfterLast()){
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



    //to check if it is correct answer
//    public boolean isCorrectAnswer(String question, String answerChoice){
//        boolean flag = true;
//        String dbString = "";
//        SQLiteDatabase db = getWritableDatabase();
//        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";
//
//        Cursor c = db.rawQuery(query, null);
//        c.moveToFirst();
//
//        while (!c.isAfterLast()){
//            if((c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD))).equals(question)){
//                flag = (c.getString(c.getColumnIndex(COLUMN_ANSWER))).equals(answerChoice);
//                break;
//            }
//            c.moveToNext();
//        }
//
//        db.close();
//        return flag;
//
//    }

//    public Cursor getRandomDataItemFromDb(String tableName, int limit) {
//        SQLiteDatabase db = getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName+ " ORDER BY RANDOM() LIMIT " + limit, null);
//        if (cursor.moveToFirst()) {
//            return cursor;
//        }
//        return cursor;
//    }

    //chooses a random row in the database and displays the question

    public String generateRandomQuestion(){

        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";
        String query2 = "SELECT * FROM " + TABLE_QUESTIONS + " ORDER BY RANDOM() LIMIT 1";


        Cursor c = db.rawQuery(query, null);

        Random rand = new Random();
        int n = 30 + rand.nextInt(10); // Gives n such that 0 <= n < 20

        c.moveToPosition(n);

        dbString += c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD));

        db.close();
        return dbString;

    }

}
