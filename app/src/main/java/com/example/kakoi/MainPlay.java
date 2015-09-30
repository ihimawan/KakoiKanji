package com.example.kakoi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

public class MainPlay extends AppCompatActivity {

    TextView databaseContent;
    MyDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);
        databaseContent = (TextView) findViewById(R.id.databaseContent);
        dbHandler = new MyDBHandler(this, null, null, 1);
        addQuestion("Chicken");
        addQuestion("Whale");
        addQuestion("Wolf");
        addQuestion("Someanimal");
        printDatabase();
    }

    //Add a product to the database
    public void addQuestion(String englishWord){
        Questions question = new Questions(englishWord);
        dbHandler.addQuestion(question);
//        printDatabase();
    }

    //Delete method
    public void deleteQuestion(String englishWord){
        dbHandler.deleteQuestion(englishWord);
        //        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        databaseContent.setText(dbString);
    }

}
