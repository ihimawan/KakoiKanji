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
        dbHighScore = new HighscoreDB(this, null);

        kanjiSwitch = (Switch) findViewById(R.id.kanjiSwitch); //obtain UI element
        highscore = (TextView) findViewById(R.id.highscore); //obtain UI element
        int profile_counts = dbHighScore.getProfilesCount(); //to see if database is empty

        //database initializations
        if (profile_counts==0) { //if database is empty, then add.
            addingHighscore(0);

            //DATABASE ADDED BY CATHERINA 11/12/15 (HER COMPUTER WAS NOT WORKING)
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
            addingQuestion("circle", "en", R.drawable.circle, "円");
            addingQuestion("book", "hon", R.drawable.book, "本");
            addingQuestion("car", "kuruma", R.drawable.car, "車");
            addingQuestion("moon", "tsuki", R.drawable.moon, "月");
            addingQuestion("fire", "hi", R.drawable.fire, "火");
            addingQuestion("water", "mizu", R.drawable.water, "水");
            addingQuestion("tree", "ki", R.drawable.tree, "木");
            addingQuestion("gold", "kin", R.drawable.gold, "金");
            addingQuestion("rain", "ame", R.drawable.rain, "雨");
            addingQuestion("snow", "yuki", R.drawable.snow, "雪");
            addingQuestion("flower", "hana", R.drawable.flower, "花");
            addingQuestion("mountain", "yama", R.drawable.mountain, "山");
            addingQuestion("river", "kawa", R.drawable.river, "川");
            addingQuestion("stone", "ishi", R.drawable.stone, "石");
            addingQuestion("cloud", "kumo", R.drawable.cloud, "雲");
            addingQuestion("red", "aka", R.drawable.red1, "赤");
            addingQuestion("blue", "ao", R.drawable.blue, "青");
            addingQuestion("white", "shiro", R.drawable.white1, "白");
            addingQuestion("black", "kuro", R.drawable.black1, "黑");
            addingQuestion("yellow", "ki", R.drawable.yellow1, "黃");
            addingQuestion("foot", "ashi", R.drawable.foot, "足");
            addingQuestion("legs", "ashi", R.drawable.leg, "脚");
            addingQuestion("head", "atama", R.drawable.head, "頭");
            addingQuestion("house", "ie", R.drawable.house, "家");
            addingQuestion("sea", "umi", R.drawable.sea, "海");
            addingQuestion("picture", "e", R.drawable.picture, "絵");
            addingQuestion("king", "o", R.drawable.king, "王");
            addingQuestion("hill", "oka", R.drawable.hill, "丘");
            addingQuestion("face", "kao", R.drawable.face, "顔");
            addingQuestion("mirror", "kagami", R.drawable.mirror, "鏡");
            addingQuestion("umbrella", "kasa", R.drawable.umbrella, "傘");
            addingQuestion("hair", "kami", R.drawable.hair, "髪");
            addingQuestion("chicken", "niwatori", R.drawable.chicken, "鶏");
            addingQuestion("whale", "kujira", R.drawable.whale, "鯨");
            addingQuestion("wolf", "okami", R.drawable.wolf, "狼");
            addingQuestion("horse", "uma", R.drawable.horse, "馬");
            addingQuestion("cow", "ushi", R.drawable.cow, "牛");
            addingQuestion("fish", "sakana", R.drawable.fish, "魚");
            addingQuestion("dog", "inu", R.drawable.dog, "犬");
            addingQuestion("cat", "neko", R.drawable.cat, "猫");
            addingQuestion("west", "nishi", R.drawable.west, "西");
            addingQuestion("south", "minami", R.drawable.south, "南");
            addingQuestion("north", "kita", R.drawable.north, "北");
            addingQuestion("east", "higashi", R.drawable.east2, "東");
            addingQuestion("spring", "haru", R.drawable.spring, "春");
            addingQuestion("summer", "natsu", R.drawable.summer, "夏");
            addingQuestion("autumn", "aki", R.drawable.autumn, "秋");
            addingQuestion("winter", "fuyu", R.drawable.winter, "冬");
            addingQuestion("ball", "tama", R.drawable.ball, "球");
            addingQuestion("wheat", "mugi", R.drawable.wheat, "麦");
            addingQuestion("forest", "mori", R.drawable.forest, "森");
            addingQuestion("grass", "kusa", R.drawable.grass, "草");
            addingQuestion("gate", "mon", R.drawable.gate, "門");
            addingQuestion("monkey", "saru", R.drawable.monkey, "猿");
            addingQuestion("finger", "yubi", R.drawable.finger, "指");
            addingQuestion("island", "shima", R.drawable.island, "島");
            addingQuestion("leaf", "ha", R.drawable.leaf, "葉");
            addingQuestion("egg", "tamago", R.drawable.egg, "卵");
            addingQuestion("lake", "mizumi", R.drawable.lake, "湖");
            addingQuestion("insect", "mushi", R.drawable.insects, "虫");
            addingQuestion("outside", "soto", R.drawable.outside, "外");
            addingQuestion("boat", "fune", R.drawable.boat, "舟");
            addingQuestion("bridge", "hashi", R.drawable.bridge, "橋");
            addingQuestion("shore", "kishi", R.drawable.shore, "岸");
            addingQuestion("shellfish", "kai", R.drawable.shellfish, "貝");
            addingQuestion("salt", "shio", R.drawable.salt, "塩");
            addingQuestion("sheep", "hitsuji", R.drawable.sheep, "羊");
            addingQuestion("prize", "sho", R.drawable.prize, "賞");
            addingQuestion("elephant", "zo", R.drawable.elephant, "象");
            addingQuestion("turtle", "kame", R.drawable.turtle, "亀");
            addingQuestion("plum", "ume", R.drawable.plum, "梅");
            addingQuestion("tiger", "tora", R.drawable.tiger, "虎");
            addingQuestion("paper", "kami", R.drawable.paper, "紙");
            addingQuestion("tooth", "ha", R.drawable.tooth, "歯");
            addingQuestion("nest", "su", R.drawable.nest, "巣");
            addingQuestion("thread", "ito", R.drawable.thread, "糸");
            addingQuestion("right", "migi", R.drawable.right, "右");
            addingQuestion("left", "hidari", R.drawable.left, "左");
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
                    isSwitchOn=1; //if it is checked, then the switch is denoted as the integer 1.
                    alert.show();
                }else{
                    isSwitchOn=0; //if it is unchecked, then the switch is denoted as the integer 0.
                    alert2.show();
                }
            }
        });

        //display the high score in the device
        String bestscore = dbHighScore.databaseToInt();
        highscore.setText(bestscore);
    }

//    //player cannot move back to previous screen
//    @Override
//    public void onBackPressed() {
//        return;
//    }

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
    public void onBackPressed() {
        //final MediaPlayer backButton = MediaPlayer.create(this, R.raw.go);
        // backButton.start();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to exit the game?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        //closes all the activity and starts from the main_menu after the
                        //game is closed and run again
                        finishAffinity();
                        System.exit(0);

                    }
                })
                .setNegativeButton("Continue", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //backButton.start();
                        dialog.cancel(); //then do nothing
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
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