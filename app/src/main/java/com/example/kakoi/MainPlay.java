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

    EditText myInput; //not used now.

    TextView feedbackText; //text that shows if the answer chosen is Correct or Wrong
    MyDBHandler dbHandler; //create the database
    TextView questionText; 
    String currentQuestion;
    int n;


    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);

        myInput = (EditText) findViewById(R.id.myInput); //not used now.

        questionText = (TextView) findViewById(R.id.questionText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        feedbackText = (TextView) findViewById(R.id.feedbackText);

//        addingQuestion("one", "ichi");
//        addingQuestion("two", "ni");
//        addingQuestion("three", "san");
//        addingQuestion("four", "yong");

//        addingQuestion("five", "go");
//        addingQuestion("six", "roku");
//        addingQuestion("seven", "nana");
//        addingQuestion("eight", "hachi");
//        addingQuestion("nine", "kyuu");
//        addingQuestion("ten", "jyuu");

        setRandomQuestion();

        //printDatabaseAnswer();
    }

    //function that adds items to the database
//    public void addButtonClicked(View view){
//        Questions question = new Questions(myInput.getText().toString());
//        dbHandler.addQuestion(question);
//        printDatabase();
//    }

    //function that runs if the first button is clicked.
    public void onClick1 (View view){
        Button answer1 = (Button) findViewById(R.id.answer1);
        String buttonText = answer1.getText().toString();
        feedbackText = (TextView) findViewById(R.id.feedbackText);
        if ((dbHandler.isCorrectAnswer(n, buttonText))){
            feedbackText.setText("Correct!");
        }else{
            feedbackText.setText("Wrong!");
        }

        setRandomQuestion();
    }

    public void onClick2 (View view){
        Button answer2 = (Button) findViewById(R.id.answer2);
        String buttonText = answer2.getText().toString();
        feedbackText = (TextView) findViewById(R.id.feedbackText);

        if ((dbHandler.isCorrectAnswer(n, buttonText))){
            feedbackText.setText("Correct!");
        }else{
            feedbackText.setText("Wrong!");
        }

        setRandomQuestion();
    }

    public void onClick3 (View view){
        Button answer3 = (Button) findViewById(R.id.answer3);
        String buttonText = answer3.getText().toString();
        feedbackText = (TextView) findViewById(R.id.feedbackText);
        if ((dbHandler.isCorrectAnswer(n, buttonText))){
            feedbackText.setText("Correct!");
        }else{
            feedbackText.setText("Wrong!");
        }

        setRandomQuestion();
    }

    public void onClick4 (View view){
        Button answer4 = (Button) findViewById(R.id.answer4);
        String buttonText = answer4.getText().toString();
        feedbackText = (TextView) findViewById(R.id.feedbackText);
        if ((dbHandler.isCorrectAnswer(n, buttonText))){
            feedbackText.setText("Correct!");
        }else{
            feedbackText.setText("Wrong!");
        }

        setRandomQuestion();
    }

    //function that adds question
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

    //random button to generate random question
    public void randomButtonClicked(View view){
        setRandomQuestion();
    }

    public void setRandomQuestion (){
        n = dbHandler.generateRandomQuestion();
        currentQuestion = dbHandler.getQuestion(n);
        questionText.setText(currentQuestion);
    }

    public String generateRandomQuestion(){
        n = dbHandler.generateRandomQuestion();
        String dbString = dbHandler.getQuestion(n);
        questionText.setText(dbString);
        return dbString;
    }

    //function that prints the contents of the database
    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        feedbackText.setText(dbString);
        //feedbackText.setText("");
    }

    public void printDatabaseAnswer(){
        String dbString = dbHandler.databaseToStringAnswer();
        //myText.setText(dbString);
        //myInput.setText("");
        feedbackText.setText(dbString);
    }


}

