package com.example.kakoi;

/**
 * Created by IndriHimawan on 9/30/15.
 */

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import android.content.Context;
import android.content.ContentValues;

public class MyDBHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questions.db";
    public static final String TABLE_QUESTIONS = "questions"; //name of the table
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ENGLISHWORD = "englishword";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_QUESTIONS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_ENGLISHWORD + " TEXT " +
                ");";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTIONS);
        onCreate(db);
    }

    public void addQuestion(Questions question){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENGLISHWORD, question.get_englishword());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_QUESTIONS, null, values);
        db.close();
    }

    public void deleteQuestion(String englishWord){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_QUESTIONS + " WHERE " + COLUMN_ENGLISHWORD + "=\"" + englishWord + "\";");
    }

    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_QUESTIONS + " WHERE 1";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        while (!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD))!= null){
                dbString += c.getString(c.getColumnIndex(COLUMN_ENGLISHWORD));
                dbString += "\n";

            }
            c.moveToNext();
        }

        db.close();
        return dbString;

    }

}
