package com.example.kakoi;

/**
 * Created by IndriHimawan on 9/30/15.
 */

/* a question class contains the ID of the question and the english word
TODO: ADD PICTURE ATTRIBUTE TO THIS CLASS
 */

public class Questions {
    private int _id;
    private String _englishword;

    public Questions(){

    }

    public Questions(String englishword) {
        this._englishword = englishword;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_englishword(String _englishword) {
        this._englishword = _englishword;
    }

    public int get_id() {
        return _id;
    }

    public String get_englishword() {
        return _englishword;
    }
}
