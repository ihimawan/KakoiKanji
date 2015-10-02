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
    String currentQuestion;


    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);
        myInput = (EditText) findViewById(R.id.myInput);
        randomText = (TextView) findViewById(R.id.randomText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        dummytext = (TextView) findViewById(R.id.dummytext);

//        addingQuestion("one", "ichi");
//        addingQuestion("two", "ni");
//        addingQuestion("three", "san");
//        addingQuestion("four", "yong");

        currentQuestion = generateRandomQuestion();

//        addingQuestion("five", "go");
//        addingQuestion("six", "roku");
//        addingQuestion("seven", "nana");
//        addingQuestion("eight", "hachi");
//        addingQuestion("nine", "kyuu");
//        addingQuestion("ten", "jyuu");

        printDatabaseAnswer();
    }

    //function that adds items to the database
//    public void addButtonClicked(View view){
//        Questions question = new Questions(myInput.getText().toString());
//        dbHandler.addQuestion(question);
//        printDatabase();
//    }

    public void onClick1 (View view){
        Button answer1 = (Button) findViewById(R.id.answer1);
        String buttonText = answer1.getText().toString();
        dummytext = (TextView) findViewById(R.id.dummytext);
        if ((dbHandler.isCorrectAnswer(currentQuestion, buttonText))==true){
            dummytext.setText("Correct!");
        }else{
            dummytext.setText("Wrong!");
        }

        currentQuestion = generateRandomQuestion();
    }

    public void onClick2 (View view){
        Button answer2 = (Button) findViewById(R.id.answer2);
        String buttonText = answer2.getText().toString();
        dummytext = (TextView) findViewById(R.id.dummytext);
        if ((dbHandler.isCorrectAnswer(currentQuestion, buttonText))==true){
            dummytext.setText("Correct!");
        }else{
            dummytext.setText("Wrong!");
        }

        currentQuestion = generateRandomQuestion();
    }

    public void onClick3 (View view){
        Button answer3 = (Button) findViewById(R.id.answer3);
        String buttonText = answer3.getText().toString();
        dummytext = (TextView) findViewById(R.id.dummytext);
        if ((dbHandler.isCorrectAnswer(currentQuestion, buttonText))==true){
            dummytext.setText("Correct!");
        }else{
            dummytext.setText("Wrong!");
        }

        currentQuestion = generateRandomQuestion();
    }

    public void onClick4 (View view){
        Button answer4 = (Button) findViewById(R.id.answer4);
        String buttonText = answer4.getText().toString();
        dummytext = (TextView) findViewById(R.id.dummytext);
        if ((dbHandler.isCorrectAnswer(currentQuestion, buttonText))==true){
            dummytext.setText("Correct!");
        }else{
            dummytext.setText("Wrong!");
        }

        currentQuestion = generateRandomQuestion();
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

    public String generateRandomQuestion(){
        String dbString = dbHandler.generateRandomQuestion();
        randomText.setText(dbString);
        return dbString;
    }

    //function that prints the contents of the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        dummytext.setText(dbString);
        //dummytext.setText("");
    }

    public void printDatabaseAnswer(){
        String dbString = dbHandler.databaseToStringAnswer();
        //myText.setText(dbString);
        //myInput.setText("");
        dummytext.setText(dbString);
    }


}

