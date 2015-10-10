package com.example.kakoi;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/*
THIS IS THE HIGHSCORE SCREEN. UPDATE
 */

public class HighScore extends AppCompatActivity {

    HighscoreDB dbHighScore; //create the database
    TextView highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        Bundle highscoreData = getIntent().getExtras();

        if (highscoreData==null){
            return;
        }

        String highscoreMessage = highscoreData.getString("highscoredisp");
        final TextView highscore2 = (TextView) findViewById(R.id.highscore2);
        highscore2.setText(highscoreMessage);


        dbHighScore = new HighscoreDB(this, null, null, 1);
        highscore = (TextView) findViewById(R.id.highscore);
        String bestscore = dbHighScore.databaseToInt2();
        highscore.setText(bestscore);
    }

    //player cannot move back to
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
