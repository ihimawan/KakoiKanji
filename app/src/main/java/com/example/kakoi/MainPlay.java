package com.example.kakoi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import java.util.logging.LogRecord;

/*
THIS IS THE MAIN PLAYING SCREEN UPDATE
 */

public class MainPlay extends AppCompatActivity {

    final Handler handler = new Handler();

    //For Random Question Generation & basic gameplay
    MyDBHandler dbHandler;  //The database that contains the english word, kanji pronounciation, kanji character, and image, all questions will be pulled from this database
    ArrayList<Integer> askedQuestions = new ArrayList<Integer>(); //the askedQuestions ArrayList keeps the question that have already been asked previously
    TextView questionText;      //text that shows the question (english word)
    ImageView questionView;     //the image representation of the question
    ImageView feedbackImg;      //the image that shows if the answer was correct/wrong
    int n;                      //the position of the random question in the database
    int nPrevious;          //keeps the id of the previously generated question, so that no same question appear twice in a row
    int roundNumber;        //tracking the round number because in round 1, there's only 1 answer choice, round 2 has 2 choices, etc.

    //kanji Pronounciation
    int kanjiSwitch;

    //HighScore related
    HighscoreDB dbHighScore;    //The database that actually stores the highscore
    int highScoreValueInt;      //the int value of the high score, for backend purposes
    TextView highScoreDisp;     //the highscore display text, for UI purposes

    //Life-counter related
    ImageView life3;        //The left heart that shows if there is a life or not, UI
    ImageView life2;        //The middle heart that shows if there is a life or not, UI
    ImageView life1;        //The right heart that shows if there is a life or not, UI
    int livesCounterInt;    //is the number of lives the player has, backend purposes

    //The four buttons on the screen
    Button answer1;     //the button on the top left
    Button answer2;     //the button on the top right
    Button answer3;     //the button on the bottom left
    Button answer4;     //the button on the bottom right
    ArrayList<Button> buttonArray = new ArrayList<Button>();

    //The kanji characters, answer'n' refers to the buttons mentioned above
    TextView kanjiChoice1;  //kanji character on answer1
    TextView kanjiChoice2;  //kanji character on answer2
    TextView kanjiChoice3;  //kanji character on answer3
    TextView kanjiChoice4;  //kanji character on answer4

    //Miscellaneous
    Button quitButton;      //The quit button
    Random rand;            //a random object to generate random numbers later
    int whereCorrectAnswer=1; //the Button number that contains the correct answer.
    int chosenAnswer = 1;

    int randomDistractor;

    //initial function
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        /*
        The assignments below are to get the UI elements to variables, and for initializations
         */

        //For Random Question Generation & basic gameplay
        dbHandler = new MyDBHandler(this, null, null, 1); //database initialization
        questionText = (TextView) findViewById(R.id.questionText);
        questionView = (ImageView) findViewById(R.id.questionImage);
        feedbackImg = (ImageView) findViewById(R.id.feedbackImg);

        //highScore related
        dbHighScore = new HighscoreDB(this, null); //highscore database initialization
        highScoreDisp = (TextView) findViewById(R.id.highScoreDisp);

        //life-related
        livesCounterInt = 3; //initialize life to 3
        life3 = (ImageView) findViewById(R.id.life3);
        life2 = (ImageView) findViewById(R.id.life2);
        life1 = (ImageView) findViewById(R.id.life1);

        //buttons
        answer1 = (Button) findViewById(R.id.answer1);
        answer2 = (Button) findViewById(R.id.answer2);
        answer3 = (Button) findViewById(R.id.answer3);
        answer4 = (Button) findViewById(R.id.answer4);
        buttonArray.add(answer1);
        buttonArray.add(answer2);
        buttonArray.add(answer3);
        buttonArray.add(answer4);

        //kanji characters
        kanjiChoice1 = (TextView) findViewById(R.id.kanjiChoice1);
        kanjiChoice2 = (TextView) findViewById(R.id.kanjiChoice2);
        kanjiChoice3 = (TextView) findViewById(R.id.kanjiChoice3);
        kanjiChoice4 = (TextView) findViewById(R.id.kanjiChoice4);

        //misc
        quitButton = (Button) findViewById(R.id.quitButton); //quit button
        roundNumber=1; //the round number initializes to 1

        //in the first round, 3 of the answer choices are not there
        answer2.setVisibility(View.GONE);
        answer3.setVisibility(View.GONE);
        answer4.setVisibility(View.GONE);
        kanjiChoice2.setVisibility(View.GONE);
        kanjiChoice3.setVisibility(View.GONE);
        kanjiChoice4.setVisibility(View.GONE);

        //for Kanji Pronounciation
        Bundle extras = getIntent().getExtras();
        kanjiSwitch = extras.getInt("isswitchon");

        if (kanjiSwitch==0){ //if user does not want to see the pronounciation, make them transparent
            answer1.setTextColor(Color.TRANSPARENT);
            answer2.setTextColor(Color.TRANSPARENT);
            answer3.setTextColor(Color.TRANSPARENT);
            answer4.setTextColor(Color.TRANSPARENT);
        }

        rand = new Random(); //creating random object to generate random numbers later
        setRound(); //starts the function that sets the whole round

    }

    //player cannot move back to previous screen
    @Override
    public void onBackPressed() {
        return;
    }

    //function that sets the round
    public void setRound(){

        //restore the colors of the buttons
        buttonArray.get(whereCorrectAnswer-1).setBackgroundResource(R.drawable.answerbutton);
        buttonArray.get(chosenAnswer-1).setBackgroundResource(R.drawable.answerbutton);

        setRandomQuestion();    //sets the question
        setAnswerChoice();      //sets the answer choices
        roundNumber++;          //increment round
    }

    //obtain distractors (incorrect answers) from the askedQuestions array list
    public ArrayList<Integer> getDistractors(){

        //the distractors arrayList
        ArrayList<Integer> distractors = new ArrayList<Integer>();

        //get 3 distractors
        for (int i=0; i<3; i++) {

            //randomDistractor contains the index of an incorrect answer in the askedQuestions array list
            randomDistractor = rand.nextInt(askedQuestions.size()); //choose an index randomly

            while(distractors.contains(askedQuestions.get(randomDistractor))||
                    askedQuestions.get(randomDistractor) == n) {

                randomDistractor = rand.nextInt(askedQuestions.size()); //keep looking for a different distractor

            }
            distractors.add(askedQuestions.get(randomDistractor)); //add that distractor int he distractor arrayList
        }

        //return the arrayList with the distractors
        return distractors;
    }

    //getting the correct answer and distractor to be put as the answer choices.
    public void setAnswerChoice(){

        //array that contains all the buttons
        Button[] answer = {answer1,answer2,answer3, answer4};

        //array that contains all the kanji characters on the buttons
        TextView[] kanjiChoice = {kanjiChoice1,kanjiChoice2,kanjiChoice3, kanjiChoice4};

        //if it's the first round, there's no need for a distractor
        if (roundNumber==1){
            answer1.setText(dbHandler.getAnswer(n));
            whereCorrectAnswer = 1;
            kanjiChoice1.setText(dbHandler.getKanji(n));

        }else if (roundNumber==2){ //if it's the second round, randomly place the correct answer between the two answer choices.

            //make the second answer choice to appear
            answer2.setVisibility(View.VISIBLE);
            kanjiChoice2.setVisibility(View.VISIBLE);

            //obtain a button placement randomly to put the correct answer
            int randomPlacement = rand.nextInt(2); // Gives n such that 0 <= n < 2

            //place the correct answer at that button placement
            answer[randomPlacement].setText(dbHandler.getAnswer(n));
            kanjiChoice[randomPlacement].setText(dbHandler.getKanji(n));
            whereCorrectAnswer = randomPlacement+1;

            //set the first asked question to the other button placement
            answer[(randomPlacement+1)%2].setText(dbHandler.getAnswer(askedQuestions.get(0)));
            kanjiChoice[(randomPlacement+1)%2].setText(dbHandler.getKanji(askedQuestions.get(0)));

        }else if (roundNumber==3){ //if it's the third round, place the correct answer, and obtain distractor from the previous two questions

            //make the third answer choice to appear
            answer3.setVisibility(View.VISIBLE);
            kanjiChoice3.setVisibility(View.VISIBLE);

            //create an array, which tells the answer placement
            int[] answerPlacementArray = {0,1,2};
            shuffleArray(answerPlacementArray); //shuffle this array to create randomization

            //place the answer on the button that appears first index of the answerPlacementArray after the randomization
            answer[answerPlacementArray[0]].setText(dbHandler.getAnswer(n));
            kanjiChoice[answerPlacementArray[0]].setText(dbHandler.getKanji(n));
            whereCorrectAnswer = answerPlacementArray[0]+1;

            //place the first two asked questions to the other buttons
            for (int i=1, j=0; i<3; i++, j++) {
                answer[answerPlacementArray[i]].setText(dbHandler.getAnswer(askedQuestions.get(j)));
                kanjiChoice[answerPlacementArray[i]].setText(dbHandler.getKanji(askedQuestions.get(j)));
            }

        }else{ //if it is the 4th round or above, place the correct answer, and obtain 3 distracts from previous questions

            //make the fourth answer choice to appear
            answer4.setVisibility(View.VISIBLE);
            kanjiChoice4.setVisibility(View.VISIBLE);

            //create an array, which tells the answer placement
            int[] answerPlacementArray = {0,1,2,3};
            shuffleArray(answerPlacementArray); //shuffle this array to create randomization

            //place the answer on the button that appears first index of the answerPlacementArray after the randomization
            answer[answerPlacementArray[0]].setText(dbHandler.getAnswer(n));
            kanjiChoice[answerPlacementArray[0]].setText(dbHandler.getKanji(n));
            whereCorrectAnswer = answerPlacementArray[0]+1;

            //get 3 distractors
            ArrayList<Integer> distractors = getDistractors();

            //place the distractors in the rest of the buttons
            for (int i=1, j=0; i<4; i++, j++) {
                answer[answerPlacementArray[i]].setText(dbHandler.getAnswer(distractors.get(j))); //EDIT: SOME PREVIOUS ANSWER
                kanjiChoice[answerPlacementArray[i]].setText(dbHandler.getKanji(distractors.get(j))); //EDIT: SOME PREVIOUS ANSWER
            }

        }
    }

    // Implementing Fisher Yates shuffle
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

        chosenAnswer = 1;
        String buttonText = answer1.getText().toString(); //get the text of the button clicked
        isCorrectAnswer(n, buttonText); //checks if it is the correct answer, and adjust button colors accordingly.

        handler.postDelayed(new Runnable() {
            public void run() {
                setRound(); //get another random question
            }
        }, 500);


    }

    //function that runs if the second button is clicked.
    public void onClick2 (View view){
        chosenAnswer = 2;
        String buttonText = answer2.getText().toString();
        isCorrectAnswer(n, buttonText);
        handler.postDelayed(new Runnable() {
            public void run() {
                setRound(); //get another random question
            }
        }, 500);
    }

    //function that runs if the third button is clicked.
    public void onClick3 (View view){
        chosenAnswer = 3;
        String buttonText = answer3.getText().toString();
        isCorrectAnswer(n, buttonText);
        handler.postDelayed(new Runnable() {
            public void run() {
                setRound(); //get another random question
            }
        }, 500);
    }

    //function that runs if the fourth button is clicked.
    public void onClick4 (View view) {
        chosenAnswer = 4;
        String buttonText = answer4.getText().toString();
        isCorrectAnswer(n, buttonText);
        handler.postDelayed(new Runnable() {
            public void run() {
                setRound(); //get another random question
            }
        }, 500);
    }

    //checks if correct answer
    public void isCorrectAnswer(int n, String answerChoice){

        if (whereCorrectAnswer == chosenAnswer){ //(dbHandler.isCorrectAnswer(n, answerChoice))

            //display "correct" image
            feedbackImg.setImageResource(R.drawable.correctsign);

            //set up button sounds
            final MediaPlayer correctButtonClick = MediaPlayer.create(this, R.raw.correct);
            correctButtonClick.start();

            //high score incrementation below
            highScoreValueInt += 5; //increment the backend highscore
            String highScoreValueStr = Integer.toString(highScoreValueInt); //convert the integer into a string
            highScoreDisp.setText(highScoreValueStr); //display that string into the UI

            //change the button to green
            buttonArray.get(whereCorrectAnswer - 1).setBackgroundResource(R.drawable.greenbox);


        } else {

            //display "incorrect" image
            feedbackImg.setImageResource(R.drawable.incorrectsign);

            //set up button sounds
            final MediaPlayer incorrectButtonClick = MediaPlayer.create(this, R.raw.wrong);
            incorrectButtonClick.start();

            //decrement the life by 1 (backend purpose)
            livesCounterInt = livesCounterInt - 1;

            //change the correct button to green, chosen answer to red
            buttonArray.get(whereCorrectAnswer-1).setBackgroundResource(R.drawable.greenbox);
            buttonArray.get(chosenAnswer-1).setBackgroundResource(R.drawable.redbox);

            //sets a hearts to grey when a life is lost (display to the UI)
            if(livesCounterInt == 2) {
                life3.setImageResource(R.drawable.heartdie);
            }else if(livesCounterInt == 1){
                life2.setImageResource(R.drawable.heartdie);
            }else if(livesCounterInt == 0) {
                life1.setImageResource(R.drawable.heartdie);
                gameEnds(); //when life is zero, the game ends.
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

        i.putExtra("isswitchon", kanjiSwitch); //extra information, using kanjiSwitch as the reference
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

        //choose a random question ID from the database
        n = rand.nextInt(dbHandler.getProfilesCount());

        if (roundNumber<5) { //if it is round 1,2,3, or 4, make sure that there are no questions that are asked twice
            while (askedQuestions.contains(n)){ //if it is already asked
                n = rand.nextInt(dbHandler.getProfilesCount()); //get another question
            }
        }else{ //if it is round 5 or above, make sure that no question are asked twice in a row
            while (nPrevious==n){ //if the previous question is the same as the current question
                n = rand.nextInt(dbHandler.getProfilesCount()); //keep looking for a new question
            }
        }

        questionText.setText(dbHandler.getQuestion(n)); //get the question based on the random position
        questionView.setImageResource(dbHandler.getImage(n)); //get the corresponding image

        //if the question is not yet asked, place it on the askedQuestions ArrayList
        if(!askedQuestions.contains(n)) {
            askedQuestions.add(n);
        }

        //saves the current question as the previous question
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

