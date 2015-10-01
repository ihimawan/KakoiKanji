package com.example.kakoi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/*
THIS IS THE MAIN PLAYING SCREEN
 */

public class MainPlay extends AppCompatActivity {

    EditText myInput;
    TextView myText;
    MyDBHandler dbHandler;
    TextView randomText;

    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);
        myInput = (EditText) findViewById(R.id.myInput);
        myText = (TextView) findViewById(R.id.myText);
        randomText = (TextView) findViewById(R.id.randomText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();
    }



    //function that adds items to the database
    public void addButtonClicked(View view){
        Questions question = new Questions(myInput.getText().toString());
        dbHandler.addQuestion(question);
        printDatabase();
    }

    public void addingQuestion(String englishWord){
        Questions question = new Questions(englishWord);
        dbHandler.addQuestion(question);
    }

    //function that deletes items from the database
    public void deleteButtonClicked(View view){
        String inputText = myInput.getText().toString();
        dbHandler.deleteQuestion(inputText);
        printDatabase();
    }

    public void randomButtonClicked(View view){
        String dbString = dbHandler.generateRandomQuestion();
        randomText.setText(dbString);
    }

    //function that prints the contents of the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        myText.setText(dbString);
        myInput.setText("");
    }


}

