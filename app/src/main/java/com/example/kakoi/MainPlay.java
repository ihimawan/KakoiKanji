package com.example.kakoi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/*
THIS IS THE MAIN PLAYING SCREEN
 */

public class MainPlay extends AppCompatActivity {

    MyDBHandler dbHandler;      //create the database for englishwords
    HighscoreDB dbHighScore;    //create the database for highscore
    TextView questionText;      //text that shows the question (english word)
    int n;                      //the position of the random question in the database
    ImageView feedbackImg;      //the image that shows if the answer was correct/wrong
    TextView highScoreDisp;     //the highscore display text
    TextView livesCounter;      //the life counter display text
    int highScoreValueInt;      //the int value of the high score
    Button quitButton;          //the quit button

    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);

        //getting the elements to variables
        questionText = (TextView) findViewById(R.id.questionText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHighScore = new HighscoreDB(this, null, null, 1);
        quitButton = (Button) findViewById(R.id.quitButton);
        feedbackImg = (ImageView) findViewById(R.id.feedbackImg);
        livesCounter = (TextView) findViewById(R.id.livesCounter);
        highScoreDisp = (TextView) findViewById(R.id.highScoreDisp);

        //starts by getting a random english word from the database
        setRandomQuestion();
    }

    //player cannot move back to previous screen
    @Override
    public void onBackPressed() {
        return;
    }

    //if quit button is clicked
    public void quitButtonClicked(final View view){
        final MediaPlayer goButtonClicked = MediaPlayer.create(this, R.raw.go);
        goButtonClicked.start();

        //then show an alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to quit?")
                .setCancelable(false)
                .setPositiveButton("Yes, I give up!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        gameEnds(); //then run the game end function
                    }
                })
                .setNegativeButton("Nevermind.", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goButtonClicked.start();
                        dialog.cancel(); //then do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    //function that runs if the first button is clicked.
    public void onClick1 (View view){
        Button answer1 = (Button) findViewById(R.id.answer1);
        String buttonText = answer1.getText().toString(); //get the text of the button clicked
        isCorrectAnswer(n, buttonText); //checks if it is the correct answer
        setRandomQuestion(); //get another random question
    }

    //function that runs if the second button is clicked.
    public void onClick2 (View view){
        Button answer2 = (Button) findViewById(R.id.answer2);
        String buttonText = answer2.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRandomQuestion();
    }

    //function that runs if the third button is clicked.
    public void onClick3 (View view){
        Button answer3 = (Button) findViewById(R.id.answer3);
        String buttonText = answer3.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRandomQuestion();
    }

    //function that runs if the fourth button is clicked.
    public void onClick4 (View view) {
        Button answer4 = (Button) findViewById(R.id.answer4);
        String buttonText = answer4.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRandomQuestion();
    }

    //checks if correct answer
    public void isCorrectAnswer(int n, String answerChoice){
        if ((dbHandler.isCorrectAnswer(n, answerChoice))){

            //display "correct" image
            feedbackImg.setImageResource(R.drawable.correctsign);

            //set up button sounds
            final MediaPlayer correctButtonClick = MediaPlayer.create(this, R.raw.correct);
            correctButtonClick.start();

            //high score incrementation below
            String highScoreValueStr = highScoreDisp.getText().toString();
            highScoreValueInt = Integer.valueOf(highScoreValueStr);
            highScoreValueInt += 5;
            highScoreValueStr = Integer.toString(highScoreValueInt);
            highScoreDisp.setText(highScoreValueStr);

        }else{

            //display "incorrect" image
            feedbackImg.setImageResource(R.drawable.incorrectsign);

            //set up button sounds
            final MediaPlayer incorrectButtonClick = MediaPlayer.create(this, R.raw.wrong);
            incorrectButtonClick.start();

            //life counter decrementation below
            String livesCounterStr = livesCounter.getText().toString();
            int livesCounterInt = Integer.valueOf(livesCounterStr);
            livesCounterInt -= 1;
            livesCounterStr = Integer.toString(livesCounterInt);
            livesCounter.setText(livesCounterStr);

            //if life reaches zero, game will end
            if(livesCounterInt==0){
                gameEnds();
            }
        }
    }

    //things that happen when the game ends
    public void gameEnds(){
        Intent i = new Intent(this, HighScore.class); //create intent
        int hsMessage = -1; //hsMessage will contain the location of the "new high score" image, which will appear on the high score screen,
        //the integer -1 denotes that the image does not need to be displayed (if the player did not reach high score)

        //if the player beats the highscore
        if (dbHighScore.getHighScore()<highScoreValueInt) {
            //winning sound creation
            final MediaPlayer winButtonClicked = MediaPlayer.create(this, R.raw.win);
            winButtonClicked.start();

            addingHighscore(highScoreValueInt); //then add the higher highscore to the database
            hsMessage = R.drawable.newscore; //give hsMessage the location of the new high score image
        }else{
            //losing sound creation
            final MediaPlayer loseButtonClicked = MediaPlayer.create(this, R.raw.lose);
            loseButtonClicked.start();
        }

        i.putExtra("newhighscoremessage", hsMessage); //pass hsMessage to the next intent

        final TextView highScoreDisp = (TextView) findViewById(R.id.highScoreDisp); //send the latest high score to next intent
        String userMessage = highScoreDisp.getText().toString();

        i.putExtra("highscoredisp", userMessage); //extra information, using userMessage as the reference
        startActivity(i); // to call the intent

    }

    //adds the new high score to the database
    public void addingHighscore(int highScoreValue){
        HighScoreItem highScoreItem = new HighScoreItem(highScoreValue);
        dbHighScore.addHighScore(highScoreItem);
    }

    //this function changes the question text to a random question in the database
    public void setRandomQuestion (){
        n = dbHandler.generateRandomQuestion(); //get the random position
        questionText.setText(dbHandler.getQuestion(n)); //get the question based on the random position
    }

    /*
    BELOW ARE FUNCTIONS FOR BACK-END/DEBUGGING PURPOSES
     */

////    function that adds items to the database (when add button is clicked)
//    public void addButtonClicked(View view){
//        Questions question = new Questions(myInput.getText().toString());
//        dbHandler.addQuestion(question);
//        printDatabase();
//    }

    //function that adds question to the database (add button is not clicked)
//    public void addingQuestion(String englishWord, String answer){
//        Questions question = new Questions(englishWord, answer);
//        dbHandler.addQuestion(question);
//
//    }

    //function that deletes items from the database
//    public void deleteButtonClicked(View view){
//        //String inputText = myInput.getText().toString();
//        //dbHandler.deleteQuestion(inputText);
//        printDatabase();
//    }

    //random button to generate random question
//    public void randomButtonClicked(View view){
//        setRandomQuestion();
//    }

    //function that prints the questions in the database (for debugging purposes)
//    public void printDatabase(){
//        String dbString = dbHandler.databaseToString();
//        feedbackText.setText(dbString);
//    }

    //function that prints the answers in the database (for debugging purposes)
//    public void printDatabaseAnswer(){
//        String dbString = dbHandler.databaseToStringAnswer();
//        feedbackText.setText(dbString);
//    }

}

