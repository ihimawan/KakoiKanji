package com.example.kakoi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/*
THIS IS THE MAIN PLAYING SCREEN
 */

public class MainPlay extends AppCompatActivity {

    EditText myInput;
    TextView dummytext;
    MyDBHandler dbHandler;
    TextView randomText;


    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);
        myInput = (EditText) findViewById(R.id.myInput);
        //myText = (TextView) findViewById(R.id.myText);
        randomText = (TextView) findViewById(R.id.randomText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        addingQuestion("one", "ichi");
        addingQuestion("two", "ni");
        addingQuestion("three", "san");
        addingQuestion("four", "yong");

        generateRandom();



//        addingQuestion("five", "go");
//        addingQuestion("six", "roku");
//        addingQuestion("seven", "nana");
//        addingQuestion("eight", "hachi");
//        addingQuestion("nine", "kyuu");
//        addingQuestion("ten", "jyuu");
        //printDatabase();
    }

    //function that adds items to the database
//    public void addButtonClicked(View view){
//        Questions question = new Questions(myInput.getText().toString());
//        dbHandler.addQuestion(question);
//        printDatabase();
//    }

    public void onClick1(View view){
        Button answer1 = (Button) findViewById(R.id.answer1);
        String buttonText = answer1.getText().toString();
        TextView dummytext = (TextView) findViewById(R.id.dummytext);

    }

    public void addingQuestion(String englishWord, String answer){
        Questions question = new Questions(englishWord, answer);
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

    public void generateRandomQuestion(){
        String dbString = dbHandler.generateRandomQuestion();
        randomText.setText(dbString);
    }

    //function that prints the contents of the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        //myText.setText(dbString);
        myInput.setText("");
    }


}

