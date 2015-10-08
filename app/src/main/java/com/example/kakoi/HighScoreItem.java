package com.example.kakoi;

/**
 * Created by IndriHimawan on 10/6/15. UPDATE
 */
public class HighScoreItem {
    private int _id;
    private int _values;//

    public HighScoreItem(){

    }

    public HighScoreItem(int _values) {
        this._values = _values;
    }

    public int get_values() {
        return _values;
    }

    public void set_values(int _values) {
        this._values = _values;
    }

}
