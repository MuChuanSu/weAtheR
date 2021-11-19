package com.example.weather;

public interface BackToLast {

    void goBack();

    //Factory pattern
    //this interface has a common method goBack()
    //that most of activities will implement to push a button and bo to the last activity
}
