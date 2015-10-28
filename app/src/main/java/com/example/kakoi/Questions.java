package com.example.kakoi;

/**
 * Created by IndriHimawan on 9/30/15.
 */

/* a question class contains the ID of the question and the english word
TODO: ADD PICTURE ATTRIBUTE TO THIS CLASS.
 */

public class Questions {
    private int _id;
    private String _englishword;
    private String _answer;
    private int _image;
    private String _kanji;

    public Questions() {

    }

    public Questions(String _englishword, String _answer, int _image, String _kanji) {
        this._englishword = _englishword;
        this._answer = _answer;
        this._image = _image;
        this._kanji = _kanji;
    }

    public String get_kanji() {
        return _kanji;
    }

    public void set_kanji(String _kanji) {
        this._kanji = _kanji;
    }

    public int get_image() {
        return _image;
    }

    public void set_image(int _image) {
        this._image = _image;
    }

    public String get_answer() {
        return _answer;
    }

    public void set_answer(String _answer) {
        this._answer = _answer;
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