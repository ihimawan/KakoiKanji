package com.example.kakoi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainPlay extends AppCompatActivity {

    EditText myInput;
    TextView myText;
    MyDBHandler dbHandler;

    //itworks!!!
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_play);
        myInput = (EditText) findViewById(R.id.myInput);
        myText = (TextView) findViewById(R.id.myText);
        dbHandler = new MyDBHandler(this, null, null, 1);
        printDatabase();
    }

    public void addButtonClicked(View view){
        Questions question = new Questions(myInput.getText().toString());
        dbHandler.addQuestion(question);
        printDatabase();
    }

    public void deleteButtonClicked(View view){
        String inputText = myInput.getText().toString();
        dbHandler.deleteQuestion(inputText);
        printDatabase();
    }

    public void printDatabase(){
        String dbString = dbHandler.databaseToString();
        myText.setText(dbString);
        myInput.setText("");
    }


}

