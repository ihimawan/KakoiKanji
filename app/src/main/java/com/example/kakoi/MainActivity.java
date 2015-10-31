package com.example.kakoi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

/*
THIS IS THE HOMEPAGESCREEN
 */

public class MainActivity extends AppCompatActivity {

    HighscoreDB dbHighScore;    //create database for high score
    MyDBHandler dbHandler;      //create database
    TextView highscore;         //text of high score in the device

    Switch kanjiSwitch;
    int isSwitchOn=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //database initializations
        dbHandler = new MyDBHandler(this, null, null, 1);
        dbHighScore = new HighscoreDB(this, null, null, 1);

        kanjiSwitch = (Switch) findViewById(R.id.kanjiSwitch);

        highscore = (TextView) findViewById(R.id.highscore); //obtain UI element

        int profile_counts = dbHighScore.getProfilesCount(); //to see if database is empty

        //database initializations
        if (profile_counts==0) { //if database is empty, then add.
            addingHighscore(0);
            addingQuestion("one", "ichi", R.drawable.kanji1, "一");
            addingQuestion("two", "ni", R.drawable.kanji2, "二");
            addingQuestion("three", "san", R.drawable.kanji3, "三");
            addingQuestion("four", "yon", R.drawable.kanji4, "四");
            addingQuestion("five", "go", R.drawable.kanji5, "五");
            addingQuestion("six", "roku", R.drawable.kanji6, "六");
            addingQuestion("seven", "nana", R.drawable.kanji7, "匕");
            addingQuestion("eight", "hachi", R.drawable.kanji8, "八");
            addingQuestion("nine", "kyuu", R.drawable.kanji9, "九");
            addingQuestion("ten", "juu", R.drawable.kanji10, "十");
            addingQuestion("child", "juu", R.drawable.kanjichild, "子");
            addingQuestion("ear", "mimi", R.drawable.kanjiear, "耳");
            addingQuestion("eye", "me", R.drawable.kanjieye, "目");
            addingQuestion("father", "chichi", R.drawable.kanjifather, "父");
            addingQuestion("female", "onna", R.drawable.kanjifemale, "女");
            addingQuestion("hand", "te", R.drawable.kanjihand, "手");
            addingQuestion("heart", "kokoro", R.drawable.kanjiheart, "心");
            addingQuestion("mother", "haha", R.drawable.kanjimother, "母");
            addingQuestion("mouth", "kuchi", R.drawable.kanjimouth, "口");
            addingQuestion("person", "hito", R.drawable.kanjiperson, "人");
        }

        //alert dialog object that notifies pronounciation will be shown
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Kanji pronounciation will be shown.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); //then do nothing
                    }
                });
        final AlertDialog alert = builder.create();

        //alert dialog object that notifies pronounciation will be hidden
        AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
        builder.setMessage("Kanji pronounciation will be hidden.")
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel(); //then do nothing
                    }
                });
        final AlertDialog alert2 = builder.create();

        //attach a listener for the switch to check for changes in state
        kanjiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if(isChecked){
                    isSwitchOn=1;
                    alert.show();
                }else{
                    isSwitchOn=0;
                    alert2.show();
                }
            }
        });

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
    public void addingQuestion(String englishWord, String answer, int image, String kanji){
        Questions question = new Questions(englishWord, answer, image, kanji);
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
        i.putExtra("isswitchon", isSwitchOn);
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