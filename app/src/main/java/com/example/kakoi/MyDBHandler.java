package com.example.kakoi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by IndriHimawan on 9/29/15.
 */

public class MyDBHandler extends SQLiteOpenHelper{
//class to specifically work on that database, responsible to do anything with the database.

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "questions.db"; //saving the users information on this file
    public static final String TABLE_PRODUCTS = "questions";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_ENGLISHWORD = "_englishWord";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        //need to pass information along to the superclass
        super(context, name, factory, version);
        //name = name of database (to know which file to save it under)
        //version = database version
    }

    @Override
    public void onCreate(SQLiteDatabase db) { //creates new table
        String query = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
                COLUMN_ENGLISHWORD + " TEXT " +
                ");";
        db.execSQL(query); //executing queries.
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //if u ever upgrade ur version, it's gonna call this.
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_PRODUCTS);
        onCreate(db);
    }

    //Add a new row to the database (like add an item)
    public void addQuestion(Questions product){
        ContentValues values = new ContentValues();
        values.put(COLUMN_ENGLISHWORD, product.get_englishWord()); //put englishWord to the ENGLISHWORD column.
        SQLiteDatabase db = getWritableDatabase(); //db := the database we're gonna write to.
        db.insert(TABLE_PRODUCTS, null, values);
        db.close(); //say to android we're done with it and they can have the memory back
    }

    //Delete product from database
    public void deleteQuestion(String englishWord){
        SQLiteDatabase db = getWritableDatabase(); //db := the database we're gonna write to.
        db.execSQL("DELETE FROM +" + TABLE_PRODUCTS + " WHERE " + COLUMN_ENGLISHWORD + "=\"" + englishWord + "\"");
    }

    //Print out the database as a string.
    public String databaseToString(){
        String dbString = "";
        SQLiteDatabase db = getWritableDatabase(); //db := the database we're gonna write to.
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1 ";

        //Cursor is gonna point to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        while(!c.isAfterLast()){
            if (c.getString(c.getColumnIndex("englishWord"))!= null){
                dbString += c.getString(c.getColumnIndex("englishWord"));
                dbString += "\n";
            }
        }

        db.close();
        return dbString;
    }

}
