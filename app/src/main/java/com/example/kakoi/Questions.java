package com.example.kakoi;

/**
 * Created by IndriHimawan on 9/29/15.
 */
public class Questions {
    public int _id;
    public String _englishWord;

    public Questions(String englishWord) {
        this._englishWord = englishWord;
    }

    public void set_englishWord(String englishWord) {
        this._englishWord = englishWord;
    }

    public void set_id(int id) {
        this._id = id;
    }

    public String get_englishWord() {
        return _englishWord;
    }

    public int get_id() {
        return _id;
    }
}
