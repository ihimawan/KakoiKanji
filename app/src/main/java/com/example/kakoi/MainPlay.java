package com.example.kakoi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/*
THIS IS THE MAIN PLAYING SCREEN UPDATE
 */

public class MainPlay extends AppCompatActivity {

    ArrayList<Integer> askedQuestions = new ArrayList<Integer>();


    MyDBHandler dbHandler;      //create the database for englishwords
    HighscoreDB dbHighScore;    //create the database for highscore
    TextView questionText;      //text that shows the question (english word)
    int n;                      //the position of the random question in the database
    ImageView feedbackImg;      //the image that shows if the answer was correct/wrong
    ImageView questionView;
    TextView highScoreDisp;     //the highscore display text
    int highScoreValueInt;      //the int value of the high score
    ImageView life3;        //The left heart that shows if there is a life or not
    ImageView life2;        //The middle heart that shows if there is a life or not
    ImageView life1;        //The right heart that shows if there is a life or not
    int livesCounterInt;    //is the number of lives the player has
    Button quitButton;
    Button answer1;
    Button answer2;
    Button answer3;
    Button answer4;
    Random rand;

    //to edit the kanji characters
    TextView kanjiChoice1;
    TextView kanjiChoice2;
    TextView kanjiChoice3;
    TextView kanjiChoice4;

    int nPrevious;

    int randomDistractor;

    int roundNumber;

    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //getting the elements to variables
        questionText = (TextView) findViewById(R.id.questionText);
        questionView = (ImageView) findViewById(R.id.questionImage);
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHighScore = new HighscoreDB(this, null, null, 1);
        quitButton = (Button) findViewById(R.id.quitButton);
        feedbackImg = (ImageView) findViewById(R.id.feedbackImg);
        life3 = (ImageView) findViewById(R.id.life3);
        life2 = (ImageView) findViewById(R.id.life2);
        life1 = (ImageView) findViewById(R.id.life1);
        livesCounterInt = 3;
        highScoreDisp = (TextView) findViewById(R.id.highScoreDisp);
        rand = new Random();

        kanjiChoice1 = (TextView) findViewById(R.id.kanjiChoice1);
        kanjiChoice2 = (TextView) findViewById(R.id.kanjiChoice2);
        kanjiChoice3 = (TextView) findViewById(R.id.kanjiChoice3);
        kanjiChoice4 = (TextView) findViewById(R.id.kanjiChoice4);

        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);

        roundNumber=1;
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);

        kanjiChoice2.setVisibility(View.GONE);
        kanjiChoice3.setVisibility(View.GONE);
        kanjiChoice4.setVisibility(View.GONE);

        setRound();

    }

    //player cannot move back to previous screen
    @Override
    public void onBackPressed() {
        return;
    }

    public void setRound(){
        setRandomQuestion();
        setAnswerChoice();
        roundNumber++;
    }

    public ArrayList<Integer> getDistractors(){

        ArrayList<Integer> distractors = new ArrayList<Integer>();

        for (int i=0; i<3; i++) {
            randomDistractor = rand.nextInt(askedQuestions.size() - 1);
            while(distractors.contains(askedQuestions.get(randomDistractor))) {
                randomDistractor = rand.nextInt(askedQuestions.size() - 1);
            }
            distractors.add(askedQuestions.get(randomDistractor));
        }

        return distractors;
    }

    public void setAnswerChoice(){

        Button[] answer = {answer1,answer2,answer3, answer4};
        TextView[] kanjiChoice = {kanjiChoice1,kanjiChoice2,kanjiChoice3, kanjiChoice4};

        if (roundNumber==1){
            answer1.setText(dbHandler.getAnswer(n));
            kanjiChoice1.setText(dbHandler.getKanji(n));

        }else if (roundNumber==2){

            answer2.setVisibility(View.VISIBLE);
            kanjiChoice2.setVisibility(View.VISIBLE);
            int randomPlacement = rand.nextInt(2); // Gives n such that 0 <= n < 2

            answer[randomPlacement].setText(dbHandler.getAnswer(n));
            kanjiChoice[randomPlacement].setText(dbHandler.getKanji(n));

            answer[(randomPlacement+1)%2].setText(dbHandler.getAnswer(askedQuestions.get(0)));
            kanjiChoice[(randomPlacement+1)%2].setText(dbHandler.getKanji(askedQuestions.get(0)));

        }else if (roundNumber==3){

            int[] answerPlacementArray = {0,1,2};

            answer3.setVisibility(View.VISIBLE);
            kanjiChoice3.setVisibility(View.VISIBLE);
            shuffleArray(answerPlacementArray);

            answer[answerPlacementArray[0]].setText(dbHandler.getAnswer(n));
            kanjiChoice[answerPlacementArray[0]].setText(dbHandler.getKanji(n));

            for (int i=1, j=0; i<3; i++, j++) {
                answer[answerPlacementArray[i]].setText(dbHandler.getAnswer(askedQuestions.get(j))); //EDIT: SOME PREVIOUS ANSWER
                kanjiChoice[answerPlacementArray[i]].setText(dbHandler.getKanji(askedQuestions.get(j))); //EDIT: SOME PREVIOUS ANSWER
            }

        }else{

            int[] answerPlacementArray = {0,1,2,3};

            answer4.setVisibility(View.VISIBLE);
            kanjiChoice4.setVisibility(View.VISIBLE);
            shuffleArray(answerPlacementArray);
            answer[answerPlacementArray[0]].setText(dbHandler.getAnswer(n));
            kanjiChoice[answerPlacementArray[0]].setText(dbHandler.getKanji(n));

            ArrayList<Integer> distractors = getDistractors();

            for (int i=1, j=0; i<4; i++, j++) {
                answer[answerPlacementArray[i]].setText(dbHandler.getAnswer(distractors.get(j))); //EDIT: SOME PREVIOUS ANSWER
                kanjiChoice[answerPlacementArray[i]].setText(dbHandler.getKanji(distractors.get(j))); //EDIT: SOME PREVIOUS ANSWER
            }

        }
    }

    // Implementing FisherñYates shuffle
    static void shuffleArray(int[] ar)
    {
        // If running on Java 6 or older, use `new Random()` on RHS here
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            // Simple swap
            int a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
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

        String buttonText = answer1.getText().toString(); //get the text of the button clicked
        isCorrectAnswer(n, buttonText); //checks if it is the correct answer
        setRound(); //get another random question
    }

    //function that runs if the second button is clicked.
    public void onClick2 (View view){

        String buttonText = answer2.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRound();
    }

    //function that runs if the third button is clicked.
    public void onClick3 (View view){

        String buttonText = answer3.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRound();
    }

    //function that runs if the fourth button is clicked.
    public void onClick4 (View view) {

        String buttonText = answer4.getText().toString();
        isCorrectAnswer(n, buttonText);
        setRound();
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

        } else {
            feedbackImg.setImageResource(R.drawable.incorrectsign);

            //set up button sounds
            final MediaPlayer incorrectButtonClick = MediaPlayer.create(this, R.raw.wrong);
            incorrectButtonClick.start();

            livesCounterInt = livesCounterInt - 1;

            //sets a hearts to grey when a life is lost
            if(livesCounterInt == 2) {
                life3.setImageResource(R.drawable.heartdie);
            }else if(livesCounterInt == 1){
                life2.setImageResource(R.drawable.heartdie);
            }else if(livesCounterInt == 0) {
                life1.setImageResource(R.drawable.heartdie);
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

        n = rand.nextInt(dbHandler.getProfilesCount());

        if (roundNumber<5) {
            while (askedQuestions.contains(n)){
                n = rand.nextInt(dbHandler.getProfilesCount());
            }
        }else{
            while (nPrevious==n){
                n = rand.nextInt(dbHandler.getProfilesCount());
            }
        }

        questionText.setText(dbHandler.getQuestion(n)); //get the question based on the random position
//        kanjiChoice1.setText(dbHandler.getKanji(n));
        questionView.setImageResource(dbHandler.getImage(n));

        if(!askedQuestions.contains(n)) {
            askedQuestions.add(n);
        }

        nPrevious=n;
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

