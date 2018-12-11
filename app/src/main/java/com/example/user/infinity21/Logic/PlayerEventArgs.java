package com.example.user.infinity21.Logic;

/**
 * Created by pc on 2/19/2018.
 */

public class PlayerEventArgs {

    public int PlayerIndexNumber;
    public Utility.EndResult PlayerEndResult;



    public PlayerEventArgs(int playerIndexNumber, Utility.EndResult endResult){
        PlayerIndexNumber = playerIndexNumber;
        PlayerEndResult = endResult;
    }

    public int getPlayerIndexNumber() {
        return PlayerIndexNumber;
    }

    public void setPlayerIndexNumber(int playerIndexNumber) {
        PlayerIndexNumber = playerIndexNumber;
    }

}
