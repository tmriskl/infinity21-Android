package com.example.user.infinity21.Logic;

import com.example.user.infinity21.R;

import java.util.UUID;

/**
 * Created by pc on 1/30/2018.
 */

public class Utility {
    public static final UUID MY_UUID = UUID.fromString("9775c3cb-0484-4a9c-981c-9d78dbce77c3");

    public static final String APPNAME = "Infinity21";
    public static final String DBNAME = "PLAYER";
    public static final int DBVER = 1;
    /// <summary>
    /// Card suit values
    /// </summary>
    private static int back = 0;
    public enum CardType{
        SPADES, HEARTS, CLUBS, DIAMONDS
    }
    public static Integer[] backs = { // array that hold all the icons
            R.drawable.card_back_black,
            R.drawable.card_back_blue,
            R.drawable.card_back_red
    };
    public static int getBack() {
        return back;
    }

    public static void setBack(int back) {
        Utility.back = back%3;
    }

    /// <summary>
    /// Card face values
    /// </summary>
    public enum FaceValue {
        Zero, One, Two, Three, Four, Five, Six, Seven, Eight,
        Nine, Ten, Jack, Queen, King, Ace
    }

    public static final Integer[] FaceValues = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};

    public enum EndResult{
        DealerBlackJack, PlayerBlackJack, PlayerBust, DealerBust, Push, PlayerWin,DealerWin,
        PlayerBetting,PlayerIsPlaying,PlayerHasLeft,Ready
    }


    public static final int cardHeight = 150;
    public static final int cardWidth = cardHeight*80/110;
    public static final int MAXNUMOFPLAYERS = 4;
    public static final int BJMAX = 21;

    public static int BET1 = 50;
    public static int BET2 = 25;
    public static int BET3 = 10;
    public static int BET4 = 5;
    public static int top = 5;







}

