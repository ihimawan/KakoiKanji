package com.example.kakoi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

/*

THIS IS THE HIGHSCORE SCREEN
 */

public class HighScore extends AppCompatActivity {

    HighscoreDB dbHighScore;    //create the database
    TextView highscore;         //the text that displays the current high score
    ImageView newhighscore;     //the image that displays if a new high score is achieved
    int kanjiSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        //get the score from previous intent
        Bundle highscoreData = getIntent().getExtras();

        if (highscoreData==null){
            return;
        }

        //getting information from previous intent
        int newhighscoreMessage = highscoreData.getInt("newhighscoremessage");
        String highscoreMessage = highscoreData.getString("highscoredisp");
        kanjiSwitch = highscoreData.getInt("isswitchon");

        //displays the score the user obtains
        final TextView highscore2 = (TextView) findViewById(R.id.highscore2);
        highscore2.setText(highscoreMessage);

        //if the user beats the high score, display the "new" image
        if (newhighscoreMessage!=-1) {
            newhighscore = (ImageView) findViewById(R.id.newHighScore);
            newhighscore.setImageResource(newhighscoreMessage);
        }

        //display the high score in the database
        dbHighScore = new HighscoreDB(this, null, null, 1);
        highscore = (TextView) findViewById(R.id.highscore);
        String bestscore = dbHighScore.databaseToInt2();
        highscore.setText(bestscore);
    }

    //player cannot move back to previous screen
    @Override
    public void onBackPressed() {
        return;
    }

    //function for quit button
    public void quitClicked(View view){
        final MediaPlayer goButtonClicked = MediaPlayer.create(this, R.raw.go);
        goButtonClicked.start();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    //function for replay button
    public void replayClicked(View view){
        final MediaPlayer goButtonClicked = MediaPlayer.create(this, R.raw.go);
        goButtonClicked.start();
        Intent i = new Intent(this, MainPlay.class);
        i.putExtra("isswitchon", kanjiSwitch);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
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