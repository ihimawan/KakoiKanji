package com.example.kakoi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

/*
THIS IS THE HOMEPAGESCREEN
 */

public class MainActivity extends AppCompatActivity {

    HighscoreDB dbHighScore;    //create database for high score
    MyDBHandler dbHandler;      //create database
    TextView highscore;         //text of high score in the device

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database initializations
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHighScore = new HighscoreDB(this, null, null, 1);

        highscore = (TextView) findViewById(R.id.highscore); //obtain UI element

        int profile_counts = dbHighScore.getProfilesCount(); //to see if database is empty

        //database initializations
        if (profile_counts==0) { //if database is empty, then add.
            addingHighscore(0);
            addingQuestion("one", "ichi");
            addingQuestion("two", "ni");
            addingQuestion("three", "san");
            addingQuestion("four", "yong");

//            addingQuestion("five", "go");
//            addingQuestion("six", "roku");
//            addingQuestion("seven", "nana");
//            addingQuestion("eight", "hachi");
//            addingQuestion("nine", "kyuu");
//            addingQuestion("ten", "jyuu");

        }

        //display the high score in the device
        String bestscore = dbHighScore.databaseToInt();
        highscore.setText(bestscore);
    }

    //player cannot move back to previous screen
    @Override
    public void onBackPressed() {
        return;
    }

    //to add english words to the database
    public void addingQuestion(String englishWord, String answer){
        Questions question = new Questions(englishWord, answer);
        dbHandler.addQuestion(question);
    }

    //to add highscore to database
    public void addingHighscore(int highScoreValue){
        HighScoreItem highScoreItem = new HighScoreItem(highScoreValue);
        dbHighScore.addHighScore(highScoreItem);
    }

    //this method does the intent and pulls up the play screen when the play button is clicked.
    public void playClicked(View view){
        final MediaPlayer goButtonClicked = MediaPlayer.create(this, R.raw.go);
        goButtonClicked.start();
        Intent i = new Intent(this, MainPlay.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}